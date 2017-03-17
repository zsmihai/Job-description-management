package Services;

import Domain.Post;
import Domain.PostSarcina;
import Domain.Sarcina;
import Repository.AbstractRepository;
import Repository.PostSarcinaFileRepository;
import Utils.Observable;
import Utils.Observer;
import Validator.RepositoryException;
import Validator.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizator on 06-Dec-16.
 */
public class PostSarcinaService implements Observable, Observer {

    private ArrayList<Observer> observers;
    private PostSarcinaFileRepository postSarcinaRepository;
    private final AbstractRepository<Post, Integer> postRepository;
    private final AbstractRepository<Sarcina, Integer> sarcinaRepository;

    public PostSarcinaService(PostSarcinaFileRepository postSarcinaRepository, AbstractRepository<Post, Integer> postRepository,AbstractRepository<Sarcina, Integer> sarcinaRepository ) {
        this.postSarcinaRepository = postSarcinaRepository;
        this.postRepository = postRepository;
        this.sarcinaRepository = sarcinaRepository;
        observers = new ArrayList<>();
    }


    public void addPostSarcina(PostSarcina postSarcina) throws ValidationException, RepositoryException {
        postSarcinaRepository.add(postSarcina);
        notifyObservers();
    }

    public void removePostSarcina(PostSarcina postSarcina) throws RepositoryException {
        postSarcinaRepository.remove(postSarcina);
        notifyObservers();
    }

    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs:observers) {
            obs.update();
        }


    }

    @Override
    public void update() {
      postSarcinaRepository.getAll().stream().filter( o-> (postRepository.findByID(o.getPostId()) == null || sarcinaRepository.findByID(o.getSarcinaId()) == null)).forEach(o -> {
          try {
              postSarcinaRepository.remove(o.getID());
          } catch (RepositoryException e) {
              e.printStackTrace();
          }
      });
        notifyObservers();
    }


    public List<PostSarcina> getAll(){
        return postSarcinaRepository.getAll();
    }
}
