package vn.ute.mobile.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "db_user_point")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPoint extends Auditable{
  @Id
  @GenericGenerator(name = "idGenerator", strategy = "vn.ute.mobile.project.service.id.CustomIdGenerator")
  @GeneratedValue(generator = "idGenerator")
  private String id;
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
  @Column(name = "point")
  private Integer point;
  @Column(name = "time")
  private LocalDateTime time;
}
