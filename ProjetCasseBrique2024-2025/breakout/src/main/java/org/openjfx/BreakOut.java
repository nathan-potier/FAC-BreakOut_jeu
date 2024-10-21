package org.openjfx;



import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class BreakOut extends Application {

    private static final String GAME_TITLE = "BreakOut";
    private static final int width = 1024;
    private static final int height = 768;



    private boolean gameStarted = false;
    private int diff = 1;
    private Niveau niv = new Niveau(5, 12, diff);

    private final int brickRows = niv.getBrickRows();
    private final int brickCols = 12;
    private Brick[][] bricks;

    private int score = 0;
    private int bestScore = 0; // Meilleur score
    
    private double rectWidth = 100;
    private double rectHeight = 50;
    private double rectX = (width - rectWidth) / 2;
    private double rectY = (height - rectHeight) / 2;

    private Bouton boutonRetour;
    private Bouton boutonJouer;

 

    


    private boolean gameOver = false;




    public static int getWidth(){ return width; }
    public static int getHeight(){ return height; }
    public static String getTitre(){ return GAME_TITLE; }

    //Réinitialise l'état du jeu pour recommencer une nouvelle partie
    private void resetGame(){

        this.score = 0;

        Balle.reset();
  
        this.gameOver = false;
        this.gameStarted = false;
        Son.StopMusique();  // La musique s'arrête
        
        for(int i=0; i<brickRows; i++){
            for(int j=0; j<brickCols; j++){
                bricks[i][j].isNotBreak = true;
            }
        }
        
        Affichage.resetPseudoInput(); // Réactiver la saisie du pseudo après réinitialisation du jeu
        Son.JouerMusique(); // Rejouer la musique
    }
    


    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        

        // Initialiser les boutons
        boutonRetour = new Bouton(width / 2 - 50, height / 2 + 60, 100, 40, "Retour");
        boutonJouer = new Bouton(rectX, rectY, rectWidth, rectHeight, "Jouer");

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
		canvas.setOnMouseMoved(e ->  Racket.setXpos( e.getX() ) );
		canvas.setOnMouseClicked(e -> {
            double clickX = e.getX();
            double clickY = e.getY();
            
            if(gameOver){
                if(boutonRetour.estClique(clickX, clickY)){
                    resetGame(); //Réinitialise le jeu -> retour au menu
                    Son.JouerMusique(); // Rejour de la music
                }
            }else if (boutonJouer.estClique(clickX, clickY)) {
                gameStarted = true;
                Affichage.enregistrerPseudo();  // Stocker le pseudo avant de commencer
                Son.JouerMusique(); // Rejouer la musique
            }
        });

        Son.initBackgroundMusic();//Initialiser la music 
        Son.initPaddleBoundSound(); //Initialiser le son de rebond de balle
        Son.initBrickBreakSound(); // Initialiser le son des briques cassées

        // Gestion des touches clavier pour entrer le pseudo
        canvas.setOnKeyPressed(Affichage::handleKeyPress);


        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Breakout");
        stage.setScene(new Scene(new StackPane(canvas)));
        canvas.setFocusTraversable(true);  // Nécessaire pour capter les événements de clavier

        // Initialise les briques
        bricks = niv.makeNiveau();
    
            

        // Charger le meilleur score au début
        bestScore = Score.getBestScore();

       

        stage.show();
        tl.play();
    }


    
    private void run(GraphicsContext gc) {

        
        Affichage.setGC(gc);


        if(gameOver){
            Son.PauseMusique(); // stop music
            Affichage.afficherFinDeJeu(gc, score, boutonRetour);
            boutonRetour.afficher(gc); // Afficher le bouton "Retour"
        }else 
        if (gameStarted) {
            if (!Son.MusiqueEnCours()) { // Rejouer la music pendant que le jeu en cours
                Son.JouerMusique();
            }
            
            gc.fillText("Score: " + score, width / 2, height / 2);

            Balle.MAJPosHorizontal();
            Balle.MAJPosVertical();
            
            gc.fillOval(Balle.getXPos(), Balle.getYPos(), Balle.getSize(), Balle.getSize());

            // Dessiner les briques
            for (int i = 0; i < brickRows; i++) {
                for (int j = 0; j < brickCols; j++) {
                    if (bricks[i][j].isNotBreak) {
                        double brickX = j * (bricks[i][j].getWidth() + 5);
                        double brickY = i * (bricks[i][j].getHeight() + 5);
                        gc.setFill(bricks[i][j].couleur());
                        gc.fillRect(brickX, brickY, bricks[i][j].getWidth(), bricks[i][j].getHeight());
                    }
                }
            }
            
            // Détection de collision avec les briques
            for (int i = 0; i < brickRows; i++) {
                for (int j = 0; j < brickCols; j++) {

                    if (bricks[i][j].isNotBreak) {
                        double brickX = j * (bricks[i][j].getWidth() + 5);
                        double brickY = i * (bricks[i][j].getHeight() + 5);
            
                        if (Balle.getXPos() + Balle.getSize() >= brickX &&
                            Balle.getXPos() <= brickX + bricks[i][j].getWidth() &&
                            Balle.getYPos() + Balle.getSize() >= brickY &&
                            Balle.getYPos() <= brickY + bricks[i][j].getHeight()) {
            
                            Balle.Rebond();

                            if(bricks[i][j].couleur() != Color.GRAY && bricks[i][j].getNbrVie() == 0){
                                bricks[i][j].isNotBreak = false;
                                score += bricks[i][j].getScore(); 

                                Son.StopSonBrique();; // le son des briques cassées
                                Son.JouerSonBrique();; // 

                            }else{
                                bricks[i][j].setNbrVie(bricks[i][j].getNbrVie() - 1);
                            }
                        }
                    }
                }
            }

            // Collision avec les murs
            if (Balle.getXPos() <= 0 || Balle.getXPos() >= width - Balle.getSize()) {
                Balle.rebondHorizontal();
            }

            if (Balle.getYPos() <= 0) {
                Balle.rebondVertical();
            }


            // Collision avec la raquette
            if (Balle.getYPos() + Balle.getSize() >= Racket.getYPos() - Racket.getHeight() / 2 &&
                Balle.getXPos() + Balle.getSize() >= Racket.getXPos() - Racket.getWidth() / 2 &&
                Balle.getXPos() <= Racket.getXPos() + Racket.getWidth() / 2) {

                Balle.Rebond();
                 
                // Jouer le son de rebond de balle
                Son.StopSonRebond();
                Son.JouerSonRebond();
            }
            if (Balle.getYPos() > Racket.getYPos()){
                Score.saveScoreToFile(score);
                bestScore = Score.getBestScore(); // Mettre à jour le meilleur score
                gameStarted = false; // Fin de la partie
                gameOver = true; 
                for (int i = 0; i < brickRows; i++) {
                    for (int j = 0; j < brickCols; j++) {
                        bricks[i][j].isNotBreak = true; // Toutes les briques sont présentes au début
                    }
                }
      
            }
            gc.setFill(Color.YELLOW);
            gc.fillRect(Racket.getXPos() - Racket.getWidth() / 2,
                         Racket.getYPos() - Racket.getHeight() / 2,
                          Racket.getWidth(), Racket.getHeight());

        } else {

            Affichage.afficherMenuPrincipal(gc, boutonJouer);
            Balle.reset();
        }
        
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
