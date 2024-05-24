package pdm.persistence.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FilmActorPK implements Serializable {
    private static final long serialVersionUID = 1;

    @Column(name = "film_id")
    private Long filmId;
    @Column(name = "actor_id")
    private Long actorId;

    public FilmActorPK() {
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmActorPK that = (FilmActorPK) o;
        return Objects.equals(filmId, that.filmId) && Objects.equals(actorId, that.actorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, actorId);
    }
}
