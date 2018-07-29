package ua.mlgmag.springboot.dota2rest.definition;

import java.util.List;

public interface GenericService<T, ID> {

    void save(T t);

    void delete(T t);

    T findById(ID id);

    List<T> findAll();

}
