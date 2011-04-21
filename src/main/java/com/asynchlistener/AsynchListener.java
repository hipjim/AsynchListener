package com.asynchlistener;

/**
 * User: cristian.popovici
 */
public interface AsynchListener<T> {

    void listenAsynch(FutureCompletionListener<T> listener);

    void registerListener(FutureCompletionListener<T> listener);

    void startListening();
}
