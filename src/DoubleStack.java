import java.util.*;


public class DoubleStack {

   private LinkedList<Double> linkedListOfDoubles;


   public static void main(String[] argum) {
   }

   DoubleStack() {
      this.linkedListOfDoubles = new LinkedList<Double>();
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      DoubleStack clone = new DoubleStack();

      for (int i = this.linkedListOfDoubles.size() - 1; i >= 0; i--)
         clone.push(this.linkedListOfDoubles.get(i));

      return clone;
   }

   public boolean stEmpty() {
      return linkedListOfDoubles.isEmpty();
   }

   public void push(double a) {
      linkedListOfDoubles.push(a);
   }


   public double pop() {
      if (linkedListOfDoubles.size() < 1) {
         throw new NoSuchElementException(String.format("Elementide ebaõnnestunud kustutamine! Avaldises pole piisavalt elemente, element peab olema vähemalt üks. Algne avaldis: '%s'", linkedListOfDoubles));
      } else {
         return linkedListOfDoubles.pop();
      }
   }

   public void op(String s) {
      if (linkedListOfDoubles.size() < 2 && !s.equals("DUP")) {
         throw new IllegalArgumentException(String.format("Ebaõnnestunud aritmeetiline operatsioon! Avaldises pole piisavalt elemente, elemente peab olema vähemalt kaks. Algne avaldis: '%s'. Aritmeetikatehe: %s", linkedListOfDoubles, s));
      }
      double firstElement;
      double secondElement;
      double thirdElement = 0;
      System.out.println(linkedListOfDoubles);
      if (s.equals("SWAP")){
         firstElement = linkedListOfDoubles.getLast();
         secondElement = linkedListOfDoubles.get(linkedListOfDoubles.size()-2);
      }
      //ROT roteerib kolme pealmist elementi, tõstes kolmanda esimeseks: a b c -- b c a  (kontrollida, et leidub kolm elementi)
      else if(s.equals("ROT")){
         firstElement = linkedListOfDoubles.getLast();
         secondElement = linkedListOfDoubles.get(linkedListOfDoubles.size()-2);
         thirdElement = linkedListOfDoubles.get(linkedListOfDoubles.size()-3);
      }
      else if(s.equals("DUP")){
         firstElement = linkedListOfDoubles.getLast();
         secondElement = firstElement;
      }
      else{
         firstElement = linkedListOfDoubles.pop();
         secondElement = linkedListOfDoubles.pop();
      }


      switch (s) {
         case "+":
            linkedListOfDoubles.push(firstElement + secondElement);
            break;
         case "-":
            linkedListOfDoubles.push(secondElement - firstElement);
            break;
         case "*":
            linkedListOfDoubles.push(firstElement * secondElement);
            break;
         case "/":
            linkedListOfDoubles.push(secondElement / firstElement);
            break;
         case "SWAP":
            LinkedList<Double> newlinkedListOfDoubles  = new LinkedList<Double>();
            for (int i = 0; i <= linkedListOfDoubles.size()-3; i++) {
               newlinkedListOfDoubles.push(linkedListOfDoubles.get(i));

            }
            newlinkedListOfDoubles.push(firstElement);
            newlinkedListOfDoubles.push(secondElement);
            linkedListOfDoubles = newlinkedListOfDoubles;
            break;
         case "ROT":
            LinkedList<Double> rootLinkedListOfDoubles  = new LinkedList<Double>();

            for (int i = 0; i <= linkedListOfDoubles.size()-4; i++) {
               rootLinkedListOfDoubles.push(linkedListOfDoubles.get(i));
            }
            rootLinkedListOfDoubles.push(secondElement);
            rootLinkedListOfDoubles.push(thirdElement);
            rootLinkedListOfDoubles.push(firstElement);
            linkedListOfDoubles = rootLinkedListOfDoubles;
            break;
         case "DUP":
            LinkedList<Double> dupLinkedListOfDoubles  = new LinkedList<Double>();

            for (int i = 0; i <= linkedListOfDoubles.size()-2; i++) {
               dupLinkedListOfDoubles.push(linkedListOfDoubles.get(i));

            }
            dupLinkedListOfDoubles.push(secondElement);
            dupLinkedListOfDoubles.push(firstElement);
            linkedListOfDoubles = dupLinkedListOfDoubles;
            break;
         default:
            try {
               throw new NoSuchElementException();
            } catch (NoSuchElementException e) {
               throw new NoSuchElementException(String.format("Ebaõnnestunud aritmeetiline operatsioon! Kehtetu aritmeetikatehe '%s', kehtivad aritmeetikatehed on '( + - * / )'%n", s));
            }
      }
   }



   public double tos() {
      if (linkedListOfDoubles.size() < 1) {
         throw new NoSuchElementException(String.format("Ülemise elemendi ebaõnnestunud lugemine! Avaldises pole piisavalt elemente, element peab olema vähemalt üks. Algne avaldis: '%s'", linkedListOfDoubles));
      } else {
         return linkedListOfDoubles.getFirst();
      }
   }

   @Override
   public boolean equals(Object o) {
      if (linkedListOfDoubles.size() != ((DoubleStack) o).linkedListOfDoubles.size()) {
         return false;
      }
      for (int i = 0; i < linkedListOfDoubles.size(); i++) {
         if (!linkedListOfDoubles.get(i).equals(((DoubleStack) o).linkedListOfDoubles.get(i))) {
            return false;
         }
      }
      return true;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      for (int i = linkedListOfDoubles.size() - 1; i >= 0; i--) {
         sb.append(linkedListOfDoubles.get(i));

      }
      return sb.toString();
   }

   static public double interpret(String pol) {
      String[] listWithNumsAndOperators = pol.trim().replaceAll(" +", " ").split(" ");

      DoubleStack stackOfNumbers = new DoubleStack();
      List<String> listOfOperators = new ArrayList<>(Arrays.asList("+", "-", "/", "*", "SWAP", "DUP", "ROT"));

      for (String element : listWithNumsAndOperators) {
         if (listOfOperators.contains(element)) {
            try {
               stackOfNumbers.op(element);
            } catch (NoSuchElementException e) {
               throw new NumberFormatException(String.format("Ebaõnnestunud aritmeetiline operatsioon! Avaldises pole piisavalt arve tehte sooritamiseks. Algne avaldis: '%s'", pol));
            }
         } else {
            try {
               stackOfNumbers.push(Double.parseDouble(element));
            } catch (NumberFormatException e) {
               throw new NumberFormatException(String.format("Avaldises esineb lubamatu sümbol: '%s' Algne avaldis: '%s'", element, pol));
            }
         }
      }

      if (stackOfNumbers.linkedListOfDoubles.size() > 1) {
         throw new RuntimeException(String.format("Avaldises on liiga palju arve. Algne avaldises: '%s'", pol));
      }


      return stackOfNumbers.tos();
   }
}