package Utils;

/**
 * Created by Utilizator on 27-Nov-16.
 */
public interface Observable {
    void addObserver(Observer obs);
    void removeObserver(Observer obs);
    void notifyObservers();

}
