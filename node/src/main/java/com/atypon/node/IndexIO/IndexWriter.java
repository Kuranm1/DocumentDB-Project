package com.atypon.node.IndexIO;
import com.atypon.node.DatabaseIO.DatabaseReader;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public final class IndexWriter {

    static BufferedWriter writer;
    private IndexWriter(){}
    private static void creatIndex(String path , String jsonObject ){

        try {
            writer = new BufferedWriter(new FileWriter("database/"+path));
            writer.write(jsonObject);
            writer.close();
        } catch (IOException ignored) {
        }
    }
    public static void checkObjectHasIndex(HashMap<String,String> params , String typePath){

        for(String property : params.keySet()){
            String typeIndexPath = typePath+"/index/"+property;
            // check if there is index on any a property
            if (DatabaseReader.isFileExist(typeIndexPath)){
                String propertyPair = property+","+params.get(property);
                String id = params.get("id");
                insertToIndex(id,typePath,propertyPair);
            }
        }
    }
    public static void insertToIndex(String id , String typePath , String property){
        String[] propertyKeyVal = property.split(",");
        String propertyKey=propertyKeyVal[0];
        String propertyValue=propertyKeyVal[1];

        String typeIndexPath = typePath+"/index/"+propertyKey;
        if (DatabaseReader.isFileExist(typeIndexPath+"/"+propertyValue+".json")) {
            String indexObjectString = IndexReader.getIndexFile(typeIndexPath + "/" + propertyValue + ".json");
            JSONObject indexObject = new JSONObject(indexObjectString);
            indexObject.put(id, "");
            creatIndex(typeIndexPath + "/" + propertyValue + ".json", indexObject.toString());
        }
        else {
            JSONObject indexObject = new JSONObject();
            indexObject.put(id,"");
            creatIndex( typeIndexPath + "/" + propertyValue + ".json", indexObject.toString());
        }
    }
    public static void removeFromIndex(String id , String typePath , String property){
        String[] propertyKeyVal = property.split(",");
        String propertyKey=propertyKeyVal[0];
        String propertyValue=propertyKeyVal[1];

        String typeIndexPath = typePath+"/index/"+propertyKey;
        String indexObjectString = IndexReader.getIndexFile(typeIndexPath+"/"+propertyValue+".json");
        JSONObject indexObject =new JSONObject(indexObjectString);
        indexObject.remove(id);
        creatIndex(typeIndexPath+"/"+propertyValue+".json",indexObject.toString());
    }
    public static void removeFromAnyIndex(String id , String typePath){
        File file = new File("database/"+typePath+"/index");
        File[] indexedProperties = file.listFiles();
        File index;
        if (indexedProperties.length > 0){
            index = indexedProperties[0];
            for(File indexObjectFile : index.listFiles()){
                String indexObjectPath= typePath+"/index/"+index.getName()+"/"+indexObjectFile.getName();
                String indexObjectString = IndexReader.getIndexFile(indexObjectPath);
                JSONObject indexObject =new JSONObject(indexObjectString);
                indexObject.remove(id);
                creatIndex(indexObjectPath,indexObject.toString());
            }
        }


    }
}
