package com.atypon.client.Controller;

import com.atypon.client.Authentication.Connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
public class DeleteController {

    int nodePort;

    String getParams(HashMap<String,String> params){
        StringBuilder nodeCallParams = new StringBuilder();
        for (String key : params.keySet()){
            nodeCallParams.append("&").append(key).append("=").append(params.get(key));
        }
        return String.valueOf(nodeCallParams);
    }

    @GetMapping(path = "/delete")
    String deleteObject(@RequestParam HashMap<String,String> params){

        String uriParams = getParams(params);

        if (Connection.isConnected()){
            nodePort = Connection.getNodePort();
            // Call the node to delete object
            String uri = "http://localhost:"+nodePort+"/delete/?"+uriParams;
            RestTemplate restTemplate = new RestTemplate();
            Boolean result = restTemplate.getForObject(uri, Boolean.class);
            if (Boolean.TRUE.equals(result))
                return "Objects are deleted";
            else return "No Object deleted";
        }
        else
            return "No connection!";
    }

}
