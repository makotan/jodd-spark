package com.makotan.example;

import javax.servlet.http.HttpServletRequest;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App {
    private static final ThreadLocal<HttpServletRequest> requestHolder = new InheritableThreadLocal<>();
    public static void main(String[] args) {
        new App().init();
    }

    public void init() {
        AppCore.getInstance().start();
        
        before((req,res) -> {
            requestHolder.set(req.raw());
        });
        
        after((req,res) -> {
            requestHolder.remove();
        });
        
        get("/hello", (req, res) -> "Hello World");
        
        get("/call" , (req, res) -> {
            SimpleService service = AppCore.getInstance().getPetite().getBean(SimpleService.class);
            service.callService();
            return "call!";
        });
    }
    
}
