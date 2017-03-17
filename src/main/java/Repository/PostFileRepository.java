package Repository;

import Domain.Post;
import Validator.IValidator;
import Validator.RepositoryException;
import Validator.ValidationException;

import java.io.*;

/**
 * Created by Utilizator on 07-Nov-16.
 */
public class PostFileRepository extends AbstractRepository<Post, Integer> {

    private String fileName;

    public PostFileRepository(IValidator validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() throws IOException {

            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            int id;
            String name, tip;
            String line = in.readLine();
            while(line!=null){
                String[] data =line.split("\\|");

                if(data.length>2)

                    try {
                        super.add(new Post(Integer.parseInt( data[0]), data[1], data[2]));
                    } catch (ValidationException e) {
                        e.printStackTrace();
                    } catch (RepositoryException e) {
                        e.printStackTrace();
                    }
                line = in.readLine();
            }
            in.close();


    }

    public void save() throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
        for (Post post:elems) {
            out.write(post.getID()+"|"+post.getName()+"|"+post.getTip());
            out.newLine();
        }
        out.close();
    }

    @Override
    public void add(Post elem) throws ValidationException, RepositoryException {
        super.add(elem);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer ID) throws RepositoryException {
        super.remove(ID);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Post post) throws RepositoryException, ValidationException {
        super.update(post);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
