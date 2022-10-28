# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Niklas Laache, S362125, s362125@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så gikk jeg fram med å lage metoden public boolean leggInn(T verdi). Startet med en requireNonNull fordi null verdier ikke er lov. Så kommer en
while-løkke som itererer dersom p ikke er null, q blir forelder til p. Bruker komparator og flytter p etter det. Deretter blir en ny node laget, slik at
referansen forelder får riktig verdi. Så kommer en if/else if/else der p blir rotnode når q er null, ellers blir q.venstre = p og q.høyre = p. Til slutt returneres
true, antall og endringer øker.

I oppgave 2 gikk jeg fram ved å lage metoden public int antall(T verdi). Starter med å returnere 0 dersom verdi er null. Deretter initialisers p som rotnode og
en hjelpevariabel teller. Deretter kommer en while-løkke som itererer dersom p ikke er null, så en komparator og flytter p. Så kommer en if-test som øker teller
dersom cmp er 0. Til slutt returneres teller.

I oppgave 3 gikk jeg fram ved å lage metoden private static <T> Node<T> førstePostorden(Node<T> p). Først kommer en requireNonNul som sjekker for nullverdier.
Deretter kommer en while-løkke som returnerer første node post orden med p som rot. 
Etter det lagde jeg metoden private static <T> Node<T> nestePostorden(Node<T> p) der f blir initialiesert som p.forelder. Deretter kommer en if-test som returnerer
null dersom forelder er null. Så kommer en til if-test som sjekker om p er høyrebarn og returnerer f, deretter kommer en else if som kjører dersom p er venstrebarn
og sjekkes om p har høyre søsken og returnerer f. Til slutt komer en else, som kjører da p er venstrebarn og har høyre søsken så er det denne som blir neste postorden.

I oppgave 4 gikk jeg fram ved å lage hjelpemetoden public void postorden(Oppgave <? super T> oppgave) her blir p initialisert som rot og førstePostorden() blir brukt
for å finne første node av p. Deretter kommer en while-løkke som itererer gjennom treet og oppdaterer neste verdi i postorden.
Deretter lagde jeg hjelpemetoden private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) først kommer en if-test som kjører dersom p.venstre ikke er
null, og deretter blir metoden postordenRecursive kalt med p.venstre som parameter. Deretter kommer en if-test som kjører dersom p.høyre ikke er null
og kaller metoden postordenRecursive med p.høyre som parameter. Til slutt blir oppgaven kjørt.

I oppgave 5 gikk jeg fram ved å implementere hjelpemetoden public ArrayList<T> serialize() Der et array blir initialisert hvor verdiene legges inn i. Og en kø
av binær-treet blir også initialisert. Først blir den første verdien lagt inn i treet. Deretter kommer en while-løkke som itererer så lenge treet ikke er tomt.
Deretter blir verdier tatt ut av toppen av treet, disse verdiene blir så lagt inn i arrayet. Så kommer en if-test som legger inn venstre barn så lenge den eksisterer
og en til if-test som legger inn høyre barn så lenge det eksisterer. Til slutt blir arrayet returnert. 
Deretter lagde jeg metoden static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) Her blir først ett nytt binærtre initialisert med komparator
og deretter kommer en while-løkke som itererer gjennom treet og legger til verdi for hver gang en ny verdi blir funnet. til slutt blir tre returnert.

I oppgave 6 gikk jeg fram ved å implementere metoden public boolean fjern(T verdi), først kommer en if-test som sjekker om treet har null-verdier, ellers returneres
false. p blir initialisert som rotnode og q er forelder til p. Deretter kommer en while-løkke som leter etter verdier, med en komparator som sammenligner.
I while-løkken kommer en if test som går til venstre dersom cmp < 0. deretter kommer en else if som går mot høyre dersom cmp > 0. og til slutt en else som bryter
dersom den søkte verdien ligger i p. Etter det kommer en if-test som sjekker om p er null og returnerer false. Deretter kommer tilfelle 1 og 2 som kjører dersom
p.venstre og p.høyre er null. som sørger for at riktig foreldre peker blir satt dersom barn ikke er null. Etter det kommer en else som er tilfelle 3 denne sørger
for riktig foreldre peker dersom r.høyre er tom. Til slutt blir antall redusert da det er en mindre node i treet. og true blir returnert.
Etter det implementerte jeg metoden public int fjernAlle(T verdi). Der en if-test kommer først som returnerer 0 dersom den ikke finner noen verdier.
Etter det kommer en while-løkke som øker antallVerdier med en for hver iterasjon av fjern() metoden. til slutt blir antallVerdier returnert.
Deretter lagde jeg metoden public void nullstill() Denne starter først med å sjekke om antall er 0 og returnerer ingenting dersom den er det. Så blir p
initialisert som rotnode, hjelpevariabel antallVerdier og første verdi av p blir initialisert ved bruk av førstePostorden(), slik at vi kan bruke nestePostorden().
Deretter kommer en while-løkke som itererer hver gang antallVerdier ikke er 0. Etter det blir fjern() metoden brukt som nullstiller verdi for hver iterasjon.
Etter det blir verdien oppdatert og metoden nestePostorden() blir brukt for å gå videre i treet. Til slutt endres antallVerdier. 