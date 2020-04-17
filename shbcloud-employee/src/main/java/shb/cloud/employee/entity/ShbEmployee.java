package shb.cloud.employee.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("employee")
@ToString
public class ShbEmployee {

  @Id private String employeeId;
  private String employeeName;
  private String contactNumber;
}
