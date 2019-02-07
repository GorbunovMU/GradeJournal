package journal;


import journal.controller.RoleRepository;
import journal.controller.SubjectRepository;
import journal.controller.UserRepository;
import journal.model.Roles;
import journal.model.Subjects;
import journal.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class LoadDatabase {
        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private SubjectRepository subjectRepository;

        @Bean
        CommandLineRunner initRoles() {
            return args -> {
                Subjects subject;
                Users user;
                Roles role;

                subject = new Subjects("Mathematics");
                subjectRepository.save(subject);

                subject = new Subjects("English");
                subjectRepository.save(subject);

                role = new Roles("Pupil");
                roleRepository.save(role);

                user = new Users();
                user.setFirstName("Ivan");
                user.setLastName("Ivanov");
                user.setPatronymic("Ivanovich");
                user.setRole(role);
                userRepository.save(user);

                user = new Users();
                user.setFirstName("James");
                user.setLastName("Smith");
                user.setPatronymic("Smithovich");
                user.setRole(role);
                userRepository.save(user);

                role = new Roles("Teacher");
                roleRepository.save(role);

                user = new Users();
                user.setFirstName("Petro");
                user.setLastName("Petrov");
                user.setPatronymic("Petrovich");
                user.setRole(role);
                userRepository.save(user);

            };
        }
    }
