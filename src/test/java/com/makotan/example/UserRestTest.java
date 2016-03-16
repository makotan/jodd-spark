package com.makotan.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makotan.example.front.UsersRest;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: makotan
 * Date: 2016/03/16
 * Time: 20:59
 */
public class UserRestTest {
    Logger logger = LoggerFactory.getLogger(UserRestTest.class);
    static Application application;

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
    public void Test() throws IOException {
        {
            HttpRequest httpRequest = HttpRequest.post("http://localhost:4567/users");
            httpRequest.contentType("application/json" , "utf-8");
            httpRequest.body("{\"name\":\"spark\"}");
            HttpResponse response = httpRequest.send();
            String usersPost = response.bodyText();

            assertThat(usersPost, is("0"));
        }

        {
            HttpRequest httpRequest = HttpRequest.post("http://localhost:4567/users");
            httpRequest.contentType("application/json" , "utf-8");
            httpRequest.body("{\"name\":\"jodd\"}");
            HttpResponse response = httpRequest.send();
            String usersPost = response.bodyText();

            assertThat(usersPost, is("1"));
        }
        
        {
            HttpRequest httpRequest = HttpRequest.get("http://localhost:4567/users");
            httpRequest.contentType("application/json" , "utf-8");
            HttpResponse response = httpRequest.send();
            String users = response.bodyText();

            Gson gson = new Gson();
            List<UsersRest.User> userList = gson.fromJson(users ,new TypeToken<List<UsersRest.User>>() {}.getType());
            
            assertThat(userList.size() , is(2));
            assertThat(response.contentType() , is("application/json;charset=UTF-8"));

        }
        
    }
}
