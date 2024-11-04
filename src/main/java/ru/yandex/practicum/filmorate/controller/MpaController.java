package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mpa")
public class MpaController {

    @Autowired
    private MpaService mpaService;

    @GetMapping
    public List<Mpa> getAllMpa() {
        return mpaService.getAllRating();
    }

    @GetMapping("/{id}")
    public Optional<Mpa> getMpaById(@PathVariable Long id) {
        return mpaService.findById(id);
    }
}
