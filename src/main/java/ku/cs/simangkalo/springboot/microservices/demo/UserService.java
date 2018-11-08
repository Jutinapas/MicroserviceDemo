package ku.cs.simangkalo.springboot.microservices.demo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserService {

    private final ArrayList<User> users = new ArrayList<>();

    public User get(int id) {
        User user = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                user = users.get(i);
                break;
            }
        }
        return user;
    }

    public User create(String name) {
        users.add(new User(name));
        return users.get(users.size()-1);
    }

    public User delete(int id) {
        User user = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                user = users.remove(i);
                break;
            }
        }
        return user;
    }

    public User update(int id, String name) {
        User u = users.get(id);
        if (u==null) return null;
        u.setName(name);
        return u;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

}
