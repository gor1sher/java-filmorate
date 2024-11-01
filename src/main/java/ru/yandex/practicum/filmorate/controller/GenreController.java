package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private FilmService filmService;

    @GetMapping
    public List<Genre> getAllGenres() {
        return filmService.getGenres();
    }

//    @GetMapping("/{id}")
//    public Optional<Genre> getGenreById(@PathVariable Long id) {
//        return genreRepository.findById(id);
//    }
}
