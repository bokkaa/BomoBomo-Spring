package com.example.bomobomo.controller;

import com.example.bomobomo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("/idCheck")
    public int idCheck(String userId) {

        int idCk = userService.idCheck(userId);

        return idCk;
    }


}
