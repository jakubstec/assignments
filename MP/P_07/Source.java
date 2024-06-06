// Jakub Stec - grupa 6
import java.util.Scanner;

/* IDEA:
Przedstawiony algorytm realizuje Quicksort w wersji iteracyjnej bez wykorzystania
dodatkowej pamieci na stos, zgodny z idea z wykladu.
Dokonujemy zamiany elementow by oznaczyc granice, zeby je nastepnie odzyskac
gdy chcemy przejsc do sortowania nastepnej podtablicy.
Gdy osiagniemy wystarczajaco mala wielkosc podtablicy, to realizujemy insertionsort
i przechodzimy do dalszej podtablicy
 */


public class Source {
    public static Scanner sc = new Scanner(System.in);
    public static void quickSort(long[] array) {
        int n = array.length-1;
        int low = 0; // dolna granica tablicy/podtablicy
        int high = n; // gorna granica tablicy/podtablicy
        int m = 19; // dlugosc podtablicy kiedy zalaczamy insertionsort
        int i;
        int j;
        // petla kontrolujaca poprawnosc indeksow granic zeby nie wyjsc poza petle
        while (low < high) {
            // sprawdzanie wielkosci podtablicy, czy jest wystarczajaco mala
            while(high - low > m) {
                i = low;
                j = high;
                // partycjonowanie Lomuto
                int pivotIndex = partition(array,i,j);
                // ustawienie nowej prawej granicy, dzielimy na mniejsza podtablice, sortujemy lewa
                high = pivotIndex-1;
            }
            // gdy podtablica jest wystarczajaco mala, robimy insertionsort
            insertionSort(array, low, high);
            // cofanie do granicy, resetujemy lewa granice do wartosci za pivotem
            low = high + 2; //index pivot + 1
            if(low >= n) { // gdy wyjdzie poza zakres
                break;
            }

            // szukamy nastepnej podtablicy
            long temp = array[low - 1]; //pivot
            int k;
            for(k = low; k < n; k++) { // wracamy sie do granicy
                // szukamy wartosci mniejszej od pivotu
                if(array[k] < temp) {
                    break;
                }
            }
            high = k; // przypisujemy nowy indeks prawej granicy na nastepna wartosc mniejsza niz pivot

            // podmieniamy granice na wlasciwe miejsce przed pivotem
            temp = array[high];
            array[high] = array[low]; // ustawiamy liczbe za pivotem na high
            array[low] = array[low - 1]; // ustawiamy pivot na swoje miejsce
            array[low - 1] = temp;
        }
    }
    public static int partition(long[] array, int low, int high) {
        int middle = low + (high - low) / 2;
        // obliczamy pivot poprzez mediane z trzech liczb (pierwsza, srodkowa i ostatnia w tablicy/podtablicy)
        int pivotIndex = medianOfThree(array, low, middle, high);
        // jezeli wybralismy pierwsza badz srodkowa, to zamieniamy zeby ustawic mediane (pivot) na ostatni indeks tablicy/podtablicy
        if(pivotIndex != high) {
            swap(array, pivotIndex, high);
        }
        // partycja Lomuto
        long pivot = array[high];
        int i = low - 1;
        int max = low;
        for(int j = low; j < high; j++) {
            if(array[j] < pivot) {
                i++;
                swap(array,i,j);
                // szukamy tutaj najwiekszej wartosci w lewej czesci partycji
                if (array[max] < array[i]) {
                    max = i;
                }
            }
        }
        // maksymalna wartosc ustawiamy na high, a pivot idzie na indeks i
        long temp = array[max];
        array[max] = array[i]; // ustawiamy wartosc z indeksu i na max zeby zrobic miejsce dla pivotu
        array[i] = array[high]; // ustawiamy pivot na jeden w lewo poniewaz wyciagnelismy max na prawa czesc pivotu
        array[high] = temp; // ustawiamy wartosc z indeksu high na max
        return i; // zwracamy pivot
    }

    // funkcja pomocnicza do zwrocenia mediany z 3 (wartosc srodkowa, gdy 3 wartosci sa niemalejace)
    public static int medianOfThree(long[] array, int low, int mid, int high) {
        if (array[low] > array[mid]) {
            if (array[mid] > array[high]) {
                return mid;
            } else if (array[low] > array[high]) {
                return high;
            } else {
                return low;
            }
        } else {
            if (array[low] > array[high]) {
                return low;
            } else if (array[mid] > array[high]) {
                return high;
            } else {
                return mid;
            }
        }
    }

    // zwykla funkcja realizujaca sortowanie przez wstawianie
    public static void insertionSort(long[] array, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            long temp = array[i];
            int j = i - 1;
            while (j >= low && array[j] > temp) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = temp;
        }
    }

    // funkcja pomocnicza do robienia swapowania
    public static void swap(long[] array, int i, int j) {
        long temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int numOfSets = sc.nextInt();
        for (int i = 0; i < numOfSets; i++) {
            int numOfElems = sc.nextInt();
            long[] tab = new long[numOfElems];
            for (int j = 0; j < numOfElems; j++) {
                int tmp = sc.nextInt();
                tab[j] = tmp;
            }
            quickSort(tab);
            // wypisywanie posortowanej tablicy
            StringBuilder sb = new StringBuilder();
            for(int k = 0; k < numOfElems; k++) {
                sb.append(tab[k]);
                sb.append(" ");
            }
            System.out.println(sb);
        }
    }
}
//TESTY
//        6
//        43
//        -253 -115 -55 -72 -297 -132 -118 93 162 -2 -20 108 -151 36 -109 -299 -97 134 177 -249 158 -179 188 -112 164 131 281 -174 28 -270 243 86 -84 292 65 -274 -239 38 2 -160 -155 -212 -210
//        71
//        -220 84 186 213 -245 178 -95 260 -296 -204 215 127 -106 -241 104 269 72 203 -130 -159 176 132 -197 -126 221 61 -273 119 -99 217 -17 -40 122 29 -32 -101 -294 -1 -96 34 -168 76 49 36 232 -107 252 -253 -23 -134 -180 71 19 48 -143 -20 272 129 197 -14 151 -275 206 -173 192 -33 -179 -102 -51 -224 -281
//        185
//        0 123 289 -96 -182 247 253 49 -115 22 -228 -250 -264 96 86 41 -146 -189 155 -45 257 -195 17 -114 -26 -183 84 -83 -144 36 -168 -278 154 -44 -94 239 65 232 27 110 138 -74 -31 74 6 169 -7 33 229 -277 -59 -269 -85 -65 -42 39 185 -121 280 -188 -91 286 177 69 -143 -54 195 151 29 -165 50 118 211 -248 231 -18 66 -229 267 -225 173 168 -36 199 -99 128 48 270 -40 -288 98 -166 -193 -52 -49 71 -79 238 140 94 171 -78 -138 -275 -202 -177 153 2 -201 10 70 -287 -149 -10 129 296 -285 -206 -92 222 207 288 264 -163 -131 -276 260 -192 -274 254 -119 59 -80 -237 60 174 -173 -117 -53 178 -153 250 11 -84 -141 130 189 244 -70 -299 73 206 -233 -273 -24 93 -6 -190 226 219 83 -221 -15 16 57 58 34 -120 -29 122 243 -60 241 182 249 -125 9 -260 -116 125 -62 79 291 228 -28
//        74
//        -136 136 174 -199 -89 46 155 -196 -10 -293 110 -244 264 -43 265 149 232 -77 131 -56 -281 226 -219 295 178 79 196 190 30 -126 -154 93 -272 227 -83 -215 38 150 235 -98 97 -226 -210 207 -142 3 -162 -300 202 -174 -132 252 99 36 292 239 24 281 77 -17 134 81 -125 -24 248 283 297 -67 117 218 -2 167 249 -105
//        263
//        104 -204 -270 -254 271 168 -132 52 277 -116 184 250 -207 20 148 123 -148 278 61 285 -239 -181 299 116 172 176 -260 -109 -7 75 -286 -184 4 -147 166 -26 -77 45 -20 -231 8 27 -275 -47 15 -161 -8 40 -145 242 -50 -122 272 -160 -149 136 -124 -280 -174 -69 31 232 175 298 -55 -123 -263 205 -53 9 -205 217 112 -240 256 -97 214 124 130 -252 222 185 -135 -213 -80 -168 -79 159 294 165 246 36 -214 -180 -217 -133 -246 -154 220 -19 -29 65 49 -15 207 -108 -183 149 -95 -56 -73 -261 -66 -49 -264 -65 -256 22 245 -120 94 -113 114 -299 97 -88 129 -142 -198 186 199 11 -5 -83 91 289 -67 196 0 -89 150 -209 -32 -262 209 -43 -300 156 -136 -192 -298 -228 239 -290 236 34 238 -92 127 -140 -128 -227 -248 -10 -37 16 78 105 93 59 177 -176 111 204 -242 42 237 -60 -125 -2 25 -255 141 -211 -177 -250 -16 229 284 32 26 -279 280 -17 174 -45 69 -4 -105 115 -33 164 2 108 12 -64 -27 -78 -251 -76 212 -216 -44 -106 213 -221 -110 183 -34 -241 -219 -278 92 51 -48 295 -139 -121 -40 -202 151 131 -104 -42 71 -3 -28 223 7 244 -58 -91 -61 73 -175 85 291 288 276 178 -234 -244 -190 126 154 -52 -11 -99 -75 90 188 133 -165
//        165
//        -35 289 287 -19 -177 4 116 -111 183 216 14 114 -243 -117 -231 214 -97 168 -79 -71 217 -108 236 -56 157 78 -31 -228 -112 -277 109 167 -266 -198 -125 -88 -58 -157 10 11 -207 17 -284 -298 266 152 195 194 -155 119 36 -147 297 -139 -62 -21 -86 -116 -236 8 -9 23 285 269 95 -73 187 93 232 -234 -6 107 -165 -233 246 121 69 -253 257 154 -81 34 50 265 -132 -188 -109 -2 87 -226 -55 -176 111 191 -49 -256 -13 -57 156 -4 67 259 -269 -93 -130 101 -184 -89 272 -227 223 -22 -191 9 -141 -18 212 -180 -12 298 293 -295 -293 230 74 262 284 -91 7 -78 202 -167 -192 -67 155 133 -90 -5 -138 -185 172 61 -261 -197 -11 -110 -107 57 97 -128 -127 126 79 5 96 115 -65 233 -229 209 -300 -181 203 222 -258
//        -299 -297 -274 -270 -253 -249 -239 -212 -210 -179 -174 -160 -155 -151 -132 -118 -115 -112 -109 -97 -84 -72 -55 -20 -2 2 28 36 38 65 86 93 108 131 134 158 162 164 177 188 243 281 292
//        -296 -294 -281 -275 -273 -253 -245 -241 -224 -220 -204 -197 -180 -179 -173 -168 -159 -143 -134 -130 -126 -107 -106 -102 -101 -99 -96 -95 -51 -40 -33 -32 -23 -20 -17 -14 -1 19 29 34 36 48 49 61 71 72 76 84 104 119 122 127 129 132 151 176 178 186 192 197 203 206 213 215 217 221 232 252 260 269 272
//        -299 -288 -287 -285 -278 -277 -276 -275 -274 -273 -269 -264 -260 -250 -248 -237 -233 -229 -228 -225 -221 -206 -202 -201 -195 -193 -192 -190 -189 -188 -183 -182 -177 -173 -168 -166 -165 -163 -153 -149 -146 -144 -143 -141 -138 -131 -125 -121 -120 -119 -117 -116 -115 -114 -99 -96 -94 -92 -91 -85 -84 -83 -80 -79 -78 -74 -70 -65 -62 -60 -59 -54 -53 -52 -49 -45 -44 -42 -40 -36 -31 -29 -28 -26 -24 -18 -15 -10 -7 -6 0 2 6 9 10 11 16 17 22 27 29 33 34 36 39 41 48 49 50 57 58 59 60 65 66 69 70 71 73 74 79 83 84 86 93 94 96 98 110 118 122 123 125 128 129 130 138 140 151 153 154 155 168 169 171 173 174 177 178 182 185 189 195 199 206 207 211 219 222 226 228 229 231 232 238 239 241 243 244 247 249 250 253 254 257 260 264 267 270 280 286 288 289 291 296
//        -300 -293 -281 -272 -244 -226 -219 -215 -210 -199 -196 -174 -162 -154 -142 -136 -132 -126 -125 -105 -98 -89 -83 -77 -67 -56 -43 -24 -17 -10 -2 3 24 30 36 38 46 77 79 81 93 97 99 110 117 131 134 136 149 150 155 167 174 178 190 196 202 207 218 226 227 232 235 239 248 249 252 264 265 281 283 292 295 297
//        -300 -299 -298 -290 -286 -280 -279 -278 -275 -270 -264 -263 -262 -261 -260 -256 -255 -254 -252 -251 -250 -248 -246 -244 -242 -241 -240 -239 -234 -231 -228 -227 -221 -219 -217 -216 -214 -213 -211 -209 -207 -205 -204 -202 -198 -192 -190 -184 -183 -181 -180 -177 -176 -175 -174 -168 -165 -161 -160 -154 -149 -148 -147 -145 -142 -140 -139 -136 -135 -133 -132 -128 -125 -124 -123 -122 -121 -120 -116 -113 -110 -109 -108 -106 -105 -104 -99 -97 -95 -92 -91 -89 -88 -83 -80 -79 -78 -77 -76 -75 -73 -69 -67 -66 -65 -64 -61 -60 -58 -56 -55 -53 -52 -50 -49 -48 -47 -45 -44 -43 -42 -40 -37 -34 -33 -32 -29 -28 -27 -26 -20 -19 -17 -16 -15 -11 -10 -8 -7 -5 -4 -3 -2 0 2 4 7 8 9 11 12 15 16 20 22 25 26 27 31 32 34 36 40 42 45 49 51 52 59 61 65 69 71 73 75 78 85 90 91 92 93 94 97 104 105 108 111 112 114 115 116 123 124 126 127 129 130 131 133 136 141 148 149 150 151 154 156 159 164 165 166 168 172 174 175 176 177 178 183 184 185 186 188 196 199 204 205 207 209 212 213 214 217 220 222 223 229 232 236 237 238 239 242 244 245 246 250 256 271 272 276 277 278 280 284 285 288 289 291 294 295 298 299
//        -300 -298 -295 -293 -284 -277 -269 -266 -261 -258 -256 -253 -243 -236 -234 -233 -231 -229 -228 -227 -226 -207 -198 -197 -192 -191 -188 -185 -184 -181 -180 -177 -176 -167 -165 -157 -155 -147 -141 -139 -138 -132 -130 -128 -127 -125 -117 -116 -112 -111 -110 -109 -108 -107 -97 -93 -91 -90 -89 -88 -86 -81 -79 -78 -73 -71 -67 -65 -62 -58 -57 -56 -55 -49 -35 -31 -22 -21 -19 -18 -13 -12 -11 -9 -6 -5 -4 -2 4 5 7 8 9 10 11 14 17 23 34 36 50 57 61 67 69 74 78 79 87 93 95 96 97 101 107 109 111 114 115 116 119 121 126 133 152 154 155 156 157 167 168 172 183 187 191 194 195 202 203 209 212 214 216 217 222 223 230 232 233 236 246 257 259 262 265 266 269 272 284 285 287 289 293 297 298
