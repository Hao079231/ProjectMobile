package vn.ute.mobile.project.model.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import vn.ute.mobile.project.model.Deck;
import vn.ute.mobile.project.model.User;

@Data
public class DeckCriteria implements Serializable {
  private static final long seriableVersionUID = 1L;
  private String name;
  private Long userId;

  public Specification<Deck> getSpecification(){
    return new Specification<Deck>() {
      private static final long seriableVersionUID = 1L;
      @Override
      public Predicate toPredicate(Root<Deck> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Deck, User> userJoin = root.join("user", JoinType.INNER);

        if (!StringUtils.isEmpty(getName())){
          predicates.add(cb.like(root.get("name"), "%" + getName() + "%"));
        }
        if (getUserId() != null){
          predicates.add(cb.equal(userJoin.get("id"), getUserId()));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
      }
    };
  }
}
