package edu.eci.SecureWebApp;

import edu.eci.SecureWebApp.Model.User;
import edu.eci.SecureWebApp.Persistence.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static spark.Spark.*;

/**
 * Esta clase representa a la aplicación web segura que permite registrar y autenticar a un usuario.
 *
 * @author Carlos Medina
 */
public class SecureWebApp {

    private static UserRepository userRepository = new UserRepository();

    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");
        secure("deploy/keyStoreSecureApp.jks",
                "aACRSecure1App",
                "deploy/truststoreDateServer.jks",
                "jQYXDate5App");
        get("/", (req, res) -> {
            res.type("text/html");
            res.redirect("/index.html");
            res.status(200);
            return null;
        });
        // Show login page.
        post("/login", (req, res) -> {
            String email = req.queryParams("email");
            User searchedUser = userRepository.getUserByEmail(email);
            if (searchedUser == null) {
                System.out.println("The user doesn't exist. Create one.");
                res.status(200);
                res.redirect("/login.html");
                return null;
            }
            String pwd = bytesToHex(getSHA(req.queryParams("pwd")));
            if (!pwd.equals(searchedUser.getPassword())) {
                System.out.println("The password is incorrect. Try again.");
                res.status(200);
                res.redirect("/login.html");
                return null;
            }
            res.status(201);
            return showProfile(searchedUser);
        });
        // Do register.
        post("/register", (req, res) -> {
            String name = req.queryParams("name");
            String email = req.queryParams("email");
            User searchedUser = userRepository.getUserByEmail(email);
            if (searchedUser != null) {
                System.out.println("The user with email '" + email + "' already exists.");
                res.status(200);
                res.redirect("/register.html");
                return null;
            }
            String pwd = bytesToHex(getSHA(req.queryParams("pwd")));
            System.out.println("Password Encrypted: '" + pwd + "'");
            User newUser = new User(0, name, email, pwd);
            userRepository.saveUser(newUser);
            res.status(201);
            res.redirect("/login.html");
            return null;
        });
    }

    /**
     * From https://www.baeldung.com/sha-256-hashing-java
     *
     * @param pwd contraseña a ser tranformada.
     * @return byte[] como el arreglo de carácteres de la contraseña.
     * @throws NoSuchAlgorithmException
     */
    private static byte[] getSHA(String pwd) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(pwd.getBytes(StandardCharsets.UTF_8));
        return encodedhash;
    }

    /**
     * From https://www.baeldung.com/sha-256-hashing-java
     *
     * @param hash contraseña ya cifrada.
     * @return String como el hex del hash.
     */
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Permite mostrar el perfil de un usuario autenticado, mostrandole sus datos. Para esto se retorna la página
     * respectiva.
     *
     * @param user usuario ingresado y autenticado.
     * @return String con la página.
     */
    private static String showProfile(User user) {
        String date = bringDate();
        String profilePage = "<!DOCTYPE html>\n" +
                "<html lang='es'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>Perfil</title>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "\n" +
                "    <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'\n" +
                "          integrity='sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm' crossorigin='anonymous'>\n" +
                "    <script src='https://code.jquery.com/jquery-3.2.1.min.js'\n" +
                "            integrity='sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4='\n" +
                "            crossorigin='anonymous'></script>\n" +
                "    <script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js'\n" +
                "            integrity='sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q'\n" +
                "            crossorigin='anonymous'></script>\n" +
                "    <script src='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js'\n" +
                "            integrity='sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl'\n" +
                "            crossorigin='anonymous'></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class='container' style='margin-top: 5%;'>\n" +
                "    <div class='row'>\n" +
                "        <div class='col-md-5'></div>\n" +
                "        <div class='col-md-2' style='text-align: center;'>\n" +
                "            <h3>Tu Perfil</h3>\n" +
                "            <img src='imgs/userAvatar.png' style='width: 100%; margin-top: 10%;'>\n" +
                "        </div>\n" +
                "        <div class='col-md-5'></div>\n" +
                "    </div>\n" +
                "    <div class='row' style='margin-top: 5%;'>\n" +
                "        <div class='col-md-4'></div>\n" +
                "        <div class='col-md-4'>\n" +
                "           <div style=\"text-align: right\">\n" +
                "                <h5> " + date + "</h5>\n" +
                "            </div>" +
                "            <form>\n" +
                "                <div class='form-group'>\n" +
                "                    <label for='inputName'>Nombre</label>\n" +
                "                    <input type='text' class='form-control' id='inputName' name='name' value='" + user.getName() + "' disabled>\n" +
                "                </div>\n" +
                "                <div class='form-group'>\n" +
                "                    <label for='inputEmail'>Correo</label>\n" +
                "                    <input type='email' class='form-control' id='inputEmail' name='email' value='" + user.getEmail() + "' disabled>\n" +
                "                </div>\n" +
                "            </form>\n" +
                "        </div>\n" +
                "        <div class='col-md-4'></div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        return profilePage;
    }

    /**
     * Este método realiza el llamado a un servidor de fechas para encontrar la fecha actual y mostrarla en el perfil
     * de un usuario.
     *
     * @return String como la fecha actual.
     */
    private static String bringDate() {
        String date = "";
        URL url = null;
        try {
            url = new URL("https://localhost:4568/date"); //https://ec2-35-173-200-145.compute-1.amazonaws.com:4568/date
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = br.readLine();
            while (line != null) {
                date = line;
                line = br.readLine();
            }
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    /**
     * Este método toma el puerto del entorno en el que se esté ejecutando para poder realizar el despliegue respectivo.
     *
     * @return int como el puerto a usar.
     */
    private static int getPort() {
        int port = 4567; //default port if heroku-port isn't set
        if (System.getenv("PORT") != null) {
            port = Integer.parseInt(System.getenv("PORT"));
        }
        return port;
    }

    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                (hostname, sslSession) -> {
                    if (hostname.equals("localhost")) { //ec2-35-173-200-145.compute-1.amazonaws.com
                        return true;
                    }
                    return false;
                });
    }
}
