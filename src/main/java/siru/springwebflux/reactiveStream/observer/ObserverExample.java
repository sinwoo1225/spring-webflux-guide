package siru.springwebflux.reactiveStream.observer;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObserverExample {

    // Iterable <-> Observer (duality)
    // Pull     <-> Push

    public static void main(String[] args) {
        Iterable<Integer> iter = () -> new Iterator<Integer>() {
            int i = 0;
            final static int MAX = 10;

            @Override
            public boolean hasNext() {
                return i < MAX;
            }

            @Override
            public Integer next() {
                return ++i;
            }
        };

        System.out.println("Iterable(Pull)");
        for(Integer i : iter) {
            System.out.println(i);
        }

        System.out.println("Observable(Push)");
        Observer ob = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        };

        IntObserver io = new IntObserver();
        io.addObserver(ob);

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(io);

        es.shutdown();

    }

    static class IntObserver extends Observable implements Runnable{

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged();
                notifyObservers(i);
            }
        }
    }

}
