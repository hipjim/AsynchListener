package com.asynchlistener;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * User: cristian.popovici
 */
public class AsynchListenerTest extends TestCase {

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    private static final List<Long> FIB_10_LIST = new ArrayList<Long>() {{
        add(1l);
        add(1l);
        add(2l);
        add(3l);
        add(5l);
        add(8l);
        add(13l);
        add(21l);
        add(34l);
        add(55l);
    }};

    @Test
    public void testAsynch() {

        FibCalculator calculator = new FibCalculator(10);

        AsynchFuture<List<Long>> asynchFuture = new AsynchFuture<List<Long>>(calculator);
        asynchFuture.listenAsynch(new FutureCompletionListener<List<Long>>() {
            public void apply(List<Long> actualResult) {
                assertEquals(FIB_10_LIST, actualResult);
            }
        });

        executor.submit(asynchFuture);
    }


    private class FibCalculator implements Callable<List<Long>> {

        private final int number;
        private final List<Long> result;

        public FibCalculator(int number) {
            this.number = number;
            result = new ArrayList<Long>();
        }


        public List<Long> call() throws Exception {
            for (int i = 1; i <= number; i++) {
                result.add(fib(i));
            }

            return result;
        }

        public long fib(int n) {
            if (n <= 1) {
                return n;
            } else {
                return fib(n - 1) + fib(n - 2);
            }
        }
    }

}
