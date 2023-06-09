package com.atypon.master.Controllers;

import com.atypon.master.LoadBalancer.Node;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class NotifyController {
    private static final ArrayList<Node> listOfNodes = Node.getListOfNodes();
    String getParams(HashMap<String,String> params){
        StringBuilder nodeCallParams = new StringBuilder();
        for (String key : params.keySet()){
            nodeCallParams.append("&").append(key).append("=").append(params.get(key));
        }
        return String.valueOf(nodeCallParams);
    }

    @GetMapping(path = "/notify/create/schema")
    void notifyNodesSchemaCreat(@RequestParam HashMap<String,String> params){
        String uriParams = getParams(params);
        String uri ;
        for (Node node : listOfNodes) {
            uri = "http://localhost:"+node.getPortNumber()+"/create/schema/?"+uriParams+"&master";
            node.callNode(uri);
        }

    }

    @GetMapping(path = "/notify/create/type")
    void notifyNodesTypeCreat(@RequestParam HashMap<String,String> params){
        String uriParams = getParams(params);
        String uri;
        for (Node node : listOfNodes) {
            uri = "http://localhost:"+node.getPortNumber()+"/create/type/?"+uriParams+"&master";
            node.callNode(uri);
        }

    }

    @GetMapping(path = "/notify/create/object")
    void notifyNodesObjectCreat(@RequestParam HashMap<String,String> params){
        String uriParams = getParams(params);
        String uri;
        for (Node node : listOfNodes) {
            uri = "http://localhost:"+node.getPortNumber()+"/create/object/?"+uriParams+"&master";
            node.callNode(uri);
        }
    }

    @GetMapping(path = "/notify/update")
    void notifyNodesUpdate(@RequestParam HashMap<String,String> params){
        String uriParams = getParams(params);
        String uri;
        for (Node node : listOfNodes) {
            uri = "http://localhost:"+node.getPortNumber()+"/update/?"+uriParams+"&master";
            node.callNode(uri);
        }
    }

    @GetMapping(path = "/notify/delete")
    void notifyNodesDelete(@RequestParam HashMap<String,String> params){
        String uriParams = getParams(params);
        String uri;
        for (Node node : listOfNodes) {
            uri = "http://localhost:"+node.getPortNumber()+"/delete/?"+uriParams+"&master";
            node.callNode(uri);
        }
    }

}
