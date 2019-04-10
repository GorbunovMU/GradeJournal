package journal.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SUBJECTS")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Size(max = 100)
    private String name;

    public Subject() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
