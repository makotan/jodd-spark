package com.makotan.example;

import sun.java2d.pipe.SpanShapeRenderer;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App {
    AppCore appCore = new AppCore();
    public static void main(String[] args) {
        new App().init();
    }

    public void init() {
        appCore.start();
        get("/hello", (req, res) -> "Hello World");
        
        get("/call" , (req, res) -> {
            SimpleService service = appCore.getPetite().getBean(SimpleService.class);
            service.callService();
            return "call!";
        });
    }
    
}
