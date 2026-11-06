package soa.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import soa.model.Login;

@Service
public class LoginService {

    JwtService JS;

    public LoginService(JwtService JS) {
        this.JS = JS;
    }

    public Object login(Login login) {

        Map<String, Object> response = new HashMap<>();

        try {

            // Create REST client
            RestClient client = RestClient.create();

            // Signup microservice URL
            String url = "http://localhost:8081/signup/"
                    + login.getUsername();

            // Call Signup microservice
            SignupResponse signup =
                    client.get()
                          .uri(url)
                          .accept(MediaType.APPLICATION_JSON)
                          .retrieve()
                          .body(SignupResponse.class);

            // Check user
            if (signup == null || signup.getUsername() == null) {

                response.put("message", "User not found");

                return response;
            }

            // Check password
            if (signup.getPassword() == null ||
                !signup.getPassword().equals(login.getPassword())) {

                response.put("message", "Invalid password");

                return response;
            }

            // Generate JWT
            String token = JS.generateToken(login.getUsername());

            // Successful login response
            response.put("message", "Login successful");
            response.put("username", login.getUsername());
            response.put("token", token);

        } catch (Exception e) {

            response.put("message", "Login Error: " + e.getMessage());
        }

        return response;
    }
}


// Response received from Signup Microservice
class SignupResponse {

    private int code;
    private String username;
    private String email;
    private String password;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}