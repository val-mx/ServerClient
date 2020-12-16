package val.mx.nelly;

import java.util.Arrays;
import java.util.LinkedList;

public enum SpielZug {
    STEIN, PAPIER, SCHERE, SPOCK, ECHSE;


    static {
        STEIN.set(SCHERE,ECHSE);
        PAPIER.set(STEIN,SPOCK);
        SCHERE.set(PAPIER, ECHSE);
        SPOCK.set(SCHERE,STEIN);
        ECHSE.set(SPOCK,PAPIER);
    }
    LinkedList<SpielZug> spielZugs;

    void set(SpielZug... gewinnendeSpielZuege) {
        spielZugs = new LinkedList<>(Arrays.asList(gewinnendeSpielZuege));
    }
}
