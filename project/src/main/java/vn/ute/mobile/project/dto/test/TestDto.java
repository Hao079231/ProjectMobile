package vn.ute.mobile.project.dto.test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestDto {
  private String id;
  private String question;
  private String answerA;
  private String answerB;
  private String answerC;
  private String answerD;
  private String correctAnswer;
}
