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


public class Try4J<T> {
    public interface ExceptionFreeFunction<T> {
        public T get() throws Exception;
    }

    public interface ExceptionFreeVoidFunction {
        public void run() throws Exception;
    }

    public static class TryBuilder<V> {
        private TryBuilder() {}
        private Consumer<Exception> exceptionHandler = (e) -> { return; } ;
        private Function<V, Boolean> emptinessChecker = (t) -> false ;
        private boolean emptyIfNull = false;

        public Optional<V> Try(ExceptionFreeFunction<V> f)
        {
            try {
                V result = (V) f.get();

                if (emptyIfNull && result==null) 
                    return Optional.empty(); 
                else 
                    return emptinessChecker.apply(result) ? Optional.empty() :  Optional.of(result);
            } catch (Exception e) {
                exceptionHandler.accept(e);
                return Optional.empty();
            }
        }

        public TryBuilder emptyIfNull(boolean _emptyIfNull)
        {
            this.emptyIfNull = _emptyIfNull;
            return this;
        }

        public TryBuilder emptyIf(Function<V, Boolean> _emptinessChecker)
        {
            this.emptinessChecker = _emptinessChecker;
            return this;
        }

        public TryBuilder onException(Consumer<Exception> _handler)
        {
            this.exceptionHandler = _handler;
            return this;
        }

    }

    public static TryBuilder initTry() {
            return new TryBuilder();
    }


    public static<U> Optional<U> Try(ExceptionFreeFunction<U> f, boolean emptyIfNull)
    {
        try {
            U result = (U) f.get();
            return emptyIfNull && result==null ? Optional.empty() :  Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static<U> Optional<U> Try(ExceptionFreeFunction<U> f)
    {
        return Try(f, false);
    }

    public static void Try(ExceptionFreeVoidFunction f)
    {
        Try(f, (e) -> {return;});
    }

    public static void Try(ExceptionFreeVoidFunction f, Consumer<Exception> exceptionHandler)
    {
        try {
            f.run();
        } catch (Exception e) {
            exceptionHandler.accept(e);
        }
    }

    
	 //See https://stackoverflow.com/questions/148828/how-to-emulate-c-sharp-as-operator-in-java
	 
	public static <T> Optional<T> as(Class<T> targetClass, Object o)
	{
	    if(targetClass.isInstance(o)){
	        return Optional.of(targetClass.cast(o));
	    }
	    return Optional.empty();
	}
    

    public static void main(String... args) {
        out.println("Hello World");
        

        var x =  Try4J.initTry().emptyIfNull(true).Try( () -> new URL("http://google.ch"));
        var y = Try4J.Try(() -> new URL("http://google.ch"));
        System.out.println(x);

        
    }
}
