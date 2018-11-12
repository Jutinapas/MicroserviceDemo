package ku.cs.simangkalo.springboot.microservices.demo_app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainController {

    public static class ParameterStringBuilder {

        public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        }

    }

    String baseUrl;
    ObjectMapper mapper;

    public MainController() {
        baseUrl = "http://localhost:8080/users";
        mapper = new ObjectMapper();
    }

    public void start() {

        try {

            Scanner choose = new Scanner(System.in);
            String choice = null;

            while (!"6".equals(choice)) {
                System.out.println("\n[1]: list all users, [2] get user, [3]: create new user, [4]: edit user, [5]: delete user, [6]: terminate.");
                choice = choose.nextLine();
                if ("1".equals(choice)) {
                    System.out.println("----- list all users ------");
                    System.out.println(getUsers());
                    choice = null;
                }
                if ("2".equals(choice)) {
                    System.out.println("----- get user -----");
                    System.out.println("ID: ");
                    int id = Integer.parseInt(choose.nextLine());
                    System.out.println("Get: " + getUser(id));
                    choice = null;
                }
                if ("3".equals(choice)) {
                    System.out.println("----- create new user -----");
                    System.out.println("Name: ");
                    String name = choose.nextLine();
                    System.out.println("Created: " + addUser(name));
                    choice = null;
                }
                if ("4".equals(choice)) {
                    System.out.println("----- edit user -----");
                    System.out.println("ID: ");
                    int id = Integer.parseInt(choose.nextLine());
                    System.out.println("Name: ");
                    String name = choose.nextLine();
                    System.out.println("Edited: " + editUser(id, name));
                    choice = null;
                }
                if ("5".equals(choice)) {
                    System.out.println("----- delete user -----");
                    System.out.println("ID: ");
                    int id = Integer.parseInt(choose.nextLine());
                    System.out.println("Deleted: " + deleteUser(id));
                    choice = null;
                }
            }

            choose.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<User> getUsers() throws IOException {
        URL url = new URL(baseUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer json = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            json.append(inputLine);

        in.close();
        con.disconnect();

        ArrayList<User> users = mapper.readValue(json.toString(), new TypeReference<ArrayList<User>>(){});
        return users;
    }

    public User getUser(int id) throws IOException {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer json = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            json.append(inputLine);

        in.close();
        con.disconnect();

        User users = mapper.readValue(json.toString(), User.class);
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
        StringBuffer json = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            json.append(inputLine);

        in.close();
        con.disconnect();

        User user = mapper.readValue(json.toString(), User.class);
        return user;
    }

    public User editUser(int id, String name) throws IOException {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id + "");

        DataOutputStream out = new DataOutputStream (con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.writeBytes(name);
        out.flush();
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer json = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            json.append(inputLine);

        in.close();
        con.disconnect();

        User user = mapper.readValue(json.toString(), User.class);
        return user;
    }

    public User deleteUser(int id) throws IOException {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id + "");

        DataOutputStream out = new DataOutputStream (con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer json = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            json.append(inputLine);

        in.close();
        con.disconnect();

        User user = mapper.readValue(json.toString(), User.class);
        return user;
    }

}
