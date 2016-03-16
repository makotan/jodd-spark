package com.makotan.example.front;

import com.makotan.example.service.SimpleService;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInitMethod;
import jodd.petite.meta.PetiteInject;

import static spark.Spark.*;

/**
 * User: makotan
 * Date: 2016/03/15
 * Time: 21:03
 */
@PetiteBean
public class Hello {

    @PetiteInject
    SimpleService service;
    
    @PetiteInitMethod
    public void init() {
        get("/hello", (req, res) -> "Hello World");

        get("/call" , (req, res) -> {
            service.callService();
            return "call!";
        });
    }

}
