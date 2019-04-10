package journal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import journal.model.Role;
import journal.DAO.RoleRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static utils.LoadToDataBase.getRoleList;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class RoleControllerTest {
    private List<Role> roleList;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleController roleController;

    @Autowired
    private MockMvc mockMvc;



    @Before
    public void addRolesToBD() {
        roleList = getRoleList(roleRepository);
    }

    @After
    public void deleteAllRolesFromBD() {
        roleRepository.deleteAll();
        roleList.clear();
    }

    @Test
    public void controllerIsLoadTest() {
        assertThat(roleController).isNotNull();
    }

    @Test
    public void getRoleByIdTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(roleList.get(0));

        this.mockMvc.perform(get("/role/" + roleList.get(0).getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(jsonInString));
    }

    @Test
    public void getRoleByIdWithExceptionTest() throws Exception {
        this.mockMvc.perform(get("/role/10"))
        .andExpect(status().isNotFound());
    }

    @Test
    public void getAllRolesTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(roleList);

        this.mockMvc.perform(get("/roles")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(jsonInString));

    }

    @Test
    public void addRoleTest() throws Exception {
        String nameRole = "qwerty";
        Role role = new Role(nameRole);

        this.mockMvc.perform(post("/role").param("name", nameRole))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Saved"));

        roleRepository.delete(role);

    }

    @Test
    public void renameRoleTest() throws Exception{
        Integer id = roleList.get(2).getId();
        String newNameRole = "qwerty";
        Role expectedRole = new Role(newNameRole);

        this.mockMvc.perform(put("/role/" + id)
                .param("newName", newNameRole))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Renamed"));

        assertTrue(roleRepository.findById(id).isPresent());
        expectedRole = roleRepository.findById(id).get();
        assertEquals(expectedRole.getName(), newNameRole);

    }

    @Test
    public void renameRoleNotFoundTest() throws Exception {
        this.mockMvc.perform(put("/role/10")
                .param("newName", "newName"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void deleteRoleTest() throws Exception {
        this.mockMvc.perform(delete("/role/" + roleList.get(2).getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        assertEquals(roleList.size() - 1,roleRepository.findAll().size());
    }

    @Test
    public void deleteRoleNotFoundTest() throws Exception {
        this.mockMvc.perform(delete("/role/10"))
                .andDo(print()).andExpect(status().isNotFound());
    }
}
