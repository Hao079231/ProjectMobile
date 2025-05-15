package vn.ute.mobile.project.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.ute.mobile.project.model.User;
import vn.ute.mobile.project.model.UserPoint;

public interface UserPointRepository extends JpaRepository<UserPoint, String> {

  Optional<UserPoint> findByUserId(String userId);

  @Modifying
  @Transactional
  @Query("UPDATE UserPoint up SET up.point = :point, up.time = CURRENT_TIMESTAMP WHERE up.id = :id")
  int updatePointById(String id, Integer point);

  List<UserPoint> findAllByOrderByPointDesc();
}
