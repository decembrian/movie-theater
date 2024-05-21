package pdm.persistence.model;

import jakarta.persistence.*;

@Entity
@Table(name = "FILM_CATEGORY")
public class FilmCategory {

    @EmbeddedId
    private FilmCategoryPK id = new FilmCategoryPK();

    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "film_id")
    private Film film;
    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
