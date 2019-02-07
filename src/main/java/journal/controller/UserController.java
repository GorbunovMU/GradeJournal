package journal.controller;

import journal.model.Roles;
import journal.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Users> getAllUsers() {

        return repository.findAll();
    }

    @GetMapping(path = "/user/{id}")
    public @ResponseBody Users getUserById(@PathVariable Integer id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

    @PostMapping(path = "/new")
    public @ResponseBody String addUser(@RequestParam String firstName,
                                        @RequestParam String lastName,
                                        @RequestParam String patronymic,
                                        @RequestParam Integer roleID) {


        Roles role;
        if (roleRepository.findById(roleID).isPresent()) {
            role = roleRepository.findById(roleID).get();
            Users user = new Users();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPatronymic(patronymic);
            user.setRole(role);
            repository.save(user);
            return "Saved";
        } else {
            return "Not found role";
        }
    }

    @PutMapping(path = "/rename")
    public @ResponseBody String renameUser(@RequestParam Integer id,
                                           @RequestParam String newFirstName,
                                           @RequestParam String newLastName,
                                           @RequestParam String newPatronymic,
                                           @RequestParam Integer newRoleID) {

        Roles newRole;
        Users user;
        if (repository.findById(id).isPresent()) {
            if (roleRepository.findById(newRoleID).isPresent()) {
                newRole = roleRepository.findById(newRoleID).get();
                user = repository.findById(id).get();
                user.setFirstName(newFirstName);
                user.setLastName(newLastName);
                user.setPatronymic(newPatronymic);
                user.setRole(newRole);
                repository.save(user);
                return "Renamed";
            } else {
                return "Not found new role";
            }
        } else {
            return "Not found user";
        }
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteUser(@RequestParam Integer id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return "Deleted";
        } else {
            return "Not found";
        }
    }
}
