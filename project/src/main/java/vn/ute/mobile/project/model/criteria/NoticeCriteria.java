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
import vn.ute.mobile.project.model.Notice;
import vn.ute.mobile.project.model.User;

@Data
public class NoticeCriteria implements Serializable {
  private static final long serialVersionUID = 1L;
  private String userId;

  public Specification<Notice> getSpecification() {
    return new Specification<Notice>() {
      private static final long serialVersionUID = 1L;

      @Override
      public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Kiểm tra userId
        if (StringUtils.hasText(getUserId())) {
          Join<Notice, User> userJoin = root.join("user", JoinType.INNER);
          predicates.add(cb.like(cb.lower(userJoin.get("id")), "%" + getUserId().toLowerCase() + "%"));
        }

        // Tách số từ id
        // Loại bỏ prefix "UTENotice" và lấy phần số
        predicates.add(cb.like(root.get("id"), "UTENotice%"));

        // Sắp xếp theo id giảm dần (dựa trên phần số)
        query.orderBy(cb.desc(cb.substring(root.get("id"), 10)));

        return cb.and(predicates.toArray(new Predicate[0]));
      }
    };
  }
}