package vn.ute.mobile.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ute.mobile.project.model.Question;

public interface QuestionRepository extends JpaRepository<Question, String>,
    JpaSpecificationExecutor<Question> {

  boolean existsByQuestionAndAllChoice(String question, String allChoice);

  boolean existsByQuestionAndAllChoiceAndIdNot(String question, String allChoice, String id);
}
