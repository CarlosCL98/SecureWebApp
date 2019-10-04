package edu.eci.SecureWebApp;

import com.google.gson.Gson;

import static spark.Spark.*;

public class SecureWebApp {

    // View example at https://localhost:4567/secureHello

    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");
        secure("deploy/KeyStore.jks", "aACRSecure1App", null, null);
        // Show login page.
        get("/", (request, response) -> {
            response.type("text/html");
            response.redirect("/login.html");
            response.status(200);
            return null;
        });
        post("/register", (req, res) -> {
            return null;
        });
    }

    static int getPort() {
        int port = 4567; //default port if heroku-port isn't set
        if (System.getenv("PORT") != null) {
            port = Integer.parseInt(System.getenv("PORT"));
        }
        return port;
    }
}
