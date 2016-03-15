package com.makotan.example;


import com.makotan.example.front.Hello;
import jodd.petite.meta.PetiteInject;

import javax.servlet.http.HttpServletRequest;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class Application {
    private static final ThreadLocal<HttpServletRequest> requestHolder = new InheritableThreadLocal<>();
    public static void main(String[] args) {
        new Application().init();
    }

    @PetiteInject
    Hello hello;
    
    public void init() {
        AppCore.getInstance().start();
        AppCore.getInstance().getPetite().wire(this);
        
        before((req,res) -> {
            requestHolder.set(req.raw());
        });
        
        after((req,res) -> {
            requestHolder.remove();
        });

        hello.init();
        
    }
    
}
