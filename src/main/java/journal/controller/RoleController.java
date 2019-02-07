package journal.controller;

import journal.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/roles")
public class RoleController {

    @Autowired
    private RoleRepository repository;

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Roles> getAllRoles() {
        return repository.findAll();
    }

    @GetMapping(path = "/role/{id}")
    public @ResponseBody Roles getRoleById(@PathVariable Integer id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

    @PostMapping(path = "/new")
    public @ResponseBody String addRole(@RequestParam String name) {
        Roles role = new Roles(name);
        repository.save(role);
        return "Saved";
    }

    @PutMapping(path = "/rename")
    public @ResponseBody String renameRole(@RequestParam Integer id, @RequestParam String newName) {
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

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteRole(@RequestParam Integer id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return "Deleted";
        } else {
            return "Not found";
        }
    }


}
