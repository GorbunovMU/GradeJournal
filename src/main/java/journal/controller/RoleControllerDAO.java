package journal.controller;

import journal.model.Role;
import journal.utils.ObjectNotFoundException;
import journal.utils.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleControllerDAO {

    @Autowired
    private RoleDAO repository;

    @RequestMapping(path = "/dao/roles", method = RequestMethod.GET)
    public List<Role> getAllRoles() {
        return repository.getAllRoles();
    }

    @RequestMapping(path = "/dao/role/{id}", method = RequestMethod.GET)
    public Role getRoleById(@PathVariable Integer id) {
        Role returnRole = repository.getRoleById(id);

        if (returnRole != null) {
            return returnRole;
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

    @RequestMapping(path = "/dao/role/name", method = RequestMethod.GET)
    public Boolean getRoleById(@RequestParam String name) {
        return repository.RoleExists(name);
    }

    @RequestMapping(path = "/dao/role", method = RequestMethod.POST)
    public String addRole(@RequestParam String name) {
        Role role = new Role(name);
        repository.addRole(role);
        return "Saved";
    }

    @RequestMapping(path = "/dao/role/{id}", method = RequestMethod.PUT)
    public @ResponseBody String renameRole(@PathVariable Integer id, @RequestParam String newName) {
        Role role = repository.getRoleById(id);
        if (role != null) {
            role.setName(newName);
            repository.updateRole(role);
            return "Updated";
        } else {
            return "Not found";
        }
    }

    @RequestMapping(path = "/dao/role/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteRole(@PathVariable Integer id) {
        repository.deleteRole(id);
        return "Deleted";
    }


}
