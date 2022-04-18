package repository.interfaces;

import domain.Admin;

public interface RepositoryAdmin extends Repository<Integer, Admin> {
    Admin findOneByUsername(String string);
}
