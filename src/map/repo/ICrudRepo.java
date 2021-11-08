package map.repo;

import map.NullValueException;

import java.util.List;

public interface ICrudRepo<T> {
    T create(T obj)throws NullValueException;

    List<T> getAll();

    T update(T obj) throws NullValueException, NullValueException;

    T delete(Long id) throws NullValueException;

}
