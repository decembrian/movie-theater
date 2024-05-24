package pdm.persistence.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    @Column(nullable = false, unique = true)
    private String category;

    @ManyToMany(mappedBy = "categories",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<Film> films = new HashSet<>();

    public Category() {
    }

    public Category(String category) {
        this.category = category;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Film> getFilms() {
        return new HashSet<>(films);
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    public void addFilm(Film film) {
        this.films.add(film);
        film.getCategories().add(this);
    }

    public void removeFilm(Film film) {
        this.films.remove(film);
        film.getCategories().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category1 = (Category) o;
        return Objects.equals(categoryId, category1.categoryId) && Objects.equals(category, category1.category) && Objects.equals(films, category1.films);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, category, films);
    }
}
