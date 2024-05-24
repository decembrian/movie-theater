package pdm.persistence.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerPay;

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public Customer getCustomerPay() {
        return customerPay;
    }

    public void setCustomerPay(Customer customerPay) {
        this.customerPay = customerPay;
    }
}
