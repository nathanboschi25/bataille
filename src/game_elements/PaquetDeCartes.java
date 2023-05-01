package game_elements;

import java.util.ArrayList;
import java.util.Collections;

public class PaquetDeCartes {
    public final byte initialSize;

    public ArrayList<Carte> cartes;

    public PaquetDeCartes(byte nbCartes) {
        initialSize = nbCartes;
        this.cartes = new ArrayList<>();
        for (byte i = 0; i < 4; i++) {
            for (byte j = (byte) (15 - (nbCartes / 4)); j < 14; j++) {
                cartes.add(new Carte(i, j));
            }
        }
    }

    public void melanger() {
        Collections.shuffle(cartes);
    }

    private Carte tirerCarte() {
        return cartes.remove(cartes.size() - 1);
    }

    public void distribuer(Joueur... joueurs) {
        while (!cartes.isEmpty()) {
            for (Joueur j : joueurs) {
                j.ajouterUneCarte(this.tirerCarte());
                if (cartes.isEmpty())
                    break;
            }
        }
    }

    public String toString() {
        return "Paquet de " + this.initialSize + " cartes.";
    }


    public static class Taille {
        public static byte PAQUET_32 = 32;
        public static byte PAQUET_52 = 52;
    }
}
