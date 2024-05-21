package pdm.persistence.model;

import jakarta.persistence.*;

@Entity
@Table(name = "FILM_ACTOR")
public class FilmActor {
    @EmbeddedId
    private FilmActorPK id = new FilmActorPK();

    @ManyToOne
    @JoinColumn(name = "film_id", insertable = false, updatable = false)
    private Film film;
    @ManyToOne
    @JoinColumn(name = "actor_id", insertable = false, updatable = false)
    private Actor actor;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
