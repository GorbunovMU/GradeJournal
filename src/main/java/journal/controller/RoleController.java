package journal.controller;

import journal.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {

    @Autowired
    private RoleRepository repository;

    @RequestMapping(path = "/roles", method = RequestMethod.GET)
    public Iterable<Roles> getAllRoles() {
        return repository.findAll();
    }

    @RequestMapping(path = "/roles/{id}", method = RequestMethod.GET)
    public Roles getRoleById(@PathVariable Integer id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

    @RequestMapping(path = "/roles", method = RequestMethod.POST)
    public String addRole(@RequestParam String name) {
        Roles role = new Roles(name);
        repository.save(role);
        return "Saved";
    }

    @RequestMapping(path = "/roles/{id}", method = RequestMethod.PUT)
    public @ResponseBody String renameRole(@PathVariable Integer id, @RequestParam String newName) {
        Roles role;
        if (repository.findById(id).isPresent()) {
            role = repository.findById(id).get();
            role.setName(newName);
            repository.save(role);
            return "Renamed";
        } else {
            return "Not found";
        }
    }

    @RequestMapping(path = "/roles/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteRole(@PathVariable Integer id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return "Deleted";
        } else {
            return "Not found";
        }
    }


}
