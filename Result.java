import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.function.BiFunction;

import static java.lang.StrictMath.log;
import static java.lang.StrictMath.sin;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {
    
    private static double first_function(double x, double y) {
        return sin(x);
    }

    private static double second_function(double x, double y) {
        return (x * y)/2;
    }

    private static double third_function(double x, double y) {
        return y - (2 * x)/y;
    }

    private static double fourth_function(double x, double y) {
        return x + y;
    }
    
    private static double default_function(double x, double y) {
        return 0.0;
    }
    
    /*
    * How to use this function:
    *    BiFunction<Double, Double, Double> func = get_function(4);
    *    func.apply(0.0001);
    */
    private static BiFunction<Double, Double, Double> get_function(int n) {
        switch (n) {
            case (1):
                return Result::first_function;
            case (2):
                return Result::second_function;
            case (3):
                return Result::third_function;
            case (4):
                return Result::fourth_function;
            default:
                return Result::default_function;
        }
    }
    
    /*
     * Complete the 'solveByEulerImproved' function below.
     *
     * The function is expected to return a DOUBLE.
     * The function accepts following parameters:
     *  1. INTEGER f
     *  2. DOUBLE epsilon
     *  3. DOUBLE a
     *  4. DOUBLE y_a
     *  5. DOUBLE b
     */
    
    public static double solveByEulerImproved(int f, double epsilon, double a, double y_a, double b) {
        if (a >= b) {
            throw new IllegalArgumentException("a must be less than b.");
        }

        double h = 0.1;
        double x = a;
        double y = y_a;

        BiFunction<Double, Double, Double> func = get_function(f);

        while (x < b) {
            double yPred = y + h * func.apply(x, y);
            double yCorrected = y + h * (func.apply(x, y) + func.apply(x + h, yPred)) / 2.0;

            double error = Math.abs(yCorrected - yPred);

            if (error < epsilon) {
                y = yCorrected;
                x += h;
            }

            h = 0.9 * h * Math.pow(epsilon / error, 0.2);

            if (x + h > b) {
                h = b - x;
            }
        }

        return y;
    }

}