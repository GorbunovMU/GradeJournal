package journal.controller;

import journal.utils.ObjectNotFoundException;
import journal.utils.SubjectRepository;
import journal.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubjectController {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @RequestMapping(path = "/subjects", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public Iterable<Subject> getAllSubjects() {

        return subjectRepository.findAll();
    }

    @RequestMapping(path = "/subject/{id}", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public Subject getSubjectById(@PathVariable Integer id) {
        if (subjectRepository.findById(id).isPresent()) {
            return subjectRepository.findById(id).get();
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

    @RequestMapping(path = "/subject", method = RequestMethod.POST)
    public String addSubject(@RequestParam String name) {
        Subject subject = new Subject(name);
        subjectRepository.save(subject);
        return "Saved";
    }

    @RequestMapping(path = "/subject/{id}", method = RequestMethod.PUT)
    public @ResponseBody String renameSubject(@PathVariable Integer id, @RequestParam String newName) {
        Subject subject;
        if (subjectRepository.findById(id).isPresent()) {
            subject = subjectRepository.findById(id).get();
            subject.setName(newName);
            subjectRepository.save(subject);
            return "Renamed";
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

    @RequestMapping(path = "/subject/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteSubject(@PathVariable Integer id) {
        if (subjectRepository.findById(id).isPresent()) {
            subjectRepository.deleteById(id);
            return "Deleted";
        } else {
            throw new ObjectNotFoundException(id);
        }
    }
}
