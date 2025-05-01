package vn.ute.mobile.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ute.mobile.project.model.Test;

public interface TestRepository extends JpaRepository<Test, String>, JpaSpecificationExecutor<Test> {

  boolean existsByQuestion(String question);
}
