package pdm.persistence.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "LANGUAGE")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lang_id;
    @Column(nullable = false, unique = true)
    private String language;

    @OneToMany(mappedBy = "language",
            fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Film> films = new HashSet<>();

    public Language(){}

    public Language(String language){
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<Film> getFilms() {
        return Collections.unmodifiableSet(films);
    }

    public void addFilm(Film film){
        films.add(film);
        film.setLanguage(this);
    }

    public void removeFilm(Film film){
        films.remove(film);
        film.setLanguage(null);
    }

    public Long getLang_id() {
        return lang_id;
    }
}
