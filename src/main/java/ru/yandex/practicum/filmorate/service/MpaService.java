package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Service
public class MpaService {

    @Autowired
    private MpaRepository mpaRepository;

    public List<Mpa> getAllRating() {
        return mpaRepository.findAll();
    }

    public Optional<Mpa> findById(Long id) {
        return mpaRepository.findById(id);
    }
}
