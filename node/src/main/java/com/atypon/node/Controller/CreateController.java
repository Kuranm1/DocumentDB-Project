package com.atypon.node.Controller;

import com.atypon.node.DatabaseIO.DatabaseReader;
import com.atypon.node.DatabaseIO.DatabaseWriter;
import com.atypon.node.IndexIO.IndexWriter;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
public class CreateController {

    String getParams(HashMap<String,String> params){
        StringBuilder nodeCallParams = new StringBuilder();
        for (String key : params.keySet()){
            nodeCallParams.append("&").append(key).append("=").append(params.get(key));
        }
        return String.valueOf(nodeCallParams);
    }

    public void notifyMaster(String route , HashMap<String,String> params){
        String uriParams = getParams(params);
        //host.docker.internal to be used instead of localhost if we run the code inside container
        String uri = "http://localhost:8084/"+route+"?"+uriParams;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(uri, Void.class);
    }

    @GetMapping(path = "/create/schema")
    public boolean createSchema(@RequestParam HashMap<String,String> params){

        if (!params.containsKey("schemaName"))
            return false;
        // if the client writes to node notify the master
        if (!params.containsKey("master"))
            notifyMaster("notify/create/schema/",params);

        return DatabaseWriter.createDirectory(params.get("schemaName"));
    }

    @GetMapping(path = "/create/type")
    public boolean createType(@RequestParam HashMap<String,String> params){

        if (!params.containsKey("schemaName") || !params.containsKey("typeName") || !params.containsKey("typeSchema")
        || params.get("typeSchema").isEmpty() || !params.containsKey("typeIndex"))
            return false;

        // if the client writes to node notify the master
        if (!params.containsKey("master"))
            notifyMaster("notify/create/type/",params);

        String schemaName = params.get("schemaName");
        String typeName = params.get("typeName");

        // prepare type schema
        String typeSchema = params.get("typeSchema");
        String typeSchemaPath = schemaName+"/"+typeName+"/"+typeName+"Schema.json";
        HashMap<String,String> typeSchemaMap =new HashMap<>();
        for(String property : typeSchema.split(",")){
            typeSchemaMap.put(property,"");
        }
        if (!typeSchemaMap.containsKey("id"))
                typeSchemaMap.put("id","");
        JSONObject typeSchemaFile = new JSONObject(typeSchemaMap);

        //prepare type index
        String typeIndex = params.get("typeIndex");
        String typeIndexPath = schemaName+"/"+typeName+"/index";


        //create the type directory with schema and index in it
        return DatabaseWriter.createDirectory(schemaName+"/"+typeName) &&
                DatabaseWriter.creatJsonObject(typeSchemaPath , typeSchemaFile.toString()) &&
                DatabaseWriter.createDirectory(typeIndexPath) &&
                DatabaseWriter.createDirectory(typeIndexPath+"/"+typeIndex);
    }

    @GetMapping(path ="/create/object")
    public boolean createObject(@RequestParam HashMap<String,String> params){

        if (!params.containsKey("schemaName") || !params.containsKey("typeName"))
            return false;

        // if the client writes to node notify the master
        if (!params.containsKey("master"))
            notifyMaster("notify/create/object/",params);
        params.remove("master");

        String schemaName = params.get("schemaName");
        String typeName = params.get("typeName");
        params.remove("schemaName");
        params.remove("typeName");

        String typeSchemaPath = schemaName+"/"+typeName+"/"+typeName+"Schema.json";
        String typePath = schemaName+"/"+typeName;

        String id = params.get("id");
        String objectPath = schemaName+"/"+typeName+"/"+id+".json";

        JSONObject jsonObject = new JSONObject(params);

        // check if json object belong to type schema.
        if (DatabaseReader.isTypeSchema(typeSchemaPath , jsonObject)){
            // creat object
            if (DatabaseWriter.creatJsonObject(objectPath , jsonObject.toString())){
                // check if object params is indexed to creat index or insert to index.
                IndexWriter.checkObjectHasIndex(params,typePath);
                return true;
            }
            else return false;
        }

        else
            return false;
    }
}
