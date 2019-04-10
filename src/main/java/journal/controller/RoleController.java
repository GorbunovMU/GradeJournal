package journal.controller;

import journal.model.Role;
import journal.utils.ObjectNotFoundException;
import journal.DAO.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @RequestMapping(path = "/roles", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @RequestMapping(path = "/role/{id}", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public Role getRoleById(@PathVariable Integer id) {
        if (roleRepository.findById(id).isPresent()) {
            return roleRepository.findById(id).get();
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

    @RequestMapping(path = "/role", method = RequestMethod.POST)
    public String addRole(@RequestParam String name) {
        Role role = new Role(name);
        roleRepository.save(role);
        return "Saved";
    }

    @RequestMapping(path = "/role/{id}", method = RequestMethod.PUT)
    public String renameRole(@PathVariable Integer id, @RequestParam String newName) {
        Role role;
        if (roleRepository.findById(id).isPresent()) {
            role = roleRepository.findById(id).get();
            role.setName(newName);
            roleRepository.save(role);
            return "Renamed";
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

    @RequestMapping(path = "/role/{id}", method = RequestMethod.DELETE)
    public String deleteRole(@PathVariable Integer id) {
        if (roleRepository.findById(id).isPresent()) {
            roleRepository.deleteById(id);
            return "Deleted";
        } else {
            throw new ObjectNotFoundException(id);
        }
    }


}
