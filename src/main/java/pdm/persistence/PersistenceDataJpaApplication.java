package pdm.persistence;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pdm.persistence.model.Actor;
import pdm.persistence.model.Category;
import pdm.persistence.model.Film;
import pdm.persistence.model.Language;
import pdm.persistence.model.repository.ActorRepository;
import pdm.persistence.model.repository.CategoryRepository;
import pdm.persistence.model.repository.FilmRepository;
import pdm.persistence.model.repository.LanguageRepository;

import java.time.LocalDate;

@SpringBootApplication
public class PersistenceDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistenceDataJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(LanguageRepository repo, FilmRepository fRepo, CategoryRepository cRepo, ActorRepository actorRepository){
        return (args) -> {
            Language language = new Language();
            language.setLanguage("Russian");
            repo.save(language);
            repo.save(new Language("English"));
            repo.save(new Language("Italian"));

            fRepo.save(new Film(
                    "Гоголь", "Мистические приключения писателя",
                    2016, 6.8F, LocalDate.of(2018, 10, 16), repo.findById(2L).orElse(null))
            );
            //repo.findAll().forEach(language -> System.out.println(language.getLanguage()));
            Category category = new Category();
            category.setCategory("Action");
            cRepo.save(category);
            cRepo.save(new Category("History"));
            cRepo.save(new Category("Drama"));

            Actor actor = new Actor("Silvester", "Stallone");
            actorRepository.save(actor);
            Actor actor2 = new Actor("Will", "Smith");
            actorRepository.save(actor2);

            Film film = new Film();
            film.setTitle("Неудержимые");
            film.setDescription("Безбашенная команда героев снова в сборе!");
            film.setReleaseYear(2014);
            film.setRating(7.1F);
            film.setLastUpdate(LocalDate.of(2016, 04, 22));
            film.setLanguage(language);
            film.addCategory(category);
            film.addActor(actor);
            film.addActor(actor2);
            fRepo.save(film);

            System.out.println(repo.findById(2L));
        };
    }
}
