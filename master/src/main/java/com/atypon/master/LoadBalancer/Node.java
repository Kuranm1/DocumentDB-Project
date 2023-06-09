package com.atypon.master.LoadBalancer;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class Node {
    private final int portNumber;
    private static final ArrayList<Node> listOfNodes = new ArrayList<>();
    private Node(int nodeNumber){
        this.portNumber = nodeNumber;
        listOfNodes.add(this);
    }
    public static Node newInstance(int nodeNumber){
        return new Node(nodeNumber);
    }
    public int getPortNumber() {
        return portNumber;
    }
    public static ArrayList<Node> getListOfNodes() {
        return listOfNodes;
    }
    public void callNode(String uri){
        RestTemplate restTemplate = new RestTemplate();
        Boolean result = restTemplate.getForObject(uri, Boolean.class);
    }
}
