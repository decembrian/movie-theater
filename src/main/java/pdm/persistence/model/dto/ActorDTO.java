package pdm.persistence.model.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.Set;

public class ActorDTO extends RepresentationModel<ActorDTO> {
    private Long actorId;
    private String firstName;
    private String lastName;

    private Set<Long> filmIDs;

    public ActorDTO() {
    }

    public ActorDTO(Long actorId, String firstName, String lastName) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public Set<Long> getFilmIDs() {
        return filmIDs;
    }

    public void setFilmIDs(Set<Long> filmIDs) {
        this.filmIDs = filmIDs;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName(){
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActorDTO actorDTO = (ActorDTO) o;
        return Objects.equals(actorId, actorDTO.actorId) && Objects.equals(firstName, actorDTO.firstName) && Objects.equals(lastName, actorDTO.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), actorId, firstName, lastName);
    }
}
