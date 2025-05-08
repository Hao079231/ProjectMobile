package vn.ute.mobile.project.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.ute.mobile.project.model.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

  User findByIdAndOtp(String id, String otp);

  @Modifying
  @Transactional
  @Query("UPDATE User u SET u.otp = NULL WHERE u.id = :id")
  void clearOtpById(String id);
}
