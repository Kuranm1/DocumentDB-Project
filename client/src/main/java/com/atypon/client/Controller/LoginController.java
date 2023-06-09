package com.atypon.client.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
public class LoginController {

    String getParams(HashMap<String,String> params){
        StringBuilder nodeCallParams = new StringBuilder();
        for (String key : params.keySet()){
            nodeCallParams.append("&").append(key).append("=").append(params.get(key));
        }
        return String.valueOf(nodeCallParams);
    }

    @GetMapping("/login")
    public String login(@RequestParam HashMap<String,String> params){
        String uriParams = getParams(params);
        String uri = "http://localhost:8084/login/?"+uriParams;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @GetMapping("/adminLogin")
    public String adminLogin(@RequestParam HashMap<String,String> params){
        String uriParams = getParams(params);
        String uri = "http://localhost:8084/adminLogin/?"+uriParams;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @GetMapping("/changeAdminDefault")
    public String changeAdminDefault(@RequestParam HashMap<String,String> params){
        String uriParams = getParams(params);
        String uri = "http://localhost:8084/changeAdminDefault/?"+uriParams;
        RestTemplate restTemplate = new RestTemplate();
        boolean result = Boolean.TRUE.equals(restTemplate.getForObject(uri, Boolean.class));
        if (result)
            return "Admin default password changed";
        return "Error changing admin default password ";
    }
}
