package pdm.persistence.web.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdm.persistence.model.entity.Actor;
import pdm.persistence.model.entity.Category;
import pdm.persistence.model.entity.Film;
import pdm.persistence.model.entity.Language;
import pdm.persistence.model.dto.ActorDTO;
import pdm.persistence.model.dto.CategoryDTO;
import pdm.persistence.model.repository.ActorRepository;
import pdm.persistence.model.repository.CategoryRepository;
import pdm.persistence.model.repository.FilmRepository;
import pdm.persistence.model.repository.LanguageRepository;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/v1/films")
@Tag(name = "Фильмы", description = "Операции извлечения/изменения/удаления фильмов.")
public class FilmController {

    private final FilmRepository filmRepository;
    private final LanguageRepository langRepository;
    private final ActorRepository actorRepository;
    private final CategoryRepository categoryRepository;

    public FilmController(FilmRepository filmRepository, LanguageRepository langRepository,
                          ActorRepository actorRepository, CategoryRepository categoryRepository) {
        this.filmRepository = filmRepository;
        this.langRepository = langRepository;
        this.actorRepository = actorRepository;
        this.categoryRepository = categoryRepository;
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


    @GetMapping("/categories/{id}")
    @Operation(
            summary = "Список фильмов по категории.",
            description = "Получить список фильмов с одной category_id"
    )
    public ResponseEntity<Set<Film>> getFilmsByCategoryId(@PathVariable("id")@Parameter(description = "ID категории")  Long id){
        Set<Film> films = filmRepository.findAllByCategoryId(id);

        return ResponseEntity.ok().body(films);
    }

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

    @DeleteMapping("/{filmId}/actors/{actorId}")
    @Operation(
            summary = "Удалить актера из фильма.",
            description = "Удаление актера из фильма по filmId и actorId."
    )
    public ResponseEntity<ActorDTO> removeActorFromFilm(@PathVariable("filmId") @Parameter(description = "Фильм ID") Long filmId,
                                                     @PathVariable("actorId") @Parameter(description = "ID актера") Long actorId){
        if(!filmRepository.existsById(filmId))
            throw new EntityNotFoundException("Фильм с ID =" + filmId + " не найден.");
        else {
                Actor actor = actorRepository.findById(actorId)
                                .orElseThrow(() -> new EntityNotFoundException("Актер с ID =" + filmId + " не найден."));
                ActorDTO dto = new ActorDTO();
                dto.setActorId(actor.getActor_id());
                dto.setFirstName(actor.getFirstName());
                dto.setLastName(actor.getLastName());
                dto.setFilmIDs(actor.getFilms().stream()
                        .map(Film::getFilmId)
                        .collect(Collectors.toSet()));

                filmRepository.deleteActorByFilmIdAndActorId(filmId, actorId);

                return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
    }

    @DeleteMapping("/{filmId}/categories/{categoryId}")
    @Operation(
            summary = "Удалить жанр из фильма.",
            description = "Удаление жанра из фильма по filmId и categoryId."
    )
    public ResponseEntity<CategoryDTO> removeCategoryFromFilm(@PathVariable("filmId") @Parameter(description = "Фильм ID") Long filmId,
                                                              @PathVariable("categoryId") @Parameter(description = "ID жанра")  Long categoryId){
        if(!filmRepository.existsById(filmId))
            throw new EntityNotFoundException("Фильм с ID =" + filmId + " не найден.");
        else {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Жанр с ID =" + filmId + " не найден."));
            filmRepository.deleteCategoryByFilmIdAndCategoryId(filmId, categoryId);

            CategoryDTO dto = new CategoryDTO();
            dto.setCategoryId(category.getCategoryId());
            dto.setCategory(category.getCategory());
            dto.setFilmIds(category.getFilms().stream()
                    .map(Film::getFilmId)
                    .collect(Collectors.toSet()));

            dto.add(linkTo(methodOn(CategoryController.class).getCategories()).withRel("categories"));
            dto.add(linkTo(methodOn(CategoryController.class).getCategoryById(dto.getCategoryId())).withSelfRel());
            dto.add(linkTo(methodOn(FilmController.class).getFilmsByCategoryId(dto.getCategoryId())).withRel("films"));

            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
    }
}

