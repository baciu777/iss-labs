package domain.validator;

import domain.Agent;

import java.util.Objects;

public class AgentValidator implements Validator<Agent> {

    @Override
    public void validate(Agent entity) throws ValidationException {
        if(Objects.equals(entity.getUsername(), ""))
            throw new ValidationException("the username should not be null");
        if(Objects.equals(entity.getPassword(), ""))
            throw new ValidationException("the password should not be null");
    }
}
