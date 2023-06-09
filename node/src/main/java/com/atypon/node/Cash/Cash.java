package com.atypon.node.Cash;

import java.util.HashMap;

public class Cash {
    private static final Cash instance = new Cash();;
    private Cash(){
    }
    public static Cash getInstance(){
        return instance;
    }
    private static final int CASH_MAX_SIZE = 10;
    private static final HashMap<String,String> cash = new HashMap<>();
    public String readFromCash(String objectPath){
        System.out.println("object read from cash");
        return cash.get(objectPath);
    }
    public void writeToCash(String objectPath , String jsonObject){
        if(!isFull())
            cash.put(objectPath,jsonObject);
    }
    public void removeFromCash(String objectPath){
        System.out.println("object removed from cash");
        cash.remove(objectPath);
    }
    public boolean isInCash(String objectPath){
        return cash.containsKey(objectPath);
    }
    public boolean isFull(){
        return cash.size()==CASH_MAX_SIZE;
    }

}
