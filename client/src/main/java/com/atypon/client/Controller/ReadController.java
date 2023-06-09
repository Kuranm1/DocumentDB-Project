package com.atypon.client.Controller;

import com.atypon.client.Authentication.Connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ReadController {
    int nodePort;

    String getParams(HashMap<String,String> params){
        StringBuilder nodeCallParams = new StringBuilder();
        for (String key : params.keySet()){
            nodeCallParams.append("&").append(key).append("=").append(params.get(key));
        }
        return String.valueOf(nodeCallParams);
    }

    @GetMapping(path = "/read")
    ArrayList getObject(@RequestParam HashMap<String,String> params){

        String uriParams = getParams(params);

        if (Connection.isConnected()){
            nodePort = Connection.getNodePort();
            // Call the node to read object
            String uri = "http://localhost:"+nodePort+"/read/?"+uriParams;
            RestTemplate restTemplate = new RestTemplate();
            return  restTemplate.getForObject(uri, ArrayList.class);
        }
        else{
            return new ArrayList<String>() {{
                add("No Connection");
            }};
        }

    }
}
