package pdm.persistence.web.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdm.persistence.model.Actor;
import pdm.persistence.model.repository.ActorRepository;
import pdm.persistence.model.repository.FilmRepository;

@RestController
@RequestMapping("/v1")
public class ActorController {

    private final ActorRepository actorRepository;
    private final FilmRepository filmRepository;

    public ActorController(ActorRepository actorRepository, FilmRepository filmRepository) {
        this.actorRepository = actorRepository;
        this.filmRepository = filmRepository;
    }

    @GetMapping("/actors")
    public Iterable<Actor> getActors(){
        return actorRepository.findAll();
    }

    @PostMapping("/actors")
    public ResponseEntity<String> addCategory(@RequestBody Actor act){
        actorRepository.save(act);
        return ResponseEntity.ok("Актер сохранен.");
    }

    @PostMapping("/films/{filmId}/actors")
    public ResponseEntity<String> addCategoryToFilm(@RequestBody Actor act, @PathVariable("filmId") Long id){
        Actor actor = filmRepository.findById(id)
                .map(film -> {
                    long actor_id = act.getActor_id();

                    if(actor_id != 0){
                        Actor _act = actorRepository.findById(actor_id)
                                .orElseThrow(() -> new EntityNotFoundException("Актерс c id = " + actor_id + "  не найден"));
                        film.addActor(_act);
                        filmRepository.save(film);
                        return _act;
                    }

                    film.addActor(act);
                    return actorRepository.save(act);
                }).orElseThrow(() -> new EntityNotFoundException("Фильм с ID = " + id + " не найден."));

        return ResponseEntity.ok("Актер успешно добавлен к фильму.");
    }

    @DeleteMapping("/actors/{id}")
    public ResponseEntity<String> removeActor(@PathVariable("id") Long id){
        if(actorRepository.existsById(id)){
            actorRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Актер с id = " + id + " был удален.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Актер с id = " + id + " не найден.");
    }
}
