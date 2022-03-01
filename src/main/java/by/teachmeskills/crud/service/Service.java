package by.teachmeskills.crud.service;

import by.teachmeskills.crud.model.Entity;

import java.util.List;

public interface Service<K, T extends Entity> {
    int FIRST_PREPARED_STATEMENT_PARAMETER = 1;
    int SECOND_PREPARED_STATEMENT_PARAMETER = 2;
    int THIRD_PREPARED_STATEMENT_PARAMETER = 3;
    int SQL_STATEMENT_RETURN_NOTHING = 0;

    boolean create(T entity);

    List<T> findAll();

    T findById(K id);

    T update(T entity);

    boolean deleteAll();

    boolean deleteById(K id);
}