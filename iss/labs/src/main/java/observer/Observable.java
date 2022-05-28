package observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    List<Observer> observer = new ArrayList<>();

    public void notifyObservers(){
        observer.stream().forEach(x->x.update());
        System.out.println("am notificat");
        System.out.println(observer.size());
    }
    public void addObserver(Observer e){
        observer.add(e);
    }
    //public void removeObserver(Observer<E> e){
    //bserver.remove(e);
    //}
}