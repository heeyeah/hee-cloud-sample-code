package shb.cloud.license.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShbTotalEntity {

    private ShbLicense shbLicense;
    private String contractorName;
    private String contractorContactNumber;

    public ShbTotalEntity(ShbLicense shbLicense) {
        this.shbLicense = shbLicense;
    }
}
