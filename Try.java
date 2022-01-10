///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17
//GAV com.github.aghasemi:try4j
package com.github.aghasemi.try4j; // The IDE will complain about this. Ignore it.

import static java.lang.System.*;

import java.net.URL;
import java.util.Optional;


public class Try {

    public interface ExceptionFreeFunction<T> {
        public T get() throws Exception;
    }

    public interface ExceptionFreeVoidFunction {
        public void run() throws Exception;
    }

    public static<U> Optional<U> get(ExceptionFreeFunction f, boolean emptyIfNull)
    {
        try {
            U result = (U) f.get();
            return emptyIfNull && result==null ? Optional.empty() :  Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static<U> Optional<U> get(ExceptionFreeFunction f)
    {
        return get(f, false);
    }

    public static void run(ExceptionFreeVoidFunction f)
    {
        try {
            f.run();
        } catch (Exception e) {
        }
    }

    

    

    public static void main(String... args) {
        out.println("Hello World");

        var x =  get( () -> new URL("http://google.ch"));
        System.out.println(x);
    }
}
