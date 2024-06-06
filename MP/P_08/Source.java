// Jakub Stec - grupa 6
import java.util.Scanner;


/*  IDEA:
Program implementuje rekurencyjny algorytm Magicznych piatek, inaczej mediane median
sluzacy do znalezenia k-tego elementu co do wielkosci tablicy w czasie O(n).
Polega on na podziale tablicy na 5 elementowe tablice (ostatnia podtablica moze byc mniejsza niz 5)
i wyznaczeniu w kazdej podtablicy mediany (sortujemy elementy podtablicy i wyznaczamy mediane (lewa_granica + prawa_granica) / 2
nastepnie te mediany umieszczamy kolejno na poczatkowych indeksach glownej tablicy / ograniczonej podtablicy z lewej strony
[bo mozemy przeszukiwac prawa strone] w celu obliczenia mediany z owych median co bedzie naszym pivotem. ustawiamy te indeksy w ten sposob by nie wykorzystac
dodatkowej pamieci. Nastepnie wykonujemy partycjonowanie [lomuto], i sprawdzamy czy ilosc elementow po lewej stronie
(miedzy lewa granica a pivotem) jest mniejsza od k-tego elementu, wtedy dzielimy tablice/podtablice i przechodzimy do lewej czesci
w innym przypadku sprawdzamy prawa, az do momentu az znajdziemy k-ty element.
 */
public class Source {

    public static Scanner sc = new Scanner(System.in);

    public static int[] tab;// glowna tablica liczb
    public static int[] tab2; // tablica k-tych szukanych elementow


    // implementacja algorytmu sortowania przez wstawianie
    // sortuje piatki oraz mediany w celu obliczenia mediany median
    public static void insertionSort(int low, int high) {
        for(int i = low + 1; i <= high; i++) {
            int temp = tab[i];
            int j = i - 1;
            while (j >= low && tab[j] > temp) {
                tab[j + 1] = tab[j];
                j--;
            }
            tab[j + 1] = temp;
        }
    }

    // funkcja do obliczania mediany piatek, i wrzucanie ich na poczatkowe indeksy tablicy/podtablicy glownej
    public static int singleMedian(int low, int high, int j) {
        insertionSort(low, high);
        int idx = low + (high - low) / 2;
        int median = tab[idx];
        swap(j, idx);
        return median;
    }
    // glowna funkcja rekurencyjna
    public static int medianOfMedians(int low, int high, int k) {
        // obliczenie ilosci elementow tablicy/podtablicy
        int n = high - low + 1;
// jezeli podalismy zla liczbe / jest wieksza niz ilosc elementow tablicy to zwracamy -1
        if(k>0 && k <= n) {

            if(low==high) {
                return tab[high];
            }
            int numOfFives = n/5;
            int rest = n - (numOfFives*5);
            int i = 0;
            int j = low;
            // dzielimy elementy na podgrupy 5 elementowe i obliczamy mediany, wrzucajac
            // je na poczatkowe indeksy
            while(i < numOfFives) {
                singleMedian(low+i*5,low+i*5+4,j);
                i++;
                j++;
            }
            // obliczenie mediany z reszty elementow ( 0 < n < 5)
            if(rest > 0) {
                if(i == 0) {
                    int m = singleMedian(low+i*5,low+i*5+rest-1,j);
                }
                else {
                    singleMedian(low+i*5,low+i*5+rest-1,j);
                    i++;
                    j++;
                }
            }
            int medOfMedians;
            // jezeli tablica/podtablica miala 0 < n <= 5 elementow
            // nie musimy wywolywac funkcji rekurencyjnie, tylko wziac element z pierwszego indeksu tablicy

            if(i == 1) {
                medOfMedians = tab[low];
            }
            else {
                // w przeciwnym wypadku obliczamy rekurencyjnie mediane median (ograniczamy sie do poczatkowych
                // indeksow tablicy bo wiemy ze mamy tam zeswapowane wczesniej mediany)
                medOfMedians = singleMedian(low,j-1,j);
            }

            // partycjonujemy cala tablice/podtablice
            int partition = partition(low,high,medOfMedians);

            // jezeli ilosc elementow pomiedzy lewa granica a indeksem pivota jest rowna (k - 1) to znalezlismy szukany k-ty element.
            if(partition-low == k - 1) {
                return tab[partition];
            }

            // jezeli wieksza, to przeszukujemy lewa podtablice
            if(partition-low >= k - 1) {
                return medianOfMedians(low, partition - 1, k);
            }
            // w innym przypadku przeszukujemy prawa i zmieniamy k, odejmujac ilosc elementow po lewej
            return medianOfMedians(partition+1,high, k - partition + low - 1);

        }
        else {
            return -1;
        }
    }

    // partycja lomuto
    public static int partition(int low, int high, int pivot) {
        int pivotIndex = -1;
        // przesuwamy sie az do pivota
        for (int i = low; i <= high; i++) {
            if (tab[i] == pivot) {
                pivotIndex = i;
                break;
            }
        }
        // jezeli nie znajdziemy to zwracamy low
        if(pivotIndex == -1) return low;
        // swapujemy pivot na koniec
        swap(pivotIndex, high);

        // dokonujemy partycji lomuto (na prawo od pivota mniejsze, na lewo wieksze)
        int index = low;
        for (int i = low; i <= high - 1; i++) {
            if (tab[i] < pivot) {
                swap(index, i);
                index++;
            }
        }
        // przywracamy pivot na dobry indeks
        swap(index, high);
        return index; // zwracamy pivot
    }
    public static void swap(int i, int j) {
        // funkcja do swapowania
        int tmp = tab[i];
        tab[i] = tab[j];
        tab[j] = tmp;
    }

    public static void main(String[] args) {
        int numOfSets = sc.nextInt();
        for (int i = 0; i < numOfSets; i++) {
            int numOfElems = sc.nextInt();
            // wczytanie tablicy elementow
            tab = new int[numOfElems];
            for (int j = 0; j < numOfElems; j++) {
                int tmp = sc.nextInt();
                tab[j] = tmp;
            }
            int numOfSelected = sc.nextInt();
            // wczytanie szukanych k-tych elementow
            tab2 = new int[numOfSelected];
            for (int j = 0; j < numOfSelected; j++) {
                int tmp = sc.nextInt();
                tab2[j] = tmp;
            }
            for(int k = 0; k < numOfSelected; k++) {
                int res = medianOfMedians(0,tab.length-1, tab2[k]);
                // jezeli nie znaleziono k-tego elementu (zly input/za mala tablica/ujemna liczba) to funkcja zwraca -1
                if(res == -1) {
                    System.out.println(tab2[k] + " brak");
                }
                else {
                    // w przeciwnym wypadku wypisujemy k-ty element
                    System.out.println(tab2[k] + " " + res);
                }
            }
        }
    }
}
//5
//        60
//        84 76 40 97 11 21 77 45 76 67 7 32 6 37 77 37 78 63 82 87 78 63 5 44 88 75 38 48 33 70 52 52 12 55 91 11 56 15 12 76 85 83 32 84 43 80 83 28 60 88 75 16 29 78 100 22 93 73 86 19
//        63
//        -1 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61
//        51
//        61 66 57 30 58 36 77 34 3 38 6 37 81 13 47 61 83 35 59 60 1 38 32 70 54 3 83 11 93 11 4 93 67 54 15 29 89 97 63 11 86 21 75 94 60 69 43 27 19 62 19
//        54
//        -1 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52
//        73
//        69 17 36 17 10 75 6 38 92 68 22 84 80 52 24 82 50 9 43 78 48 83 87 75 97 50 64 91 94 10 70 4 52 80 100 77 9 57 2 28 2 82 41 74 8 88 10 47 79 99 60 93 89 54 14 91 93 71 15 74 79 3 39 19 61 86 50 19 24 47 72 50 29
//        -1 brak
//0 brak
//1 5
//        2 6
//        3 7
//        4 11
//        5 11
//        6 12
//        7 12
//        8 15
//        9 16
//        10 19
//        11 21
//        12 22
//        13 28
//        14 29
//        15 32
//        16 32
//        17 33
//        18 37
//        19 37
//        20 38
//        21 40
//        22 43
//        23 44
//        24 45
//        25 48
//        26 52
//        27 52
//        28 55
//        29 56
//        30 60
//        31 63
//        32 63
//        33 67
//        34 70
//        35 73
//        36 75
//        37 75
//        38 76
//        39 76
//        40 76
//        41 77
//        42 77
//        43 78
//        44 78
//        45 78
//        46 80
//        47 82
//        48 83
//        49 83
//        50 84
//        51 84
//        52 85
//        53 86
//        54 87
//        55 88
//        56 88
//        57 91
//        58 93
//        59 97
//        60 100
//        61 brak
//-1 brak
//0 brak
//1 1
//        2 3
//        3 3
//        4 4
//        5 6
//        6 11
//        7 11
//        8 11
//        9 13
//        10 15
//        11 19
//        12 19
//        13 21
//        14 27
//        15 29
//        16 30
//        17 32
//        18 34
//        19 35
//        20 36
//        21 37
//        22 38
//        23 38
//        24 43
//        25 47
//        26 54
//        27 54
//        28 57
//        29 58
//        30 59
//        31 60
//        32 60
//        33 61
//        34 61
//        35 62
//        36 63
//        37 66
//        38 67
//        39 69
//        40 70
//        41 75
//        42 77
//        43 81
//        44 83
//        45 83
//        46 86
//        47 89
//        48 93
//        49 93
//        50 94
//        51 97
//        52 brak
