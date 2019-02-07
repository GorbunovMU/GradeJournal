package journal.controller;

import journal.model.Roles;
import journal.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public Iterable<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public Users getUserById(@PathVariable Integer id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public String addUser(@RequestBody Users newUser) {
        if (roleRepository.findById(newUser.getRole().getId()).isPresent()) {
            userRepository.save(newUser);
            return "Saved";
        } else {
            return "Not found role";
        }
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.PUT)
    public @ResponseBody String renameUser(@PathVariable Integer id, @RequestBody Users newUser) {
        Users user;
        if (userRepository.findById(id).isPresent()) {
            if (roleRepository.findById(newUser.getRole().getId()).isPresent()) {
                user = userRepository.findById(id).get();
                user.setFirstName(newUser.getFirstName());
                user.setLastName(newUser.getLastName());
                user.setPatronymic(newUser.getPatronymic());
                user.setRole(newUser.getRole());
                userRepository.save(user);
                return "Renamed";
            } else {
                return "Not found role";
            }
        } else {
            return "Not found user";
        }
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteUser(@PathVariable Integer id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return "Deleted";
        } else {
            return "Not found";
        }
    }

}
