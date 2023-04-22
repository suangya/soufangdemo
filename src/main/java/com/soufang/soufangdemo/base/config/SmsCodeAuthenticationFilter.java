package com.soufang.soufangdemo.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soufang.soufangdemo.base.ApiResponse;
import com.soufang.soufangdemo.entity.Role;
import com.soufang.soufangdemo.entity.User;
import com.soufang.soufangdemo.repository.RoleRepository;
import com.soufang.soufangdemo.repository.UserRepository;
import com.soufang.soufangdemo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
* 手机号验证码登陆
*
* URL: /api/users/login?method=sms
* method: POST
* body: phone=xxx&code=xxx
*
* 成功：
* response status: 200
* response body: ApiResponse.success()
* 失败：
* response status: 200
* response body: ApiResponse.error(Status.SMS_CODE_INVALID)
* */
@Component
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final SmsRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new SmsRequestMatcher("/api/users/login",
            "POST","method","sms");
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    private RedisService redisService;
    /*private static final String DEFAULT_CODE = "1234";*/

    /*
    * 自定义路由+参数请求匹配器
    * */
    public static class SmsRequestMatcher implements RequestMatcher{
        private AntPathRequestMatcher matcher;
        private final String parameterName;
        private final String value;

        public SmsRequestMatcher(String pattern, String httpMethod, String param, String value){
            this.matcher = new AntPathRequestMatcher(pattern,httpMethod);
            this.parameterName = param;
            this.value = value;
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            return this.matcher.matches(request) && cheakParams(request);
        }

        private boolean cheakParams(HttpServletRequest request){
            String parameter = request.getParameter(this.parameterName);
            return value.equals(parameter);
        }

    }

    public SmsCodeAuthenticationFilter(UserRepository userRepository, RoleRepository roleRepository, ObjectMapper mapper){
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.setAuthenticationSuccessHandler(getAuthenticationSuccessHandler(mapper));
        this.setAuthenticationFailureHandler(getFailureHandler(mapper));
    }


    public AuthenticationSuccessHandler getAuthenticationSuccessHandler(ObjectMapper mapper){
        return (request, response, authentication) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mapper.writeValueAsString(ApiResponse.success()));
            response.getWriter().flush();
        };
    }

    public AuthenticationFailureHandler getFailureHandler(ObjectMapper mapper){
        return (request, response, exception) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(mapper.writeValueAsString(ApiResponse.error(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase())));
            response.getWriter().flush();
        };
    }

    @Override
    public void afterPropertiesSet() {
        //重写当前方法并留空的原因是不需要父类校验AuthenticatManager实例
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String phone = request.getParameter("phone");
        phone = phone != null ? phone.trim() : "";
        String code = request.getParameter("code");
        code = code != null ? code : "";

        User user = userRepository.findByPhoneNumber(phone);
        if(user == null){
            //用户不存在时自动注册
            user = new User();
            user.setId(System.currentTimeMillis());
            user.setPhoneNumber(phone);
            user.setStatus(1);
            user.setCreateTime(new Date());
            user.setLastLoginTime(new Date());
            user.setLastUpdateTime(new Date());
            user = userRepository.save(user);
          /*  throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
*/
        }
        // TODO：4.校验验证码
        HttpSession session = request.getSession();
        String prePhone = (String) session.getAttribute("phone");
        String preCode = (String) session.getAttribute("code");
        String codeTime = String.valueOf(session.getAttribute("codeTime"));
        if (preCode==null || prePhone==null ||codeTime==null){
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        if ((System.currentTimeMillis() - Long.valueOf(codeTime)) > TimeUnit.MINUTES.toMillis(10)){
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        if (!prePhone.equals(phone)||!code.equals(preCode)){
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        List<Role> roles = roleRepository.findAllByUserId(user.getId());
        return new SmsCodeAuthenticationToken(new SecurityUser(user,roles));
    }
}
