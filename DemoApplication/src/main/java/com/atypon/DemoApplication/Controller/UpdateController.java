package com.atypon.DemoApplication.Controller;

import com.atypon.DemoApplication.Driver.DBDriver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
public class UpdateController {

    DBDriver client = new DBDriver("admin","admin");

    @GetMapping(path="/update")
    public String getUpdatePage(){
        return "update";
    }

    @PostMapping(path="/update")
    public String updateStudent(@RequestParam HashMap<String,String> params, ModelMap mp){
        client.createSchema("school");
        client.createType("school","student");
        String condition = params.get("conditionKey")+","+params.get("conditionValue");
        String property = params.get("propertyKey")+","+params.get("propertyValue");
        if (client.updateObject("school","student",condition,property))
            mp.put("message","success update");
        else mp.put("errorMessage","wrong update");
        return "update";
    }
}
