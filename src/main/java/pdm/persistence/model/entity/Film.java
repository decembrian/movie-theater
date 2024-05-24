package pdm.persistence.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FILM",
       indexes = {@Index(name = "film_title_idx", columnList = "title", unique = true)})
public class Film implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Long filmId;
    private String title;
    private String description;

    @Column(nullable = false)
    private int releaseYear;
    private Float rating;
    private LocalDate lastUpdate;

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "lang_id")
    private Language language;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    @OneToMany(mappedBy = "scheduleFilm")
    @JsonIgnore
    private Set<Inventory> filmInventories = new HashSet<>();

    public Film(){};

    public Film(String title, String description, int releaseYear, Float rating, LocalDate lastUpdate, Language language) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.lastUpdate = lastUpdate;
        this.language = language;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Inventory> getFilmInventories() {
        return filmInventories;
    }

    public void setFilmInventories(Set<Inventory> filmInventories) {
        this.filmInventories = filmInventories;
    }

    public void addCategory(Category category){
        categories.add(category);
        category.getFilms().add(this);
    }


    //TODO удалить из репозитория кастомный скл
    public void removeCategory(Category category){
        this.categories.remove(category);
        category.getFilms().remove(this);
    }

    public void addActor(Actor actor){
        actors.add(actor);
        actor.getFilms().add(this);
    }

    //TODO удалить из репозитория кастомный скл.
    //TODO использовать эти два метода
    public void removeActor(Actor actor){
        this.actors.remove(actor);
        actor.getFilms().remove(this);
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                ", lastUpdate=" + lastUpdate +
                ", language=" + language +
                ", categories=" + categories +
                '}';
    }
}
