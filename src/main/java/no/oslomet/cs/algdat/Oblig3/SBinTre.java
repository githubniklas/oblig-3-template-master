package no.oslomet.cs.algdat.Oblig3;


import java.util.*;
/*
    Niklas Laache s362125, s362125@oslomet.no
 */
public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi);  //Ikke lov med null verdier

        Node<T> p = rot;
        Node<T> q = null;
        int cmp = 0;
        while (p != null) {   //Bruker komparator og flytter current dersom current ikke er null
            q = p;
            cmp = comp.compare(verdi, p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }
        p = new Node<>(verdi, q);
        if (q == null) {   //Current blir rotnode dersom forelder er null
            rot = p;
        }
        else if (cmp < 0) { //venstre barn til forelder
            q.venstre = p;
        }
        else {  //høyre barn til forelder
            q.høyre = p;
        }
        antall++;
        endringer++;
        return true;
    }

    public boolean fjern(T verdi) {
        if  (verdi == null) {   //returnerer false dersom tre har nullverdier
            return false;
        }
        Node<T> p = rot;    //q er forelderen til p
        Node<T> q = null;

        while (p != null) { //while løkke som leter etter verdier
            int cmp = comp.compare(verdi, p.verdi); //komparator for å sammenligne
            if (cmp < 0) {  //går til venstre
                q = p;
                p = p.venstre;
            }
            else if (cmp > 0) { //går til høyre
                q = p;
                p = p.høyre;
            }
            else {  //søkt verdi ligger i p
                break;
            }
        }
        if (p == null) {    //returnerer false dersom verdi ikke blir funnet
            return false;
        }
        if (p.venstre == null || p.høyre == null) { //tilfelle 1 og tilfelle 2
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;
            if (p == rot) {
                rot = b;
            }
            else if (p == q.venstre) {
                q.venstre = b;
            }
            else {
                q.høyre = b;
            }
            if (b != null) {
                b.forelder = q;
            }
        }
        else {  //tilfelle 3
            Node<T> s = p;
            Node<T> r = p.høyre;
            while (r.venstre != null) {
                s = r;  //s er forelder til r
                r = r.venstre;
            }
            p.verdi = r.verdi;

            if (s != p) {   //sørger for riktig foreldre peker dersom r.høyre ikke er tom
                s.venstre = r.høyre;
            }
            else {
                s.høyre = r.høyre;
            }
            if (r.høyre != null) {  //sørger for riktig foreldre peker dersom r.høyre ikke er tom
                r.høyre.forelder = s;
            }
        }
        antall--;   //antall reduseres fordi det er en mindre node
        return true;
    }

    public int fjernAlle(T verdi) {
        if (tom()) {    //dersom det ikke er noen verdier, returneres 0
            return 0;
        }
        int antallVerdier = 0;  //teller antall verdier fjernet
        while (fjern(verdi)) {  //while løkke som legger til 1 i antallVerdier for hver iterasjon av fjern()
            antallVerdier++;
        }
        return antallVerdier;
    }

    public int antall(T verdi) {
        if (verdi == null) {    //dersom verdi er null returneres 0
            return 0;
        }
        Node<T> p = rot;
        int teller = 0;

        while (p != null) {   //Bruker komparator og flytter current dersom current ikke er null
            int cmp = comp.compare(verdi, p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
            if (cmp == 0) { //teller går opp dersom den er 0
                teller++;
            }
        }
        return teller;
    }

    public void nullstill() {
        if (antall == 0) {  //returnerer ingenting dersom antall er 0
            return;
        }
        Node<T> p = rot;
        int antallVerdier = antall;
        p = førstePostorden(p); //oppdaterer antall verdier

        while (antallVerdier != 0) {    //kjører dersom antallverdier ikke er 0
            if (p != null) {    //bruker fjern() metoden og nullstiller hver iterasjon der antallverdier ikke er 0
                fjern(p.verdi);
            }
            if (p != null) {    //oppdaterer p verdien
                p.verdi = null;
            }
            if (p != null) {    //bruker nestePostorden for å gå videre i treet.
                p = nestePostorden(p);
            }
            antallVerdier--;    //antall verdier minskes
        }
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        Objects.requireNonNull(p);  //Sjekker for nullverdier

        while (true) {
            if (p.venstre != null) {
                p = p.venstre;  //Venstrebarn til p
            }
            else if (p.høyre != null) {
                p = p.høyre;    //Høyrebarn til p
            }
            else {
                return p;
            }
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Node<T> f = p.forelder;

        if (f == null) {
            return null;
        }
        if (f.høyre == p) { //Sjekker om p er høyrebarn
            return f;   //Neste postorden er forelder til p
        }
        else if (f.høyre == null) { //Er p venstrebarn sjekkes om p har høyre søsken
            return f;   //Neste postorden er forelder til p
        }
        else {  //Er p venstrebarn og har høyre søsken er denne neste postorden
            return førstePostorden(f.høyre);
        }
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = rot;
        p = førstePostorden(p); //Finner første porstorden

        while (p != null) { //Stopper dersom p er null
            oppgave.utførOppgave(p.verdi);
            p = nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p.venstre != null) {    //sjekker venstre subtre
            postordenRecursive(p.venstre, oppgave); //kaller metoden med p.venstre som parameter
        }
        if (p.høyre != null) {  //sjekker høyre subtre
            postordenRecursive(p.høyre, oppgave);   //kaller metoden med p.høyre som parameter
        }
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        ArrayList<T> subTreArray = new ArrayList<>();
        Queue<Node<T>> kø = new LinkedList<>();

        kø.add(rot);    //legger inn første verdi i treet
        while (!kø.isEmpty()) { //while-løkke som kjører dersom treet ikke er tomt
            Node<T> p = kø.remove();
            subTreArray.add(p.verdi);
            if (p.venstre != null) {    //venstre barn blir lagt inn i køen, dersom den ikke er null.
                kø.add(p.venstre);
            }
            if (p.høyre != null) {  //høyre barn blir lagt til i køen, dersom den ikke er null
                kø.add(p.høyre);
            }
        }
        return subTreArray; //returnerer arrayet
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        SBinTre<K> tre = new SBinTre<>(c);  //lager tomt tre
        for (K verdi : data) {  //for-løkke som itererer gjennom treet og legger til verdien hver gang en ny verdi finnes
            tre.leggInn(verdi);
        }
        return tre; //returnerer verdien
    }


} // ObligSBinTre
