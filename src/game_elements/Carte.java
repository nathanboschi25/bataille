package game_elements;

import assets.ConsoleColors;
import javafx.scene.image.Image;

public class Carte {

    public int couleur;

    public int valeur;

    public Carte(int couleur, int valeur){
        this.couleur = couleur;
        this.valeur = valeur;
    }

    public String toString(){
        return ConsoleColors.YELLOW + Valeur.str[valeur] + " de " + Couleur.str[couleur] + ConsoleColors.RESET;
    }

    /**
     * Récupere l'image liée à la carte (Image javafx)
     * @return Image .png de la carte
     */
    public Image getUIimage(){
        return new Image("assets/google_cards/" + valeur + '_' + Couleur.str[couleur] + ".png");
//        return new Image("assets/google_cards/back.png");
    }

    /**
     * Compare la valeur des deux cartes
     * @param c2 gameElements.Carte à comparer avec la carte actuelle
     * @return 1 si c1.val > c2.val, -1 si c1.val < c2.val, 0 sinon
     */
    public byte compareTo(Carte c2){
        if (this.valeur > c2.valeur)
            return 1;
        else if (this.valeur < c2.valeur)
            return -1;
        else
            return 0;
    }

    public static class Couleur {
        private Couleur() {
        }

        public static final int PIQUE = 3;
        public static final int COEUR = 2;
        public static final int CARREAU = 1;
        public static final int TREFLE = 0;

        public static final String[] str = new String[]{"TREFLE", "CARREAU", "COEUR", "PIQUE"};
    }

    public static class Valeur {
        private Valeur() {
        }

        public static final int SEPT = 7;
        public static final int HUIT = 8;
        public static final int NEUF = 9;
        public static final int DIX = 10;
        public static final int VALET = 11;
        public static final int DAME = 12;
        public static final int ROI = 13;
        public static final int AS = 14;

        public static final String[] str = new String[]{"", "", "DEUX", "TROIS", "QUATRE", "CINQ", "SIX", "SEPT", "HUIT", "NEUF", "DIX", "VALET", "DAME", "ROI", "AS"};
    }
}
