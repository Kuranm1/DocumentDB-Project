package com.atypon.DemoApplication.Controller;

import com.atypon.DemoApplication.Driver.DBDriver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
public class DeleteController {
    DBDriver client = new DBDriver("admin","admin");

    @GetMapping(path="/delete")
    public String getDeletePage(){
        return "delete";
    }

    @PostMapping(path="/delete")
    public String deleteStudent(@RequestParam HashMap<String,String> params, ModelMap mp){
        client.createSchema("school");
        client.createType("school","student");
        String condition = params.get("conditionKey")+","+params.get("conditionValue");
        if (client.deleteObject("school","student",condition))
            mp.put("message","success delete");
        else mp.put("errorMessage","wrong delete");
        return "delete";
    }
}
