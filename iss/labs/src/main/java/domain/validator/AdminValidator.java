package domain.validator;

import domain.Admin;
import domain.Agent;

import java.util.Objects;

public class AdminValidator implements Validator<Admin> {

    @Override
    public void validate(Admin entity) throws ValidationException {
        if(Objects.equals(entity.getUsername(), ""))
            throw new ValidationException("the username should not be null");
        if(Objects.equals(entity.getPassword(), ""))
            throw new ValidationException("the password should not be null");
    }
}
