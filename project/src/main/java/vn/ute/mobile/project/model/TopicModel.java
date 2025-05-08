package vn.ute.mobile.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "db_topic_model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicModel extends Auditable{
  @Id
  @GenericGenerator(name = "idGenerator", strategy = "vn.ute.mobile.project.service.id.CustomIdGenerator")
  @GeneratedValue(generator = "idGenerator")
  private String id;
  @Column(name = "image")
  private String imageView;
  @Column(name = "topic", unique = true)
  private String topic;
}
