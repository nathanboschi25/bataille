package game_elements;

import java.util.ArrayList;
import java.util.Collections;

public class Joueur {

    public String nom;

    private static byte idCounter;
    public byte id;

    public ArrayList<Carte> main;

    public ArrayList<Carte> tas;

    public Joueur(String nom) {
        Joueur.idCounter++;
        this.id = idCounter;
        this.nom = nom;
        this.main = new ArrayList<>();
        this.tas = new ArrayList<>();
    }

    /**
     * Ajoute la carte fournie à la main du joueur
     * @param c gameElements.Carte à ajouter dans la main
     */
    public void ajouterUneCarte(Carte c) {
        main.add(c);
    }

    /**
     * Tire la prochaine carte de la main du joueur.
     * Si la main est vide alors on essaie de remplir avec le tas des cartes récuperées, si celui ci est vide, retourne null.
     * @return Prochaine carte, null si joueur.estVide()
     */
    public Carte tirerUneCarte() {
        if (this.main.isEmpty()) {
            if (this.tas.isEmpty())
                return null;
            else {
                Collections.shuffle(tas);
                this.main.addAll(this.tas);
                this.tas.clear();
                System.out.println(this.nom + " mélange et récupère son tas.");
            }
        }

        return main.remove(main.size()-1);
    }

    public boolean estVide(){
        return this.main.isEmpty() && this.tas.isEmpty();
    }

    /**
     * Récupere les cartes contenues dans le plateau et vide le plateau
     * @param plateau Plateau à récuperer/vider
     */
    public void prendsPlateau(ArrayList<Carte> plateau){
        this.tas.addAll(plateau);
        plateau.clear();
    }
}
