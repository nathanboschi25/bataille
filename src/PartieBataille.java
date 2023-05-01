import assets.ConsoleColors;
import game_elements.Carte;
import game_elements.Joueur;
import game_elements.PaquetDeCartes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PartieBataille {

    private static boolean victoire;
    PaquetDeCartes paquetDeCartes;

    Joueur joueurA;
    Joueur joueurB;

    ArrayList<Carte> plateau;

    short nbTours;


    public PartieBataille(String nomJ1, String nomJ2, byte nbCartes) {
        paquetDeCartes = new PaquetDeCartes(nbCartes);

        this.joueurA = new Joueur(nomJ1);
        this.joueurB = new Joueur(nomJ2);

        plateau = new ArrayList<>();

        nbTours = 0;

        victoire = false;
    }

    public void initGame() {
        paquetDeCartes.melanger();
        paquetDeCartes.distribuer(joueurA, joueurB);
    }

    public boolean nouveauTour() {
        System.out.println();
        Carte c1 = joueurA.tirerUneCarte();
        Carte c2 = joueurB.tirerUneCarte();

        plateau.addAll(Arrays.asList(c1, c2));

        System.out.println(ConsoleColors.BLUE + "Tour " + (nbTours + 1) + ConsoleColors.RESET);
        System.out.println("[" + joueurA.nom + "]: " + c1 + " vs [" + joueurB.nom + "]: " + c2);
        if (c1.compareTo(c2) == 1) {
            nbTours++;
            return gagneLeTour(joueurA);
        } else if (c1.compareTo(c2) == -1) {
            nbTours++;
            return gagneLeTour(joueurB);
        } else {
            nbTours++;
            return bataille();
        }
    }

    private boolean bataille() {
        System.out.println(ConsoleColors.CYAN + "Il y a égalité, Bataille !!!" + ConsoleColors.RESET);

        Carte c1Retournee = joueurA.tirerUneCarte();
        Carte c2Retournee = joueurB.tirerUneCarte();

        if (c1Retournee == null)
            return gagneLeTour(joueurB);
        else if (c2Retournee == null)
            return gagneLeTour(joueurA);

        Carte c1 = joueurA.tirerUneCarte();
        Carte c2 = joueurB.tirerUneCarte();

        if (c1 == null) {
            c1 = c1Retournee;
            plateau.addAll(Arrays.asList(c1, c2Retournee, c2));
        } else if (c2 == null) {
            c2 = c2Retournee;
            plateau.addAll(Arrays.asList(c1Retournee, c1, c2));
        } else
            plateau.addAll(Arrays.asList(c1Retournee, c1, c2Retournee, c2));

        System.out.println("[" + joueurA.nom + "]: " + c1 + " vs [" + joueurB.nom + "]: " + c2);
        if (c1.compareTo(c2) == 1) {
            return gagneLeTour(joueurA);
        } else if (c1.compareTo(c2) == -1) {
            return gagneLeTour(joueurB);
        } else {
            return bataille();
        }
    }

    private boolean gagneLeTour(Joueur joueur) {
        System.out.println(ConsoleColors.CYAN_BOLD + joueur.nom + " gagne le tour!" + ConsoleColors.RESET);
        joueur.prendsPlateau(plateau);
        System.out.println(joueurA.nom + " a " + joueurA.main.size() + " cartes dans sa main, et " + joueurA.tas.size() + " cartes dans son tas.");
        System.out.println(joueurB.nom + " a " + joueurB.main.size() + " cartes dans sa main, et " + joueurB.tas.size() + " cartes dans son tas.");
        System.out.println();
        return gagnePartie(joueur.id);
    }

    private boolean gagnePartie(byte id) {
        if (id == joueurA.id)
            return joueurB.estVide();
        if (id == joueurB.id)
            return joueurA.estVide();
        return false;
    }

    private byte whoWins() {
        System.out.print(ConsoleColors.RED_BOLD_BRIGHT);
        if (gagnePartie(joueurA.id)) {
            System.out.println(joueurA.nom + " a gagné la partie haut la main !");
            return joueurA.id;
        }
        if (gagnePartie(joueurB.id)) {
            System.out.println(joueurB.nom + " a gagné la partie haut la main !");
            return joueurB.id;
        }
        System.out.println("Vous avez dépassé le nombre de tours maximum, il y a donc égalité.");
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] nomJoueurs = new String[2];
        System.out.println("Nom du joueur 1 :");
        nomJoueurs[0] = sc.next();
        System.out.println("Nom du joueur 2 :");
        nomJoueurs[1] = sc.next();
        System.out.println("Combien de cartes dans le paquet ? (1: Paquet de 52 cartes, 2: Paquet de 32 cartes)");

        PartieBataille bataille;

        switch (sc.nextByte()) {
            case 1 -> {
                bataille = new PartieBataille(nomJoueurs[0], nomJoueurs[1], PaquetDeCartes.Taille.PAQUET_52);
            }
            case 2 -> {
                bataille = new PartieBataille(nomJoueurs[0], nomJoueurs[1], PaquetDeCartes.Taille.PAQUET_32);
            }
            default -> {
                bataille = new PartieBataille(nomJoueurs[0], nomJoueurs[1], PaquetDeCartes.Taille.PAQUET_32);
            }
        }

        bataille.initGame();

        while (!victoire && bataille.nbTours < bataille.paquetDeCartes.initialSize * bataille.paquetDeCartes.initialSize) {
            victoire = bataille.nouveauTour();
        }

        bataille.whoWins();
    }
}
