package Repository;

import Domain.PostSarcina;
import Domain.Sarcina;
import Validator.IValidator;
import Validator.RepositoryException;
import Validator.ValidationException;

import java.io.*;

/**
 * Created by Utilizator on 06-Dec-16.
 */
public class PostSarcinaFileRepository extends AbstractRepository<PostSarcina, PostSarcina> {

    private String fileName;

    public PostSarcinaFileRepository(IValidator validator, String fileName) {

        super(validator);
        this.fileName = fileName;
        try{
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

            if(data.length>1)

                try {
                    PostSarcina postSarcina = new PostSarcina(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                    super.add(postSarcina);
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
        for (PostSarcina postSarcina:elems) {
            out.write(postSarcina.getPostId()+"|"+postSarcina.getSarcinaId());
            out.newLine();
        }
        out.close();
    }


    @Override
    public void add(PostSarcina postSarcina) throws ValidationException, RepositoryException {
        super.add(postSarcina);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(PostSarcina postSarcina) throws RepositoryException {
        super.remove(postSarcina);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

