package pdm.persistence.web.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pdm.persistence.model.entity.Actor;
import pdm.persistence.model.entity.Film;
import pdm.persistence.model.dto.ActorDTO;
import pdm.persistence.model.repository.ActorRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActorToDTOService {
    private final ActorRepository actorRepository;

    public ActorToDTOService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public ActorDTO getActorDTO(Long id){
        Actor actor = actorRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Актера с ID = " + " не найден.")
                );
        ActorDTO dto = new ActorDTO();
        dto.setActorId(actor.getActor_id());
        dto.setFirstName(actor.getFirstName());
        dto.setLastName(actor.getLastName());
        Set<Long> ids = actor.getFilms().stream()
                .map(Film::getFilmId)
                .collect(Collectors.toSet());
        dto.setFilmIDs(ids);
        return dto;
    }
}
