package journal.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(max = 100)
    private String lastName;
    @NotNull
    @Size(max = 100)
    private String firstName;
    @Size(max = 100)
    private String patronymic;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="users_roles",
            joinColumns=
            @JoinColumn(name="user_id", referencedColumnName="ID"),
            inverseJoinColumns=
            @JoinColumn(name="role_id", referencedColumnName="ID")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean addRole(Role role) {
        return roles.add(role);
    }

    public void deleteAllRoles() {
        this.roles.clear();
    }

    public boolean findRole(Role role) {
        return roles.contains(role);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId().equals(user.getId()) &&
                getLastName().equals(user.getLastName()) &&
                getFirstName().equals(user.getFirstName()) &&
                getPatronymic().equals(user.getPatronymic()) &&
                Objects.equals(getRoles(), user.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLastName(), getFirstName(),
                getPatronymic(), getRoles());
    }
}
