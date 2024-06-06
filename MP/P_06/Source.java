// Jakub Stec - grupa 6
import java.util.Arrays;
import java.util.Scanner;

/*
IDEA: Implementacja dwoch funkcji (recPackage i stackSimPackage) realizujace algorytm pakowania plecaka
za pomoca rekurencji oraz iteracyjnej symulacji funkcji rekurencyjnej z wykorzystaniem stosu
do wypisania wyniku uzywamy tablicy boolean ktora pamieta pasujace indeksy.
 */

public class Source {
    public static Scanner sc = new Scanner(System.in);

    // klasa przechowujaca brakujaca sume, indeks liczby i tablice elementow podzbioru
    public static class Params {
        int sum;
        int index;
        boolean[] stackArr;

        // konstruktor
        Params(int sum, int index,boolean[] stackArr) {
            this.sum = sum;
            this.index = index;
            this.stackArr = stackArr;
        }
    }

    // stos przechowujacy obiekty typu Params, implementacja z wykladu
    public static class MyStack1 {
        private int maxSize;
        private Params[] Elem;
        private int top;
        public MyStack1(int size) {
            maxSize = size;
            Elem = new Params[maxSize];
            top = -1;
        }

        public void push(Params x) {
            Elem[++top] = x;
        }

        public Params pop() {
            if(!isEmpty()) {
                return Elem[top--];
            }
            else {
                return null;
            }
        }

        public Params peek() {
            return Elem[top];
        }

        public boolean isEmpty() {
            return top == -1;
        }
    }
    static boolean[] arr; // tablica przechowujaca wynik


    static StringBuilder recSb; // przechowanie wyniku rekurencji
    public static boolean recPackage(int t,int max, int[] tab, int index) {

        // gdy znaleziono podzbior, wypisujemy te indeksy ktore sa "true" w tablicy arr
        if(max == 0) {
            // t - to jest szukana wartosc
            System.out.print("REC:  " + t + " = ");
            printRec(tab, 0, index);
            System.out.print(recSb);
            System.out.println();
            return true; // wyjscie z funkcji
        }

        // przypadke bazowy, gdy suma jest zbyt duza, lub wyszlismy poza zakres
        if(max < 0 || index >= tab.length) {
            return false;
        }

        arr[index] = true; // wybor elementu na biezacej pozycji

        // sprawdzenie czy biezacy element pasuje
        if(recPackage(t,max - tab[index], tab, index + 1)) {
            return true;
        }
        // wycofanie wyboru elementu
        arr[index] = false;

        // przejscie do nastepnego elementu
        return recPackage(t,max,tab,index+1);
    }

    // wypisanie wyniku rekurencji
    public static void printRec(int[] tab, int i, int index) {
        if (i >= index) {
            return; // przypadek bazowy: gdy przeszlismy przez wszystkie sprawdzane indeksy
        }
        if (arr[i]) {
            recSb.append(tab[i]);
            recSb.append(" ");
        }
        printRec(tab, i + 1, index); // rekurencyjne wywolanie dla nastepnego indeksu
    }

    // Iteracyjna symulacja rekurencji z wykorzystaniem stosu
    // wrzucamy na stos obiekt przechowujacy brakujaca sume i indeks
    public static boolean StackSimPackage(int max, MyStack1 stack, int[] tab, int numOfElems) {
        boolean[] stackArr = new boolean[numOfElems]; // tablica pasujacych indeksow
        stack.push(new Params(max, 0,stackArr));
        // wykonujemy operacje dopoki nie skoncza sie obiekty (czyli elementy tablicy)
        while (!stack.isEmpty()) {
            Params current = stack.pop();
            stackArr = current.stackArr;
            // gdy znaleziono podzbior, wypisujemy te indeksy ktore sa "true" w tablicy arr
            if (current.sum == 0) {
                StringBuilder sb = new StringBuilder();
                System.out.print("ITER: " + max + " = ");
                for (int i = 0; i < numOfElems; i++) {
                    if (stackArr[i]) {
                        sb.append(tab[i]);
                        sb.append(" ");
                    }
                }
                System.out.println(sb);
                return true;
            }
            // przypadke bazowy, gdy suma jest zbyt duza, lub wyszlismy poza zakres
            if (current.sum < 0 || current.index >= numOfElems) {
                continue;
            }
            // przesuniecie indeksu jezeli nie przekracza liczby elementow
            if (current.index + 1 <= numOfElems) {
                stack.push(new Params(current.sum, current.index + 1,Arrays.copyOf(stackArr,numOfElems)));
            }

            // sprawdzenie, czy biezacy element mozna dodac do sumy
            // kopiujemy aktualna tablice, wybieramy element na biezacej pozycji i pushujemy nowy obiekt z nowa tablica
            if (current.sum >= tab[current.index]) {
                boolean[] newStackArr = Arrays.copyOf(stackArr, numOfElems);
                newStackArr[current.index] = true;
                stack.push(new Params(current.sum - tab[current.index], current.index+1, newStackArr));
            }
        }
        return false;
    }


    // wczytanie ilosci zestawow, szukana liczbe, liczbe elementow tablicy , inicjalizacja tablic i wczytanie elementow do tablicy

    public static void main(String[] args) {
        int numOfSets = sc.nextInt();
        for (int i = 0; i < numOfSets; i++) {
            int max = sc.nextInt();
            int numOfElems = sc.nextInt();
            int[] tab = new int[numOfElems];
            arr = new boolean[numOfElems];
            recSb = new StringBuilder();
            for (int j = 0; j < numOfElems; j++) {
                int tmp = sc.nextInt();
                tab[j] = tmp;
            }
            // jezeli rekurencyjna funkcja nie znalazla rozwiazania to wypisujemy brak
            if(!recPackage(max,max,tab,0)) {
                System.out.println("BRAK");
            }
            else {
                MyStack1 stack = new MyStack1(5000);
                StackSimPackage(max,stack,tab,numOfElems);
            }
        }
    }
}
// TESTY:
//        4
//        9
//        6
//        6 2 3 4 6 9
//        13
//        4
//        13 8 5 11
//        14
//        3
//        3 5 9
//        149
//        13
//        19 14 120 24 137 58 76 110 98 97 70 33 145
//REC:  9 = 6 3
//ITER: 9 = 6 3
//REC:  13 = 13
//ITER: 13 = 13
//REC:  14 = 5 9
//ITER: 14 = 5 9
//REC:  149 = 19 97 33
//ITER: 149 = 19 97 33
