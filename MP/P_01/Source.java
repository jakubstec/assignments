// Jakub Steć - nr grupy 6

import java.util.Scanner;

/*
Ogólna idea tego algorytmu polega na skanowaniu tablicy w poszukiwaniu maksymalnej sumy podtablicy dwuwymiarowej.
Algorytm ten jest skonstruowany w taki sposób, aby móc efektywnie obliczać sumę podtablicy, wykorzystujac m.in zmodyfikowany algorytm Kadane, który pozwala zredukować złożoność czasową do O( (max(n, m))^3 ).
Liczymy maksymalna sume rozpiętą na kolumnach, ograniczajac "prostokat" (potoczne okreslenie na podtablice) z lewej i z prawej strony. Nastepnie rozszerzamy ją o górną i dolną granice, w poszukiwaniu maksymalnej podtablicy
Zachowane zostaje przy tym sprawdzanie warunkow, ktore przypilnuja by algorytm zwrocil podtablice o jak najmniejszej liczbie elementow, a sposob w ktory funkcjonuje zapewnia nam to, ze indeksy i,j,k,l zawsze beda tworzyc ciag
leksykograficznie najmniejszy.
Gdy algorytm przejdzie przez caly zakres danych, wypisze w konsoli informacje o indeksach i sumie podtablicy, a nastepnie przejdzie do sprawdzania kolejnego zestawu danych.
 */


class Source {

    // Metoda obliczająca maksymalną sumę podtablicy jednowymiarowej, sprawdzamy maksymalna podtablice na kolumnach jednowymiarowych
    public static int maxColumnSubArraySum(int[] rowArray, int[] indexes) {

        int maxColumnSum = 0; // maksymalna suma podtablicy
        int maxColumnSumTmp = 0; // aktualna suma podtablicy
        int maxStart = 0; // początkowy indeks maksymalnej podtablicy
        int maxEnd = 0; // końcowy indeks maksymalnej podtablicy
        int maxTmpStart = 0; // tymczasowy początkowy indeks
        int minSize = 1; // minimalny rozmiar
        int minTmpSize = 1; // tymczasowy minimalny rozmiar


        for (int i = 0; i < rowArray.length; i++) {

            // Dodawanie aktualnego elementu do tymczasowej sumy
            // i zwiekszenie tymczasowej liczby elementow w podtablicy
            maxColumnSumTmp = maxColumnSumTmp + rowArray[i];
            minTmpSize++;


            // Aktualizacja indeksu początkowego tymczasowej podtablicy, jeśli obecna suma jest równa maksymalnej sumie
            // oraz obecny element jest równy 0, a liczba elementów w tymczasowej podtablicy jest mniejsza niż minimalna
            // szukamy podtablicy o najmniejszej liczbie elementow wiec omijamy zera, poniewaz nie zmieniaja nic w naszym wyniku
            if (maxColumnSumTmp == maxColumnSum && rowArray[i] == 0 && minTmpSize < minSize) {
                maxTmpStart = i + 1; // Aktualizacja tymczasowego początkowego indeksu, przesuwamy sie o jeden
                minTmpSize--; // Zmniejszenie tymczasowej liczby elementów w podtablicy, wyrzucamy redundantne elementy
            }

            // Aktualizacja maksymalnej sumy i indeksów, jeśli obecna suma jest większa niż maksymalna suma
            // lub obecna suma jest równa maksymalnej sumie, ale liczba elementów w podtablicy jest mniejsza niż minimalna
            if (maxColumnSumTmp > maxColumnSum || (maxColumnSumTmp == maxColumnSum && minTmpSize < minSize)) {
                maxColumnSum = maxColumnSumTmp; // Aktualizacja maksymalnej sumy podtablicy
                maxStart = maxTmpStart; // Aktualizacja początkowego indeksu maksymalnej podtablicy
                maxEnd = i; // Aktualizacja końcowego indeksu maksymalnej podtablicy
                minSize = minTmpSize; // Aktualizacja minimalnej liczby elementów w maksymalnej podtablicy
            }

            // Resetowanie tymczasowej sumy i aktualizacja początkowego indeksu, jeśli obecna suma jest mniejsza lub równa 0
            // niepotrzebujemy ujemnej sumy wiec wracamy do wartosci domyslnej
            if (maxColumnSumTmp <= 0) {
                maxColumnSumTmp = 0;
                maxTmpStart = i + 1;
            }

        }
        indexes[0] = maxStart; // przypisanie wspolrzednej k, odpowiadajacej za lewa granice
        indexes[1] = maxEnd; // przypisanie wspolrzednej l, odpowiadacej za prawa granice
        return maxColumnSum; // zwrocenie sumy
    }

    public static void maxSumSubarray2D(int[][] array2d, int height, int width, int numOfSet) {
        int maxSum2d = 0;
        int minSize = 0;
        int[] startendIndexes = new int[4];
        int[] tmpIndexes = new int[2];
        startendIndexes[0] = startendIndexes[1] = startendIndexes[2] = startendIndexes[3] = 0;

        boolean onlyZeros = true;
        boolean onlyNeg = true;
        boolean positive = false;
        boolean quit = false;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // sprawdzamy czy mamy doczynenia z tablica samych ujemnych liczb, zerowych, lub niedodatnich
                if (array2d[i][j] > 0) {
                    onlyNeg = false;
                    onlyZeros = false;
                    positive = true;
                }
                if (array2d[i][j] < 0) {
                    onlyZeros = false;
                }

                if (array2d[i][j] == 0) {
                    onlyNeg = false;
                }
            }
        }
        // jezeli sa same zera
        if (onlyZeros) {
            System.out.println(numOfSet + ": ms_tab = a[0..0][0..0], msum=0");
            return;
        }
        // jezeli same ujemne
        if (onlyNeg) {
            System.out.println(numOfSet + ": ms_tab is empty");
            return;
        }


        // case w ktorym sa jedynie elementy ujemne i zerowe, szukamy leksykograficznie zerowego i wychodzimy z petli
        if (!positive) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (array2d[i][j] == 0) {
                        startendIndexes[0] = i;
                        startendIndexes[1] = i;
                        startendIndexes[2] = j;
                        startendIndexes[3] = j;
                        quit = true;
                        break;
                    }
                }
                if (quit) {
                    break;
                }
            }
        }

        // iteracja po tablicy 2D
        for (int i = 0; i < height; i++) {
            int[] tmp = new int[width]; // tablica pomocnicza kolumn 1d
            for (int j = i; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    tmp[k] = tmp[k] + array2d[j][k]; // aktualizacja tablicy pomocniczej
                }
                // wywolanie przerobionego algorytmu kadane na tablicy kolumn 1d
                int maxSum2dTmp = maxColumnSubArraySum(tmp, tmpIndexes);
                // Aktualizacja maksymalnej sumy i rozmiaru, jeśli znaleziono większą sumę lub sumy są równe, ale rozmiar jest mniejszy
                // szukanie zwyciescy, najlepszej podtablicy wg wymagan zadania
                if (maxSum2dTmp > maxSum2d || (maxSum2dTmp == maxSum2d && (j - i + 1) * width < minSize)) {
                    maxSum2d = maxSum2dTmp;
                    minSize = (j - i + 1) * width;
                    startendIndexes[0] = i; // gorna granica
                    startendIndexes[1] = j; // dolna granica
                    startendIndexes[2] = tmpIndexes[0]; // lewa granica
                    startendIndexes[3] = tmpIndexes[1]; // prawa granica
                }
            }
        }
        // zwrocenie wyniku
        System.out.println(numOfSet + ": ms_tab = a[" + startendIndexes[0] + ".." + startendIndexes[1] + "][" + startendIndexes[2] + ".." + startendIndexes[3] + "], msum=" + maxSum2d);
    }

    public static void main(String[] args) {
        // Pobieramy dane od uzytkownika
        Scanner scanner = new Scanner(System.in);
        int numOfSets = scanner.nextInt(); // pobieramy ilosc zestawow
        int numOfSet, height, width; //
        scanner.nextLine();
        while (numOfSets > 0) { // petla wykona sie tyle razy ile jest zestawow
            numOfSet = scanner.nextInt(); // pobieramy numer zestawu
            scanner.next(); // " : "
            height = scanner.nextInt(); // pobieramy wysokosc tablicy
            width = scanner.nextInt(); // pobieramy szerokosc tablicy
            int[][] array2d = new int[height][width]; // tworzymy tablice 2D

            // wypelniamy tablice wedlug przelicznika z tresci zadania ( 3 razy liczba dodatnia, 2 razy liczba ujemna)
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int tmp = scanner.nextInt();
                    if (tmp > 0) {
                        array2d[i][j] = 3 * tmp;
                    } else {
                        array2d[i][j] = 2 * tmp;
                    }
                }
            }
            // wywolujemy funkcje
            maxSumSubarray2D(array2d, height, width, numOfSet);
            // dekrementacja liczby zestawow
            numOfSets--;
        }
    }
}
//                20
//                1 : 4 9
//                0 -4 0 -1 4 4 1 -4 -2
//                -2 0 4 -3 2 0 -4 1 -4
//                -3 -3 4 4 -1 3 4 -2 -4
//                1 -4 0 -2 -2 -2 -4 -4 4
//                2 : 6 9
//                -2 -2 0 1 0 -3 1 0 -3
//                -3 1 0 -1 0 -3 4 0 2
//                -1 0 2 4 1 3 4 -2 -2
//                -3 -3 2 0 -1 0 0 3 0
//                0 4 3 0 0 0 0 -1 0
//                0 3 4 2 4 0 -2 0 3
//                3 : 2 10
//                0 -1 -4 -2 -2 -1 3 -4 -2 0
//                -2 2 0 -1 -1 -2 0 0 3 1
//                4 : 5 9
//                4 4 0 0 -2 -4 3 2 -1
//                3 0 4 4 -2 2 -4 -4 4
//                -2 2 4 -4 2 -1 0 2 -2
//                0 -1 1 4 2 3 4 -2 4
//                -4 4 -2 -3 4 0 4 -4 -1
//                5 : 5 2
//                1 -2
//                -1 -4
//                -4 -2
//                0 2
//                -2 0
//                6 : 7 3
//                -4 -3 -2
//                4 1 1
//                -2 -3 -2
//                4 1 4
//                3 4 4
//                4 -2 -2
//                4 -3 -1
//                7 : 8 6
//                -4 2 1 0 -4 -2
//                -4 4 -2 -3 4 -2
//                3 0 3 0 -1 1
//                -2 -3 2 -2 -4 -2
//                1 -3 2 -2 -4 0
//                -3 -2 -2 4 0 4
//                4 -3 -3 2 2 3
//                2 -4 -1 -3 -1 0
//                8 : 6 6
//                3 3 -4 1 -1 2
//                -1 1 3 -3 0 -2
//                -3 -1 2 -3 2 -3
//                -3 3 -1 3 4 0
//                0 -2 1 1 0 1
//                0 1 -2 0 2 -1
//                9 : 4 5
//                4 -3 1 0 -2
//                0 -2 -3 3 0
//                4 -1 2 1 4
//                0 3 -4 -2 1
//                10 : 5 1
//                -1
//                4
//                -3
//                -3
//                2
//                11 : 8 2
//                -3 0
//                2 -1
//                2 1
//                -1 3
//                -1 4
//                0 0
//                -1 3
//                1 2
//                12 : 7 1
//                1
//                2
//                -4
//                -1
//                1
//                0
//                3
//                13 : 4 9
//                0 -4 0 -1 4 4 1 -4 -2
//                -2 0 4 -3 2 0 -4 1 -4
//                -3 -3 4 4 -1 3 4 -2 -4
//                1 -4 0 -2 -2 -2 -4 -4 4
//                14 : 6 9
//                -2 -2 0 1 0 -3 1 0 -3
//                -3 1 0 -1 0 -3 4 0 2
//                -1 0 2 4 1 3 4 -2 -2
//                -3 -3 2 0 -1 0 0 3 0
//                0 4 3 0 0 0 0 -1 0
//                0 3 4 2 4 0 -2 0 3
//                15 : 2 10
//                0 -1 -4 -2 -2 -1 3 -4 -2 0
//                -2 2 0 -1 -1 -2 0 0 3 1
//                16 : 5 9
//                4 4 0 0 -2 -4 3 2 -1
//                3 0 4 4 -2 2 -4 -4 4
//                -2 2 4 -4 2 -1 0 2 -2
//                0 -1 1 4 2 3 4 -2 4
//                -4 4 -2 -3 4 0 4 -4 -1
//                17 : 5 2
//                1 -2
//                -1 -4
//                -4 -2
//                0 2
//                -2 0
//                18 : 7 3
//                -4 -3 -2
//                4 1 1
//                -2 -3 -2
//                4 1 4
//                3 4 4
//                4 -2 -2
//                4 -3 -1
//                19 : 8 6
//                -4 2 1 0 -4 -2
//                -4 4 -2 -3 4 -2
//                3 0 3 0 -1 1
//                -2 -3 2 -2 -4 -2
//                1 -3 2 -2 -4 0
//                -3 -2 -2 4 0 4
//                4 -3 -3 2 2 3
//                2 -4 -1 -3 -1 0
//                20 : 6 6
//                3 3 -4 1 -1 2
//                -1 1 3 -3 0 -2
//                -3 -1 2 -3 2 -3
//                -3 3 -1 3 4 0
//                0 -2 1 1 0 1
//                0 1 -2 0 2 -1
//
//        1: ms_tab = a[0..2][2..6], msum=72
//        2: ms_tab = a[1..5][1..8], msum=117
//        3: ms_tab = a[1..1][8..9], msum=12
//        4: ms_tab = a[0..4][0..8], msum=124
//        5: ms_tab = a[3..3][1..1], msum=6
//        6: ms_tab = a[1..6][0..2], msum=72
//        7: ms_tab = a[5..6][3..5], msum=45
//        8: ms_tab = a[0..5][1..4], msum=47
//        9: ms_tab = a[0..2][0..4], msum=35
//        10: ms_tab = a[1..1][0..0], msum=12
//        11: ms_tab = a[1..7][0..1], msum=46
//        12: ms_tab = a[4..6][0..0], msum=12
//        13: ms_tab = a[0..2][2..6], msum=72
//        14: ms_tab = a[1..5][1..8], msum=117
//        15: ms_tab = a[1..1][8..9], msum=12
//        16: ms_tab = a[0..4][0..8], msum=124
//        17: ms_tab = a[3..3][1..1], msum=6
//        18: ms_tab = a[1..6][0..2], msum=72
//        19: ms_tab = a[5..6][3..5], msum=45
//        20: ms_tab = a[0..5][1..4], msum=47