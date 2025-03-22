package vn.ute.mobile.project.constant;

import java.util.Arrays;
import java.util.List;

public class AppConstant {
  public static final Boolean ACCOUNT_IS_ADMIN = true;
  public static final Boolean ACCOUNT_IS_USER = false;

  public static final Integer ACCOUNT_STATUS_ACTIVE = 1;
  public static final Integer ACCOUNT_STATUS_PENDING = 0;

  public static final Integer USER_GENDER_MALE = 1;
  public static final Integer USER_GENDER_FEMALE = 2;
  public static final Integer USER_GENDER_OTHER = 3;

  public static final List<Integer> VALID_GENDER = Arrays.asList(USER_GENDER_MALE, USER_GENDER_FEMALE, USER_GENDER_OTHER);
}
