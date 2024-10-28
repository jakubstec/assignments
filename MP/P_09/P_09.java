// Jakub Stec - grupa 6
import java.util.Scanner;

/* IDEA:
Algorytm implementuje kolejke piorytetowa zawierajaca elementy typu person zrealizowana
jako strukture danych Binary Search Tree. Osoby przechowywane sa w wezlach drzewa (Node)
i maja przypisane piorytety ktore decyduja o pozycji w drzewie.
Wszystkie funkcje sluzace do operowania na drzewie z wyjatkiem create i height sa
zaimplementowane iteracyjnie. Do wypisania (raportowania) kolejki w zadanej kolejnosci (preorder, inorder, postorder)
wykorzystuje w zaleznosci stos/stosy wiazane.
 */

public class Source {

    public static Scanner sc = new Scanner(System.in);

    public static StringBuilder sb;

    // klasa obiektu Person, dodawana do kolejki piorytetowej (drzewa BST)
    public static class Person {
        public int priority; // piorytet, decydujacy o pozycji w drzewie
        public String name;
        public String surname;

        // konstruktor
        Person(int priority, String name, String surname) {
            this.priority = priority;
            this.name = name;
            this.surname = surname;
        }

        // nadpisana metoda toString, zeby latwiej wypisywac dane obiekty kolejki
        @Override
        public String toString() {
            return priority + " - " + name + " " + surname;
        }

    }

    // klasa wezla drzewa BST
    public static class Node {
        public Person info; // klucz
        public Node left; // lewy potomek wezla
        public Node right; // prawy potomek wezla

        // konstruktor
        Node(Person info) {
            this.info = info;
            this.left = null;
            this.right = null;
        }
    }

    // klasa Node'a ktory bedzie wrzucany na stos
    public static class StackNode {
        public Node node; // klucz
        public StackNode  next; // element nastepny

        // konstruktor
        public StackNode(Node node) {
            this.node = node;
            this.next = null;
        }
    }

    // stos wiazany node'ow
    public static class LinkedStack {
        public StackNode head; // pierwszy element

        // konstruktor
        public LinkedStack() {
            head = null;
        }

        // metoda sprawdzajaca czy jest pusty
        public boolean isEmpty() {
            return head == null;
        }

        // metoda wrzucajaca node'a na stos
        public void push(Node x) {
            StackNode newNode = new StackNode(x);
            newNode.next = head;
            head = newNode;
        }

        // metoda wyrzucajaca node'a ze stosu
        public Node pop() {
            Node temp = head.node;
            head = head.next;
            return temp;
        }

        // metoda podgladajaca node'a na stosie
        public Node peek() {
            return head.node;
        }

    }

    // funkcja pomocnicza wykorzystywana w create
    // zwraca index pacjenta z piorytetem mniejszym od roota (drzewa lub poddrzewa)
    // przechodzi po tablicy Person[] (elementy podane do dodania do kolejki)
    public static int getIndexSmaller(Person[] people, Person p, int start, int end) {
        int index = -1;
        for (int i = start; i <= end; i++) {
            if (people[i].priority < p.priority) {
                index = i;
            }
        }
        return index;
    }


    // funkcja pomocnicza wykorzystywana w create
    // zwraca index pacjenta z piorytetem wiekszym od roota (drzewa lub poddrzewa)
    // przechodzi po tablicy Person[] (elementy podane do dodania do kolejki)
    public static int getIndexBigger(Person[] people, Person p, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (people[i].priority > p.priority) {
                return i;
            }
        }
        return -1;
    }

    // implementacja struktury drzewa BST
    public static class BinarySearchTree {
        public Node root;
        // modul edycji
        // -----------------------------------------------

        //rec
        // funkcja tworzaca drzewo BST na podstawie listy elementow, zaleznie od wyznaczonego
        // porzadku. Jezeli drzewo juz istnieje, to zostanie zastapione nowym
        public void create(String order, Person[] people) {
            if(order.equals("POSTORDER")) {
                root = createPostOrder(people,0,people.length-1);
            }
            else if(order.equals("PREORDER")) {
                root = createPreOrder(people,0,people.length-1);
            }

        }
        // funkcja rekurencyjna, porzadek PostOrder
        public Node createPostOrder(Person[] people, int start, int end) {
            if(start>end) return null;
            // wybieramy najpierw element ostatni w tablicy/podtablicy osob w kolejce
            Person p = people[end];
            Node node = new Node(p);
            // znajdujemy indeks mniejszy od wybranego
            int index = getIndexSmaller(people,p,start,end);
            // jezeli nie znaleziono, to dajemy wszystkie elementy bez podzialu do prawej galezi
            if (index == -1) {
                node.left = null;
                node.right = createPostOrder(people, start, end - 1);
            } else {
                // jezeli znaleziono, to rozdzielamy elementy na mniejsze od indeksu (ida do lewej galezi)
                // i wieksze badz rowne (ida do prawej galezi)
                node.left = createPostOrder(people, start, index);
                node.right = createPostOrder(people, index + 1, end - 1);
            }
            return node;
        }

        // funkcja rekurencyjna, porzadek PreOrder
        public Node createPreOrder(Person[] people, int start, int end) {
            if(start>end) return null;
            // wybieramy najpierw element pierwszy w tablicy/podtablicy osob w kolejce
            Person p = people[start];
            Node node = new Node(p);
            int index = getIndexBigger(people,p,start,end);

            // jezeli nie znaleziono, to dajemy wszystkie elementy bez podzialu do lewej galezi
            if (index == -1) {
                node.left = createPreOrder(people, start + 1, end);
                node.right = null;
            } else {
                // jezeli znaleziono, to rozdzielamy elementy na wieksze od indeksu (ida do lewej galezi)
                // i mniejsze badz rowne (ida do prawej galezi)
                node.left = createPreOrder(people, start + 1, index - 1);
                node.right = createPreOrder(people, index, end);
            }

            return node;
        }

        // funkcja implementujaca usuwanie z kolejki na podstawie piorytetu.
        public boolean delete(int priority) {
            boolean found = false;
            Node current = root;
            Node parent = null;

            // w petli ustawiamy sie na pozycji szukanego elementu
            // jezeli mamy duplikaty, to zatrzymujemy sie na pierwszym napotkanym
            while(current != null) {
                if(current.info.priority == priority) {
                    found = true;
                    break;
                }
                if (current.info.priority > priority) {
                    parent = current;
                    current = current.left;
                }
                else if(current.info.priority < priority) {
                    parent = current;
                    current = current.right;
                }
            }

            // jezeli nie znaleziono szukanego elementu po calym przejsciu drzewa, zwracamy falsz
            if(!found) {
                return false;
            }

            // przypadek, gdy element nie ma dzieci
            if(current.left == null && current.right == null) {
                if(current == root) { // jezeli to root to ustawiamy go na null
                    root = null;
                }
                else {
                    // ustawiamy odpowiednia galaz poprzednika na null
                    if(parent.right == current) {
                        parent.right = null;
                    }
                    else if(parent.left == current) {
                        parent.left = null;
                    }
                }
            }
            // jezeli ma dziecko na lewej galezi to odpowiednio przepinamy galaz usuwanego elementu do galezi poprzednika
            else if(current.right == null) {
                if(current == root) {
                    root = current.left;
                } else {
                    if(parent.right == current) {
                        parent.right = current.left;
                    }
                    else if(parent.left == current) {
                        parent.left = current.left;
                    }
                }
            }
            // jezeli ma dziecko na prawej galezi to odpowiednio przepinamy galaz usuwanego elementu do galezi poprzednika
            else if(current.left == null) {
                if(current == root) {
                    root = current.right;
                } else {
                    if(parent.right == current) {
                        parent.right = current.right;
                    }
                    else if(parent.left == current) {
                        parent.left = current.right;
                    }
                }
            }
            // jezeli ma dzieci na obu galeziach to zamieniamy node'a
            // z dzieckiem najbardziej po lewej po prawej stronie zamienianego node'a
            else {
                Node nextParent = current;
                Node next = current;
                // przechodzimy raz na prawa galaz
                Node tmp = current.right;

                // nastepnie idziemy do konca drzewa idac w lewo
                while(tmp != null) {
                    nextParent = next;
                    next = tmp;
                    tmp = tmp.left;
                }
                // przypisujemy rodzicowi node'a z konca drzewa, prawe dziecko tego z konca
                // nastepnie prawe dziecko tego z konca jest rowne prawemu dziecku usuwanego node'a
                if(next != current.right) {
                    nextParent.left = next.right;
                    next.right = current.right;
                }

                // jezeli usuwamy roota z dwoma dziecmi
                if(current == root) {
                    root = next;
                }
                else {
                    if(parent.right == current) {
                        parent.right = next;
                    }
                    else if(parent.left == current) {
                        parent.left = next;
                    }
                }
                // ustawiamy ten ostatni node'a drzewa po lewej na miejsce
                // usuwanego przez nas node'a
                next.left = current.left;
            }
            return true;
        }

        // koniec modulu edycji
        // -----------------------------------------------

        // modul kolejkowania
        // -----------------------------------------------

        // funkcja implementujaca dodawanie obiektow do kolejki
        public void enque(Person person) {
            // jezeli drzewo jest puste, dodajemy obiekt na root'a
            if(this.root == null) {
                this.root = new Node(person);
                return;
            }
            Node current = this.root;
            // traversujemy po drzewie, porownujac odpowiednio piorytety obiektow
            // jezeli nasz element jest mniejszy od napotkanego, to idziemy na lewo
            // w innym przypadku na prawo
            // gdy doszlismy do konca, przypisujemy node'a do lewego/prawego dziecka korzenia/podkorzenia
            while(current.left != null || current.right != null) {
                if(person.priority < current.info.priority) {
                    if(current.left != null) {
                        current = current.left;
                    } else {
                        current.left = new Node(person);
                        break;
                    }
                }
                else if(person.priority > current.info.priority){
                    if(current.right != null) {
                        current = current.right;
                    } else {
                        current.right = new Node(person);
                        break;
                    }
                }
                else {
                    if(current.right != null) {
                        current = current.right;
                    }
                    else {
                        current.right = new Node(person);
                        break;
                    }
                }
            }
            //jezeli mamy root bez dzieci
            if(person.priority < current.info.priority) {
                current.left = new Node(person);
            }
            else if(person.priority > current.info.priority) {
                current.right = new Node(person);
            }
            else {
                current.right = new Node(person);
            }
        }

        // funkcja do usuwania najwiekszego elementu w drzewie
        public Person dequeMax() {
            // jezeli pusta, zwroc null
            if(root == null) return null;
            Node current = root;
            // jezeli ma tylko elementy mniejsze od roota, usun root
            if (root.right == null) {
                root = root.left;
                return current.info;
            }

            Node parent = null;


            // traversujemy po drzewie maksymalnie do prawej
            // mamy jedynie 2 przypadki: 1 dziecko badz brak
            // wiec usuwanie jest prostsze
            while (current.right != null) {
                parent = current;
                current = current.right;
            }
            parent.right = current.left;
            return current.info;
        }

        public Person dequeMin() {
            // jezeli puste zwroc null
            if(root == null) return null;

            Node current = root;
            // jezeli ma tylko elementy wieksze od roota, usun root
            if (root.left == null) {
                root = root.right;
                return current.info;
            }

            Node parent = null;

            // traversujemy po drzewie maksymalnie do prawej
            // mamy jedynie 2 przypadki: 1 dziecko badz brak
            // wiec usuwanie jest prostsze
            while (current.left != null) {
                parent = current;
                current = current.left;
            }

            parent.left = current.right;
            return current.info;
        }

        // funkcja do zwrocenia nastepnego elementu (wiekszego od danego)
        public Person next(int priority) {
            boolean found = false;
            Node current = root;
            Node bigger = null;
            // przechodzimy po drzewie szukajac node'a o podanym przez nas piorytecie
            while(current != null) {
                if(current.info.priority == priority) {
                    found = true;
                    // jezeli go znalezlismy, i nie ma dziecka po prawej stronie ( wiekszego od niego)
                    // to przerywamy petle, jezeli znalezlismy to przechodzimy na nie
                    if(current.right != null) {
                        current = current.right;
                    }
                    else {
                        break;
                    }
                }
                else if (current.info.priority > priority) {
                    bigger = current;
                    current = current.left;
                }
                else if(current.info.priority < priority) {
                    current = current.right;
                }
            }
            // jesli nie znaleziono, null
            if(bigger == null || !found) {
                return null;
            }
            else {
                // w innym przypadku zwracaamy nastepny
                return bigger.info;
            }
        }

        // funkcja do zwrocenia poprzedniego elementu (mniejszego od danego)
        public Person prev(int priority) {
            boolean found = false;
            Node current = root;
            Node smaller = null;
            // przechodzimy po drzewie szukajac node'a o podanym przez nas piorytecie
            while(current != null) {
                if(current.info.priority == priority) {
                    found = true;
                    // jezeli go znalezlismy, i nie ma dziecka po lewej stronie ( mniejszego od niego [ sa duplikaty ] )
                    // to przerywamy petle, jezeli znalezlismy to przechodzimy na nie
                    if(current.left != null) {
                        current = current.left;
                    }
                    else {
                        break;
                    }
                }
                else if (current.info.priority > priority) {
                    current = current.left;
                }
                else if(current.info.priority < priority) {
                    smaller = current;
                    current = current.right;
                }
            }
            // jesli nie znaleziono, null
            if(!found || smaller == null) {
                return null;
            }

            // w innym przypadku zwracaamy nastepny
            if(smaller != null) {
                return smaller.info;
            }
            return null;
        }
        // koniec modulu kolejkowania
        // -----------------------------------------------

        // modul raportowania
        // -----------------------------------------------

        // przejscie po drzewie z kolejnoscia preorder
        // wykorzystujac jeden stos wiazany
        public void preorder() {
            LinkedStack stack = new LinkedStack();
            if (root == null) {
                return;
            }
            // wrzucamy korzen na stos
            stack.push(root);
            sb = new StringBuilder();
            // dopoki stos nie jest pusty
            while (!stack.isEmpty()) {
                Node node = stack.pop();
                sb.append(node.info.toString() + ", ");
                // pushujemy prawe dziecko popp'nietego obiektu
                if (node.right != null) {
                    stack.push(node.right);
                }
                // pushujemy lewe dziecko popp'nietego obiektu
                if (node.left != null) {
                    stack.push(node.left);
                }
            }
            // zwracamy finalny wynik
            if(sb.length() > 2) {
                System.out.println("PREORDER: " + sb.substring(0, sb.length() - 2));
            }
        }

        // przejscie po drzewie z kolejnoscia inorder
        // wykorzystujac jeden stos wiazany
        public void inorder() {
            LinkedStack stack = new LinkedStack();
            Node current = root;
            sb = new StringBuilder();
            while (current != null || !stack.isEmpty()) {
                // wrzucamy na stos elementy po lewej stronie drzewa
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
                // nastepnie pop'ujemy ostatni, wpisujemy go do wyniku i przechodzimy do prawego elementu i powtarzamy proces
                current = stack.pop();
                sb.append(current.info.toString() + ", ");
                current = current.right;
            }
            // zwracamy finalny wynik
            if(sb.length() > 2) {
                System.out.println("INORDER: " + sb.substring(0, sb.length() - 2));
            }
        }

        // przejscie po drzewie z kolejnoscia inorder
        // wykorzystujac dwa stosy wiazane
        public void postorder() {
            LinkedStack s1 = new LinkedStack();
            LinkedStack s2 = new LinkedStack();
            if (root == null) {
                return;
            }
            // wrzucamy roota na stos
            s1.push(root);
            // dopoki stos nie jest pusty
            while (!s1.isEmpty()) {
                // popujemy node'a z pierwszego stacka i wrzucamy do drugiego
                Node node = s1.pop();
                s2.push(node);
                // nastepnie pushujemy prawe i lewe dziecko spoppowanego node'a do pierwszego stacka
                if (node.left != null) {
                    s1.push(node.left);
                }
                if (node.right != null) {
                    s1.push(node.right);
                }
            }
            sb = new StringBuilder();
            while (!s2.isEmpty()) {
                // dodajemy do stringbuildera obiekty z drugiego stosu
                Node node = s2.pop();
                sb.append(node.info.toString() + ", ");
            }
            // wypisujemy finalny wynik
            if(sb.length() > 2) {
                System.out.println("POSTORDER: " + sb.substring(0, sb.length() - 2));
            }
        }

        //rec
        // funkcja do zwracania dlugosci drzewa
        public int height() {
            return heightHelper(root) - 1 ;
        }

        // rekurencyjna funkcja pomocnicza
        public int heightHelper(Node node) {
            if (node == null)
                return 0; // jesli puste
            else {
                // oblicz dlugosc lewej strony rekurencyjnie
                int leftHeight = heightHelper(node.left);
                // oblicz dlugosc prawej strony rekurencyjnie
                int rightHeight = heightHelper(node.right);

                if (leftHeight >= rightHeight)
                    return leftHeight + 1;
                else
                    return rightHeight + 1;
            }
        }
        // koniec modulu raportowania
        // -----------------------------------------------
    }

    public static void main(String[] args) {
        // wczytanie ilosci zestawow
        int numOfSets = sc.nextInt();
        for (int i = 0; i < numOfSets; i++) {
            // wypisanie ktory to zestaw i stworzenie obiektu drzewa
            System.out.println("ZESTAW " + (i+1));
            BinarySearchTree tree = new BinarySearchTree();
            // wczytanie ilosci komend
            int numOfCommands = sc.nextInt();
            for (int j = 0; j < numOfCommands; j++) {
                String command = sc.next();
                // switch case do wyboru komend i ich obsluga
                switch (command) {
                    case "CREATE": {
                        String order = sc.next();
                        int numOfPeople = sc.nextInt();
                        Person[] people = new Person[numOfPeople];
                        for (int k = 0; k < numOfPeople; k++) {
                            int priority = sc.nextInt();
                            String name = sc.next();
                            String surname = sc.next();
                            people[k] = new Person(priority, name, surname);
                        }
                        tree.create(order,people);
                        break;
                    }
                    case "DELETE": {
                        int priority = sc.nextInt();
                        if(!tree.delete(priority)) {
                            System.out.println("DELETE " + priority + ": BRAK");
                        }
                        break;
                    }
                    case "ENQUE": {
                        int p = sc.nextInt();
                        String n = sc.next();
                        String s = sc.next();
                        Person enque_person = new Person(p, n, s);
                        tree.enque(enque_person);
                        break;
                    }
                    case "PREORDER": {
                        tree.preorder();
                        break;
                    }
                    case "INORDER": {
                        tree.inorder();
                        break;
                    }
                    case "POSTORDER": {
                        tree.postorder();
                        break;
                    }
                    case "DEQUEMAX": {
                        Person max = tree.dequeMax();
                        if(max != null) {
                            System.out.println("DEQUEMAX: " + max);
                        }
                        else {
                            System.out.println("DEQUEMAX: BRAK");
                        }
                        break;
                    }
                    case "DEQUEMIN": {
                        Person min = tree.dequeMin();
                        if(min != null) {
                            System.out.println("DEQUEMIN: " + min);
                        }
                        else {
                            System.out.println("DEQUEMIN: BRAK");
                        }
                        break;
                    }
                    case "NEXT": {
                        int priority = sc.nextInt();
                        Person next = tree.next(priority);
                        if(next != null) {
                            System.out.println("NEXT " + priority + ": " + next);
                        }
                        else {
                            System.out.println("NEXT " + priority + ": BRAK");
                        }
                        break;
                    }
                    case "PREV": {
                        int priority = sc.nextInt();
                        Person prev = tree.prev(priority);
                        if(prev != null) {
                            System.out.println("PREV " + priority + ": " + prev);
                        }
                        else {
                            System.out.println("PREV " + priority + ": BRAK");
                        }
                        break;
                    }
                    case "HEIGHT": {
                        System.out.println("HEIGHT: " + tree.height());
                        break;
                    }
                    default:
                        break;
                }
            }
        }
    }
}

// TESTY
//1
//        17
//CREATE PREORDER 6 5 A A -5 B B 0 C C -4 D D -1 E E 10 F F
//HEIGHT
//PREV -8
//DELETE -10
//DEQUEMAX
//DELETE -2
//DEQUEMIN
//        DEQUEMAX
//DELETE -6
//DELETE -7
//DEQUEMIN
//        DEQUEMAX
//ENQUE 4 G G
//DELETE 5
//DEQUEMIN
//DELETE -5
//DELETE 4
//ZESTAW 1
//HEIGHT: 4
//PREV -8: BRAK
//DELETE -10: BRAK
//DEQUEMAX: 10 - F F
//DELETE -2: BRAK
//DEQUEMIN: -5 - B B
//DEQUEMAX: 5 - A A
//DELETE -6: BRAK
//DELETE -7: BRAK
//DEQUEMIN: -4 - D D
//DEQUEMAX: 0 - C C
//DELETE 5: BRAK
//DEQUEMIN: -1 - E E
//DELETE -5: BRAK
