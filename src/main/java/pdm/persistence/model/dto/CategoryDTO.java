package pdm.persistence.model.dto;

import java.util.Objects;
import java.util.Set;
import org.springframework.hateoas.RepresentationModel;

public class CategoryDTO extends RepresentationModel<CategoryDTO>{
    private Long categoryId;
    private String category;
    private Set<Long> filmIds;

    public CategoryDTO(){
    }

    public CategoryDTO(Long categoryId, String category, Set<Long> filmIds) {
        this.categoryId = categoryId;
        this.category = category;
        this.filmIds = filmIds;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Long> getFilmIds() {
        return filmIds;
    }

    public void setFilmIds(Set<Long> filmIds) {
        this.filmIds = filmIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return Objects.equals(categoryId, that.categoryId) && Objects.equals(category, that.category) && Objects.equals(filmIds, that.filmIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, category, filmIds);
    }
}
