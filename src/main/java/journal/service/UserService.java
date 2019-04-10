package journal.service;

import journal.model.Role;
import journal.DAO.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean areHaveRolesInDataBase(Set<Role> roleSet) {
        boolean findAllRoles = false;
        for (Role role: roleSet) {
            findAllRoles = true;
            if (!roleRepository.findById(role.getId()).isPresent()) {
                findAllRoles = false;
            }
        }
        return findAllRoles;
    }
}
