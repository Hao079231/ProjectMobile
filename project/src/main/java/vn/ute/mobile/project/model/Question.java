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
@Table(name = "db_question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends Auditable{
  @Id
  @GenericGenerator(name = "idGenerator", strategy = "vn.ute.mobile.project.service.id.CustomIdGenerator")
  @GeneratedValue(generator = "idGenerator")
  private String id;
  @Column(name = "question")
  private String question;
  @Column(name = "answer")
  private String answer;
  @Column(name = "all_choice")
  private String allChoice;
}
