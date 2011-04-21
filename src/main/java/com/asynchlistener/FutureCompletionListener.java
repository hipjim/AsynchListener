package com.asynchlistener;

/**
 * User: cristian.popovici
 */
public interface FutureCompletionListener<T> {

    public void apply(T result);

}
