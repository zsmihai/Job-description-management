package Repository;

import Domain.HasID;
import Utils.Observer;
import Validator.IValidator;
import Validator.RepositoryException;
import Validator.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public abstract class AbstractRepository<T extends HasID<ID>, ID> implements IRepository<T, ID> {

    protected ArrayList<T> elems;
    protected IValidator<T> validator;

    public AbstractRepository(IValidator validator){
        elems = new ArrayList<>();
        this.validator = validator;
    }

    @Override
    public void add(T elem) throws ValidationException, RepositoryException {
        validator.validate(elem);
        if(!elems.stream().anyMatch(t -> t.getID().equals( elem.getID())))
            elems.add(elem);
        else throw new RepositoryException("ID duplicat! \n") ;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(elems);
    }

    @Override
    public void remove(ID id) throws RepositoryException{
        if(!elems.removeIf(t -> t.getID().equals(id)))
            throw new RepositoryException("Nu exista element cu ID-ul "+id+". Nu se poate realiza stergerea!\n");
    }

    @Override
    public T findByID(ID id) {
        ArrayList<T> t = new ArrayList<T>();
        elems.stream().filter(x->x.getID()==id).forEach(t::add);
        if(t.size()>0)
            return t.get(0);
        else
            return null;
    }
    public void update(T elem) throws RepositoryException, ValidationException {
        validator.validate(elem);
        for(int i=0; i<elems.size(); i++)
            if(elems.get(i).getID().equals(elem.getID())) {
                elems.set(i, elem);
                return;
            }
        throw new RepositoryException("Nu exista element cu ID-ul "+elem.getID()+". Nu se poate realiza update!\n");
    }



}
