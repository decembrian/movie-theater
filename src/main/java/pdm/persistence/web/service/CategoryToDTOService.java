package pdm.persistence.web.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pdm.persistence.model.entity.Category;
import pdm.persistence.model.entity.Film;
import pdm.persistence.model.dto.CategoryDTO;
import pdm.persistence.model.repository.CategoryRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryToDTOService{
    private final CategoryRepository repository;

    public CategoryToDTOService(CategoryRepository repository) {
        this.repository = repository;
    }

    public CategoryDTO getCategoryDTO(Long id){
        Category category = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Категория с id = " + id + " не найдена.")
                );
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategory(category.getCategory());
        Set<Long> filmIds = category.getFilms().stream()
                .map(Film::getFilmId)
                .collect(Collectors.toSet());
        dto.setFilmIds(filmIds);
        return dto;
    }
}
