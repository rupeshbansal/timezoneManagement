package com.toptalpremierleague.rest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.toptalpremierleague.rest.representations.User;

public class UserDB {

    public static HashMap<Integer, User> users = new HashMap<>();
    static{
        users.put(1, new User(1, "Lokesh", "Gupta", "India"));
        users.put(2, new User(2, "John", "Gruber", "USA"));
        users.put(3, new User(3, "Melcum", "Marshal", "AUS"));
    }

    public static List<User> getUsers(){
        return new ArrayList<User>(users.values());
    }

    public static User getUser(Integer id){
        return users.get(id);
    }

    public static void updateUser(Integer id, User user){
        users.put(id, user);
    }

    public static void removeUser(Integer id){
        users.remove(id);
    }
}