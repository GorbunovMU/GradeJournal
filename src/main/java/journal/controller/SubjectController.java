package journal.controller;

import journal.model.Subjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping(path = "/subjects", method = RequestMethod.GET)
    public Iterable<Subjects> getAllSubjects() {

        return subjectRepository.findAll();
    }

    @RequestMapping(path = "/subjects/{id}", method = RequestMethod.GET)
    public Subjects getSubjectById(@PathVariable Integer id) {
        if (subjectRepository.findById(id).isPresent()) {
            return subjectRepository.findById(id).get();
        } else {
            throw new ObjectNotFoundException(id);
        }
    }

    @RequestMapping(path = "/subjects", method = RequestMethod.POST)
    public String addSubject(@RequestParam String name) {
        Subjects subject = new Subjects(name);
        subjectRepository.save(subject);
        return "Saved";
    }

    @RequestMapping(path = "/subjects/{id}", method = RequestMethod.PUT)
    public @ResponseBody String renameSubject(@PathVariable Integer id, @RequestParam String newName) {
        Subjects subject;
        if (subjectRepository.findById(id).isPresent()) {
            subject = subjectRepository.findById(id).get();
            subject.setName(newName);
            subjectRepository.save(subject);
            return "Renamed";
        } else {
            return "Not found";
        }
    }

    @RequestMapping(path = "/subjects/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteSubject(@PathVariable Integer id) {
        if (subjectRepository.findById(id).isPresent()) {
            subjectRepository.deleteById(id);
            return "Deleted";
        } else {
            return "Not found";
        }
    }
}
