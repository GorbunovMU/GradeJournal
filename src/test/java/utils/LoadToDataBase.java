package utils;

import journal.model.Role;
import journal.model.Subject;
import journal.model.User;
import journal.DAO.RoleRepository;
import journal.DAO.SubjectRepository;
import journal.DAO.UserRepository;

import java.util.List;
import java.util.Set;

public class LoadToDataBase {
    public static List<Role> getRoleList(RoleRepository roleRepository) {
        roleRepository.save(new Role("Pupil"));
        roleRepository.save(new Role("Teacher"));
        roleRepository.save(new Role("Admin"));
        return roleRepository.findAll();
    }

    @SuppressWarnings("unchecked")
    public static List<User> getUserList(UserRepository userRepository, Set<Role> roleSet) {
        int count = 0;
//        String hql = "select u from users u left join fetch u.roles";

        for (Role r: roleSet) {
            User user = new User();
            user.setFirstName("Petr_" + count);
            user.setLastName("Petrov_"+ count);
            user.setPatronymic("Petrovich_"+ count);
            user.addRole(r);
            userRepository.save(user);
            count++;
        }

        return userRepository.findAll();
    }

    public static List<Subject> getSubjectList(SubjectRepository subjectRepository) {
        subjectRepository.save(new Subject("Mathematics"));
        subjectRepository.save(new Subject("English"));
        subjectRepository.save(new Subject("Grammar"));
        return subjectRepository.findAll();
    }

    public static User getNewUser(Role role) {
        User newUser = new User();
        newUser.addRole(role);
        newUser.setFirstName("John");
        newUser.setLastName("Smith");
        newUser.setPatronymic("Abramovich");
        return newUser;
    }
}
