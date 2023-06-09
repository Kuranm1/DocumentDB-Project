package com.atypon.master.Controllers;

import com.atypon.master.Authentication.Connection;
import com.atypon.master.Authentication.Users;
import com.atypon.master.LoadBalancer.LoadBalancer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ConnectController {

    @GetMapping(path ="/connect")
    int getPortNumber(){
        if (Connection.isConnected())
            return LoadBalancer.getCurrentNodeTurn();
        else
            return 0;
    }


}


