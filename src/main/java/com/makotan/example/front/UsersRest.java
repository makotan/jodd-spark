package com.makotan.example.front;

import com.google.gson.Gson;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInitMethod;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

/**
 * User: makotan
 * Date: 2016/03/16
 * Time: 20:40
 */
@PetiteBean
public class UsersRest {
    
    
    public static class User {
        public Long id;
        public String name;
    }

    List<User> userList = new ArrayList<>();
    Gson gson = new Gson();
    
    @PetiteInitMethod
    public void init() {

        after("/users" , (req,res) -> {
            res.type(req.contentType());
        });


        post("/users", "application/json" , (req,res) -> {
            User user = gson.fromJson(req.body() , User.class);
            user.id = (long)userList.size();
            userList.add(user);
            return "" + user.id;
        });

        get("/users", "application/json" , (req,res) -> userList, gson::toJson);
        
    }
}
