package shb.cloud.license.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("sw_license")
@ToString
public class ShbLicense {

    @Id
    private String licenseId;

    private String licenseName;
    private String vendorId;
    private String contractorId;

}
