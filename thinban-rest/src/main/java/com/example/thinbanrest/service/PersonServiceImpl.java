package com.example.thinbanrest.service;


import com.example.thinbanrest.domain.Person;
import com.example.thinbanrest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mbart on 28.02.2016.
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> loadAll() {
        return personRepository.findAll();
    }
}
