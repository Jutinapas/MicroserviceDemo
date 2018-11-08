package ku.cs.simangkalo.springboot.microservices.demo_app;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private int id;
    private static int ID = 1;

    public User(@JsonProperty("name") String name) {
        this.id = User.ID++;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("User#%d[%s]", this.id, this.name);
    }
}
