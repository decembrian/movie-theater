package pdm.persistence.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdm.persistence.model.entity.Actor;

@Repository
public interface ActorRepository extends CrudRepository<Actor, Long> {
}
