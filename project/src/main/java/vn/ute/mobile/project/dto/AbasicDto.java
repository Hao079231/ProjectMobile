package vn.ute.mobile.project.dto;

import jakarta.persistence.MappedSuperclass;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class AbasicDto {
  private Date createdDate;
  private Date modifiedDate;
  private Integer status;
}
