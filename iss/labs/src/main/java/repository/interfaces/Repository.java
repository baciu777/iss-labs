package repository.interfaces;


/**
 * Repository class
 * @param <ID> generic
 * @param <E> entity
 */
public interface Repository<ID, E > {

    void add(E elem);


    Iterable<E> findAll();

}