package pdm.persistence.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdm.persistence.model.Film;
import pdm.persistence.model.Language;
import pdm.persistence.model.repository.FilmRepository;
import pdm.persistence.model.repository.LanguageRepository;

import java.util.Set;

@RestController
@RequestMapping(path = "/v1/films")
@Tag(name = "Фильмы", description = "Операции извлечения/изменения/удаления фильмов.")
public class FilmController {

    private final FilmRepository filmRepository;
    private final LanguageRepository langRepository;

    public FilmController(FilmRepository filmRepository, LanguageRepository langRepository) {
        this.filmRepository = filmRepository;
        this.langRepository = langRepository;
    }

    @Operation(
            summary = "Найти фильм по его ID.",
            description = "Извлечение из БД фильма по его ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable("id")@Parameter(description = "Фильм ID") Long id){
        return filmRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "Список фильмов.",
            description = "Извлечение списка всех фильмов из БД"
    )
    @GetMapping
    public Iterable<Film> getFilms(){
        return filmRepository.findAll();
    }

    //TODO ИЗМЕНИТЬ С FILM НА FILM_DTO
    @GetMapping("/categories/{id}")
    @Operation(
            summary = "Список фильмов по категории.",
            description = "Получить список фильмов с одной category_id"
    )
    public ResponseEntity<Set<Film>> getFilmsByCategoryId(@PathVariable("id")@Parameter(description = "ID категории")  Long id){
        Set<Film> films = filmRepository.findAllByCategoryId(id);

        return ResponseEntity.ok().body(films);
    }

    //TODO ИЗМЕНИТЬ ВСЕ RESPONCE_ENTITY<STRING> НА НУЖНЫЕ СУЩНОСТИ
    //TODO УДАЛЕНИЕ ЖАНРА ИЗ ФИЛЬМА
    //TODO УДАЛЕНИЕ АКТЕРА ИЗ ФИЛЬМА
    @Operation(
            summary = "Добавить фильм.",
            description = "Добавление фильма в БД"
    )
    @PostMapping
    public ResponseEntity<String> addFilm(@RequestBody @Parameter(description = "Объект Film") Film film){
        Language language = langRepository.findById(film.getLanguage().getLang_id())
                        .orElseThrow(() -> new EntityNotFoundException("Язык с id = " +
                                film.getLanguage().getLang_id() + " не найден."));
        film.setLanguage(language);

        filmRepository.save(film);

        return ResponseEntity.ok("Фильм успешно добавлен.");
    }

    @Operation(
            summary = "Удалить фильм.",
            description = "Удаление фильма из БД по его ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable("id")@Parameter(description = "Фильм ID") Long id){
        boolean exitst = filmRepository.existsById(id);
        if(exitst){
            filmRepository.deleteById(id);
            return ResponseEntity.ok("Фильм с ID = " + id + " удален.");
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Фильм с ID = " + id + " не найден");
    }
    //TODO добавить возможность получения фильмов по категории/актеру (/v1/films/{id}/categories/{id}) / (/v1/films/{id}/actors/{id})
}

