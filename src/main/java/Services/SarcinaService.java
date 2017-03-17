package Services;

import Domain.Sarcina;
import Repository.AbstractRepository;
import Utils.Observable;
import Utils.Observer;
import Validator.RepositoryException;
import Validator.ValidationException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Utilizator on 05-Dec-16.
 */
public class SarcinaService implements Observable{

    private String filter;

    private AbstractRepository<Sarcina, Integer> sarcinaRepository;

    private ArrayList<Observer> observers = new ArrayList<>();

    public SarcinaService(AbstractRepository<Sarcina, Integer> sarcinaRepository) {
        this.sarcinaRepository = sarcinaRepository;
        filter="";
    }


    public <E> List<E> genericFilter(List<E> list, Predicate<E> filter){
        return list.stream().filter(filter).collect(Collectors.toList());
    }


    public  <E> List<E> genericSort(List<E> list, Comparator<E> comparator){
        return  list.stream().sorted(comparator).collect(Collectors.toList());
    }

    public void addSarcina(Sarcina sarcina) throws ValidationException, RepositoryException {
        sarcinaRepository.add(sarcina);
        notifyObservers();
    }

    public void setFilter(String filter){
        this.filter = filter;
        notifyObservers();
    }

    public void removeSarcina(Integer sarcinaId) throws RepositoryException {
        sarcinaRepository.remove(sarcinaId);
        notifyObservers();
    }

    public void updateSarcina(Sarcina sarcina) throws RepositoryException, ValidationException {
        sarcinaRepository.update(sarcina);
        notifyObservers();
    }

    public List<Sarcina> getAll(){
        return filterSarcina(filter);
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





    public List<Sarcina> filterSarcina(String str){
        Predicate<Sarcina> filter = (Sarcina p1)->p1.getDescription().contains(str);
        return genericFilter(sarcinaRepository.getAll(), filter);
    }

}
