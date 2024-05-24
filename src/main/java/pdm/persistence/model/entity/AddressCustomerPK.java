package pdm.persistence.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AddressCustomerPK implements Serializable {
    private static final long serialVersionUID = 1;

    @Column(name = "address_id")
    private Long adressId;
    @Column(name = "customer_id")
    private Long customerId;

    public AddressCustomerPK() {
    }

    public Long getAdressId() {
        return adressId;
    }

    public void setAdressId(Long adressId) {
        this.adressId = adressId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressCustomerPK that = (AddressCustomerPK) o;
        return Objects.equals(adressId, that.adressId) && Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adressId, customerId);
    }
}
