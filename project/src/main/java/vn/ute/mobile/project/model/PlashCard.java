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
@Table(name = "db_plash_card")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlashCard extends Auditable{
  @Id
  @GenericGenerator(name = "idGenerator", strategy = "vn.ute.mobile.project.service.id.CustomIdGenerator")
  @GeneratedValue(generator = "idGenerator")
  private Long id;
  @Column(name = "english_word")
  private String englishWord;
  @Column(name = "vietnamese_meaning")
  private String vietnameseMeaning;
  @Column(name = "type")
  private String type;
  @Column(name = "example")
  private String example;
  @Column(name = "image")
  private String image;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToOne
  @JoinColumn(name = "deck_id")
  private Deck deck;
}
