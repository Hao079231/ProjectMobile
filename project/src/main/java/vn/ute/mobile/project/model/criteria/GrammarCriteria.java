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
import vn.ute.mobile.project.model.Grammar;

@Data
public class GrammarCriteria implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;

  public Specification<Grammar> getSpecification(){
    return new Specification<Grammar>() {
      private static final long serialVersionUID = 1L;
      @Override
      public Predicate toPredicate(Root<Grammar> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(getName())){
          predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
      }
    };
  }
}
