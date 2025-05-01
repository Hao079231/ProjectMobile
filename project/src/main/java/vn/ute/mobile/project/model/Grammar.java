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
@Table(name = "db_grammar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grammar extends Auditable{
  @Id
  @GenericGenerator(name = "idGenerator",strategy = "vn.ute.mobile.project.service.id.CustomIdGenerator")
  @GeneratedValue(generator = "idGenerator")
  private String id;
  @Column(name = "name", unique = true)
  private String name;
  @Column(name = "content")
  private String content;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
