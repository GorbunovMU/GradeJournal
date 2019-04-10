package journal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import journal.model.Subject;
import journal.utils.SubjectRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.LoadToDataBase.getSubjectList;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SubjectControllerTest {

    private List<Subject> subjectList;

    @Autowired
    private SubjectRepository subjectRepository;


    @Autowired
    private MockMvc mockMvc;

    @Before
    public void addSubjectsToBD() {
        subjectList = getSubjectList(subjectRepository);
    }

    @After
    public void deleteAllSubjectsFromBD() {
        subjectRepository.deleteAll();
        subjectList.clear();
    }

    @Test
    public void getSubjectByIdTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(subjectList.get(0));

        this.mockMvc.perform(get("/subject/" + subjectList.get(0).getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(jsonInString));
    }

    @Test
    public void getSubjectByIdWithExceptionTest() throws Exception {
        this.mockMvc.perform(get("/subject/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllSubjectsTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(subjectList);

        this.mockMvc.perform(get("/subjects")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(jsonInString));

    }

    @Test
    public void addSubjectTest() throws Exception {
        String nameSubject = "qwerty";
        Subject subject = new Subject(nameSubject);

        this.mockMvc.perform(post("/subject").param("name", nameSubject))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Saved"));

        subjectRepository.delete(subject);

    }

    @Test
    public void renameSubjectTest() throws Exception{
        Integer id = subjectList.get(2).getId();
        String newNameSubject = "qwerty";
        Subject expectedSubject = new Subject(newNameSubject);

        this.mockMvc.perform(put("/subject/" + id)
                .param("newName", newNameSubject))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Renamed"));

        assertTrue(subjectRepository.findById(id).isPresent());
        expectedSubject = subjectRepository.findById(id).get();
        assertEquals(expectedSubject.getName(), newNameSubject);

    }

    @Test
    public void renameSubjectNotFoundTest() throws Exception {
        this.mockMvc.perform(put("/subject/100")
                .param("newName", "newName"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void deleteSubjectTest() throws Exception {
        this.mockMvc.perform(delete("/subject/" + subjectList.get(2).getId()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        assertEquals(subjectList.size() - 1,subjectRepository.findAll().size());
    }

    @Test
    public void deleteSubjectNotFoundTest() throws Exception {
        this.mockMvc.perform(delete("/subject/100"))
                .andDo(print()).andExpect(status().isNotFound());
    }
}