package Repository;

import Domain.HasID;
import Validator.RepositoryException;
import Validator.ValidationException;

import java.util.List;
import Utils.Observable;
public interface IRepository<T extends HasID, ID> {

    void add(T elem) throws RepositoryException, ValidationException;

    List<T> getAll();

    void remove(ID id) throws RepositoryException;

    T findByID(ID id);
}
