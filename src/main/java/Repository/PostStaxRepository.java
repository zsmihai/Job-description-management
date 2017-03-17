package Repository;

import Domain.Post;
import Validator.IValidator;
import Validator.RepositoryException;
import Validator.ValidationException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizator on 12-Dec-16.
 */
public class PostStaxRepository extends AbstractRepository<Post, Integer> {

    private String fileName;

    public PostStaxRepository(IValidator validator, String fileName) {
        super(validator);
        this.fileName=fileName;

        try {
            load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    ArrayList<Post> readXML(XMLStreamReader reader){
        ArrayList<Post> posts = new ArrayList<>();
        Integer id = null;
        String name = null;
        String tip = null;
        try {
            while(reader.hasNext() ){
                Integer curentEvent =  reader.next();
                if(curentEvent == XMLStreamReader.START_ELEMENT){
                    if(reader.getLocalName().equals("post")){
                        reader.next();
                        if(reader.getLocalName().equals("id")){
                            id = Integer.parseInt(reader.getElementText());
                            reader.next();
                        }

                        if(reader.getLocalName().equals("name")){
                            name = reader.getElementText();
                            reader.next();
                        }

                        if(reader.getLocalName().equals("tip")){
                            tip = reader.getElementText();
                        }
                    }
                }else if(curentEvent == XMLStreamReader.END_ELEMENT){
                    if(reader.getLocalName().equals("post")){
                        posts.add(new Post(id,name,tip));
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public void saveData()  {
        OutputStream outputStream = null;
        XMLStreamWriter writer = null;
        try {
            outputStream = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        try {
            factory.createXMLEventWriter(outputStream);
            writer = factory.createXMLStreamWriter(outputStream);
            this.writeToXML(writer);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void writeToXML(XMLStreamWriter writer) {
        try {
            writer.writeStartDocument();
            writer.writeStartElement("posts");
            for (Post p : elems) {
                writer.writeStartElement("post");

                writer.writeStartElement("id");
                writer.writeCharacters(p.getID().toString());
                writer.writeEndElement();

                writer.writeStartElement("name");
                writer.writeCharacters(p.getName());
                writer.writeEndElement();

                writer.writeStartElement("tip");
                writer.writeCharacters(p.getTip());
                writer.writeEndElement();

                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void load() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(fileName);
        XMLInputFactory factory = XMLInputFactory.newFactory();
        try {
            XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
            elems = readXML(reader);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void add(Post post) throws ValidationException, RepositoryException {
        super.add(post);
        saveData();
    }

    @Override
    public void remove(Integer Id) throws RepositoryException {
        super.remove(Id);
        saveData();
    }

    @Override
    public void update(Post post) throws ValidationException, RepositoryException {
        super.update(post);
        saveData();
    }

}
