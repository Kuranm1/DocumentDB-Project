package com.atypon.master.Controllers;

import com.atypon.master.Authentication.Connection;
import com.atypon.master.Authentication.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam("username") String username,
                              @RequestParam("password") String password ){

        if (Users.isValidLogin(username,password) && Users.getUserRole(username).equals("user")){
            Connection.setConnected();
            Connection.setIsAdmin(false);
            return "correct login";
        }

        return "invalid login";
    }

    @GetMapping("/adminLogin")
    public String adminLogin(@RequestParam("username") String username,
                        @RequestParam("password") String password ){

        if (Users.isFirstLogin())
            return "Change the admin default password first!!";

        if (Users.isAdmin(username,password)){
            if (Users.isValidLogin(username,password)){
                Connection.setConnected();
                Connection.setIsAdmin(true);
                return "correct login";
            }
        }
        return "invalid login";
    }

    @GetMapping("/changeAdminDefault")
    public boolean changeAdminDefault(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("newPassword") String newPassword){
        if (Users.isAdmin(username,password)){
            Users.changeAdminPassword(newPassword);
            return true;
        }
        return false;
    }

}
