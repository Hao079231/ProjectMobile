package vn.ute.mobile.project.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ute.mobile.project.model.Notice;
import vn.ute.mobile.project.model.User;

public interface NoticeRepository extends JpaRepository<Notice, String>, JpaSpecificationExecutor<Notice> {

  Optional<Notice> findByIdAndUser(String id, User user);
}
