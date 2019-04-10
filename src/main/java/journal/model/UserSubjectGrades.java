package journal.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_grades")
public class UserSubjectGrades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Type(type="date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull
    @OneToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;



    @ElementCollection
//    @CollectionTable(name="Grades", joinColumns=@JoinColumn(name="user_grades_id"))
    @CollectionTable(name="Grades")
    @Column(name="grade")
    private List<Integer> gradesList;


    public UserSubjectGrades() {
        gradesList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }


    public List<Integer> getGradesList() {
        return gradesList;
    }

    public void setGradesList(List<Integer> gradesList) {
        this.gradesList = gradesList;
    }

    public void addGrade(Integer grade) {
        gradesList.add(grade);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
