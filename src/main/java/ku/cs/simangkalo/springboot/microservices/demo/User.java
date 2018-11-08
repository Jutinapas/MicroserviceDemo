package ku.cs.simangkalo.springboot.microservices.demo;

public class User {

    private String name;
    private int id;
    private static int ID = 1;

    public User(String name) {
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

}
