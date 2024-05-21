package pdm.persistence.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdm.persistence.model.Film;

import java.util.Set;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {

    @Query(value = "select f.film_id, f.title, f.description, f.release_year, f.rating, f.last_update, f.language_id, c.category from film f\n" +
            "join film_category fc on f.film_id  = fc.film_id  \n" +
            "join category c on fc.category_id = c.category_id \n" +
            "join language l on l.lang_id = f.language_id \n" +
            "where c.category_id = :id", nativeQuery = true)
    Set<Film> findAllByCategoryId(Long id);
}
