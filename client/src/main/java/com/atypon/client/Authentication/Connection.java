package com.atypon.client.Authentication;

import org.springframework.web.client.RestTemplate;


 public final class Connection {
     private static int nodePort;
     private static boolean connected = false;

     private Connection(){}
     public static void connectToMaster() {

         // Connect to master and get port number
         String uri = "http://localhost:8084/connect";
         RestTemplate restTemplate = new RestTemplate();
         Integer result = restTemplate.getForObject(uri, Integer.class);

         if (result == null)
             result = 0;

         setNodePort(result);
         setConnected(getNodePort() > 0);
     }

     public static int getNodePort() {
         return nodePort;
     }

     private static void setNodePort(int nodePort) {
         Connection.nodePort = nodePort;
     }

     public static boolean isConnected() {
         return connected;
     }

     private static void setConnected(boolean connected) {
         Connection.connected = connected;
     }


 }
