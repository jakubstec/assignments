// Jakub Steć gr. 6
import java.util.Scanner;

    /*
    Główną ideą tego algorytmu jest policzenie wszystkich możliwych trójkątów w zadanej tablicy intów
    ,oraz wypisanie indeksów zawierających odcinki w sposób leksykograficznie poprawny. Wykorzystuje w tym celu wyszukiwanie binarne oraz algorytm Quicksort.
    Przechodzimy po tablicy od lewej strony wykorzystujac "dwa wskazniki", czyli index najmniejszej oraz drugiej srodkowej dlugosci.
    wszystkie trzy boki musza spelniac warunki
    a + b > c
    a + c > b
    b + c > a
    algorytm znajduje indeks trzeciej wartosci za pomoca wyszukiwania binarnego. Mianowicie wiemy, ze tablica jest posortowana
    wiec dodajac do siebie 2 potencjalnie najmniejsze wartosci i odejmujac od nich 1, uzyskamy maksymalna liczbe ktora spelni warunki utworzenia trojkata
    (a + b > c), reszty nie musimy sprawdzac
    Majac tą maksymalna wartosc, mozemy wyszukac na jakim indeksie sie ona znajduje za pomoca funkcji SearchBinFirst
    nastepnie wykorzystujac rowniez funkcje SearchBinLast, dostajemy zakres w ktorym mieszcza sie liczby
    ktore spelniaja warunki dwoch odcinkow na zafiksowanych indeksach, ktore iterujemy poprzez zagniezdzone petle
    najglebsza petla while odpowiada wlasnie za przejscie po tym zakresie i zaktualizowaniu stringa ktory zwroci wynik 10 pierwszych trojkatow
    Przeszkoda w tym algorytmie jest przypadek, gdy SearchBinFirst/Last zwroci wartosc -1. oznacza to ze szukana wartosc nie zostala znaleziona
    nie oznacza to jednak, ze nie znajduje sie w tablicy odcinek ktory by spelnil nasze wymogi.
    W przypadku SearchBinFirst, mozemy trafic na indeks jednego z wytypowanych juz wczesniej odcinkow / badz odcinka
    ktory znajduje sie pomiedzy "wskaznikami", czyli takiego ktorego nie bierzemy aktualnie pod uwage,
    badz trafi sie przypadek gdy indexOfFirstBiggest == indexOfLastBiggest, badz inna niedokladnosc, gdzie nie odwzorowujemy pelnego zakresu,
    wiec nawet jak juz ustawimy index poprawnie poprzez poprzednia petle, badz cos innego pojdzie nie tak, to iterujac w dol o jeden dostaniemy
    indeks rowny pierwszemu wystapieniu
    W przypadku SearchBinLast, wiemy ze jest on potencjalnie najwiekszy
    wiec jedyna drogą, jest pomniejszanie go, az nie natrafimy na odpowiedni odcinek, badz wyjdziemy poza zakres liczb naturalnych wiekszych od zera.

    zlozonosc wynosi O(n^2 log n), poniewaz przechodzimy dwukrotnie przez n liczbe elementow tablicy ( czyli n*n) oraz wykorzystujemy wewnatrz binary search majacy zlozonosc logn
     */


public class Source {

    public static int[] arr; // deklaracja statycznej tablicy liczb calkowitych uzytej w algorytmie
    // zadeklarowana w ten sposob by mogla byc uzywana przez dowolna metode w tej klasie bez koniecznosci
    // przekazywania jej jako parametr


    // funkcja pomocnicza w algorytmie quicksort
    // mniejsze elementy wstawiamy na lewo od pivot'a, a wieksze na prawo
    public static int partition(int arr[], int begin, int end) {
        // ustalanie punktu odniesienia (pivot) na podstawie wartości na końcu podtablicy
        int pivot = arr[end];
        // inicjowanie zmiennej i na wartość o jeden mniejszą od początkowego indeksu tablicy
        int i = (begin-1);

        // iteracja od początkowego indeksu do końcowego-1
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                // sprawdzenie, czy wartość w aktualnej pozycji tablicy jest mniejsza lub równa od pivot
                i++;
                // zamiana wartości na pozycjach w tablicy
                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }
        // zamiana pivotu z wartością na pozycji i+1
        int swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;

        // zwrocenie indeksu pivotu
        return i+1;
    }
    public static void quickSort(int arr[], int begin, int end) {
        // sprawdzenie czy nie jest pusta
        if (begin < end) {
            // // wykonanie partycjonowania i uzyskanie indeksu pivotu
            int partitionIndex = partition(arr, begin, end);
            // rekurencyjne sortowanie lewej części tablicy
            quickSort(arr, begin, partitionIndex-1);
            // rekurencyjne sortowanie prawej części tablicy
            quickSort(arr, partitionIndex+1, end);
        }
    }

    // funkcja robiaca wciecia co 25 elementow tablicy
    public static void print(int n) {
        int i = 0;

        while(i<n) {
            if(i>=25 && (i % 25 == 0)) {
                System.out.println();
            }
            System.out.print(arr[i] + " ");
            i++;
        }
    }

    // algorytm binarySearch, z wykladu sluzacy do wyszukania pierwszego wystapienia danej liczby
    public static int SearchBinFirst(int x) {
        int low = 0; // dolna granica
        int upp = arr.length - 1; // gorna granica
        int curr; // aktualny indeks

        //
        while(low <= upp) { // gdy low > upp - tablica jest pusta / nie znaleziono liczby
            curr = (low + upp) / 2; // wyznaczamy srodek
            if(arr[curr] == x) {
                return curr; // znaleziony zostal element x
            }
            else if(arr[curr] < x) { // podzial przedzialu
                low = curr + 1; // szukaj x w prawej polowie
            }
            else {
                upp = curr - 1; // szukaj x w lewej polowie
            }
        }

        return -1; // jak brak x zwraca -1
    }

    // funkcja sluzaca do wyszukania ostatniego wystapienia danej liczby
    public static int SearchBinLast(int x) {
        int low = 0; // dolna granica
        int upp = arr.length - 1; // gorna granica
        int curr = -1; // aktualny indeks, początkowo -1
        while (low <= upp) { // gdy low > upp to znaczy ze tablica jest pusta / nie znaleziono szukanej liczby
            int mid = (low + upp) / 2; // wyznaczamy srodek
            // jezeli szukana wartosc znajduje sie na srodku
            // to przechodzimy do prawej polowy i tam znowu szukamy wartosci
            if (arr[mid] == x) {
                curr = mid;
                low = mid + 1;
                // sprawdz czy jest wieksza czy mniejsza, jezeli szukana wieksza od srodka do szukaj w prawej polowie
                // w przeciwnym wypadku przejdz do lewej i powtorz proces
            } else if (arr[mid] < x) { // podzial przedzialu
                low = mid + 1; // szukaj x w prawej polowie
            } else {
                upp = mid - 1; // szukaj x w lewej polowie
            }
        }

        return curr; // Zwróć ostatnie wystąpienie x lub -1, jeśli nie znaleziono
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // inicjalizacja skanera do odczytu danych wejsciowych
        int numOfSets = scanner.nextInt(); // zmienna odpowiadająca za ilosc zestawow danych

        for(int i = 1; i <= numOfSets; i++) { // petla przechodzaca przez kazdy zestaw danych
            int n = scanner.nextInt(); // wczytanie wielkosci tablicy
            arr = new int[n]; // inicjalizacja tablicy z potencjalnymi odcinkami
            // (czyli resetowana co zestaw danych

            String firstTenTriangles = "";

            // wczytanie bokow trojkatow do tablicy
            for(int j = 0; j < n; j++) {
                int tmp = scanner.nextInt();
                arr[j] = tmp;
            }

            // przypisanie dlugosci tablicy
            int arraySize = arr.length;

            // sortowanie tablicy odcinkow rosnaco
            quickSort(arr,0,arraySize-1);

            // zmienna odpowiada za laczna ilosc utworzonych trojkatow
            int trianglesCount = 0;

            // zmienna odpowiada za liczenie trojkatow, by nie przekroczyla ona ilosc 10
            // ma to znaczenie przy wypisaniu wyniku
            int countToTen = 0;


            // iteracja poprzez petle i zafiksowanie "wskaznikow"
            for(int indexOfSmallest = 0; indexOfSmallest < (arraySize-2); indexOfSmallest++) {
                for(int indexOfMid = indexOfSmallest + 1; indexOfMid < (arraySize - 1); indexOfMid++) {

                    int biggestPossible = arr[indexOfSmallest] + arr[indexOfMid] - 1; // zmienna odpowiadajaca za najwieksza mozliwa wartosc ktora spelni wymagania trojkata

                    int indexOfFirstBiggest = SearchBinFirst(biggestPossible); // zmienna odpowiadajaca za przechowywanie jej indeksu (pierwszego powtorzenia / pierwszej mozliwej wartosci)
                    int indexOfLastBiggest = SearchBinLast(biggestPossible); // zmienna odpowiadajaca za przechowywanie jej indeksu (ostatniego powtorzenia / najwiekszej mozliwej wartosci)


                    // w przypadku gdy nie znajdziemy takiej, zmniejszamy ja dopoki nie znajdziemy jakiejs liczby i aktualizujemy jej indeks lub jak bedzie mniejsza niz 1
                    while(indexOfLastBiggest == -1 && biggestPossible > 0) {
                        biggestPossible--;
                        indexOfLastBiggest = SearchBinLast(biggestPossible);
                        indexOfFirstBiggest = SearchBinFirst(biggestPossible);
                    }

                    // w przypadku, gdy znajdziemy taką liczbe ktora pasowalaby wedlug wymiarow, musimy sprawdzic czy nie znajduje sie ona pomiedzy "wskaznikami", czyli dwoma pierwszymi odcinkami
                    // badz indeks wskazuje na jeden z nich
                    // wiec iterujemy do tego momentu az trafimy na dobra wartosc i pierwszy pasujacy indeks
                    while(indexOfFirstBiggest != -1 && indexOfFirstBiggest <= indexOfMid) {
                        indexOfFirstBiggest++;
                    }

                    // w przypadku gdy nie obejmiemy calego zakresu to musimy cofac sie w lewo az nie bedziemy na indeksie pierwszego mozliwie pasujacego odcinka
                    while(indexOfFirstBiggest != -1 && indexOfFirstBiggest != (indexOfMid + 1)) {
                        indexOfFirstBiggest--;
                    }

                    // w przypadku gdy ostatnie powtorzenie trzeciego odcinka znajduje sie w posortowanej tablicy "nizej"
                    // niz indeks drugiego to znaczy ze nie znaleziono odcinka spelniajacego wymogi aktualnych "wskaznikow"
                    if(indexOfLastBiggest > indexOfMid) {
                        trianglesCount = trianglesCount + (indexOfLastBiggest - indexOfMid); // (indexOfLastBiggest - indexOfMid) to roznica od ostatniego powtorzenia / najwiekszej wartosci do indeksu drugiej wartosci ktora jest <= pierwszej
                        while(indexOfFirstBiggest <= indexOfLastBiggest) { // przechodzimy przez wszystkie z nich (zakres liczb spelniajacych, od najmniejszej do najwiekszej ktore spelniaja
                            if(countToTen<10) { // pilnujemy by nie przekroczyc 10, bo takie sa warunki zadania
                                firstTenTriangles += "(" + indexOfSmallest + "," + indexOfMid + "," + indexOfFirstBiggest + ") "; // aktualizujemy stringa o wspolrzedne
                                countToTen++; // iterujemy ilosc zapisanych juz wspolrzednych
                            }
                            else {
                                break;

                            }
                            indexOfFirstBiggest++; // przechodzimy o jeden dalej w naszym zakresie
                        }
                    }
                }
            }
            // wypisujemy wynik

            System.out.println(i + ": n= " + n);
            // wypisanie elementow tablicy (posortowanych z wcieciami co 25 linii)
            print(n);
            System.out.println();
            // jezeli nie zapisano zadnych wspolrzednych to znaczy ze nie znaleziono zadnego
            if(firstTenTriangles.isEmpty()) {
                System.out.println("Total number of triangles is: 0");
            }
            // w przeciwnym wypadku wypisujemy pelny wynik
            else {
                System.out.println(firstTenTriangles);
                System.out.println("Total number of triangles is: " + trianglesCount);
            }
        }
    }
}
//                10
//                4
//                15 11 17 8
//                4
//                10 5 5 19
//                20
//                16 19 9 20 5 14 16 7 17 5 18 20 14 4 7 18 3 17 17 17
//                10
//                13 5 11 16 9 11 11 17 3 9
//                4
//                14 5 3 12
//                20
//                8 15 20 16 2 15 2 19 1 19 7 19 2 1 20 1 5 12 3 13
//                18
//                15 15 15 17 9 5 12 14 16 11 19 17 1 9 12 18 2 10
//                8
//                17 4 16 8 16 16 18 11
//                14
//                7 1 7 9 15 1 4 13 11 14 14 19 15 6
//                20
//                14 11 2 7 17 9 12 19 17 13 1 19 18 3 19 2 13 14 16 14
//
//                1: n= 4
//                8 11 15 17
//                (0,1,2) (0,1,3) (0,2,3) (1,2,3)
//                Total number of triangles is: 4
//                2: n= 4
//                5 5 10 19
//                Total number of triangles is: 0
//                3: n= 20
//                3 4 5 5 7 7 9 14 14 16 16 17 17 17 17 18 18 19 20 20
//                (0,1,2) (0,1,3) (0,2,3) (0,2,4) (0,2,5) (0,3,4) (0,3,5) (0,4,5) (0,4,6) (0,5,6)
//                Total number of triangles is: 806
//                4: n= 10
//                3 5 9 9 11 11 11 13 16 17
//                (0,2,3) (0,2,4) (0,2,5) (0,2,6) (0,3,4) (0,3,5) (0,3,6) (0,4,5) (0,4,6) (0,4,7)
//                Total number of triangles is: 88
//                5: n= 4
//                3 5 12 14
//                (0,2,3) (1,2,3)
//                Total number of triangles is: 2
//                6: n= 20
//                1 1 1 2 2 2 3 5 7 8 12 13 15 15 16 19 19 19 20 20
//                (0,1,2) (0,3,4) (0,3,5) (0,4,5) (0,12,13) (0,15,16) (0,15,17) (0,16,17) (0,18,19) (1,3,4)
//                Total number of triangles is: 328
//                7: n= 18
//                1 2 5 9 9 10 11 12 12 14 15 15 15 16 17 17 18 19
//                (0,3,4) (0,7,8) (0,10,11) (0,10,12) (0,11,12) (0,14,15) (1,3,4) (1,3,5) (1,4,5) (1,5,6)
//                Total number of triangles is: 544
//                8: n= 8
//                4 8 11 16 16 16 17 18
//                (0,1,2) (0,3,4) (0,3,5) (0,3,6) (0,3,7) (0,4,5) (0,4,6) (0,4,7) (0,5,6) (0,5,7)
//                Total number of triangles is: 46
//                9: n= 14
//                1 1 4 6 7 7 9 11 13 14 14 15 15 19
//                (0,4,5) (0,9,10) (0,11,12) (1,4,5) (1,9,10) (1,11,12) (2,3,4) (2,3,5) (2,3,6) (2,4,5)
//                Total number of triangles is: 165
//                10: n= 20
//                1 2 2 3 7 9 11 12 13 13 14 14 14 16 17 17 18 19 19 19
//                (0,1,2) (0,8,9) (0,10,11) (0,10,12) (0,11,12) (0,14,15) (0,17,18) (0,17,19) (0,18,19) (1,2,3)
//                Total number of triangles is: 645