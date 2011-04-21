package com.asynchlistener;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: cristian.popovici
 */
public class AsynchListenerTest {

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    @Test
    public void testAsynch() {
        FibCalculator calculator = new FibCalculator(10);
        AsynchFuture<List<Long>> asynchFuture = new AsynchFuture<List<Long>>(calculator);
        asynchFuture.listenAsynch(new FutureCompletionListener<List<Long>>() {
            public void apply(List<Long> result) {
                System.out.println(result);
            }
        });
        executor.submit(asynchFuture);


    }


    private class FibCalculator implements Callable<List<Long>> {

        private final int number;
        private List<Long> result;

        public FibCalculator(int number) {
            this.number = number;
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
