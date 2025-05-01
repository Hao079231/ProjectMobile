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
import vn.ute.mobile.project.model.Test;

@Data
public class TestCriteria implements Serializable {
  private static final long serialVersionUID = 1L;
  private String question;

  public Specification<Test> getSpecification(){
    return new Specification<Test>() {
      private static final long serialVersionUID = 1L;
      @Override
      public Predicate toPredicate(Root<Test> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(getQuestion())){
          predicates.add(cb.like(cb.lower(root.get("question")), "%" + getQuestion().toLowerCase() + "%"));
        }
        predicates.add(cb.like(root.get("id"), "UTETest%"));
        query.orderBy(cb.desc(cb.substring(root.get("id"), 8)));
        return cb.and(predicates.toArray(new Predicate[0]));
      }
    };
  }
}
