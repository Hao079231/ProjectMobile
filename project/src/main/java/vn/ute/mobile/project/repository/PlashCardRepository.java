package vn.ute.mobile.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ute.mobile.project.model.PlashCard;

public interface PlashCardRepository extends JpaRepository<PlashCard, Long>,
    JpaSpecificationExecutor<PlashCard> {

}
