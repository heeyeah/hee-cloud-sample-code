package shb.cloud.employee

import lombok.Getter
import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Getter
@Setter
@Table("employee")
class ShbEmployee {

    @Id
    var employeeId: String = "sampleId";
    var employeeName: String = "sampleName";
    var contactNumber: String = "010-0000-0000";

}