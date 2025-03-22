package vn.ute.mobile.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@MappedSuperclass
public abstract class Auditable {
  @Column(name = "created_date")
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date createdDate;

  @Column(name = "modified_date")
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date modifiedDate;

  private Integer status;
}
