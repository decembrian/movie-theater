package pdm.persistence.model.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pdm.persistence.model.entity.Film;

import java.util.Set;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {

    @Query(value = "select f.film_id, f.title, f.description, f.release_year, f.rating, f.last_update, f.language_id, c.category from film f\n" +
            "join film_category fc on f.film_id  = fc.film_id  \n" +
            "join category c on fc.category_id = c.category_id \n" +
            "join language l on l.lang_id = f.language_id \n" +
            "where c.category_id = :id", nativeQuery = true)
    Set<Film> findAllByCategoryId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from film_actor fa where fa.film_id = :film_id and fa.actor_id = :actor_id", nativeQuery = true)
    void deleteActorByFilmIdAndActorId(@Param("film_id") Long filmId, @Param("actor_id") Long actorId);

    @Modifying
    @Transactional
    @Query(value = "delete from film_category fa where fa.film_id = :film_id and fa.category_id = :category_id", nativeQuery = true)
    void deleteCategoryByFilmIdAndCategoryId(@Param("film_id") Long filmId, @Param("category_id") Long categoryId);
}
