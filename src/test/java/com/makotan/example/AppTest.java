package com.makotan.example;


import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.junit.*;
import spark.Spark;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
    @BeforeClass
    public static void setup() {
        App app = new App();
        app.init();

        Spark.awaitInitialization();
    }
    
    @AfterClass
    public static void shutdown() {
        Spark.stop();
    }
    
    @Test
    public void HelloTest() throws IOException {

        HttpRequest httpRequest = HttpRequest.get("http://localhost:4567/hello");
        HttpResponse response = httpRequest.send();
        String hello = response.bodyText();
        
        assertThat(hello , is("Hello World"));
    }

    @Test
    public void Test404() throws IOException {

        HttpRequest httpRequest = HttpRequest.get("http://localhost:4567/");
        HttpResponse response = httpRequest.send();

        assertThat(response.statusCode() , is(404));
    }
}
