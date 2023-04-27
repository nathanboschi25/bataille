import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BatailleNextEvent implements EventHandler<KeyEvent> {
    PartieBatailleGUI bataille;

    BatailleNextEvent(PartieBatailleGUI app) {
        this.bataille = app;
    }


    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.RIGHT) {
            if (!bataille.victoire && bataille.nbTours < bataille.paquetDeCartes.initialSize * bataille.paquetDeCartes.initialSize) {
                bataille.victoire = bataille.nouveauTour();
                return;
            }
            bataille.whoWins();
            bataille.stage.close();
        }
    }
}