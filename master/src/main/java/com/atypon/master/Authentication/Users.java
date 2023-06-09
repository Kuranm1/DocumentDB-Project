package com.atypon.master.Authentication;

import java.util.HashMap;

public final class Users {
    private static final HashMap<String,String> users = new HashMap<String,String>() {{
        put("admin","123");
        put("mohammad","mohammad");
    }};

    private static final HashMap<String,String> roles = new HashMap<String,String>() {{
        put("admin","admin");
        put("mohammad","user");
    }};

    private Users(){}
    public static boolean isFirstLogin(){
        return users.get("admin").equals("123");
    }
    public static boolean isValidLogin(String username, String password){
        return  (users.get(username).equals(password));
    }
    public static void changeAdminPassword(String password){
            users.put("admin",password);
    }
    public static boolean isAdmin(String username, String password){
        return (getUserRole(username).equals("admin") && isValidLogin(username,password));
    }
    public static void addUser(String username, String password){
        users.put(username,password);
    }
    public static void removeUser(String username){
        users.remove(username);
    }
    public static String getUserRole(String username){
        return roles.get(username);
    }
    public static void setRole(String username, String role){
         roles.put(username,role);
    }
    public static void removeRule(String username){
        roles.remove(username);
    }
    public static HashMap<String, String> readUsers(){
        return users;
    }
    public static HashMap<String, String> readRoles(){
        return roles;
    }
}
