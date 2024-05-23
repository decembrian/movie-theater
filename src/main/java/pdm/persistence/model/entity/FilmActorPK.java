package pdm.persistence.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

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
}
