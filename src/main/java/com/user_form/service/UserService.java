package com.user_form.service;

import com.user_form.model.User;
import com.user_form.repository.UserRepository;
import com.user_form.validator.user.UserValidator;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserRepository<User, Long> {

    private static final String SELECT_ALL = "SELECT user FROM User user";

    private EntityManagerFactory entityManagerFactory;
    private UserValidator userValidator = new UserValidator();

    @Autowired
    public UserService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<User> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<User> usersList = entityManager.createQuery(SELECT_ALL).getResultList();
        entityManager.close();
        if (usersList.isEmpty()) {
            throw new UserNotFoundException("Not found any users in the database.");
        }
        return usersList;
    }

    @Override
    public Optional<User> findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, id);
        entityManager.close();
        if (user == null) {
            throw new UserNotFoundException("User with id: " + id + " not found in the database.");
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> save(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            userValidator.validate(user);
            entityManager.getTransaction().begin();
            User userToSave = entityManager.merge(user);
            entityManager.getTransaction().commit();
            entityManager.close();
            return Optional.of(userToSave);
        } catch (PersistenceException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public Optional<User> update(User user, Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            if (entityManager.find(User.class, id) == null) {
                throw new UserNotFoundException("User with id: " + id + " not found in the database.");
            }
            userValidator.validate(user);
            user.setId(id);
            entityManager.getTransaction().begin();
            User userToUpdate = entityManager.merge(user);
            entityManager.getTransaction().commit();
            entityManager.close();
            return Optional.of(userToUpdate);
        } catch (PersistenceException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void deleteById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new UserNotFoundException("User with id: " + id + " not found in the database.");
        }
        entityManager.remove(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
