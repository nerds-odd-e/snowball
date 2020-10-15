package com.odde;

public class FizzBuzz {
    private int number=0;

    public String call() {
        number++;
        String result = "";
        if(number % 3 == 0) {
            result += "Fizz";
        }
        if(number % 5 == 0) {
            result+= "Buzz";
        }
        if (result.isEmpty()) {
            result = String.valueOf(number);
        }

        return result;
    }
}
