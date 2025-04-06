package vn.ute.mobile.project.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "db_deck")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Deck extends Auditable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name")
  private String name;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
//  @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL)
//  private List<PlashCard> plashCards;
}
