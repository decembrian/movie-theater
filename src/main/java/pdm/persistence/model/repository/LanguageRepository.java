package pdm.persistence.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdm.persistence.model.entity.Language;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Long> {
}
