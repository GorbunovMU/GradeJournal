package journal.utils;

import journal.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class RoleDAO implements IRoleDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @SuppressWarnings("unchecked")
    @Override
    public List<Role> getAllRoles() {
        String hql = "from Roles as role order by role.id";
        return (List<Role>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public Role getRoleById(int roleId) {
        return entityManager.find(Role.class, roleId);
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void updateRole(Role role) {
        Role updatedRole = getRoleById(role.getId());
        updatedRole.setName(role.getName());
        entityManager.flush();
    }

    @Override
    public void deleteRole(int roleId) {
        entityManager.remove(getRoleById(roleId));
    }

    @Override
    public boolean RoleExists(String name) {
        String hql = "from Roles as role where role.name = :nameRole";
        int count = entityManager.createQuery(hql).setParameter("nameRole", name)
                .getResultList().size();

        return (count > 0);
    }
}
