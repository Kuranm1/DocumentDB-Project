package com.atypon.master.Controllers;

import com.atypon.master.Authentication.Connection;
import com.atypon.master.Authentication.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class UsersController {

    @GetMapping("/users/add")
    public boolean addUser(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("role") String role){
        if (Connection.isAdmin() && Connection.isConnected()){
            Users.addUser(username,password);
            Users.setRole(username,role);
            return true;
        }
        return false;
    }

    @GetMapping("/users/remove")
    public boolean removeUser(@RequestParam("username") String username,
                           @RequestParam("role") String role){
        if (Connection.isAdmin() && Connection.isConnected()){
            Users.removeUser(username);
            Users.removeRule(role);
            return true;
        }
        return false;
    }

    @GetMapping("/users/read")
    public HashMap<String,String> readUsers(){
        HashMap<String,String> users = new HashMap<>();
        if (Connection.isAdmin() && Connection.isConnected()){
            users=Users.readUsers();
         return users;
        }
        return users;
    }
    @GetMapping("/users/roles")
    public HashMap<String,String> readRoles(){
        HashMap<String,String> roles = new HashMap<>();
        if (Connection.isAdmin() && Connection.isConnected()){
            roles=Users.readRoles();
            return roles;
        }
        return roles;
    }

}
