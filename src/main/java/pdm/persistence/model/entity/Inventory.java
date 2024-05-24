package pdm.persistence.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "INVENTORY")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film scheduleFilm;

    @OneToMany(mappedBy = "rentalInventory")
    @JsonIgnore
    private Set<Rental> rentalSet = new HashSet<>();


    public Inventory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Film getScheduleFilm() {
        return scheduleFilm;
    }

    public void setScheduleFilm(Film scheduleFilm) {
        this.scheduleFilm = scheduleFilm;
    }

    public Set<Rental> getRentalSet() {
        return rentalSet;
    }

    public void setRentalSet(Set<Rental> rentalSet) {
        this.rentalSet = rentalSet;
    }
}
