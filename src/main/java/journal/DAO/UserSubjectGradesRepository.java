package journal.DAO;

import journal.model.UserSubjectGrades;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubjectGradesRepository extends JpaRepository<UserSubjectGrades, Integer> {
}
