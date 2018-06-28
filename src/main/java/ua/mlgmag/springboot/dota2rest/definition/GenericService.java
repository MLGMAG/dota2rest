package ua.mlgmag.springboot.dota2rest.definition;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {

    void save(T t);

    void delete(T t);

    Optional<T> findById(ID id);

    List<T> findAll();

}
