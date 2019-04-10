package journal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import journal.model.Role;
import journal.model.User;
import journal.utils.RoleRepository;
import journal.utils.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import utils.LoadToDataBase;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MockMvc mockMvc;

    private List<User> userList;
    private User newUser;

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        Set<Role> roleSet;

        roleSet = new HashSet<>(LoadToDataBase.getRoleList(roleRepository));
        userList = LoadToDataBase.getUserList(userRepository, roleSet);

        newUser = LoadToDataBase.getNewUser(userList.get(0).getRoles().iterator().next());
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        userList.clear();
    }

    @Test
    public void getAllUsersTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userList);

        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(jsonInString));
    }

    @Test
    public void getUserByIdTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userList.get(0));

        this.mockMvc.perform(get("/user/" + userList.get(0).getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(jsonInString));
    }

    @Test
    public void getUserByIdWithExceptionTest() throws Exception {
        this.mockMvc.perform(get("/user/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addUserTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(post("/user")
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Saved"));

        userRepository.delete(newUser);
    }

    @Test
    public void addUserNotFoundRoleTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Role role = new Role("qwerty");
        role.setId(200);
        newUser.deleteAllRoles();
        newUser.addRole(role);
        String jsonInString = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(post("/user")
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string("Not found roles"));
    }

    @Test
    public void renameUserTest() throws Exception {
        User expectedUser;
        Integer id = userList.get(2).getId();
        ObjectMapper mapper = new ObjectMapper();
        newUser.setId(id);
        String jsonInString = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(put("/user/" + id)
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Renamed"));

        assertTrue(userRepository.findById(id).isPresent());
        expectedUser = userRepository.findById(id).get();
        assertEquals(expectedUser.getLastName(), newUser.getLastName());
    }

    @Test
    public void renameUserNotFoundRoleTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Integer id = userList.get(2).getId();
        Role role = new Role("qwerty");
        role.setId(200);
        newUser.deleteAllRoles();
        newUser.addRole(role);
        String jsonInString = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(put("/user/" + id)
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string("Not found roles"));
    }

    @Test
    public void renameUserNotFoundByIdTest() throws Exception {
        User expectedUser;
        Integer id = 500;
        ObjectMapper mapper = new ObjectMapper();
        newUser.setId(id);
        String jsonInString = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(put("/user/" + id)
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonInString))
                .andDo(print()).andExpect(status().isNotFound());

    }

    @Test
    public void deleteUserTest() throws Exception{
        this.mockMvc.perform(delete("/user/" + userList.get(2).getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        assertEquals(userList.size() - 1,userRepository.findAll().size());
    }


    @Test
    public void deleteUserNotFoundTest() throws Exception {
        this.mockMvc.perform(delete("/user/100"))
                .andDo(print()).andExpect(status().isNotFound());
    }
}