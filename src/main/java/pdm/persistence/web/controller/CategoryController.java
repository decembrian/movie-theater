package pdm.persistence.web.controller;

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
import pdm.persistence.model.entity.Category;
import pdm.persistence.model.dto.CategoryDTO;
import pdm.persistence.model.repository.CategoryRepository;
import pdm.persistence.model.repository.FilmRepository;
import pdm.persistence.web.service.CategoryToDTOService;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1")
@Tag(name = "Жанры", description = "Операции извлечения/изменения/удаления жанров фильма.")
public class CategoryController {

    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryToDTOService toDTOService;

    public CategoryController(FilmRepository filmRepository, CategoryRepository categoryRepository, CategoryToDTOService toDTOService) {
        this.filmRepository = filmRepository;
        this.categoryRepository = categoryRepository;
        this.toDTOService = toDTOService;
    }

    @GetMapping("/categories")
    @Operation(
            summary = "Список жанров.",
            description = "Извлечение из БД списка категорий."
    )
    public CollectionModel<CategoryDTO> getCategories(){
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        Set<CategoryDTO> dtos = new HashSet<>();

        for(Category ctg: categories){
            CategoryDTO dto = toDTOService.getCategoryDTO(ctg.getCategoryId());

            dtos.add(dto);

            dto.add(linkTo(methodOn(getClass()).getCategoryById(ctg.getCategoryId())).withSelfRel());
            dto.add(linkTo(methodOn(getClass()).removeCategory(ctg.getCategoryId())).withRel("delete"));
            dto.add(linkTo(methodOn(FilmController.class).getFilmsByCategoryId(ctg.getCategoryId())).withRel("films"));
        }
        return CollectionModel.of(dtos);
    }

    @GetMapping("/categories/{id}")
    @Operation(
            summary = "Найти жанр по его ID.",
            description = "Извлечение из БД жанра по его ID."
    )
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id")@Parameter(description = "ID категории")  Long id) {
        CategoryDTO dto = toDTOService.getCategoryDTO(id);

        dto.add(linkTo(methodOn(getClass()).getCategories()).withRel("categories"));
        dto.add(linkTo(methodOn(getClass()).getCategoryById(id)).withSelfRel());
        dto.add(linkTo(methodOn(FilmController.class).getFilmsByCategoryId(dto.getCategoryId())).withRel("films"));

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/categories")
    @Operation(
            summary = "Добавить жанр.",
            description = "Добавление в БД категории."
    )
    public ResponseEntity<EntityModel<Category>> addCategory(@RequestBody @Parameter(description = "Объект CategoryDTO") CategoryDTO dto){
        Category category = new Category();
        category.setCategory(dto.getCategory());


        Category savedCategory = categoryRepository.save(category);

        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(savedCategory.getCategoryId())
                        .toUri();

        EntityModel<Category> model = EntityModel.of(savedCategory);

        model.add(linkTo(methodOn(getClass()).getCategoryById(savedCategory.getCategoryId())).withRel("test"));
        model.add(linkTo(methodOn(getClass()).getCategories()).withRel("categories"));

        return ResponseEntity.created(uri).body(model);
    }


    @PostMapping("/films/{filmId}/categories")
    @Operation(
            summary = "Добавление жанра к фильму.",
            description = "Добавление жанра к фильму по его ID."
    )
    public ResponseEntity<String> addCategoryToFilm(@RequestBody @Parameter(description = "Объект CategoryDTO") CategoryDTO dto, @PathVariable("filmId")@Parameter(description = "Фильм ID") Long id){
        Category ctg = filmRepository.findById(id)
                .map(film -> {
                    Category category = new Category();
                    category.setCategory(dto.getCategory());
                    long category_id = dto.getCategoryId();

                    if(category_id != 0){
                        Category _ctg = categoryRepository.findById(category_id)
                                .orElseThrow(() -> new EntityNotFoundException("Категория c id = " + category_id + "  не найдена"));
                        film.addCategory(_ctg);
                        filmRepository.save(film);
                        return _ctg;
                    }

                    film.addCategory(category);

                    dto.add(linkTo(methodOn(getClass()).getCategories()).withRel("categories"));

                    return categoryRepository.save(category);
                }).orElseThrow(() -> new EntityNotFoundException("Фильм с ID = " + id + " не найден."));

        return ResponseEntity.ok("Категория успешно добавлена к фильму.");
    }

    @DeleteMapping("/categories/{id}")
    @Operation(
            summary = "Удаление категории.",
            description = "Удаление из БД категории по ее ID."
    )
    public ResponseEntity<String> removeCategory(@PathVariable("id")@Parameter(description = "ID категории") Long id){
        if(categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK).body("Категория с id = " + id + " была удалена.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Категория с id = " + id + " не найдена.");
    }
}
