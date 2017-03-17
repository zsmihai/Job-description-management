package Repository;

import Domain.Sarcina;
import Validator.IValidator;
import Validator.RepositoryException;
import Validator.ValidationException;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Utilizator on 07-Nov-16.
 */
public class SarcinaSerializeRepository extends AbstractRepository<Sarcina, Integer> {

    private String fileName;

    public SarcinaSerializeRepository(IValidator validator, String fileName) {
        super(validator);
        this.fileName = fileName;
    }

    public void save() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(elems);
    }


    public void load() throws IOException, ClassNotFoundException {
        ObjectInputStream in  = new ObjectInputStream(new FileInputStream(fileName));
        elems = (ArrayList<Sarcina>) in.readObject();
    }

    @Override
    public void add(Sarcina sarcina) throws ValidationException, RepositoryException {
        super.add(sarcina);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void remove(Integer id) throws RepositoryException {
        super.remove(id);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
