package Validator;

public interface IValidator<T> {
    public void validate(T obiect) throws ValidationException;

}
