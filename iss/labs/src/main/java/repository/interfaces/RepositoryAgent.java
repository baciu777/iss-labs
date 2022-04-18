package repository.interfaces;

import domain.Agent;

public interface RepositoryAgent extends Repository<Integer, Agent> {
    Agent findOneByUsername(String string);
}
