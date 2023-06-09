package com.atypon.client.Controller;

import com.atypon.client.Authentication.Connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
public class UpdateController {
    int nodePort;

    String getParams(HashMap<String,String> params){
        StringBuilder nodeCallParams = new StringBuilder();
        for (String key : params.keySet()){
            nodeCallParams.append("&").append(key).append("=").append(params.get(key));
        }
        return String.valueOf(nodeCallParams);
    }

    @GetMapping(path = "/update")
    String updateObject(@RequestParam HashMap<String,String> params){

        String uriParams = getParams(params);

        if (Connection.isConnected()){
            nodePort = Connection.getNodePort();
            // Call the node to Update objects
            String uri = "http://localhost:"+nodePort+"/update/?"+uriParams;
            RestTemplate restTemplate = new RestTemplate();
            Boolean result = restTemplate.getForObject(uri, Boolean.class);
            if (Boolean.TRUE.equals(result))
                return "Objects are Updated";
            else return "No Object Updated";
        }
        else
            return "No connection!";


    }
}
