package journal.model;

import org.hibernate.annotations.Fetch;

import javax.naming.Name;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "groups_people")
public class GroupsOfPeople {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name = "name")
    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "start_period_id", nullable = false)
    private Period startPeriod;

    @Column(name = "group_by_start_period")
    @NotNull
    private Boolean groupByStartPeriod;

    @Column(name = "level_of_group")
    @NotNull
    private Integer levelOfGroup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_group_id")
    private GroupsOfPeople parentGroup;

    public GroupsOfPeople() {
    }

    public GroupsOfPeople(@NotNull String name, Period startPeriod,
                          @NotNull Boolean groupByStartPeriod, @NotNull Integer levelOfGroup) {

        this.name = name;
        this.startPeriod = startPeriod;
        this.groupByStartPeriod = groupByStartPeriod;
        this.levelOfGroup = levelOfGroup;
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

    public Period getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Period startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Boolean getGroupByStartPeriod() {
        return groupByStartPeriod;
    }

    public void setGroupByStartPeriod(Boolean groupByStartPeriod) {
        this.groupByStartPeriod = groupByStartPeriod;
    }

    public Integer getLevelOfGroup() {
        return levelOfGroup;
    }

    public void setLevelOfGroup(Integer levelOfGroup) {
        this.levelOfGroup = levelOfGroup;
    }

    public GroupsOfPeople getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(GroupsOfPeople parentGroup) {
        this.parentGroup = parentGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupsOfPeople that = (GroupsOfPeople) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName()) &&
                getStartPeriod().equals(that.getStartPeriod()) &&
                getGroupByStartPeriod().equals(that.getGroupByStartPeriod()) &&
                getLevelOfGroup().equals(that.getLevelOfGroup()) &&
                getParentGroup().equals(that.getParentGroup());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getStartPeriod(), getGroupByStartPeriod(),
                getLevelOfGroup(), getParentGroup());
    }
}
