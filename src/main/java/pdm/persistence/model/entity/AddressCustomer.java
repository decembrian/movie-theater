package pdm.persistence.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ADDRESS_CUSTOMER")
public class AddressCustomer {
    @EmbeddedId
    private AddressCustomerPK id = new AddressCustomerPK();

    @ManyToOne
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    private Address address;
    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
