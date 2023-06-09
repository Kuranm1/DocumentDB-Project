package com.atypon.node.DatabaseIO;

import com.atypon.node.Cash.Cash;
import com.atypon.node.IndexIO.IndexReader;
import com.atypon.node.IndexIO.IndexWriter;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;


public final class DatabaseWriter {
    static Cash cash = Cash.getInstance();
    static BufferedWriter writer;
    private DatabaseWriter(){}
    public static boolean createDirectory(String path){
        File file = new File("database/"+path);
        if (!file.exists())
            return file.mkdir();
        else
            return true;
    }
    public static boolean creatJsonObject (String path , String jsonObject ){
        File file = new File("database"+"/"+path);
        if (file.exists()){
            JSONObject newJsonObject = new JSONObject(jsonObject);
            JSONObject existJsonObject = new JSONObject(DatabaseReader.readObjectByID(path));
            // if the object is already exist and both are similar return true
            return existJsonObject.similar(newJsonObject);
        }

        if (!cash.isFull())
            cash.writeToCash("database"+"/"+path,jsonObject);

        try {
            writer = new BufferedWriter(new FileWriter("database/"+path));
            writer.write(jsonObject);
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public synchronized static boolean updateJsonObject(String typePath , String condition , String property){

        String[] propertyKeyVal = property.split(",");
        String propertyKey=propertyKeyVal[0];
        String propertyValue=propertyKeyVal[1];

        ArrayList<Map<String,Object>> objects;

        objects= DatabaseReader.readObject(condition,typePath);

        // if the property is indexed manipulate the index
        if(IndexReader.isConditionHasIndex(property,typePath)){
            for (Map<String,Object> object : objects){
                String oldProperty = propertyKey+","+ object.get(propertyKey);
                IndexWriter.removeFromIndex(object.get("id").toString(),typePath,oldProperty);
                IndexWriter.insertToIndex(object.get("id").toString(),typePath,property);
            }
        }

        for (Map<String,Object> mapObject : objects){
            JSONObject object = new JSONObject(mapObject);
            if (object.has(propertyKey)){
                object.put(propertyKey,propertyValue);
                // If object in cash update it
                if (cash.isInCash("database/"+typePath+"/"+object.get("id")+".json"))
                    cash.writeToCash("database/"+typePath+"/"+object.get("id")+".json",object.toString());
                try { // write the object again to the file system
                    writer = new BufferedWriter(new FileWriter("database/"+typePath+"/"+object.get("id")+".json"));
                    writer.write(object.toString());
                    writer.close();
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }
    public synchronized static boolean deleteJsonObject(String condition , String typePath){
        ArrayList<Map<String,Object>> objects;

        objects= DatabaseReader.readObject(condition,typePath);

        for (Map<String,Object> mapObject : objects){
            JSONObject object = new JSONObject(mapObject);
            // If object in cash delete it
            if (cash.isInCash("database/"+typePath+"/"+object.get("id")+".json")){
                cash.removeFromCash("database/"+typePath+"/"+object.get("id")+".json");
            }
            IndexWriter.removeFromAnyIndex(object.get("id").toString(),typePath);
            File objectFile = new File("database/"+typePath+"/"+object.get("id")+".json");
            return objectFile.delete();
        }

        return true;

    }

}
