import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoubleStack {

   private final LinkedList<Double> stk = new LinkedList<>();
   static Pattern pattern = Pattern.compile("[a-zA-z]+");
   private DoubleStack(LinkedList<Double> stk) {}

   public static void main (String[] argum) {
      // TODO!!! Your tests here!
   }

   DoubleStack() {
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      DoubleStack m2 = new DoubleStack(stk);
      for (Double aDouble : stk) {
         m2.push(aDouble);
      }

      return m2;
   }

   public boolean stEmpty() {
      return stk.isEmpty();
   }

   public void push (double a) {
      stk.add(a);
   }

   public double pop() {
      if (stk.size() < 1) {
         throw new NoSuchElementException("Nothing to pop in " + stk + " !");
      }
      return stk.removeLast();
   } // pop

   public void op (String s) {
      if (stk.size() < 2) {
         throw new IndexOutOfBoundsException("Not enough elements in " +  stk + "! Math: " + s);
      }
      Double a = stk.pop();
      Double b = stk.pop();
      switch (s) {
         case "+":
            stk.push(a + b);
            break;
         case "-":
            stk.push(a - b);
            break;
         case "*":
            stk.push(a * b);
            break;
         case "/":
            stk.push(a / b);
            break;
         default:
            throw new NoSuchElementException("Invalid Math: "+ s + ", try (+ - * /)");
      }
   }

   public double tos() {
      if (stk.size() < 1) {
         throw new NullPointerException("no elements in: " + stk + "(length = 0");
      }
      return stk.getLast();
   }

   @Override
   public boolean equals (Object o) {
      DoubleStack o_copy = (DoubleStack) o;
      if (stk.size() != o_copy.stk.size()) {
         return false;
      }
      int i = 0;
      for (double elem : stk) {
         if (elem != o_copy.stk.get(i)) {
            return false;
         }
         i++;
      }
      return true;
   }

   @Override
   public String toString() {
      return String.valueOf(String.valueOf(stk));
   }

   public static double interpret (String pol) {

      pol = pol.replaceAll("\t", "");
      pol = pol.trim().replaceAll(" +", " ");
      String[] polFormatted = pol.split("[ \\s]");
      Stack<Double> ret = new Stack<>();
      String operators = "+-*/";
      int numbers_count = 0;
      for (String elem : polFormatted) {
         if (!operators.contains(elem)) {
            numbers_count++;
            ret.push(Double.valueOf(elem));
            continue;
         }
         double a = ret.pop();
         double b = ret.pop();
         switch (elem) {
            case "+":
               ret.push(b + a);
               break;
            case "-":
               ret.push(b - a);
               break;
            case "*":
               ret.push(b * a);
               break;
            case "/":
               ret.push(b / a);
               break;
            default:
               throw new NoSuchElementException(("Invalid Math "+ pol + ", try (+ - * /)"));
         }

      }
      Matcher m = pattern.matcher(pol);
      if ((numbers_count - 1) != polFormatted.length - numbers_count
              && polFormatted.length > 3) {
         if (numbers_count - 1 >= polFormatted.length - numbers_count) {
            throw new RuntimeException("Not enough math: " + "math " +
                    Arrays.toString(polFormatted) + "| numbers_count " + numbers_count);
         } else if (numbers_count - 1 <= polFormatted.length - numbers_count) {
            throw new RuntimeException("Not enough numbers! math: " +
                    (Arrays.toString(polFormatted)) + " | numbers_count: " + numbers_count);
         }
      } else if (pol.length() == 0) {
         throw new NullPointerException("Empty parameter " + pol);
      } else if (pol.contains("[a-zA-z]+")) {
         throw new NumberFormatException("Argument contains letters at "
                 + (m.start() + " position in " + pol));
      }
      return ret.pop();
   }
}
