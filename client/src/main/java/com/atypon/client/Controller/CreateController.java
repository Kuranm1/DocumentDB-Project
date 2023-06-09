package com.atypon.client.Controller;

import com.atypon.client.Authentication.Connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;


@RestController
public class CreateController {

    int nodePort;

    String getParams(HashMap<String,String> params){
        StringBuilder nodeCallParams = new StringBuilder();
        for (String key : params.keySet()){
            nodeCallParams.append("&").append(key).append("=").append(params.get(key));
        }
        return String.valueOf(nodeCallParams);
    }

    @GetMapping(path = "/create/schema")
    public String createSchema(@RequestParam HashMap<String,String> params){

        String uriParams = getParams(params);

        if (Connection.isConnected()){
            nodePort = Connection.getNodePort();

            // Call the node to create schema
            String uri = "http://localhost:"+nodePort+"/create/schema/?"+uriParams;
            RestTemplate restTemplate = new RestTemplate();
            Boolean result = restTemplate.getForObject(uri, Boolean.class);

            if (Boolean.TRUE.equals(result))
                return "Schema is created";
            else return "Error in schema creation";
        }
        else
            return "No connection!";
    }

    @GetMapping(path = "create/type")
    public String createType(@RequestParam HashMap<String,String> params){

        String uriParams = getParams(params);

        if (Connection.isConnected()){
            nodePort = Connection.getNodePort();
            // Call the node to create type
            String uri = "http://localhost:"+nodePort+"/create/type/?"+uriParams;
            RestTemplate restTemplate = new RestTemplate();
            Boolean result = restTemplate.getForObject(uri, Boolean.class);
            if (Boolean.TRUE.equals(result))
                return "type is created";
            else return "Error in typeName creation";
        }
        else
            return "No connection!";

    }

    @GetMapping(path = "create/object")
    public String createObject(@RequestParam HashMap<String,String> params){

        String uriParams = getParams(params);

        if (Connection.isConnected()){
            nodePort = Connection.getNodePort();
            // Call the node to create object
            String uri = "http://localhost:"+nodePort+"/create/object/?"+uriParams;
            RestTemplate restTemplate = new RestTemplate();
            Boolean result = restTemplate.getForObject(uri, Boolean.class);
            if (Boolean.TRUE.equals(result))
                return "Object is created";
            else return "Error in Object creation / Not valid schema";
        }
        else
            return "No connection!";
    }
}
