package com.makotan.example;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        new App().init();
    }

    public void init() {
        get("/hello", (req, res) -> "Hello World");
    }
    
}
