package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.GenresRepository;

@Service
public class GenresService {

    @Autowired
    private GenresRepository genresRepository;

    public void getAllGenres() {
        genresRepository.findAll();
    }

    public void findById(Long id) {
        genresRepository.findById(id);
    }
}
