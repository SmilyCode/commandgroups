package smily.animate.core.select;

import java.util.HashMap;
import java.util.Map;

//For building database object
public class Database<T>{
    private final Map<Integer, T> database;

    //copy a database to this
    private Database(Database<T> database){
        this.database = database.getMap();
    }

    public Database(){
        this.database = new HashMap<>();
    }

    public Map<Integer, T> getMap(){
        return database;
    }

    public T getDataFromId(int id){
        return this.database.get(id);
    }

    public void storeData(int id, T obj){
        if(database.containsKey(id)) throw new RuntimeException("Cannot insert 2 same id.");

        database.put(id, obj);
    }

    public int size(){
        return database.size();
    }

    public void changeData(int id, T obj){
        if(!database.containsKey(id)) return;

        database.remove(id);
        database.put(id, obj);
    }

    public void removeData(int id){
        database.remove(id);
    }

    public boolean containID(int id){
        return database.containsKey(id);
    }

    public static <T> Database<T> fromDatabase(Database<T> database){
        return new Database<>(database);
    }
}
