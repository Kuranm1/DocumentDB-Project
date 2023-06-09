package com.atypon.client.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
public class UsersController {

    String getParams(HashMap<String,String> params){
        StringBuilder nodeCallParams = new StringBuilder();
        for (String key : params.keySet()){
            nodeCallParams.append("&").append(key).append("=").append(params.get(key));
        }
        return String.valueOf(nodeCallParams);
    }

    @GetMapping("/users/add")
    public boolean addUser(@RequestParam HashMap<String,String> params){
        String uriParams = getParams(params);
        // Connect to master and get port number
        String uri = "http://localhost:8084/users/add?"+uriParams;
        RestTemplate restTemplate = new RestTemplate();
        return Boolean.TRUE.equals(restTemplate.getForObject(uri, Boolean.class));
    }

    @GetMapping("/users/remove")
    public boolean removeUser(@RequestParam HashMap<String,String> params){
        String uriParams = getParams(params);
        // Connect to master and get port number
        String uri = "http://localhost:8084/users/remove?"+uriParams;
        RestTemplate restTemplate = new RestTemplate();
        return Boolean.TRUE.equals(restTemplate.getForObject(uri, Boolean.class));
    }

    @GetMapping("/users/read")
    public HashMap readUsers(){
        String uri = "http://localhost:8084/users/read";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, HashMap.class);
    }

    @GetMapping("/users/roles")
    public HashMap readRoles(){
        String uri = "http://localhost:8084/users/roles";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, HashMap.class);
    }
}
