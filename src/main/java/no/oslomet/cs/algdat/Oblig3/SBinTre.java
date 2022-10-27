package no.oslomet.cs.algdat.Oblig3;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

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
        Node<T> parent = null;
        int cmp = 0;
        while (p != null) {   //Bruker komparator og flytter current dersom current ikke er null
            parent = p;
            cmp = comp.compare(verdi, p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }
        p = new Node<>(verdi, parent);
        if (parent == null) {   //Current blir rotnode dersom forelder er null
            rot = p;
        }
        else if (cmp < 0) { //venstre barn til forelder
            parent.venstre = p;
        }
        else {  //høyre barn til forelder
            parent.høyre = p;
        }
        antall++;
        endringer++;
        return true;
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
