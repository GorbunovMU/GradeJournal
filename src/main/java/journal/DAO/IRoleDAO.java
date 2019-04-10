package journal.DAO;

import journal.model.Role;

import java.util.List;

public interface IRoleDAO {
    List<Role> getAllRoles();
    Role getRoleById(int roleId);
    void addRole(Role role);
    void updateRole(Role role);
    void deleteRole(int roleId);
    boolean RoleExists(String name);
}
