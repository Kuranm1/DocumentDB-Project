package com.atypon.DemoApplication.Controller;
import com.atypon.DemoApplication.Driver.DBDriver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
public class ReadController {

    DBDriver client = new DBDriver("admin","admin");

    @GetMapping(path="/read")
    public String getReadPage(){
        return "read";
    }
    @PostMapping(path="/read")
    public String readStudent(@RequestParam HashMap<String,String> params, ModelMap mp){
        client.createSchema("school");
        client.createType("school","student");
        String condition = params.get("conditionKey")+","+params.get("conditionValue");
        System.out.println(condition);
        mp.put("data",client.readObject("school","student",condition));
        return "data";
    }
}
