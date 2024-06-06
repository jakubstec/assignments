// Jakub Stec - grupa 6

/*
    4 funkcje recFirst recLast iterFirst iterLast osobno opisane.
 */

import java.util.Scanner;

public class Source {
    public static Scanner in = new Scanner(System.in);

    /*
    Funkcja przeszukuje tablice 2D zaczynajac od prawego gornego rogu
    idac w dol i w lewo uzywajac rekurencji. Jesli napotkamy wyszukiwany wyraz w danym wierszu
    to wykorzystujemy na nim binarne wyszukiwanie zeby znalezc pierwsze wystapienie,
    Jesli nie, to jako iz tablica jest posortowana i zaczynamy od wartosc mniejszych i prawej strony
    (co implikuje to ze wartosci na lewo beda niewieksze) to gdy napotkany wyraz bedzie wiekszy od szukanego,
    mamy pewnosc ze musi byc albo w tym wierszu albo nie ma go w tablicy, wiec ograniczamy nasz wiersz od prawej strony
    przesuwajac prawa granice w lewo o jeden. Jednakze gdy wartosc jest wieksza badz rowna, to przechodzimy do nastepnego wiersza i powtarzamy proces.

    Maksymalnie dla kazdego wiersza wykona sie jedno wyszukiwanie binarne,
    wiec zlozonosc wynosi O(m * logn) gdzie
    m - liczba wierszy
    n - liczba kolumn
     */
    public static String recFirst(int[][] arr, int x, int row, int column) {
        if (column < 0 || row >= arr.length) {
            return x + " missing in array"; // warunek stopu gdy przeszlismy cala tablice (wszystkie wiersze albo kolumny)
        }
        if (arr[row][column] == x) {
            int tmpColumn = SearchBinFirst(x,arr[row],0,column); // wywolanie szukania binarnego rekurencyjnie
            return x + " = a[" + row + "][" + tmpColumn + "]"; // wypisanie wyniku
        } else if (arr[row][column] > x) { // jesli wartosc jest napotkana wartosc jest wieksza od szukanej to zawezamy przedzial od prawej strony
            return recFirst(arr, x, row, column - 1);
        } else { // w przeciwnym przypadku przechodzimy do nastepnego wiersza
            return recFirst(arr, x, row + 1, column);
        }
    }

    /*
    Funkcja przeszukuje tablice 2D zaczynajac od lewego dolnego rogu
    idac w gore i w prawo uzywajac rekurencji. Jesli napotkamy wyszukiwany wyraz w danym wierszu
    to wykorzystujemy na nim binarne wyszukiwanie zeby znalezc ostatnie wystapienie,
    Jesli nie, to jako iz tablica jest posortowana i zaczynamy od wartosci wiekszych i lewej strony
    (co implikuje to ze wartosci na prawo beda wieksze) to gdy napotkany wyraz bedzie niewiekszy od szukanego,
    mamy pewnosc ze musi byc albo w tym wierszu albo nie ma go w tablicy, wiec ograniczamy nasz wiersz od lewej strony
    przesuwajac lewa granice w prawo o jeden. Jednakze gdy wartosc jest wieksza, to przechodzimy do poprzedniego wiersza i powtarzamy proces.

    Maksymalnie dla kazdego wiersza wykona sie jedno wyszukiwanie binarne,
    wiec zlozonosc wynosi O(m * logn) gdzie
    m - liczba wierszy
    n - liczba kolumn
     */
    public static String recLast(int[][] arr, int x, int row, int column) {
        if (column >= arr[0].length || row < 0) {
            return x + " missing in array"; // warunek stopu gdy przeszlismy cala tablice (wszystkie wiersze albo kolumny)
        }
        if (arr[row][column] == x) {
            int tmpColumn = SearchBinLast(x,arr[row],column,arr[0].length-1); // wywolanie szukania binarnego rekurencyjnie
            return x + " = a[" + row + "][" + tmpColumn + "]"; // zwrocenie wyniku
        } else if (arr[row][column] > x) {
            return recLast(arr, x, row - 1, column); // jesli wartosc jest napotkana wartosc jest wieksza od szukanej to przechodzimy do poprzedniego wiersza
        } else { // w przeciwnym przypadku zawezamy przedzial z lewej strony
            return recLast(arr, x, row, column + 1);
        }
    }

    /* Zmodyfikowana funkcja binarnego wyszukiwania sluzaca do znalezenia pierwszego
wystapienia szukanego wyrazu w danym rzedzie. wykorzystuje ona rekurencje do przesuwania sie
granic lewej i prawej strony wiersza zaleznie od tego, czy srodkowa wartosc tablicy jest mniejsza/wieksza od wyszukiwanej
wtedy ograniczamy ja z lewej/prawej strony, az do pierwszego potencjalnego wystapienia.
 */
    public static int SearchBinFirst(int x, int[] arr, int low, int upp) {
        if(low > upp) {
            return -1; // warunek stopu gdy nie znaleziono wartosci
        }
        int mid = (low + upp) / 2; // indeks wartosci srodkowej
        if(arr[mid] == x) { // jesli znaleziono szukana wartosc
            if(mid == low || arr[mid - 1] != x) { // sprawdzamy czy nie ma juz potencjalnego indeksu
                return mid; // zwracamy wynik
            }
            else {
                return SearchBinFirst(x,arr,low,mid-1); // zawezamy z prawej strony i szukamy dalej
            }
        } else if(arr[mid] > x) { // jesli srodek jest wiekszy od szukanej to zawezamy z prawej
            return SearchBinFirst(x,arr,low,mid - 1);
        }
        else {
            return SearchBinFirst(x,arr,mid + 1,upp); // w innym przypadku z lewej
        }
    }


    /* Zmodyfikowana funkcja binarnego wyszukiwania sluzaca do znalezenia ostatniego
    wystapienia szukanego wyrazu w danym rzedzie. wykorzystuje ona rekurencje do przesuwania sie
    granic lewej i prawej strony wiersza zaleznie od tego, czy srodkowa wartosc tablicy jest mniejsza/wieksza od wyszukiwanej
    wtedy ograniczamy ja z lewej/prawej strony, az do ostatniego potencjalnego wystapienia.
     */
    public static int SearchBinLast(int x, int[] arr, int low, int upp) {
        if(low > upp) {
            return -1; // warunek stopu gdy nie znaleziono wartosci
        }

        int mid = (low + upp) / 2; // indeks wartosci srodkowej

        if(arr[mid] == x) { // jesli znaleziono szukana wartosc
            if(mid == upp || arr[mid+1] != x) { // sprawdzamy czy nie ma juz potencjalnego indeksu
                return mid; // zwracamy wynik
            } else {
                return SearchBinLast(x,arr,mid+1,upp); // zawezamy z lewej strony i szukamy dalej
            }
        } else if(arr[mid] > x) { //jesli srodek jest wiekszy od szukanej to zawezamy z prawej
            return SearchBinLast(x,arr,low,mid-1);
        } else {
            return SearchBinLast(x,arr,mid+1,upp); // w innym przypadku z lewej
        }
    }

    /*
    Funkcja przeszukuje tablice 2D zaczynajac od prawego gornego rogu
    idac w dol i w lewo uzywajac iteracji. Jesli napotkamy wyszukiwany wyraz w danym wierszu
    to wykorzystujemy na nim binarne wyszukiwanie zeby znalezc pierwsze wystapienie,
    Jesli nie, to jako iz tablica jest posortowana i zaczynamy od wartosc mniejszych i prawej strony
    (co implikuje to ze wartosci na lewo beda niewieksze) to gdy napotkany wyraz bedzie wiekszy od szukanego,
    mamy pewnosc ze musi byc albo w tym wierszu albo nie ma go w tablicy, wiec ograniczamy nasz wiersz od prawej strony
    przesuwajac prawa granice w lewo o jeden. Jednakze gdy wartosc jest wieksza badz rowna, to przechodzimy do nastepnego wiersza i powtarzamy proces.

    Maksymalnie dla kazdego wiersza wykona sie jedno wyszukiwanie binarne,
    wiec zlozonosc wynosi O(m * logn) gdzie
    m - liczba wierszy
    n - liczba kolumn
     */
    public static String iterFirst(int[][] arr, int x) {
        int row = 0;
        int column = arr[0].length - 1;
        int tmpColumn = -1;

        while( row < arr.length && column >= 0) { // dopoki indeksy znajduja sie w wymiarach tablicy 2D
            if(arr[row][column] == x ) { // jesli znaleziono
                tmpColumn = SearchBinFirst(x,arr[row],0,column); // wywyolujemy wyszukiwanie binarne
                return x + " = a[" + row + "][" + tmpColumn + "]"; // zwracamy wynik
            } else if (arr[row][column] > x) { // jesli wartosc jest wieksza od szukanej zawezamy kolumny
                column--;
            }
            else {
                row++; // w przeciwnym wypadku przechodzimy do nastepnego wiersza
            }
        }
        return x + " missing in array"; // jesli przeszlismy cala tablice i nie bylo wartosci
    }

    /*
    Funkcja przeszukuje tablice 2D zaczynajac od lewego dolnego rogu
    idac w gore i w prawo uzywajac iteracji. Jesli napotkamy wyszukiwany wyraz w danym wierszu
    to wykorzystujemy na nim binarne wyszukiwanie zeby znalezc ostatnie wystapienie,
    Jesli nie, to jako iz tablica jest posortowana i zaczynamy od wartosci wiekszych i lewej strony
    (co implikuje to ze wartosci na prawo beda wieksze) to gdy napotkany wyraz bedzie niewiekszy od szukanego,
    mamy pewnosc ze musi byc albo w tym wierszu albo nie ma go w tablicy, wiec ograniczamy nasz wiersz od lewej strony
    przesuwajac lewa granice w prawo o jeden. Jednakze gdy wartosc jest wieksza, to przechodzimy do poprzedniego wiersza i powtarzamy proces.

    Maksymalnie dla kazdego wiersza wykona sie jedno wyszukiwanie binarne,
    wiec zlozonosc wynosi O(m * logn) gdzie
    m - liczba wierszy
    n - liczba kolumn
     */
    public static String iterLast(int[][] arr, int x) {
        int row = arr.length - 1;
        int column = 0;
        int tmpColumn = -1;

        while( row >= 0 && column < arr[0].length) {  // dopoki indeksy znajduja sie w wymiarach tablicy 2D
            if(arr[row][column] == x ) { // jesli znaleziono
                tmpColumn = SearchBinLast(x,arr[row],column,arr[0].length-1); // wywolujemy wyszukiwanie binarne
                return x + " = a[" + row + "][" + tmpColumn + "]"; // zwracamy wynik
            } else if (arr[row][column] < x) { // jesli wartosc jest mniejsza od szukanej to zawezamy kolumny
                column++;
            }
            else {
                row--; // w przeciwnym wypadku przechodzimy do poprzedniego wiersza
            }
        }
        return x + " missing in array"; // jesli przeszlismy cala tablice i nie bylo wartosci
    }



    public static void main(String[] args) {
        int numOfSets = in.nextInt(); // wczytanie ilosci zestawow

        //
        for (int i = 0; i < numOfSets; i++) {
           // wczytanie wymiarow tablicy 2D i inicjalizacja jej
            int rows = in.nextInt();
            int columns = in.nextInt();
            int[][] array = new int[rows][columns];

            // wypelnienie tablicy 2D
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < columns; k++) {
                    array[j][k] = in.nextInt();
                }
            }
            // wczytanie szukanej liczby
            int x = in.nextInt();

            // wywolanie funkcji i wypisanie wynikow
            System.out.println("recFirst: " + recFirst(array,x,0,array[0].length - 1)); // prawy gorny rog
            System.out.println("recLast: " + recLast(array,x, array.length - 1, 0)); // lewy dolny rog
            System.out.println("iterFirst: " + iterFirst(array,x));
            System.out.println("iterLast: " + iterLast(array,x));
            System.out.println("---");
        }
    }
}
//        TESTY
//        10
//        3 4
//        10 15 20 25
//        20 25 30 35
//        30 35 40 45
//        20
//        2 3
//        5 10 15
//        20 25 30
//        20
//        4 4
//        10 15 20 25
//        15 20 25 30
//        20 25 30 35
//        25 30 35 40
//        25
//        3 3
//        10 20 30
//        20 30 40
//        30 40 50
//        25
//        2 2
//        10 20
//        20 30
//        15
//        2 2
//        10 20
//        20 30
//        25
//        1 1
//        10
//        10
//        3 3
//        10 20 30
//        15 25 35
//        20 30 40
//        40
//        3 3
//        10 15 20
//        15 20 25
//        20 25 30
//        35
//        4 3
//        10 15 20
//        20 25 30
//        30 35 40
//        40 45 50
//        35
//
//recFirst: 20 = a[0][2]
//recLast: 20 = a[1][0]
//iterFirst: 20 = a[0][2]
//iterLast: 20 = a[1][0]
//---
//recFirst: 20 = a[1][0]
//recLast: 20 = a[1][0]
//iterFirst: 20 = a[1][0]
//iterLast: 20 = a[1][0]
//---
//recFirst: 25 = a[0][3]
//recLast: 25 = a[3][0]
//iterFirst: 25 = a[0][3]
//iterLast: 25 = a[3][0]
//---
//recFirst: 25 missing in array
//recLast: 25 missing in array
//iterFirst: 25 missing in array
//iterLast: 25 missing in array
//---
//recFirst: 15 missing in array
//recLast: 15 missing in array
//iterFirst: 15 missing in array
//iterLast: 15 missing in array
//---
//recFirst: 25 missing in array
//recLast: 25 missing in array
//iterFirst: 25 missing in array
//iterLast: 25 missing in array
//---
//recFirst: 10 = a[0][0]
//recLast: 10 = a[0][0]
//iterFirst: 10 = a[0][0]
//iterLast: 10 = a[0][0]
//---
//recFirst: 40 = a[2][2]
//recLast: 40 = a[2][2]
//iterFirst: 40 = a[2][2]
//iterLast: 40 = a[2][2]
//---
//recFirst: 35 missing in array
//recLast: 35 missing in array
//iterFirst: 35 missing in array
//iterLast: 35 missing in array
//---
//recFirst: 35 = a[2][1]
//recLast: 35 = a[2][1]
//iterFirst: 35 = a[2][1]
//iterLast: 35 = a[2][1]
//---
