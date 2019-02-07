package journal;


import journal.controller.RoleRepository;
import journal.controller.UserRepository;
import journal.model.Roles;
import journal.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class LoadDatabase {
        @Autowired
        RoleRepository IRoleRepository;

        @Autowired
        UserRepository IUserRepository;

        @Bean
        CommandLineRunner initRoles() {
            return args -> {
                Users user;
                Roles role = new Roles("Pupil");
                IRoleRepository.save(role);

                user = new Users();
                user.setFirstName("Ivan");
                user.setLastName("Ivanov");
                user.setPatronymic("Ivanovich");
                user.setRole(role);
                IUserRepository.save(user);

                user = new Users();
                user.setFirstName("James");
                user.setLastName("Smith");
                user.setPatronymic("Smithovich");
                user.setRole(role);
                IUserRepository.save(user);


                role = new Roles("Teacher");
                IRoleRepository.save(role);

                user = new Users();
                user.setFirstName("Petro");
                user.setLastName("Petrov");
                user.setPatronymic("Petrovich");
                user.setRole(role);
                IUserRepository.save(user);
            };
        }
    }
