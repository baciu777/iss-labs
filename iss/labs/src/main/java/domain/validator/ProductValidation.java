package domain.validator;

import domain.Agent;
import domain.Product;

import java.util.Objects;

public class ProductValidation implements Validator<Product> {

    @Override
    public void validate(Product entity) throws ValidationException {
        if(Objects.equals(entity.getName(), ""))
            throw new ValidationException("the name should not be null");
        if(Objects.equals(entity.getProvider(), ""))
            throw new ValidationException("the provider should not be null");
        if(entity.getPrice()<1)
            throw new ValidationException("the price should be greater than 0");
        if(entity.getQuantity()<1)
            throw new ValidationException("the quantity should be greater than 0");
    }
}
