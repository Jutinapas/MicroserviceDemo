package ku.cs.simangkalo.springboot.microservices.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ArrayList<User>> getUsers() {
        ArrayList<User> users = this.service.getUsers();
        if (users == null) return new ResponseEntity<ArrayList<User>>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<ArrayList<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}")
    public ResponseEntity<User> get(@PathVariable("id") int id) {
        User user = this.service.get(id);
        if (user == null) return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> delete(@PathVariable("id") int id) {
        User user = this.service.delete(id);
        if (user == null) return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody String name) {
        User user = this.service.create(name);
        if (user == null) return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> update(@PathVariable("id") int id, @RequestBody String name) {
        User user = this.service.update(id, name);
        if (user == null) return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

}