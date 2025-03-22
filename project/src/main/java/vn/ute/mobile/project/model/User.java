package vn.ute.mobile.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "db_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable{
  @Id
  private Long id;
  @OneToOne
  @MapsId
  @JoinColumn(name = "id")
  private Account account;
  @Column(name = "gender")
  private Integer gender;
  @Column(name = "phone")
  private String phone;
  @Column(name = "otp")
  private String otp;
}
