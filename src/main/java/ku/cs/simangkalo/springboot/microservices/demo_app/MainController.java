package ku.cs.simangkalo.springboot.microservices.demo_app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainController {
    String baseUrl;
    ObjectMapper mapper;
    public MainController() {
        baseUrl = "http://localhost:8080/users";
        mapper = new ObjectMapper();
    }

    private void log(Object info) {
        System.out.println(info);
    }

    public void start() {
        try {
            log(getUser());
            log(addUser("fuk"));
            log(addUser("ice"));
            log(addUser("fluke"));
            log(getUser());
            log(addUser("kuyton"));
            log(getUser());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUser() throws IOException {
        URL url = new URL(baseUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        ArrayList<User> users = mapper.readValue(content.toString(), new TypeReference<ArrayList<User>>(){});
        return users;
    }

    public User addUser(String name) throws IOException {
        URL url = new URL(baseUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream (con.getOutputStream());
        out.writeBytes(name);
        out.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        User user = mapper.readValue(content.toString(), User.class);
        return user;
    }

}
