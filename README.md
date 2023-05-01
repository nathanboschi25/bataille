# Jeu de la Bataille

## Version console
### Compilation du projet
```shell
cd ./src
javac PartieBataille.java
```
### Lancement d'une partie
```shell
java PartieBataille
```
Entrez les noms des joueurs, le nombre de cartes à jouer et c'est parti !
n


## Version graphique
### Compilation du projet
```shell
javac --module-path **PathToYourJavaFXLibPackage** --add-modules javafx.controls,javafx.fxml src/PartieBatailleGUI.java
```
### Lancement d'une partie
```shell
java --module-path **PathToYourJavaFXLibPackage** --add-modules javafx.controls,javafx.fxml PartieBatailleGUI
```
Appuyez sur `Flèche Droite` du clavier pour passer au tour suivant. Utilisez `Entrée` ou le bouton `Ok` pour fermer les fenètres contextuelles.