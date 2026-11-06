package soa.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import soa.model.Login;
import soa.service.LoginService;

@RestController
public class LoginController {

    LoginService LS;

    public LoginController(LoginService LS) {
        this.LS = LS;
    }

    @PostMapping("/login")
    public Object login(@RequestBody Login login) {
        return LS.login(login);
    }
}