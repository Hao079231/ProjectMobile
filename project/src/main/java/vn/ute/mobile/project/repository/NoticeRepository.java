package vn.ute.mobile.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ute.mobile.project.model.Notice;

public interface NoticeRepository extends JpaRepository<Notice, String>, JpaSpecificationExecutor<Notice> {

}
