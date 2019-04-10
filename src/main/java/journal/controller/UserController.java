package journal.controller;

import journal.model.User;
import journal.service.UserService;
import journal.utils.ObjectNotFoundException;
import journal.DAO.RoleRepository;
import journal.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public User getUserById(@PathVariable Integer id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public String addUser(@RequestBody User newUser) {

        if (userService.areHaveRolesInDataBase(newUser.getRoles())) {
            userRepository.save(newUser);
            return "Saved";
        } else {
            throw new ObjectNotFoundException("Not found roles");
        }
    }

    @RequestMapping(path = "/user/{id}", method = RequestMethod.PUT)
    public @ResponseBody String renameUser(@PathVariable Integer id, @RequestBody User newUser) {
        User user;

        if (userService.areHaveRolesInDataBase(newUser.getRoles())) {
            if (userRepository.findById(id).isPresent()) {
                user = userRepository.findById(id).get();
                user.setPatronymic(newUser.getPatronymic());
                user.setLastName(newUser.getLastName());
                user.setFirstName(newUser.getFirstName());
                userRepository.save(user);
                return "Renamed";
            } else {
                throw new ObjectNotFoundException(id);
            }
        } else {
            throw new ObjectNotFoundException("Not found roles");
        }

    }

    @RequestMapping(path = "/user/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteUser(@PathVariable Integer id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return "Deleted";
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

}
