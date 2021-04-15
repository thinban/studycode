package com.example.thinbanrest.service;

import com.example.thinbanrest.domain.Person;

import java.util.List;

/**
 * Created by mbart on 28.02.2016.
 */
public interface PersonService {
    List<Person> loadAll();
}