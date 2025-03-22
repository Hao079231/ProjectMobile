package vn.ute.mobile.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "db_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends Auditable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "user_name", unique = true)
  private String username;
  @Column(name = "full_name")
  private String fullname;
  @Column(name = "email")
  private String email;
  @Column(name = "pass_word")
  private String password;
  @Column(name = "is_admin")
  private Boolean isAdmin;
}
