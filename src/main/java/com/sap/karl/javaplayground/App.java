package com.sap.karl.javaplayground;

import com.sap.karl.javaplayground.hystrix.CommandHelloWorld;
import com.sap.karl.javaplayground.observer.NumObservable;
import com.sap.karl.javaplayground.observer.NumObserver;


public class App 
{
    public static void main( String[] args )
    {
        String result = new CommandHelloWorld("World").execute();
        System.out.print(result);
    }
    
    
    public static void TestObserverMode() {
        NumObservable n = new NumObservable();
        NumObserver o =  new NumObserver();
        n.addObserver(o);
        
        n.setData(100);
    }
}
