package com.atypon.DemoApplication.Driver;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

public class DBDriver {
    public DBDriver(String username, String password){
        String loginUri = "http://localhost:8081/login/?username="+username+"&password="+password;
        String connectUri = "http://localhost:8081/connect";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(loginUri, String.class);
        restTemplate.getForObject(connectUri, Integer.class);
    }

    public void createSchema(String schemaName){
        String uri = "http://localhost:8081/create/schema/?schemaName="+schemaName;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
    }
    public void createType(String schemaName , String typeName){
        String uri = "http://localhost:8081/create/type/?schemaName="+schemaName+"&typeName="+typeName
                +"&typeSchema=id,name,year,major,gpa&typeIndex=name";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
    }
    public boolean crateObject(String schemaName , String typeName ,HashMap<String,String> properties) {
        StringBuilder uriProperties = new StringBuilder();
        for (String key : properties.keySet()) {
            uriProperties.append("&").append(key).append("=").append(properties.get(key));
        }
        String uri = "http://localhost:8081/create/object/?schemaName=" + schemaName + "&typeName=" + typeName + uriProperties.toString();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result.equals("Object is created");
    }
    public boolean updateObject(String schemaName , String typeName, String condition , String property){
        String uri = "http://localhost:8081/update/?schemaName="+schemaName+"&typeName="+typeName
                +"&condition="+condition+"&property="+property;
        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result.equals("Objects are Updated");
    }
    public boolean deleteObject(String schemaName , String typeName, String condition){
        String uri = "http://localhost:8081/delete/?schemaName="+schemaName+"&typeName="+typeName
                +"&condition="+condition;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result.equals("Objects are deleted");
    }
    public ArrayList readObject(String schemaName , String typeName, String condition){
        String uri = "http://localhost:8081/read/?schemaName="+schemaName+"&typeName="+typeName
                +"&condition="+condition;
        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, ArrayList.class);

    }

}
