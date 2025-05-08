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
import vn.ute.mobile.project.model.Vocabulary;

@Data
public class VocabularyCriteria implements Serializable {
  private static final long serialVersionUID = 1L;
  private String word;
  private String topicId;

  public Specification<Vocabulary> getSpecification(){
    return new Specification<Vocabulary>() {
      private static final long serialVersionUID = 1L;
      @Override
      public Predicate toPredicate(Root<Vocabulary> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(getWord())){
          predicates.add(cb.like(cb.lower(root.get("word")), "%" + getWord().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(getTopicId())) {
          predicates.add(cb.equal(root.get("topicModel").get("id"), getTopicId()));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
      }
    };
  }
}
