package vn.ute.mobile.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ute.mobile.project.model.Grammar;

public interface GrammarRepository extends JpaRepository<Grammar, String>,
    JpaSpecificationExecutor<Grammar> {

  boolean existsByName(String name);
}
