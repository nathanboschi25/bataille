import assets.ConsoleColors;
import game_elements.Carte;
import game_elements.Joueur;
import game_elements.PaquetDeCartes;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javax.swing.text.LabelView;
import java.util.ArrayList;
import java.util.Arrays;

public class PartieBatailleGUI extends Application {

    PaquetDeCartes paquetDeCartes;

    Joueur joueurA;
    Joueur joueurB;

    ArrayList<Carte> plateau;

    short nbTours;

    public Stage stage;

    boolean victoire;

    private ImageView c1PotJeuGUI;
    private ImageView c2PotJeuGUI;
    private ImageView j1ImageView;
    private Label nbCartesJ1;
    private ImageView j2ImageView;
    private Label nbCartesJ2;

    private final static Image cardBackImage = new Image("assets/google_cards/back.png");
    private Label noTour;

    public void initGame(String nomJ1, String nomJ2, byte nbCartes) {
        paquetDeCartes = new PaquetDeCartes(nbCartes);

        this.joueurA = new Joueur(nomJ1);
        this.joueurB = new Joueur(nomJ2);

        plateau = new ArrayList<>();

        nbTours = 0;

        paquetDeCartes.melanger();
        paquetDeCartes.distribuer(joueurA, joueurB);
    }

    private void initGUI(BorderPane fenetre) {
        BorderPane borderPane = new BorderPane();

        GridPane potJeuGUI = new GridPane();

        c1PotJeuGUI = new ImageView();
        c1PotJeuGUI.setFitWidth(80);
        c1PotJeuGUI.setPreserveRatio(true);

        c2PotJeuGUI = new ImageView();
        c2PotJeuGUI.setFitWidth(80);
        c2PotJeuGUI.setPreserveRatio(true);

        potJeuGUI.add(c1PotJeuGUI, 1, 0);
        potJeuGUI.add(c2PotJeuGUI, 0, 0);
        potJeuGUI.setAlignment(Pos.CENTER);
        potJeuGUI.setHgap(80);
        borderPane.setCenter(potJeuGUI);

        addPlayersDeck(borderPane);

        fenetre.setCenter(borderPane);
    }

    private void addPlayersDeck(BorderPane borderPane) {
        BorderPane j1B = new BorderPane();

        j1ImageView = new ImageView();
        j1ImageView.setImage(cardBackImage);
        j1ImageView.setFitWidth(70);
        j1ImageView.setPreserveRatio(true);
        j1B.setLeft(j1ImageView);

        nbCartesJ1 = new Label("Main:\t" + joueurA.main.size() + "\nTas:  \t" + joueurA.tas.size());
        nbCartesJ1.setStyle("-fx-font-size: 15px;");
        j1B.setRight(nbCartesJ1);

        j1B.setCenter(new Label('[' + joueurA.nom + ']'));
        j1B.getCenter().setStyle("-fx-font-size: 20px");

        j1B.setStyle("-fx-background-color: #7cd4ff; -fx-padding: 10px");
        borderPane.setTop(j1B);

        BorderPane j2B = new BorderPane();

        j2ImageView = new ImageView();
        j2ImageView.setImage(cardBackImage);
        j2ImageView.setFitWidth(70);
        j2ImageView.setPreserveRatio(true);
        j2B.setRight(j2ImageView);

        nbCartesJ2 = new Label("Main:\t" + joueurB.main.size() + "\nTas:  \t" + joueurB.tas.size());
        nbCartesJ2.setStyle("-fx-font-size: 15px;");
        j2B.setLeft(nbCartesJ2);

        j2B.setCenter(new Label('[' + joueurB.nom + ']'));
        j2B.getCenter().setStyle("-fx-font-size: 20px");

        j2B.setStyle("-fx-background-color: #ffd17c; -fx-padding: 10px");
        borderPane.setBottom(j2B);

        Label bataille = new Label("Bataille");
        bataille.setStyle("-fx-font-size: 15px; -fx-padding: 10px");
        borderPane.setLeft(bataille);

        noTour = new Label("Tour x");
        noTour.setStyle("-fx-font-size: 15px; -fx-padding: 10px");
        borderPane.setRight(noTour);
    }

    public void updateGUI() {
        noTour.setText("Tour " + nbTours);

        if (plateau.size() >= 2) {
            c1PotJeuGUI.setImage(plateau.get(plateau.size() - 1).getUIimage());
            c2PotJeuGUI.setImage(plateau.get(plateau.size() - 2).getUIimage());
        } else {
            nbCartesJ1.setText("Main:\t" + joueurA.main.size() + "\nTas:  \t" + joueurA.tas.size());
            nbCartesJ2.setText("Main:\t" + joueurB.main.size() + "\nTas:  \t" + joueurB.tas.size());
        }
    }

    public boolean nouveauTour() {
        System.out.println();
        Carte c1 = joueurA.tirerUneCarte();
        Carte c2 = joueurB.tirerUneCarte();

        plateau.addAll(Arrays.asList(c1, c2));
        System.out.println(ConsoleColors.BLUE + "Tour " + (nbTours + 1) + ConsoleColors.RESET);
        System.out.println("[" + joueurA.nom + "]: " + c1 + " vs [" + joueurB.nom + "]: " + c2);
        updateGUI();
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bataille !");
        alert.setHeaderText("Vos cartes sont égales, Battez vous !");
        alert.showAndWait();

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
        updateGUI();
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
        updateGUI();
        return gagnePartie(joueur.id);
    }

    private boolean gagnePartie(byte id) {
        if (id == joueurA.id)
            return joueurB.estVide();
        if (id == joueurB.id)
            return joueurA.estVide();
        return false;
    }

    public byte whoWins() {
        System.out.print(ConsoleColors.RED_BOLD_BRIGHT);
        if (gagnePartie(joueurA.id)) {
            System.out.println(joueurA.nom + " a gagné la partie haut la main !");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fin de partie");
            alert.setHeaderText("C'est la fin de la partie, " + joueurA.nom + " a gagné la partie haut la main !");
            alert.showAndWait();
            return joueurA.id;
        }
        if (gagnePartie(joueurB.id)) {
            System.out.println(joueurB.nom + " a gagné la partie haut la main !");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fin de partie");
            alert.setHeaderText("C'est la fin de la partie, " + joueurA.nom + " a gagné la partie haut la main !");
            alert.showAndWait();
            return joueurB.id;
        }
        System.out.println("Vous avez dépassé le nombre de tours maximum, il y a donc égalité.");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de partie");
        alert.setHeaderText("C'est la fin de la partie, le nombre de tours maximum à été dépassé.\nIl y a donc égalité.");
        alert.showAndWait();
        return 0;
    }


    @Override
    public void start(Stage stage) {
        this.stage = stage;

        stage.setTitle("Partie de Bataille");

        BorderPane fenetre = new BorderPane();

        initGame("Tom", "Jerry", PaquetDeCartes.Taille.PAQUET_32);
        initGUI(fenetre);

        victoire = false;

        Scene scene = new Scene(fenetre, 450, 600);
        scene.setOnKeyPressed(new BatailleNextEvent(this));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
