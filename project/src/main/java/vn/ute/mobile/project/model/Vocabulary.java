package vn.ute.mobile.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "db_vocabulary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vocabulary extends Auditable{
  @Id
  @GenericGenerator(name = "idGenerator", strategy = "vn.ute.mobile.project.service.id.CustomIdGenerator")
  @GeneratedValue(generator = "idGenerator")
  private String id;
  @Column(name = "image")
  private String image;
  @Column(name = "word", unique = true)
  private String word;
  @Column(name = "answer")
  private String answer;
  @ManyToOne
  @JoinColumn(name = "topic_id")
  private TopicModel topicModel;
}
