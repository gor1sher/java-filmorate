package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class MpaController {

    @Autowired
    private FilmService filmService;

    @GetMapping
    public List<MpaRating> getAllMpa() {
        return filmService.getMpaRatings();
    }

//    @GetMapping("/{id}")
//    public Optional<Mpa> getMpaById(@PathVariable Long id) {
//        return mpaRepository.findById(id);
//    }
}
