package Repository;

import Domain.HasID;
import Validator.IValidator;

/**
 * Created by Utilizator on 14-Nov-16.
 */
public class RepositoryInMemory<T extends HasID<ID>, ID> extends AbstractRepository<T , ID> {
    public RepositoryInMemory(IValidator validator) {
        super(validator);
    }
}
