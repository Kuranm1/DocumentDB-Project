package com.atypon.node.Controller;

import com.atypon.node.DatabaseIO.DatabaseReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ReadController {

    @GetMapping(path = "/read")
    ArrayList <Map<String,Object>>  readObject(@RequestParam HashMap<String,String> params){

        if (!params.containsKey("schemaName") || !params.containsKey("typeName") )
            return null;

        String schemaName = params.get("schemaName");
        String typeName = params.get("typeName");
        String condition = params.get("condition");

        String typePath = schemaName+"/"+typeName;
        // if there is no condition return all objects in type
        if(condition == null || condition.split(",").length != 2){
            return DatabaseReader.readAllObject(typePath);
        }
        else{
            return DatabaseReader.readObject(condition,typePath);
        }

    }
}
