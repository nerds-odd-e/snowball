package com.odde;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FizzBuzzTests {

    @Test
    public void test3と5の倍数以外のときに数値と返す() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        assertThat(fizzBuzz.call(), is("1"));
    }

    @Test
    public void test3の倍数のときにFizzと返す() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        fizzBuzz.call();
        fizzBuzz.call();
        assertThat(fizzBuzz.call(), is("Fizz"));
    }

    @Test
    public void test5の倍数のときにBuzzと返す() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        assertThat(fizzBuzz.call(), is("Buzz"));
    }

    @Test
    public void test15のときにFizzBuzzと返す() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        fizzBuzz.call();
        assertThat(fizzBuzz.call(), is("FizzBuzz"));
    }

}
