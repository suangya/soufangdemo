package com.soufang.soufangdemo.controller;

import com.soufang.soufangdemo.base.ApiResponse;
import com.soufang.soufangdemo.base.config.SecurityUser;
import com.soufang.soufangdemo.dto.UserDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/info")
    public ApiResponse getCurrentUserInfo(){
        SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO dto = new UserDTO();
        dto.setName(user.getUsername());
        dto.setId(user.getUser().getId());
        dto.setEmail(user.getUser().getEmail());
        dto.setAvatar(user.getUser().getAvatar());
        dto.setPhone(user.getUser().getPhoneNumber());
        dto.setAuthorities(user.getAuthorities());
        return ApiResponse.success(dto);
    }


}
