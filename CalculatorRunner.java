package CalculatorApp;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class CalculatorRunner {
    private static final Scanner SC = new Scanner(System.in).useLocale(Locale.US);

    private enum AngleUnit { DEGREES, RADIANS }

    public static void main(String[] args) {
        typeMenu();
    }

    //Entry point menu to select type. Loops until user quits.
    public static int typeMenu() {
        while (true) {
            System.out.println("=== Calculator ===");
            System.out.println("1) Int values");
            System.out.println("2) Double values");
            System.out.println("3) Trig functions");
            System.out.println("0) Exit");
            System.out.print("Select: ");
            int sel = readInt();
            switch (sel) {
                case 1 -> intMenu();
                case 2 -> doubleMenu();
                case 3 -> trigMenu();
                case 0 -> {
                    System.out.println("Goodbye.");
                    return 0;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // Int Menu/Operations

    private static void intMenu() {
        while (true) {
            System.out.println("-- Int Operations --");
            System.out.println("1) Add");
            System.out.println("2) Subtract");
            System.out.println("3) Multiply");
            System.out.println("4) Divide (quotient & remainder + reduced fraction)");
            System.out.println("5) Power (a^b)");
            System.out.println("6) GCD");
            System.out.println("7) LCM");
            System.out.println("8) Factorial");
            System.out.println("0) Back");
            System.out.print("Select: ");
            int sel = readInt();
            if (sel == 0) return;
            try {
                switch (sel) {
                    case 1 -> {
                        int a = askInt("a");
                        int b = askInt("b");
                        System.out.println(a + " + " + b + " = " + (a + b));
                    }
                    case 2 -> {
                        int a = askInt("a");
                        int b = askInt("b");
                        System.out.println(a + " - " + b + " = " + (a - b));
                    }
                    case 3 -> {
                        int a = askInt("a");
                        int b = askInt("b");
                        System.out.println(a + " * " + b + " = " + (a * b));
                    }
                    case 4 -> {
                        int a = askInt("numerator");
                        int b = askIntNonZero("denominator");
                        int q = a / b;
                        int r = a % b;
                        System.out.println(a + " / " + b + " = " + q + " (quotient), remainder " + r);
                        printReducedFraction(a, b);
                    }
                    case 5 -> {
                        int a = askInt("base (a)");
                        int b = askInt("exponent (b >= 0)");
                        if (b < 0) {
                            System.out.println("Exponent must be >= 0 for int power.");
                        } else {
                            System.out.println(a + "^" + b + " = " + powInt(a, b));
                        }
                    }
                    case 6 -> {
                        int a = askInt("a");
                        int b = askInt("b");
                        System.out.println("gcd(" + a + "," + b + ") = " + gcd(Math.abs(a), Math.abs(b)));
                    }
                    case 7 -> {
                        int a = askInt("a");
                        int b = askInt("b");
                        int g = gcd(Math.abs(a), Math.abs(b));
                        long lcm = (long)Math.abs((long)a / g * (long)b);
                        System.out.println("lcm(" + a + "," + b + ") = " + lcm);
                    }
                    case 8 -> {
                        int n = askInt("n (0..20)");
                        if (n < 0 || n > 20) {
                            System.out.println("n must be in [0,20] to avoid overflow.");
                        } else {
                            System.out.println(n + "! = " + factorial(n));
                        }
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (ArithmeticException ex) {
                System.out.println("Math error: " + ex.getMessage());
            }
        }
    }

    private static long powInt(int a, int b) {
        long res = 1;
        long base = a;
        int exp = b;
        while (exp > 0) {
            if ((exp & 1) == 1) res *= base;
            base *= base;
            exp >>= 1;
        }
        return res;
    }

    private static void printReducedFraction(int num, int den) {
        if (den == 0) {
            System.out.println("Undefined (division by zero).");
            return;
        }
        int g = gcd(Math.abs(num), Math.abs(den));
        int rn = num / g;
        int rd = den / g;
        if (rd < 0) { rn = -rn; rd = -rd; }
        System.out.println("Reduced fraction: " + rn + "/" + rd);
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    private static long factorial(int n) {
        long f = 1;
        for (int i = 2; i <= n; i++) f *= i;
        return f;
    }

    // Double Menu/Operations

    private static void doubleMenu() {
        while (true) {
            System.out.println("-- Double Operations --");
            System.out.println("1) Add");
            System.out.println("2) Subtract");
            System.out.println("3) Multiply");
            System.out.println("4) Divide");
            System.out.println("0) Back");
            System.out.print("Select: ");
            int sel = readInt();
            if (sel == 0) return;
            switch (sel) {
                case 1 -> {
                    double a = askDouble("a");
                    double b = askDouble("b");
                    System.out.printf(Locale.US, "%.6f + %.6f = %.6f%n", a, b, a + b);
                }
                case 2 -> {
                    double a = askDouble("a");
                    double b = askDouble("b");
                    System.out.printf(Locale.US, "%.6f - %.6f = %.6f%n", a, b, a - b);
                }
                case 3 -> {
                    double a = askDouble("a");
                    double b = askDouble("b");
                    System.out.printf(Locale.US, "%.6f * %.6f = %.6f%n", a, b, a * b);
                }
                case 4 -> {
                    double a = askDouble("numerator");
                    double b = askDoubleNonZero("denominator");
                    System.out.printf(Locale.US, "%.6f / %.6f = %.6f%n", a, b, a / b);
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // Trig Menu/Operations

    private static void trigMenu() {
        while (true) {
            System.out.println("-- Trig Functions --");
            System.out.println("1) sin(x)");
            System.out.println("2) cos(x)");
            System.out.println("3) tan(x)");
            System.out.println("4) asin(x)");
            System.out.println("5) acos(x)");
            System.out.println("6) atan(x)");
            System.out.println("0) Back");
            System.out.print("Select: ");
            int sel = readInt();
            if (sel == 0) return;

            boolean inverse = (sel >= 4 && sel <= 6);
            AngleUnit unit = inverse ? askAngleUnit("Output angle in (D/R)? ") : askAngleUnit("Is your input x in (D/R)? ");

            if (!inverse) {
                double x = askDouble("x");
                double xr = (unit == AngleUnit.DEGREES) ? Math.toRadians(x) : x;
                double result = switch (sel) {
                    case 1 -> Math.sin(xr);
                    case 2 -> Math.cos(xr);
                    case 3 -> Math.tan(xr);
                    default -> Double.NaN;
                };
                System.out.printf(Locale.US, "Result = %.10f%n", result);
            } else {
                double x = askDouble("x");
                double angleRad = switch (sel) {
                    case 4 -> Math.asin(x);
                    case 5 -> Math.acos(x);
                    case 6 -> Math.atan(x);
                    default -> Double.NaN;
                };
                double out = (unit == AngleUnit.DEGREES) ? Math.toDegrees(angleRad) : angleRad;
                System.out.printf(Locale.US, "Angle = %.10f %s%n", out, unit == AngleUnit.DEGREES ? "deg" : "rad");
            }
        }
    }

    // Reading user input - checking all inputs are valid

    private static int readInt() {
        while (true) {
            try {
                return SC.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Enter an integer: ");
                SC.next(); // discard
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                return SC.nextDouble();
            } catch (InputMismatchException e) {
                System.out.print("Enter a number: ");
                SC.next(); // discard
            }
        }
    }

    private static int askInt(String name) {
        System.out.print("Enter " + name + ": ");
        return readInt();
    }

    private static int askIntNonZero(String name) {
        while (true) {
            int v = askInt(name);
            if (v != 0) return v;
            System.out.println("Value must not be zero.");
        }
    }

    private static double askDouble(String name) {
        System.out.print("Enter " + name + ": ");
        return readDouble();
    }

    private static double askDoubleNonZero(String name) {
        while (true) {
            double v = askDouble(name);
            if (Math.abs(v) > 0.0) return v;
            System.out.println("Value must not be zero.");
        }
    }

    private static AngleUnit askAngleUnit(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.next().trim().toUpperCase(Locale.ROOT);
            if (s.startsWith("D")) return AngleUnit.DEGREES;
            if (s.startsWith("R")) return AngleUnit.RADIANS;
            System.out.println("Type D for degrees or R for radians.");
        }
    }
}