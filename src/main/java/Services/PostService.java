package Services;

import Domain.Post;
import Repository.AbstractRepository;
import Utils.Observable;
import Utils.Observer;
import Validator.RepositoryException;
import Validator.ValidationException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Utilizator on 14-Nov-16.
 */
public class PostService implements Observable{
    private String filter;
    private AbstractRepository<Post, Integer> postRepository;

    private ArrayList<Observer> observers = new ArrayList<>();

    public PostService(AbstractRepository<Post, Integer> postRepository) {
        this.postRepository = postRepository;
        this.filter="";
    }


    public <E> List<E> genericFilter(List<E> list, Predicate<E> filter){
        return list.stream().filter(filter).collect(Collectors.toList());
    }

    public List<Post> filterPosts(String str){
        Predicate<Post> filter = (Post p1)->p1.getName().contains(str);
        Predicate<Post> filter2 = (Post p1)->p1.getTip().contains(str);

        return genericFilter(postRepository.getAll(), filter.and(filter2));
    }

    public void setFilter(String filter){
        this.filter = filter;
        notifyObservers();
    }


    public  <E> List<E> genericSort(List<E> list, Comparator<E> comparator){
        return  list.stream().sorted(comparator).collect(Collectors.toList());
    }

    public void addPost(Post post) throws ValidationException, RepositoryException {
        postRepository.add(post);
        notifyObservers();
    }

    public void removePost(Integer postId) throws RepositoryException {
        postRepository.remove(postId);
        notifyObservers();
    }

    public void updatePost(Post post) throws RepositoryException, ValidationException {
        postRepository.update(post);
        notifyObservers();
    }

    public List<Post> getAll(){
        return filterPosts(filter);
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
        observers.forEach(Observer::update);
    }
}
