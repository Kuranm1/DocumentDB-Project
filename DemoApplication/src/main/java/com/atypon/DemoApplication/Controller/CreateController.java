package com.atypon.DemoApplication.Controller;

import com.atypon.DemoApplication.Driver.DBDriver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;

@Controller
public class CreateController {

    DBDriver client = new DBDriver("mohammad","mohammad");

    @GetMapping(path={"/","/create"})
    public String getCreatePage(){
        return "create";
    }

    @PostMapping(path={"/","/create"})
    public String insertStudent(@RequestParam HashMap<String,String> params, ModelMap mp){

        client.createSchema("school");
        client.createType("school","student");

        if (client.crateObject("school","student",params))
            mp.put("message","success insertion");
        else mp.put("errorMessage","wrong insertion");
        return "create";
    }
}
