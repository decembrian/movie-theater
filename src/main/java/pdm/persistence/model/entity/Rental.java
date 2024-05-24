package pdm.persistence.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RENTAL")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory rentalInventory;

    @OneToMany(mappedBy = "rental")
    @JsonIgnore
    private Set<Payment> payments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff rentStaff;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerRent;

    public Rental() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inventory getRentalInventory() {
        return rentalInventory;
    }

    public void setRentalInventory(Inventory rentalInventory) {
        this.rentalInventory = rentalInventory;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Staff getRentStaff() {
        return rentStaff;
    }

    public void setRentStaff(Staff rentStaff) {
        this.rentStaff = rentStaff;
    }

    public Customer getCustomerRent() {
        return customerRent;
    }

    public void setCustomerRent(Customer customerRent) {
        this.customerRent = customerRent;
    }
}
