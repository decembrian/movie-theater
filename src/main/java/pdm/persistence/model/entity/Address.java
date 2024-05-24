package pdm.persistence.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long adressId;

    private String address;
    private int district;
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToMany(mappedBy = "customerAddresses",
                cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(mappedBy = "storeAddress", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Store> stores = new HashSet<>();

    @OneToMany(mappedBy = "staffAddress")
    @JsonIgnore
    private Set<Staff> staffs = new HashSet<>();

    public Address() {
    }

    public Address(String address, int district, String postalCode) {
        this.address = address;
        this.district = district;
        this.postalCode = postalCode;
    }

    public Long getAdressId() {
        return adressId;
    }

    public void setAdressId(Long adressId) {
        this.adressId = adressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
}
