///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 11
//GAV com.github.aghasemi:try4j
package com.github.aghasemi.try4j; // The IDE will complain about this. Ignore it.

import static java.lang.System.*;

import java.io.PrintStream;
import java.net.URL;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


public class Try<T> {
    public interface ExceptionFreeFunction<T> {
        public T get() throws Exception;
    }

    public interface ExceptionFreeVoidFunction {
        public void run() throws Exception;
    }

    private Consumer<Exception> exceptionHandler = (e) -> { return; } ;
    private Function<T, Boolean> emptinessChecker = (t) -> false ;
    private boolean emptyIfNull = false;

    public Optional<T> runAndGet(ExceptionFreeFunction f)
    {
        try {
            T result = (T) f.get();

            if (emptyIfNull && result==null) 
                return Optional.empty(); 
            else 
                return emptinessChecker.apply(result) ? Optional.empty() :  Optional.of(result);
        } catch (Exception e) {
            exceptionHandler.accept(e);
            return Optional.empty();
        }
    }


    private Try() {}

    public static Try init() {
        return new Try();
    }

    public Try emptyIfNull(boolean _emptyIfNull)
    {
        this.emptyIfNull = _emptyIfNull;
        return this;
    }

    public Try emptyIf(Function _emptinessChecker)
    {
        this.emptinessChecker = _emptinessChecker;
        return this;
    }

    public Try onException(Consumer<Exception> _handler)
    {
        this.exceptionHandler = _handler;
        return this;
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
        run(f, (e) -> {return;});
    }

    public static void run(ExceptionFreeVoidFunction f, Consumer<Exception> exceptionHandler)
    {
        try {
            f.run();
        } catch (Exception e) {
            exceptionHandler.accept(e);
        }
    }

    

    

    public static void main(String... args) {
        out.println("Hello World");
        

        var x =  Try.init().emptyIfNull(true).runAndGet( () -> new URL("http://google.ch"));
        System.out.println(x);

        
    }
}
