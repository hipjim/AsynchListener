package com.asynchlistener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * User: cristian.popovici
 */
public class AsynchFuture<T> extends FutureTask<T> implements AsynchListener<T> {

    private final List<FutureCompletionListener<T>> registry = new ArrayList<FutureCompletionListener<T>>();

    public AsynchFuture(Callable callable) {
        super(callable);
    }

    public AsynchFuture(Runnable runnable, T result) {
        super(runnable, result);
    }

    public void listenAsynch(FutureCompletionListener<T> listener) {
        AsynchListener<T> asynchListener = new AsynchListener<T>(listener);
        Thread t = new Thread(asynchListener);
        t.start();
    }

    public void registerListener(FutureCompletionListener<T> listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }

        registry.add(listener);
    }

    public void startListening() {
        for (FutureCompletionListener listener : registry) {
            listenAsynch(listener);
        }
    }


    private final class AsynchListener<T> implements Runnable {

        private final FutureCompletionListener listener;

        public AsynchListener(FutureCompletionListener listener) {
            this.listener = listener;
        }

        public void run() {
            try {
                T value = (T) get();
                listener.apply(value);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
