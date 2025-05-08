package vn.ute.mobile.project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.ute.mobile.project.model.Vocabulary;

public interface VocabularyRepository extends JpaRepository<Vocabulary, String>,
    JpaSpecificationExecutor<Vocabulary> {

  boolean existsByWord(String word);

  @Modifying
  @Transactional
  @Query("DELETE FROM Vocabulary v WHERE v.topicModel.id = :topicId")
  void deleteByTopicId(String topicId);
}
