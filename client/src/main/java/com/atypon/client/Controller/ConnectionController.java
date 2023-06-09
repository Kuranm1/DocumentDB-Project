package com.atypon.client.Controller;

import com.atypon.client.Authentication.Connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ConnectionController {

    @GetMapping(path = "/connect")
    public int setConnection(){
        Connection.connectToMaster();
        return Connection.getNodePort();
    }
}
