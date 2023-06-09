package com.atypon.node.DatabaseIO;
import com.atypon.node.Cash.Cash;
import com.atypon.node.IndexIO.IndexReader;
import org.json.JSONObject;

import java.io.*;
import java.util.*;


public final class DatabaseReader {
    private DatabaseReader(){}
    static Cash cash = Cash.getInstance();
    static BufferedReader reader;
    public static Boolean isFileExist(String path){
        File file = new File("database/"+path);
        return file.exists();
    }
    public static Boolean isTypeSchema (String path, JSONObject jsonObject){
        String typeSchema;
        try {
            reader = new BufferedReader(new FileReader("database/"+path));
            typeSchema = reader.readLine();
            reader.close();
        } catch (IOException e) {
           return false;
        }

        JSONObject typeSchemaObject = new JSONObject(typeSchema);
        return typeSchemaObject.keySet().equals(jsonObject.keySet());
    }
    public static File[] getListOfFiles(String path){
        File folder = new File(path);
        return folder.listFiles();
    }
    public static ArrayList<Map<String,Object>>  readObject(String condition ,String path){
        String[] conditionKeyVal = condition.split(",");
        String conditionKey=conditionKeyVal[0];
        String conditionValue=conditionKeyVal[1];
        ArrayList<Map<String,Object>>  objects = new ArrayList<> ();

        if (conditionKey.equals("id")){
            // Try read from cash
            if (cash.isInCash("database/"+path+"/"+conditionValue+".json")){
                JSONObject object = new JSONObject(cash.readFromCash("database/"+path+"/"+conditionValue+".json"));
                objects.add(object.toMap());
                return objects;
            }
            // Read from index
            objects.add(readObjectByID(path+"/"+conditionValue+".json"));
            return objects;
        }

        if (getListOfFiles("database/"+path) == null)
            return objects;

        if (IndexReader.isConditionHasIndex(condition,path)) {
            // read from index if the condition is indexed
            objects = IndexReader.readObjectByIndex(condition, path);
            return objects;
        }

        for (File file : getListOfFiles("database/"+path)){
            try {
                reader = new BufferedReader(new FileReader(file));
                JSONObject object = new JSONObject(reader.readLine());
                if (object.has(conditionKey)){
                    if (object.get(conditionKey).equals(conditionValue))
                        objects.add(object.toMap());
                }
                reader.close();
            } catch (IOException e) {
                 return objects;
            }
        }
        return objects;
    }
    public static Map <String,Object> readObjectByID(String path){
        Map <String,Object> object = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader("database/"+path));
            JSONObject jsonObject = new JSONObject(reader.readLine());
            reader.close();
            object = jsonObject.toMap();
            return object;
        } catch (IOException e) {
            return object;
        }
    }
    public static ArrayList<Map<String,Object>>  readAllObject(String path ){
        ArrayList<Map<String,Object>>  objects = new ArrayList<> ();

        if (getListOfFiles("database/"+path) == null)
            return objects;
        for (File file : getListOfFiles("database/"+path)){
            try {
                if (!file.isFile())
                    continue;
                reader = new BufferedReader(new FileReader(file));
                JSONObject object = new JSONObject(reader.readLine());
                objects.add(object.toMap());
                reader.close();
            } catch (IOException e) {
                return objects;
            }
        }
        return objects;
    }

}
