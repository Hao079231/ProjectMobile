package vn.ute.mobile.project.constant;

import java.util.Arrays;
import java.util.List;

public class AppConstant {
  public static final Integer ACCOUNT_STATUS_ACTIVE = 1;
  public static final Integer ACCOUNT_STATUS_PENDING = 0;

  public static final Integer USER_GENDER_MALE = 1;
  public static final Integer USER_GENDER_FEMALE = 2;
  public static final Integer USER_GENDER_OTHER = 3;

  public static final Integer APP_STATUS_ACTIVE = 1;

  public static final List<Integer> VALID_GENDER = Arrays.asList(USER_GENDER_MALE, USER_GENDER_FEMALE, USER_GENDER_OTHER);
  public static final List<String> VALID_WORD_TYPES = Arrays.asList(
      "Noun", "Verb", "Adjective", "Adverb", "Pronoun",
      "Determiner", "Preposition", "Conjunction", "Interjection");
}
