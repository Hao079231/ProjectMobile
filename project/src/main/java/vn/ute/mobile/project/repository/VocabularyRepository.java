package vn.ute.mobile.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ute.mobile.project.model.Vocabulary;

public interface VocabularyRepository extends JpaRepository<Vocabulary, String>,
    JpaSpecificationExecutor<Vocabulary> {

  boolean existsByWord(String word);

}
