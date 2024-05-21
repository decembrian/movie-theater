package pdm.persistence.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdm.persistence.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
