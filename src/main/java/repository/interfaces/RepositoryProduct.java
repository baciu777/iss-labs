package repository.interfaces;

import domain.Agent;
import domain.Product;

public interface RepositoryProduct extends Repository<Integer, Product> {
    void delete(Product elem);
    void update(Product elem,Integer id);
    void order(Product elem,int quantity,Integer id);

}
