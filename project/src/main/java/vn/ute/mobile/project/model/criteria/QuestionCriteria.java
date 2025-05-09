package vn.ute.mobile.project.model.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import vn.ute.mobile.project.model.Question;

@Data
public class QuestionCriteria implements Serializable {
  private static final long serialVersionUID = 1L;
  private String question;

  public Specification<Question> getSpecification(){
    return new Specification<Question>() {
      private static final long serialVersionUID = 1L;
      @Override
      public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(question)){
          predicates.add(cb.like(cb.lower(root.get("question")), "%" + getQuestion().toLowerCase() + "%"));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
      }
    };
  }
}
