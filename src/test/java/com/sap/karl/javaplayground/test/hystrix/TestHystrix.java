package com.sap.karl.javaplayground.test.hystrix;

import org.junit.Test;

import com.sap.karl.javaplayground.hystrix.CommandHelloWorld;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Future;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class TestHystrix {
    @Test
    public void testSynchronous() {
        assertEquals("Hello World!", new CommandHelloWorld("World").execute());
        assertEquals("Hello Bob!", new CommandHelloWorld("Bob").execute());
    }
    
    @Test
    public void testAsynchronous1() throws Exception {
        assertEquals("Hello World!", new CommandHelloWorld("World").queue().get());
        assertEquals("Hello Bob!", new CommandHelloWorld("Bob").queue().get());
    }

    @Test
    public void testAsynchronous2() throws Exception {

        Future<String> fWorld = new CommandHelloWorld("World").queue();
        Future<String> fBob = new CommandHelloWorld("Bob").queue();

        assertEquals("Hello World!", fWorld.get());
        assertEquals("Hello Bob!", fBob.get());
    }
    
    @Test
    public void testObservable() throws Exception {

        Observable<String> fWorld = new CommandHelloWorld("World").observe();
        Observable<String> fBob = new CommandHelloWorld("Bob").observe();

        // blocking
        assertEquals("Hello World!", fWorld.toBlocking().single());
        assertEquals("Hello Bob!", fBob.toBlocking().single());

        // non-blocking 
        // - this is a verbose anonymous inner-class approach and doesn't do assertions
        fWorld.subscribe(new Observer<String>() {

            public void onCompleted() {
                // nothing needed here
            }

            public void onError(Throwable e) {
                e.printStackTrace();
            }

            public void onNext(String v) {
                System.out.println("onNext: " + v);
            }

        });

        // non-blocking
        // - also verbose anonymous inner-class
        // - ignore errors and onCompleted signal
        fBob.subscribe(new Action1<String>() {

            public void call(String v) {
                System.out.println("onNext: " + v);
            }

        });
    }
}
