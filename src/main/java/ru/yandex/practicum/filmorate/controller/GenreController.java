package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dal.GenresRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenresRepository genresRepository;

    @GetMapping
    public List<String> getAllGenres() {
        return genresRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<String> getGenreById(@PathVariable Long id) {
        return genresRepository.findById(id);
    }
}
