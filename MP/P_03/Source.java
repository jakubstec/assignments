// Jakub Stec
import java.util.Scanner;

/*
GLOWNA IDEA
Algorytm realizuje czyszczenie wyrazenia z niedozwolonych znakow oraz konwersje wyrazenia liniowego INF->ONP oraz ONP->INF, na biezaco sprawdzajac poprawnosc podanego przez uzytkownika wyrazenia.
Wykorzystujemy w tym celu wlasnorecznie zaimplementowane struktury stosu, jedna wykorzystujaca tablice "charow" (sprawdzanie poprawnosci nawiasow oraz INF->ONP),
druga tablice dwuwymiarowa Stringow (ONP->INF, przechowuje ona podwyrazenie oraz najwazniejszy operator), obie czynnosci sa wykonywane w jednej petli.
Konwersja polega na rozwazaniu roznych przypadkow, w zaleznosci od napotkanych operatorow (jedno/dwuargumentowych / prawo/lewostronnie lacznych oraz roznych piorytetow) dazac do najmniejszej ilosci nawiasow.
 */

public class Source {

    // Implementacja struktury stosu za pomoca tablicy charow wykorzystywanego w algorytmie
    // (INF->ONP, sprawdzanie poprawnosci nawiasow wyrazenia INF)
    public static class MyStack1 { // stos rosnie w gore
        private int maxSize; // rozmiar tablicy zawierającej stos
        private char[] Elem; // tablica zawierająca stos
        private int top; // indeks wierzcholka

        public MyStack1(int size) { // konstruktor klasy
            maxSize = size; // ustalamy rozmiar tablicy
            Elem = new char[maxSize]; // tworzymy tablice dla elementow
            top = maxSize; // rosnie w gore, ustawiamy wierzcholek na 'zerowy' element (brak elementow)
        }

        // funkcja do wstawiania elementu na szczyt stosu
        public void push(char x) {
            Elem[--top] = x;
        }

        // funkcja do usuwania elementu ze szczytu stosu
        public char pop() {
            return Elem[top++];
        }

        // funkcja do podejrzenia co znajduje sie na szczycie stosu
        public char peek() {
            return Elem[top];
        }

        // funkcja do sprawdzenia czy stos jest pusty
        public boolean isEmpty() {
            return top == maxSize;
        }
    }

    // Implementacja struktury stosu za pomoca dwuwymiarowej tablicy Stringow, wykorzystywana w algorytmie ONP->INF
    // Tablica dwuwymiarowa ma za zadanie trzymac aktualne podwyrazenie oraz najbardziej znaczacy / ostatni operator
    // oprocz tego, implementacja identyczna jak w MyStack1
    public static class MyStack2 {
        private int maxSize;
        private String[][] Elem;
        private int top;

        // konstruktor
        public MyStack2(int size) {
            maxSize = size;
            Elem = new String[maxSize][2];
            top = maxSize;
        }

        // Funkcja do wstawienia elementu na szczyt stosu
        public void push(String x, String y) {
            top--;
            Elem[top][0] = x;
            Elem[top][1] = y;


        }
        // Funkcja do usuniecia elementu ze szczytu stosu
        public String pop() {
            return Elem[top++][0];
        }

        // Funkcja do podejrzenia podwyrazenia
        public String peek() {
            return Elem[top][0];
        }

        // Funkcja do podejrzenia operatora podwyrazenia
        public String peekPrio() {
            return Elem[top][1];
        }

        // Funkcja do sprawdzenia czy stos jest pusty
        public boolean isEmpty() {
            return top == maxSize;
        }
    }

    // funkcja ktora zwraca piorytet operatora na podstawie hierarchii zalaczonej w poleceniu
    public static int getOperatorPriority(char operator) {
        if(operator == '!' || operator == '~') return 10;
        else if(operator == '^') return 9;
        else if(operator == '*' || operator == '/' || operator == '%') return 8;
        else if(operator == '+' || operator == '-') return 7;
        else if(operator == '<' || operator == '>') return 6;
        else if(operator == '&') return 5;
        else if(operator == '|') return 4;
        else if(operator == '=') return 3;
        else return -1;
    }

    // funkcja sluzaca do wyczyszczenia wyrazenia, tworzymy obiekt clear_str oraz appendujemy do niego litery z zakresu a-z, nawiasy oraz operatory podane w poleceniu, reszte ignorujemy
    public static String clear_inf(String str) {
        StringBuilder clear_str = new StringBuilder();
        // przechodzimy przez kazdy znak wyrazenia
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(isOperand(ch) || (ch == '(') || (ch == ')') || isOperator(ch)) clear_str.append(ch);
        }
        // zwracamy wyczyszczone wyrazenie
        return clear_str.toString();
    }

    // funkcja dziala identycznie jak clear_inf, jedynie bez uwzglednienia nawiasow ktorych nie ma w wyrazeniu ONP
    public static String clear_onp(String str) {
        StringBuilder clear_str = new StringBuilder();
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(isOperand(ch) || isOperator(ch)) clear_str.append(ch);
        }
        return clear_str.toString();
    }

    // funkcja sprawdzajaca, czy aktualny znak to operand (litera z zakresu a-z)
    public static boolean isOperand(char ch) {
        return ch >= 97 && ch <= 122;
    }

    // funkcja sprawdzajaca, czy aktualny znak to operator jednoargumentowy
    public static boolean isOneArgOperator(char ch) {
        return (ch == '!') || (ch == '~');
    }

    // funkcja sprawdzajaca, czy aktualny znak to operator o lacznosci prawostronnej
    public static boolean isRightSideOperator(char ch) {
        return (ch == '!') || (ch == '~') || (ch == '^') || (ch == '=');
    }

    // funkcja sprawdzajaca, czy aktualny znak to operator dwuargumentowy
    public static boolean isTwoArgOperator(char ch) {
        return  (ch == '^')
                || (ch == '*')  || (ch == '/')
                || (ch == '%')  || (ch == '+')
                || (ch == '-')  || (ch == '<')
                || (ch == '>')  || (ch == '=')
                || (ch == '&')  || (ch == '|');
    }

    // funkcja sprawdzajaca, czy aktualny znak to operator
    public static boolean isOperator(char ch) {
        return isOneArgOperator(ch) || isTwoArgOperator(ch);
    }

    // funkcja realizujaca 'automat' (nieuzywana w kodzie, poniewaz algorytm sprawdzania znajduje sie w glownej petli
    // zostawilem w celu podgladowym, czytelniejsze
    // funkcja realizujaca sprawdzanie poprawnosci wyrazenia w INF za pomoca maszyny turinga
    // oraz sprawdza czy nawiasy sa poprawnie postawione za pomoca stosu
    public static boolean valid_inf(String str) { //
        MyStack1 check_inf = new MyStack1(256);
        int i = 0;
        int state = 0;
        boolean isValid = true;
        while(i < str.length()) {
            char ch = str.charAt(i);
            if(state == 0) {
                if(isOperand(ch)) state = 1;
                else if(isOneArgOperator(ch)) state = 2;
                else if(ch == '(') state = 0;
                else return false;
            }
            else if(state == 1) {
                if(isTwoArgOperator(ch)) state = 0;
                else if(ch == ')') state = 1;
                else return false;
            }
            else if(state == 2) {
                if(isOperand(ch)) state = 1;
                else if(ch == '(') state = 0;
                else if(isOneArgOperator(ch)) state = 2;
                else return false;
            }
            if(ch == '(') {
                check_inf.push(ch);
            } else if(ch == ')') {
                if(check_inf.isEmpty() || check_inf.pop() != '(') return false;
            }
            i++;
        }
        if(state != 1) isValid = false;

        if(!check_inf.isEmpty()) isValid = false;

        return isValid;
    }

    // funkcja realizujaca sprawdzanie poprawnosci wyrazenia w ONP (nieuzywana w kodzie, poniewaz algorytm sprawdzania znajduje sie w glownej petli
    // zostawilem w celu podgladowym, czytelniejsze
    // sprawdzamy czy ilosc operandow zgadza sie z iloscia operatorow
    public static boolean valid_onp(String str) {
        int subExp = 0;
        int i = 0;
        while(i < str.length()) {
            char ch = str.charAt(i);
            if(isOperand(ch)) subExp++;
            if(isTwoArgOperator(ch)) subExp--;
            if(subExp <= 0) return false;
            i++;
        }
        if(subExp!=1) return false;
        return true;
    }

    // algorytm realizujacy konwersje onp -> inf
    public static void onp_to_inf(String str) {
            StringBuilder sb = new StringBuilder(); // tworzymy obiekt przechowujacy wynik konwersji
            str = clear_onp(str); // czyscimy wyrazenie
            int subExp = 0; // zmienna ktora wykorzystamy w algorytmie sprawdzania poprawnosci wyrazenia
            sb.append("INF: ");
            String exp = ""; // zmienna ktora wykorzystamy w algorytmie do przechowywania aktualnych podwyrazen
            int prio = -1; // piorytet aktualnego operatora
            MyStack2 mainStack = new MyStack2(256); // stos ktory wykorzystamy do konwersji
            int i = 0;
            // przechodzimy po kazdym znaku wyrazenia
            while(i < str.length()) {
                char ch = str.charAt(i);

                // sprawdzenie poprawnosci czy zgadza sie ilosc operatorow do operandow
                if(isOperand(ch)) subExp++;
                if(isTwoArgOperator(ch)) subExp--;
                if(subExp <= 0) {
                    System.out.println("INF: error");
                    return;
                }

                String tmp = String.valueOf(ch);

                // jesli trafimy na operand, wrzucamy go na stos
                if(isOperand(ch)) {
                    mainStack.push(tmp,"");
                }

                // jesli trafimy na operator jednoargumentowy
                if(isOneArgOperator(ch)) {
                    // zapisujemy w zmiennej p piorytet naszego operanda
                    String p = mainStack.peekPrio();
                    // zapisujemy tego operanda/podwyrazenia w zmiennej zrzucajac go ze stosu
                    String elem = mainStack.pop();
                    if(elem.length()>2) {
                        // jezeli jest to podwyrazenie (np a+b, nie interesuje nas np 'a' lub '!a')
                        prio = getOperatorPriority(p.charAt(0));
                        // jednoargumentowe operatory sa prawostronnie laczne
                        // wiec jedynie w zaleznosci od piorytetu dajemy nawias
                        if(getOperatorPriority(ch) <= prio) {
                            exp = ch + " " + elem;
                        }
                        else {
                            exp = ch + " " + "(" + " " + elem + " " + ")";
                        }
                    }
                    else {
                        exp = ch + " " + elem;
                    }
                    mainStack.push(exp,tmp); // wrzucamy podwyrazenie na stos, wraz z najwazniejszym operatorem
                }

                // jezeli trafimy na operator dwuargumentowy prawostronny
                if(isTwoArgOperator(ch) && isRightSideOperator(ch)) {
                    // zapisujemy piorytet najwazniejszego operatora prawego i lewego wyrazenia
                    // zapisujemy operand/powyrazenie w zmiennej zrzucajac je ze stosu
                    String r = mainStack.peekPrio();
                    String right = mainStack.pop();
                    String l = mainStack.peekPrio();
                    String left = mainStack.pop();
                    // sprawdzamy wszystkie mozliwe przypadki, (# - operator)
                    // operand # podwyrazenie
                    // podwyrazenie # operand
                    // operand # operand
                    // podwyrazenie # podwyrazenie
                    // nastepnie w zaleznosci od piorytetu aktualnego operatora i sciagnietego ze stosu zapisanego w zmienne j r/l
                    // ustalamy jakie nawiasy zrobimy (pamietajac o prawostronnej lacznosci)
                    if((left.length() == 1) && (right.length() > 2)) {
                        // np a * ( b + c)
                        prio = getOperatorPriority(r.charAt(0)); // zmienna przechowujaca piorytet aktualnego operatora
                        if(getOperatorPriority(ch) <= prio) {
                            exp = left + " " + ch + " " + right;
                        }
                        else {
                            exp = left + " " + ch + " " + "(" + " " + right + " " + ")";
                        }
                    }
                    else if((right.length() == 1) && (left.length() > 2)) {
                        // np ( b + c ) * a
                        prio = getOperatorPriority(l.charAt(0));
                        if(getOperatorPriority(ch) <= prio) {
                            exp = left + " " + ch + " " + right;
                        }
                        else {
                            exp = "(" + " " + left + " " + ")" + " " + ch + " " + right;
                        }
                    }
                    else if((left.length() == 1) && (right.length() == 1)) {
                        // a + b
                        exp = left + " " + ch + " " + right;
                    }
                    else {
                        // podwyrazenie # podwyrazenie
                        int prio1 = getOperatorPriority(l.charAt(0));
                        int prio2 = getOperatorPriority(r.charAt(0));
                        // naszym aktualnym piorytetem sposrod dwoch operator bedzie ten niewiekszy
                        if(prio1<=prio2) {
                            prio = prio1;
                        }
                        else {
                            prio = prio2;
                        }
                        // jezeli na stosie sa jakies operandy jeszcze, to znaczy ze moga byc potrzebne
                        // dodatkowe nawiasy na zewnatrz podwyrazenia
                        // dlatego sprawdzamy to, jezeli nie ma nic to nie dodajemy ich
                        // poniewaz chcemy jak najmniej nawiasow
                        if(!mainStack.isEmpty()) {
                            if(getOperatorPriority(ch) <= prio) {
                                // c>a & c<b
                                exp = "(" + " " + left + " " + ch + " " + right + " " + ")";
                            }
                            else {
                                exp = "(" + " " + "(" + " " + left + " " + ")"+ " " + ch + " " + "(" + " " + right + " " + ")" + " " + ")";
                            }
                        }
                        else {
                            if(getOperatorPriority(ch) <= prio) {
                                // c>a & c<b
                                exp = left + " " + ch + " " + right;
                            }
                            else {
                                exp = "(" + " " + left + " " + ")"+ " " + ch + " " + "(" + " " + right + " " + ")";
                            }
                        }
                    }
                    // wrzucamy podwyrazenie/wyrazenie na stos wraz z najwazniejszym operatorem
                    mainStack.push(exp,tmp);
                }

                // jezeli trafimy na operator dwuargumentowy lewostronny
                if(isTwoArgOperator(ch) && !isRightSideOperator(ch)) {
                    // zapisujemy piorytet najwazniejszego operatora prawego i lewego wyrazenia
                    // zapisujemy operand/powyrazenie w zmiennej zrzucajac je ze stosu
                    String r = mainStack.peekPrio();
                    String right = mainStack.pop();
                    String l = mainStack.peekPrio();
                    String left = mainStack.pop();
                    // sprawdzamy wszystkie mozliwe przypadki, (# - operator)
                    // operand # podwyrazenie
                    // podwyrazenie # operand
                    // operand # operand
                    // podwyrazenie # podwyrazenie
                    // nastepnie w zaleznosci od piorytetu aktualnego operatora i sciagnietego ze stosu zapisanego w zmienne j r/l
                    // ustalamy jakie nawiasy zrobimy (pamietajac o prawostronnej lacznosci)
                    if((left.length() == 1) && (right.length() > 2)) {
                        // np a * ( b + c)
                        prio = getOperatorPriority(r.charAt(0)); // zmienna przechowujaca piorytet aktualnego operatora
                        if(getOperatorPriority(ch) < prio) {
                            exp = left + " " + ch + " " + right;
                        }
                        else {
                            exp = left + " " + ch + " " + "(" + " " + right + " " + ")";
                        }
                    }
                    else if((right.length() == 1) && (left.length() > 2)) {
                        // np ( b + c ) * a
                        prio = getOperatorPriority(l.charAt(0));
                        if (getOperatorPriority(ch) <= prio) {
                            exp = left + " " + ch + " " + right;
                        } else {
                            exp = "(" + " " + left + " " + ")" + " " + ch + " " + right;
                        }
                    }
                    else if((left.length() == 1) && (right.length() == 1)) {
                        // a + b
                        exp = left + " " + ch + " " + right;
                    }
                    else {
                        // podwyrazenie # podwyrazenie
                        int prio_tmp = getOperatorPriority(l.charAt(0));
                        prio = getOperatorPriority(r.charAt(0));
                        // specjalne przypadki, by uniknac redundantnych nawiasow
                        if(isOneArgOperator(r.charAt(0)) || left.charAt(0) == '(') {
                            exp = left + " " + ch + " " + right;
                        }
                        else if((getOperatorPriority(ch) <= prio) && !isOneArgOperator(r.charAt(0))) {
                            if(prio_tmp == prio && (l.charAt(0) == '<' || l.charAt(0) == '>')) {
                                exp = left + " " + ch + " " + right;
                            }
                            else {
                                exp = left + " " + ch + " " + "(" + " " + right + " " + ")";
                            }
                        }
                        else {
                            exp = "(" + " " + left + " " + ")"+ " " + ch + " " + "(" + " " + right + " " + ")";
                        }
                    }
                    // wrzucamy podwyrazenie/wyrazenie na stos wraz z najwazniejszym operatorem
                    mainStack.push(exp,tmp);
                }

                i++;
            }
            // zapisujemy finalny wynik
            sb.append(mainStack.pop());
        if(subExp!=1) { // jezeli podczas sprawdzania poprawnosci wyszla niepoprawnosc, wypisujemy blad i wychodzimy z funkcji
            System.out.println("INF: error");
            return;
        }
        System.out.println(sb); // wypisujemy wynik
    }

    // algorytm realizujacy konwersje inf -> onp
    public static void inf_to_onp(String str) {
            StringBuilder sb = new StringBuilder(); // tworzymy obiekt ktory bedzie przechowywal wynik koncowy
            str = clear_inf(str); // czyscimy wyrazenie
            // tworzymy 2 stacki, jeden do konwersji, drugi do sprawdzenia poprawnosci
            MyStack1 stack = new MyStack1(256);
            MyStack1 check_inf = new MyStack1(256);
            sb.append("ONP: ");
            // zawartosc funkcji valid_inf, by algorytm spelnial wymogi zadania
            // implementacja maszyny turinga oraz sprawdzanie nawiasow
            int state = 0;
            boolean isValid = true;
            int i = 0;
            while(i < str.length()) {
                char ch = str.charAt(i);

                // gdy w stanie 0
                if(state == 0) {
                    if(isOperand(ch)) state = 1;
                    else if(isOneArgOperator(ch)) state = 2;
                    else if(ch == '(') state = 0;
                    else {
                        System.out.println("ONP: error");
                        return;
                    }
                }
                // gdy w stanie 1
                else if(state == 1) {
                    if(isTwoArgOperator(ch)) state = 0;
                    else if(ch == ')') state = 1;
                    else {
                        System.out.println("ONP: error");
                        return;
                    }
                }
                // gdy w stanie 2
                else if(state == 2) {
                    if(isOperand(ch)) state = 1;
                    else if(ch == '(') state = 0;
                    else if(isOneArgOperator(ch)) state = 2;
                    else {
                        System.out.println("ONP: error");
                        return;
                    }
                }
                // sprawdzanie nawiasow, dodawanie je na stos i sprawdzanie czy sa w dobrej kolejnosci
                // i czy sa sparowane
                if(ch == '(') {
                    check_inf.push(ch);
                } else if(ch == ')') {
                    if(check_inf.isEmpty() || check_inf.pop() != '(') {
                        System.out.println("ONP: error");
                        return;
                    }
                }
                // koniec sprawdzania poprawnosci


                // algorytm konwersji
                if(isOperand(ch)) { // jesli napotkamy operand dodajemy go odrazu do wyniku
                    sb.append(ch);
                    sb.append(" ");
                }
                if(ch == '(') stack.push(ch); // jesli natrafimy na nawias, wrzucamy go na stos
                if(isRightSideOperator(ch)) { // jezeli natrafimy na prawostronny operator to porownujemy piorytety aktualnego operatora
                    // z tymi ktore sa na stosie i sciagamy wszystkie o piorytecie wiekszym
                    int priority = getOperatorPriority(ch);
                    while(!stack.isEmpty() && (priority < getOperatorPriority(stack.peek()))) {
                        sb.append(stack.pop());
                        sb.append(" ");
                    }
                    stack.push(ch); // wstawiamy nasz operator na stos
                }
                if((!isRightSideOperator(ch) && isOperator(ch))) {
                    int priority = getOperatorPriority(ch);
                    // sciagamy ze stosu wszystkie operatory o piorytecie niemniejszym niz aktualny operator
                    while(!stack.isEmpty() && (priority <= getOperatorPriority(stack.peek()))) {
                        sb.append(stack.pop());
                        sb.append(" ");
                    }
                    stack.push(ch); // wstawiamy nasz operator na stos
                }
                if(ch == ')') {
                    // jesli trafimy na nawias zamykajacy, to zdejmujemy go ze stosu i dopisujemy
                    // na wyjscie wszystkie operatory az do napotkania nawiasu otwierajacego
                    // ktory usuwamy pozniej ze stosu
                    while(!stack.isEmpty() && stack.peek() != '(') {
                        sb.append(stack.pop());
                        sb.append(" ");
                    }
                    stack.pop();
                }
                i++;
            }
            // po zakonczeniu wyrazenia, przepisujemy ze stosu
            // na wyjscie wszystko co zostalo na stosie
            while(!stack.isEmpty()) {
                sb.append(stack.pop());
                sb.append(" ");
            }

            // sprawdzamy czy wyrazenie jest poprawne
        if(state != 1) isValid = false;

        if(!check_inf.isEmpty()) isValid = false;

        // jezeli nie jest, to wypisujemy blad i konczymy dzialanie funkcji
        if(!isValid) {
            System.out.println("ONP: error");
            return;
        }
        // w przeciwnym wypadku wypisujemy wynik konwersji
        System.out.println(sb);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // tworzymy obiekt skanera
        int numOfSets = scanner.nextInt(); // wczytujemy ilosc zestawow
        scanner.nextLine();
        for(int i = 1; i <= numOfSets; i++) {
            String input = scanner.nextLine(); // wczytujemy wyrazenie
            String[] input_split = input.split(": "); // dzielimy je na "INF/ONP" oraz "wyrazenie"
            // w zaleznosci od podzialu wybieramy opcje konwersji i wysylamy do funkcji w argumencie wyrazenie
            if(input_split[0].equals("ONP")) {
                onp_to_inf(input_split[1]);
            }
            else if(input_split[0].equals("INF")) {
                inf_to_onp(input_split[1]);
            }
        }
    }
}
// testy
//24
//INF: a)+(b
//ONP: ab+a~a-+
//INF: a+b+(~a-a)
//INF: x=~~a+b*c
//INF: t=~a<x<~b
//INF: ~a-~~b<c+d&!p|!!q
//INF: a^b*c-d<xp|q+x
//INF: x=~a*b/c-d+e%~f
//ONP: xabcdefg++++++=
//ONP: ab+c+d+e+f+g+
//ONP: abc++def++g++
//ONP: abc++def++g+++
//INF: x=a=b=c
//ONP: xabc===
//INF: x=a^b^c
//INF: x=a=b=c^d^e
//ONP: xabcde^^===
//INF: x=(a=(b=c^(d^e)))
//ONP: xa~~~~~~=
//INF: x=~~~~a
//INF: x=~(~(~(~a)))
//ONP: xa~~~~=
//INF: x=a+(b-c+d)
//ONP: xabc-d++=
//ONP: error
//INF: a + b + ( ~ a - a )
//ONP: a b + a ~ a - +
//ONP: x a ~ ~ b c * + =
//ONP: t a ~ x < b ~ < =
//ONP: a ~ b ~ ~ - c d + < p ! & q ! ! |
//ONP: error
//ONP: x a ~ b * c / d - e f ~ % + =
//INF: x = a + ( b + ( c + ( d + ( e + ( f + g ) ) ) ) )
//INF: a + b + c + d + e + f + g
//INF: a + ( b + c ) + ( d + ( e + f ) + g )
//INF: error
//ONP: x a b c = = =
//INF: x = a = b = c
//ONP: x a b c ^ ^ =
//ONP: x a b c d e ^ ^ = = =
//INF: x = a = b = c ^ d ^ e
//ONP: x a b c d e ^ ^ = = =
//INF: x = ~ ~ ~ ~ ~ ~ a
//ONP: x a ~ ~ ~ ~ =
//ONP: x a ~ ~ ~ ~ =
//INF: x = ~ ~ ~ ~ a
//ONP: x a b c - d + + =
//INF: x = a + ( b - c + d )