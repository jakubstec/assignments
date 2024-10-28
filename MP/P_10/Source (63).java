// Jakub Stec - grupa 6
import java.util.Scanner;

/* IDEA:

Algorytm implementuje scalanie posortowanych ciagow za pomoca kopca. Wrzucamy poczatkowo
pierwsze elementy ( najmniejsze) z kazdego ciagu do niego, utrzymujac strukture kopca za kazdym
insertem (min heap, w root jest najmniejszy element, kazdy parent node jest niewiekszy od child node)
nastepnie w petli zrzucamy roota, naprawiamy strukture heapa, wrzucamy zrzucony element do finalnej tablicy
jezeli sa jeszcze jakies elementy w obecnym ciagu, bierzemy kolejny, jezeli nie - przechodzimy do nastepnego.

 */

public class Source {

    public static Scanner sc = new Scanner(System.in);

    // implementacja struktury obiektu w kopcu
    public static class HeapNode {
        int num; // wartosc elementu w ciagu
        int arrayNum; // numer ciagu
        int index; // indeks elementu w danym ciagu

        // konstruktor
        public HeapNode(int num, int arrayNum, int index) {
            this.num = num;
            this.arrayNum = arrayNum;
            this.index = index;
        }
    }
    // implementacja kopca MinHeap (w korzeniu jest element najmniejszy)
    public static class MinHeap {
        public HeapNode[] heap; // tablica reprezentujaca kopiec
        public int size; // biezaca wielkosc kopca (ilosc ciagow)
        // konstruktor
        public MinHeap(int maxSize) {
            this.heap = new HeapNode[maxSize];
            this.size = 0;
        }

        // metoda dodajaca obiekty do kopca
        public void insert(HeapNode node) {
            int current = size; // ustawiamy size na ostatni element, dodajemy na koncowy indeks
            heap[size] = node;
            size++; // inkrementujemy biezaca wielkosc
            upHeap(current); // przesiewanie obiektu do gory, czyli naprawa kopca
        }

        // metoda usuwajaca obiekt z roota (najmniejszy)
        public HeapNode deleteMin() {
            HeapNode root = heap[0];
            size--; // zmniejszamy biezaca wielkosc
            heap[0] = heap[size];
            downHeap(0); // naprawiamy kopiec przesiewajac obiekt w dol (znajdujemy nowego roota)
            return root;
        }

        // metoda przesiewajaca obiekt w gore zeby zachowac wlasnosc kopca
        public void upHeap(int i) {
            // porownujemy element na danym indeksie z jego rodzicem
            // jezeli spelnia warunki, to zamieniamy go, czyli przesuwamy do gory
            while(i > 0 && heap[i].num < heap[(i - 1) / 2].num) {
                HeapNode temp = heap[i];
                heap[i] = heap[(i - 1) / 2];
                heap[(i - 1) / 2] = temp;
                i = (i - 1) / 2;
            }
        }

        // metoda przesiewajaca obiekt w dol zeby zachowac wlasnosc kopca
        public void downHeap(int i) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            int smallest = i;
            // sprawdzamy indeksy lewego i prawego dziecka, poniewaz szukamy mniejszego elementu
            // od wezla zeby go wrzucic go gory
            if (leftChild < size && heap[leftChild].num < heap[smallest].num) {
                smallest = leftChild;
            }
            if (rightChild < size && heap[rightChild].num < heap[smallest].num) {
                smallest = rightChild;
            }

            // jesli najmniejszy nie jest wezlem poczatkowym
            if (smallest != i) {
                HeapNode temp = heap[i]; // zamiana z dzieckiem
                heap[i] = heap[smallest];
                heap[smallest] = temp;
                downHeap(smallest); // powtarzamy ten proces
            }
        }
        // sprwadzamy czy heap jest pusty
        public boolean isEmpty() {
            return size == 0;
        }
    }

    // metoda scalajaca posortowane ciagi w jedna tablice
    public static int[] merge(int[][] arrays,MinHeap heap) {
        int totalSize = 0; // calkowity rozmiar finalnej tablicy
        for (int[] seq : arrays) {
            totalSize += seq.length; // zsumowanie dlugosci ciagow
        }

        // tworzymy minHeap z pierwszych elementow
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length > 0) {
                heap.insert(new HeapNode(arrays[i][0],i,0));
            }
        }

        int[] result = new int[totalSize]; // tablica wynikowa
        int index = 0;
        int nextIndex = 0;

        // dopoki kopiec nie jest pusty to
        while (!heap.isEmpty()) {
            // sciagamy najmniejszy element z kopca
            HeapNode min = heap.deleteMin();
            result[index] = min.num; // dodajemy go do tablicy wynikowej
            index++; // przesuwamy indeks w tablicy wynikowej
            nextIndex = min.index + 1; // przesuwamy indeks w aktualnym ciagu
            //jesli w aktualnym ciagu sa jeszcze jakies elementy, dodajemy nastepny element do kopca
            if (nextIndex < arrays[min.arrayNum].length) {
                heap.insert(new HeapNode(arrays[min.arrayNum][nextIndex], min.arrayNum, nextIndex));
            }
        }

        // zwracamy finalna tablice
        return result;
    }

    public static void main(String[] args) {
        int numOfSets = sc.nextInt(); // wczytanie ilosci zestawow
        for (int i = 0; i < numOfSets; i++) {

            int numOfSeq = sc.nextInt(); // wczytanie ilosci ciagow
            int[] len = new int[numOfSeq]; // tablica trzymajaca dlugosci ciagow
            int[][] seq = new int[numOfSeq][]; // tablica trzymajaca dlugosci ciagow i ciagi

            // wczytanie dlugosci
            for (int l = 0; l < numOfSeq; l++) {
                len[l] = sc.nextInt();
            }

            //odczytanie wartosci
            for (int j = 0; j < numOfSeq; j++) {

                seq[j] = new int[len[j]];

                for (int k = 0; k < len[j]; k++) {
                    seq[j][k] = sc.nextInt();
                }

            }

            MinHeap heap = new MinHeap(numOfSeq); // inicjalizacja kopca

            int[] mergedArrays = merge(seq,heap); // scalenie tablicy

            StringBuilder sb = new StringBuilder();

            for (int num : mergedArrays) {
                sb.append(num).append(" ");
            }

            System.out.println(sb); // wypisanie jej
        }
    }
}

// TESTY
//4
//        3
//        3 2 4
//        1 3 5
//        2 4
//        6 7 8 9
//        2
//        3 3
//        1 3 5
//        2 4 6
//        3
//        2 2 2
//        1 4
//        2 5
//        3 6
//        4
//        4 3 2 5
//        10 20 30 40
//        5 15 25
//        1 11
//        3 13 23 33 43
//1 2 3 4 5 6 7 8 9
//        1 2 3 4 5 6
//        1 2 3 4 5 6
//        1 3 5 10 11 13 15 20 23 25 30 33 40 43
