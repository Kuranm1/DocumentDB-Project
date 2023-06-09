package com.atypon.node.IndexIO;
import com.atypon.node.DatabaseIO.DatabaseReader;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class IndexReader {

    static BufferedReader reader;

    private IndexReader(){}
    public static String getIndexFile(String path){
        String indexFile;
        try {
            reader = new BufferedReader(new FileReader("database/"+path));
            indexFile = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return indexFile;
    }
    public static boolean isConditionHasIndex(String condition , String path){
        String[] conditionKeyVal = condition.split(",");
        String conditionKey=conditionKeyVal[0];
        for (File dir : DatabaseReader.getListOfFiles("database/"+path+"/index")){
            if (conditionKey.equals(dir.getName()))
                return true;
        }
        return false;
    }
    private static Set<String> readIndexIDs(String condition, String path){
        String[] conditionKeyVal = condition.split(",");
        String conditionKey=conditionKeyVal[0];
        String conditionValue=conditionKeyVal[1];
        Set<String> IDs = new HashSet<>();

        File file = new File("database/"+path+"/"+conditionKey+"/"+conditionValue+".json");

        if(!file.exists())
            return IDs;

        try {
            reader = new BufferedReader(new FileReader("database/"+path+"/"+conditionKey+"/"+conditionValue+".json"));
            JSONObject object = new JSONObject(reader.readLine());
            IDs = object.keySet();
            return IDs;
        } catch (IOException e) {
            return IDs;
        }
    }
    public static ArrayList<Map<String,Object>> readObjectByIndex(String condition, String path ){
        ArrayList<Map<String,Object>> objects = new ArrayList<>();
        Set<String> IDs = readIndexIDs(condition,path+"/index");
        if (IDs.isEmpty())
            return objects;

        for (String id : IDs){
            objects.add(DatabaseReader.readObjectByID(path+"/"+id+".json"));
        }
        return objects;
    }
}
