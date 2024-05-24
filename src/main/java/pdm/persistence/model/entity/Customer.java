package pdm.persistence.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private boolean isActive;
    private Date createDate;

    @ManyToMany()
    @JoinTable(
            name = "address_customer",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Address> customerAddresses = new HashSet<>();

    @OneToMany(mappedBy = "customerRent")
    @JsonIgnore
    private Set<Rental> customersRental = new HashSet<>();

    @OneToMany(mappedBy = "customerPay")
    @JsonIgnore
    private Set<Payment> payments;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, boolean isActive, Date createDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isActive = isActive;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName(){
        return firstName + ' ' + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<Address> getAddresses() {
        return customerAddresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.customerAddresses = addresses;
    }

    public Set<Address> getCustomerAddresses() {
        return customerAddresses;
    }

    public void setCustomerAddresses(Set<Address> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }

    public Set<Rental> getCustomersRental() {
        return customersRental;
    }

    public void setCustomersRental(Set<Rental> customersRental) {
        this.customersRental = customersRental;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
