package journal.model;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "periods")
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "begin_date")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date beginDate;

    @Column(name = "end_date")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public Period() {
    }

    public Period(@NotNull String name, @NotNull Date beginDate,
                  @NotNull Date endDate) {

        this.name = name;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return getId().equals(period.getId()) &&
                getName().equals(period.getName()) &&
                getBeginDate().equals(period.getBeginDate()) &&
                getEndDate().equals(period.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getBeginDate(), getEndDate());
    }
}
