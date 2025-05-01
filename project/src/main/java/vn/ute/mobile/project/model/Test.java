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
@Table(name = "db_test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test extends Auditable{
  @Id
  @GenericGenerator(name = "idGenerator", strategy = "vn.ute.mobile.project.service.id.CustomIdGenerator")
  @GeneratedValue(generator = "idGenerator")
  private String id;
  @Column(name = "question", unique = true)
  private String question;
  @Column(name = "answer_a")
  private String answerA;
  @Column(name = "answer_b")
  private String answerB;
  @Column(name = "answer_c")
  private String answerC;
  @Column(name = "answer_d")
  private String answerD;
  @Column(name = "correct_answer")
  private String correctAnswer;
}
