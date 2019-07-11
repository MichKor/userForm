package com.user_form.repository;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin
public interface UserRepository<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    Optional<T> save(T t);

    Optional<T> update(T t, ID id);

    void deleteById(ID id);
}
