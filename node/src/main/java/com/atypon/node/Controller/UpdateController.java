package com.atypon.node.Controller;

import com.atypon.node.DatabaseIO.DatabaseWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
public class UpdateController {

    String getParams(HashMap<String,String> params){
        StringBuilder nodeCallParams = new StringBuilder();
        for (String key : params.keySet()){
            nodeCallParams.append("&").append(key).append("=").append(params.get(key));
        }
        return String.valueOf(nodeCallParams);
    }
    public void notifyMaster(String route , HashMap<String,String> params){
        String uriParams = getParams(params);
        String uri = "http://localhost:8084/"+route+"?"+uriParams;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(uri, Void.class);
    }

    @GetMapping(path = "/update")
    Boolean updateObject(@RequestParam HashMap<String,String> params){

        if (!params.containsKey("schemaName") || !params.containsKey("typeName") || !params.containsKey("condition")
                || !params.containsKey("property")  || params.get("condition").split(",").length !=2 ||
                params.get("property").split(",").length !=2 )
            return false;

        // if the client writes to node notify the master
        if (!params.containsKey("master"))
            notifyMaster("notify/update/",params);

        String schemaName = params.get("schemaName");
        String typeName = params.get("typeName");
        String condition = params.get("condition");
        String property= params.get("property");


        String typePath = schemaName+"/"+typeName;
        return DatabaseWriter.updateJsonObject(typePath,condition,property);

    }
}
