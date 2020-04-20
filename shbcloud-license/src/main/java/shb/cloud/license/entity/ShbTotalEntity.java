package shb.cloud.license.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShbTotalEntity {

    private ShbLicense shbLicense;
    private String contractorName;
    private String contractorContactNumber;

    public ShbTotalEntity(ShbLicense shbLicense) {
        this.shbLicense = shbLicense;
    }
}
