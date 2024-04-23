import java.util.function.BiFunction;
import static java.lang.StrictMath.sin;


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
    
    public static double solveByRungeKutta(int f, double epsilon, double a, double y_a, double b) {
        double h = (b - a) / 1000;
        double y = y_a;
        double x = a;

        while (x < b) {
            var hMin = (x + h > b) ? (b - x) : h;
            double k1 = hMin * get_function(f).apply(x, y);
            double k2 = hMin * get_function(f).apply(x + hMin/2, y + k1/2);
            double k3 = hMin * get_function(f).apply(x + hMin/2, y + k2/2);
            double k4 = hMin * get_function(f).apply(x + hMin, y + k3);
            var yNew = y + (k1 + 2 * k2 + 2 * k3 + k4) / 6;

            if (Math.abs(yNew - y) > epsilon) {
                h /= 2;
            } else if (Math.abs(yNew - y) < epsilon) {
                h *= 2;
            }
        
            x += hMin;
            y = yNew;
        }

        return y;
    }

}