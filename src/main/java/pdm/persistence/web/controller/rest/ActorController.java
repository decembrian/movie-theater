package pdm.persistence.web.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import pdm.persistence.model.entity.Actor;
import pdm.persistence.model.entity.Film;
import pdm.persistence.model.dto.ActorDTO;
import pdm.persistence.model.repository.ActorRepository;
import pdm.persistence.model.repository.FilmRepository;
import pdm.persistence.web.service.ActorToDTOService;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1")
@Tag(name = "Актеры", description = "Операции извлечения/изменения/удаления для актеров.")
public class ActorController {

    private final ActorRepository actorRepository;
    private final FilmRepository filmRepository;
    private final ActorToDTOService actorService;

    public ActorController(ActorRepository actorRepository, FilmRepository filmRepository, ActorToDTOService actorService) {
        this.actorRepository = actorRepository;
        this.filmRepository = filmRepository;
        this.actorService = actorService;
    }

    @GetMapping("/actors")
    @Operation(
            summary = "Список актеров.",
            description = "Извлечение из БД списка актеров."
    )
    public CollectionModel<ActorDTO> getActors(){
        List<Actor> actors = (List<Actor>) actorRepository.findAll();
        Set<ActorDTO> dtos = new HashSet<>();

        for(Actor actor:  actors){
            ActorDTO dto = actorService.getActorDTO(actor.getActor_id());
            dtos.add(dto);

            dto.add(linkTo(methodOn(getClass()).getActorById(dto.getActorId())).withSelfRel());
            //TODO добавить ссылки
        }

        return CollectionModel.of(dtos);
    }

    @GetMapping("/actors/{id}")
    @Operation(
            summary = "Найти актера по ID",
            description = "Извлечение из БД актера по его ID."
    )
    public ResponseEntity<ActorDTO> getActorById(@PathVariable("id") Long id){
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Актер с Id = " + id + " не найден."));
        ActorDTO dto = actorService.getActorDTO(id);

        dto.add(linkTo(methodOn(getClass()).getActors()).withRel("actors"));
        dto.add(linkTo(methodOn(getClass()).getActorById(dto.getActorId())).withSelfRel());
        //TODO ссылка на /films/{id}/actors/{id}

        return ResponseEntity.ok(dto);
    }


    @PostMapping("/actors")
    @Operation(
            summary = "Добавить актера",
            description = "Добавить нового актера в БД."
    )
    public ResponseEntity<EntityModel<Actor>> addActor(@RequestBody @Parameter(description = "Сущность Actor") Actor act){
        Actor actor = new Actor();
        actor.setFirstName(act.getFirstName());
        actor.setLastName(act.getLastName());
        actor.setFilms(act.getFilms());

        Actor savedActor = actorRepository.save(actor);

        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                                .path("/{id}")
                                .buildAndExpand(savedActor.getActor_id())
                                .toUri();

        EntityModel<Actor> model = EntityModel.of(savedActor);

        model.add(linkTo(methodOn(getClass()).getActorById(savedActor.getActor_id())).withRel("get by id"));
        model.add(linkTo(methodOn(getClass()).getActors()).withRel("actors"));

        return ResponseEntity.created(uri).body(model);
    }


    @PostMapping("/films/{filmId}/actors")
    @Operation(
            summary = "Добавить актера к фильму.",
            description = "Добавление существующего актера к фильму."
    )
    public ResponseEntity<ActorDTO> addActorToFilm(@RequestBody @Parameter(description = "Сущность Actor") ActorDTO act,
                                                 @PathVariable("filmId") @Parameter(description = "ID фильма") Long id){

        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Фильм с ID = " + id + " не найден."));

        Actor actor = actorRepository.findById(act.getActorId())
                .orElseThrow(() -> new EntityNotFoundException("Актер с ID = " + id + " не найден."));

        film.addActor(actor);
        filmRepository.save(film);

        act.add(linkTo(methodOn(getClass()).getActors()).withRel("actors"));
        act.add(linkTo(methodOn(getClass()).getActorById(act.getActorId())).withRel("get by id"));

        return ResponseEntity.ok(act);
    }

    @DeleteMapping("/actors/{id}")
    @Operation(
            summary = "Удаление актера.",
            description = "Удаление актера из БД по его ID."
    )
    public ResponseEntity<String> removeActor(@PathVariable("id")@Parameter(description = "ID актера") Long id){
        if(actorRepository.existsById(id)){
            actorRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Актер с id = " + id + " был удален.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Актер с id = " + id + " не найден.");
    }
}
