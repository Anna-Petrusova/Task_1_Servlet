package ru.appline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {
    private static final Model instance = new Model();

    private final Map<Integer,User> model;

    public static Model getInstance()
    {
        return instance;
    }

    private Model()
    {
        model = new HashMap<>();

        model.put(1,new User("Alice", "Vin", 3500));
        model.put(2,new User("Amy", "Lee", 3600));
        model.put(3,new User("Lilly", "Miro", 3700));
        model.put(4,new User("Tom", "Rickly", 3800));
    }

    public void add(User user, int id)
    {
        model.put(id,user);
    }

    public Map<Integer, User> getFrontList() {
        return model;
    }

    public void deleteAll() { model.clear();}
    public void deleteById(int id) { model.remove(id);}
    public boolean haveId(int id) { return model.containsKey(id);

    }

}
