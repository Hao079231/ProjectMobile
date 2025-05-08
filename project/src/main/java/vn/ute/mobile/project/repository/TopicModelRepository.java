package vn.ute.mobile.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ute.mobile.project.model.TopicModel;

public interface TopicModelRepository extends JpaRepository<TopicModel, String>,
    JpaSpecificationExecutor<TopicModel> {

  boolean existsByTopic(String topic);
}
