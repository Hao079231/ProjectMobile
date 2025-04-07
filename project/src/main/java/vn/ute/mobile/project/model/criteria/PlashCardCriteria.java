package vn.ute.mobile.project.model.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import vn.ute.mobile.project.model.Deck;
import vn.ute.mobile.project.model.PlashCard;
import vn.ute.mobile.project.model.User;

@Data
public class PlashCardCriteria {
  private static final long seriableVersionUID = 1L;
  private Long userId;
  private Long deckId;
  public Specification<PlashCard> getSpecification(){
    return new Specification<PlashCard>() {
      private static final long seriableVersionUID = 1L;
      @Override
      public Predicate toPredicate(Root<PlashCard> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (getUserId() != null){
          Join<PlashCard, User> userJoin = root.join("user", JoinType.INNER);
          predicates.add(cb.equal(userJoin.get("id"), getUserId()));
        }
        if (getDeckId() != null) {
          Join<PlashCard, Deck> deckJoin = root.join("deck", JoinType.INNER);
          predicates.add(cb.equal(deckJoin.get("id"), getDeckId()));
        }
        query.orderBy(cb.asc(root.get("id")));
        return cb.and(predicates.toArray(new Predicate[0]));
      }
    };
  }
}
