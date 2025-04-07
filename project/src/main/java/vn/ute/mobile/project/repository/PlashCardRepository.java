package vn.ute.mobile.project.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ute.mobile.project.model.PlashCard;

public interface PlashCardRepository extends JpaRepository<PlashCard, Long>,
    JpaSpecificationExecutor<PlashCard> {

  int countByDeckId(Long id);

  Optional<PlashCard> findByIdAndUserIdAndDeckId(Long id, Long userId, Long deckId);

  @Transactional
  @Modifying
  @Query("DELETE FROM PlashCard pc WHERE pc.deck.id = :deckId")
  void deleteByDeckId(@Param("deckId") Long deckId);
}
