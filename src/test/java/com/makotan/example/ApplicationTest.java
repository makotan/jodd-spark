package com.makotan.example;


import com.makotan.example.service.SimpleService;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.petite.meta.PetiteInject;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for simple App.
 */
public class ApplicationTest {
    Logger logger = LoggerFactory.getLogger(ApplicationTest.class);
    static Application application;

    @PetiteInject
    SimpleService service;

    @BeforeClass
    public static void setup() {
        application = new Application();
        application.init();

        Spark.awaitInitialization();
    }
    
    @AfterClass
    public static void shutdown() {
        Spark.stop();
    }
    
    @Before
    public void initTesT() {
        AppCore.getInstance().getPetite().wire(this);
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

    @Test
    public void SimpleServiceTest() throws IOException {

        HttpRequest httpRequest = HttpRequest.get("http://localhost:4567/call");
        HttpResponse response = httpRequest.send();
        String hello = response.bodyText();

        assertThat(hello , is("call!"));
    }
    
    @Test
    public void SimpleServiceCall() {
        logger.info("{}", service.getClass());
        service.callService();
    }
}
