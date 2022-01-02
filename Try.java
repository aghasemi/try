///usr/bin/env jbang "$0" "$@" ; exit $?
// //DEPS <dependency1> <dependency2>
//JAVA 17
//GAV com.github.aghasemi:try
package io.aghasemi.jtry;

import static java.lang.System.*;

import java.net.URL;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


public class Try {

    public interface ExceptionFreeFunction<T> {
        public T run() throws Exception;
    }

    public static<U> Optional<U> doTry(ExceptionFreeFunction f, boolean nullIsValid, AtomicReference exception)
    {
        try {
            U result = (U) f.run();
            if(exception!=null) exception.set(null);
            return !nullIsValid && result==null ? Optional.empty() :  Optional.of(result);
        } catch (Exception e) {
            if(exception!=null) exception.set(e);
            return Optional.empty();
        }
    }

    public static<U> U doTry(ExceptionFreeFunction f, U defaultValue, boolean nullIsValid, AtomicReference exception)
    {
        try {
            U result = (U) f.run();
            if(exception!=null) exception.set(null);
            return !nullIsValid && result==null ? defaultValue :  result;
        } catch (Exception e) {
            if(exception!=null) exception.set(e);
            return defaultValue;
        }
    }

    public static<U> Optional<U> doTry(ExceptionFreeFunction f, boolean nullIsValid)
    {
        return doTry(f, nullIsValid, null);
    }

    public static<U> U doTry(ExceptionFreeFunction f, U defaultValue, boolean nullIsValid)
    {
        return doTry(f, defaultValue, nullIsValid, null);
    }


    public static<U> Optional<U> doTry(ExceptionFreeFunction f)
    {
        return doTry(f, true);
    }

    public static<U> U doTry(ExceptionFreeFunction f, U defaultValue)
    {
        return doTry(f, defaultValue, true);
    }

    public static void main(String... args) {
        out.println("Hello World");

        var x =  doTry( () -> new URL("http://google.ch"));
        System.out.println(x);
    }
}
