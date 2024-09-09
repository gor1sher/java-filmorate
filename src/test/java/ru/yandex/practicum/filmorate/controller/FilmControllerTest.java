package ru.yandex.practicum.filmorate.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmControllerTest {

    @Autowired
    FilmController filmController;

    @Test
    public void testFilmCreate() {
        Film film = new Film();

        film.setName("run");
        film.setDescription("run on the forest");
        film.setDuration(Duration.ofMinutes(123));
        film.setReleaseDate(LocalDate.now().minusDays(2));

        Film film2 = filmController.create(film);

        assertEquals(film, film2);
    }

    @Test
    public void testFilmCreate_EmptyMovieTitle() {
        Film film = new Film();

        film.setName("");
        film.setDescription("run on the forest");
        film.setDuration(Duration.ofMinutes(123));
        film.setReleaseDate(LocalDate.now().minusDays(2));

        assertThrows(ConditionsNotMetException.class, () -> {
            filmController.create(film);
        }, "Название фильма не может быть пустым");
    }

    @Test
    public void testFilmCreate_EmptyMovieDescription() {
        Film film = new Film();

        film.setName("run");
        film.setDescription("run on the forest");
        film.setDuration(Duration.ofMinutes(123));
        film.setReleaseDate(LocalDate.of(1894, 12, 28));

        assertThrows(ConditionsNotMetException.class, () -> {
            filmController.create(film);
        }, "Не выполнены условия для регистрации фильма в приложении");
    }

    @Test
    public void testFindAll_ReturnListOfMovies() {
        Film film1 = new Film();

        film1.setName("run");
        film1.setDescription("run on the forest");
        film1.setDuration(Duration.ofMinutes(123));
        film1.setReleaseDate(LocalDate.now().minusDays(2));

        filmController.create(film1);

        Film film2 = new Film();

        film2.setName("out");
        film2.setDescription("run on the forest");
        film2.setDuration(Duration.ofMinutes(123));
        film2.setReleaseDate(LocalDate.now().minusDays(4));

        filmController.create(film2);

        Collection<Film> list = filmController.findAll();

        assertTrue(list.contains(film1));
        assertTrue(list.contains(film2));
    }

    @Test
    public void testUpdateFilm() {
        Film film1 = new Film();

        film1.setName("run");
        film1.setDescription("run on the forest");
        film1.setDuration(Duration.ofMinutes(123));
        film1.setReleaseDate(LocalDate.now().minusDays(2));

        filmController.create(film1);

        Film film2 = new Film();

        film2.setId(1L);
        film2.setName("out");
        film2.setDescription("on the forest");
        film2.setDuration(Duration.ofMinutes(123));
        film2.setReleaseDate(LocalDate.now().minusDays(4));

        assertEquals("on the forest", filmController.update(film2).getDescription());
    }
}
