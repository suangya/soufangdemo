package com.soufang.soufangdemo.base;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ErrorApiController implements ErrorController {
    private ErrorAttributes errorAttributes;

    public ErrorApiController(ErrorAttributes errorAttributes){this.errorAttributes = errorAttributes;}

    @RequestMapping("/error")
    @ResponseBody
    public ApiResponse errorApiHandler(HttpServletRequest request){
        Map<String,Object> attrs = getErrorAttributes(request);
        Integer status = (Integer) request.getAttribute(WebConstant.ATTR_ERROR_CODE);
        return ApiResponse.error(status, (String) attrs.getOrDefault(WebConstant.ATTR_ERROR_MESSAGE,
                WebConstant.DEFAULT_ERROR_MESSAGE));
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        return errorAttributes.getErrorAttributes(webRequest,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION));
    }

}
