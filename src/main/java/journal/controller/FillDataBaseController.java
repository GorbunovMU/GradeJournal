package journal.controller;

import journal.model.Role;
import journal.model.Subject;
import journal.model.User;
import journal.model.UserSubjectGrades;
import journal.DAO.RoleRepository;
import journal.DAO.SubjectRepository;
import journal.DAO.UserRepository;
import journal.DAO.UserSubjectGradesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;

@RestController
public class FillDataBaseController {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserSubjectGradesRepository userSubjectGradesRepository;

    @RequestMapping(path = "/fill", method = RequestMethod.GET)
    public String fillAllTablesInDataBase() {
        Subject subject;
        User user;
        Role role;
        UserSubjectGrades userSubjectGrades;

        subject = new Subject("Mathematics");
        subjectRepository.save(subject);

        subject = new Subject("English");
        subjectRepository.save(subject);

        role = new Role("Pupil");
        roleRepository.save(role);

        user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setPatronymic("Ivanovich");
        user.addRole(role);
        userRepository.save(user);

        userSubjectGrades = new UserSubjectGrades();
        userSubjectGrades.setUser(user);
        userSubjectGrades.setSubject(subject);
        userSubjectGrades.setDate(new Date());
        userSubjectGrades.setGradesList(Arrays.asList(10,9,11));
        userSubjectGradesRepository.save(userSubjectGrades);

        user = new User();
        user.setFirstName("James");
        user.setLastName("Smith");
        user.setPatronymic("Smithovich");
        user.addRole(role);
        userRepository.save(user);

        userSubjectGrades = new UserSubjectGrades();
        userSubjectGrades.setUser(user);
        userSubjectGrades.setSubject(subject);
        userSubjectGrades.setDate(new Date());
        userSubjectGrades.setGradesList(Arrays.asList(8,9,10));
        userSubjectGradesRepository.save(userSubjectGrades);

        role = new Role("Teacher");
        roleRepository.save(role);

        user = new User();
        user.setFirstName("Petro");
        user.setLastName("Petrov");
        user.setPatronymic("Petrovich");
        user.addRole(role);
        userRepository.save(user);

        return "Filled";
    }
}
