package org.openjfx;



import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    private static double timePU1 = 0;
    private static double  timePU2 = 0;
    public static int nbBalle = 1;
    public static ArrayList<Balle> balles = new ArrayList<>();
    
    private boolean gameOver = false;
    private final Balle balle1 = new Balle(BreakOut.getWidth() / 2,BreakOut.getHeight() / 2);
    




    public static int getWidth(){ return width; }
    public static int getHeight(){ return height; }
    public static String getTitre(){ return GAME_TITLE; }

    //Réinitialise l'état du jeu pour recommencer une nouvelle partie
    private void resetGame(){

        this.score = 0;
        balle1.reset();
        balles = new ArrayList<>();
        balles.add(balle1);
        Racket.resetSize();
        nbBalle = 1;
  
        this.gameOver = false;
        this.gameStarted = false;
        Son.StopMusique();  // La musique s'arrête
        
        for(int i=0; i<brickRows; i++){
            for(int j=0; j<brickCols; j++){
                bricks[i][j].isNotBreak = true;
            }
        }
        
        Son.JouerMusique(); // Rejouer la musique
    }
        

    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
		canvas.setOnMouseMoved(e ->  Racket.setXpos( e.getX() ) );
		canvas.setOnMouseClicked(e -> {
            double clickX = e.getX();
            double clickY = e.getY();
            
            if(gameOver){
                if(verifierCliqueRetour(clickX, clickY, Affichage.getBtnX(), Affichage.getBtnY(), Affichage.getBtnWidth(), Affichage.getBtnHeight())){
                    resetGame(); //Réinitialise le jeu -> retour au menu
                    Son.JouerMusique(); // Rejour de la music
                }
            }else if (clickX >= rectX && clickX <= rectX + rectWidth && clickY >= rectY && clickY <= rectY + rectHeight) {
                gameStarted = true;
                Son.JouerMusique(); // Rejouer la musique
            }
        });

        Son.initBackgroundMusic();//Initialiser la music 
        Son.initPaddleBoundSound(); //Initialiser le son de rebond de balle
        Son.initBrickBreakSound(); // Initialiser le son des briques cassées
        balles.add(balle1);// Ajout de la balle initiale a la liste


        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Breakout");
        stage.setScene(new Scene(new StackPane(canvas)));

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
            Affichage.afficherFinDeJeu(gc, score); //Affiche la fin du jeu

            Affichage.afficherBoutonRetour(gc); //Affiche le bouton "Retour"
        }else 
        if (gameStarted) {
            if (!Son.MusiqueEnCours()) { // Rejouer la music pendant que le jeu en cours
                Son.JouerMusique();
            }
            gc.fillText("Score: " + score, width / 2, height / 2);



            for(int i = 0; i<balles.size(); i++){
                balles.get(i).MAJPosHorizontal();
                balles.get(i).MAJPosVertical();
                gc.fillOval(balles.get(i).getXPos(), balles.get(i).getYPos(), Balle.getSize(), Balle.getSize());
            }
            

            

            

            // Dessiner les briques
            for (int i = 0; i < brickRows; i++) {
                for (int j = 0; j < brickCols; j++) {
                    if (bricks[i][j].isNotBreak) {
                        double brickX = j * (bricks[i][j].getWidth() + 5);
                        double brickY = i * (bricks[i][j].getHeight() + 5);
                        //application des powerUp aux briques rouges
                        if (bricks[i][j].couleur() == Color.RED && !bricks[i][j].getHavePower()){
                            bricks[i][j].setPowerUp(new TreeBall(brickX,brickY));
                            bricks[i][j].setHavePower(true);

                        }
                        
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

                        
                        for(int k = 0; k<balles.size(); k++){
                        if (balles.get(k).getXPos() + Balle.getSize() >= brickX &&
                            balles.get(k).getXPos() <= brickX + bricks[i][j].getWidth() &&
                            balles.get(k).getYPos() + Balle.getSize() >= brickY +5 &&
                            balles.get(k).getYPos() <= brickY + bricks[i][j].getHeight() + 5) {
            
                            balles.get(k).Rebond();
                        
                        
                        

                            if(bricks[i][j].couleur() == Color.RED){
                                bricks[i][j].isNotBreak = false;
                                score += bricks[i][j].getScore(); 
                                bricks[i][j].getPowerUp().setTombe(true);

                                Son.StopSonBrique(); // le son des briques cassées
                                Son.JouerSonBrique(); // 
                            }

                            else if(bricks[i][j].couleur() != Color.GRAY && bricks[i][j].getNbrVie() == 0){
                                bricks[i][j].isNotBreak = false;
                                score += bricks[i][j].getScore(); 

                                Son.StopSonBrique(); // le son des briques cassées
                                Son.JouerSonBrique(); // 

                            }
                            else{
                                bricks[i][j].setNbrVie(bricks[i][j].getNbrVie() - 1);
                            }
                            }
                        }

                        
                    }
                }
            }

            //actualisation powerUp
            for (int i = 0; i < brickRows; i++) {
                for (int j = 0; j < brickCols; j++) {
                    if (bricks[i][j].couleur() == Color.RED){
                        if (bricks[i][j].getPowerUp().getTombe()){
                            gc.setFill(Color.BLUE);
                            gc.fillOval(bricks[i][j].getPowerUp().getPux() + bricks[i][j].getWidth()/2, bricks[i][j].getPowerUp().getPuy(), PowerUp.getSize(), PowerUp.getSize());
                            bricks[i][j].getPowerUp().actuPUy();
                            if (bricks[i][j].getPowerUp().getPuy() + PowerUp.getSize() >= Racket.getYPos() - Racket.getHeight() /2 &&
                            bricks[i][j].getPowerUp().getPux() + PowerUp.getSize() >= Racket.getXPos() - Racket.getWidth() / 2 &&
                            bricks[i][j].getPowerUp().getPux() <= Racket.getXPos() + Racket.getWidth() / 2){
                                bricks[i][j].getPowerUp().applyEffect();
                                bricks[i][j].getPowerUp().setTombe(false);
                            }
                            if(bricks[i][j].getPowerUp().getPuy() > Racket.getYPos() +10){
                                bricks[i][j].getPowerUp().setTombe(false);
                            }
                        
    
                        }
                    }
                    
                }
            }
            //verif powerUp BALLSIZE actif si oui augmente le temps passé
            if (Balle.getBallSize() == 50){
                timePU1++;
                if (timePU1 >= 700){
                    Balle.resetSize();
                    timePU1 = 0;
                }

            }

            //Verif powerUp RacketSize actif si oui augmente le temps passé
            if (Racket.getWidth() > 50){
                timePU2++;
                if (timePU2 >= 700){
                    Racket.resetSize();
                    timePU2 = 0;
                }

            }
            
            


            for (int i = 0; i < balles.size(); i++) {
                
            
            // Collision avec les murs
            if (balles.get(i).getXPos() <= 0 || balles.get(i).getXPos() >= width - Balle.getSize()) {
                balles.get(i).rebondHorizontal();
            }

            if (balles.get(i).getYPos() <= 0) {
                balles.get(i).rebondVertical();
            }


            // Collision avec la raquette
            if (balles.get(i).getYPos() + Balle.getSize() >= Racket.getYPos() - Racket.getHeight() /2 &&
                balles.get(i).getXPos() + Balle.getSize() >= Racket.getXPos() - Racket.getWidth() / 2 &&
                balles.get(i).getXPos() <= Racket.getXPos() + Racket.getWidth() / 2 && balles.get(i).getYSpeed() > 0){

                balles.get(i).Rebond();
                 
                // Jouer le son de rebond de balle
                Son.StopSonRebond();
                Son.JouerSonRebond();
            }

            }


            for (int i = 0; i < balles.size(); i++) {
                if (balles.get(i).getYPos() > Racket.getYPos()){
                    nbBalle = nbBalle -1;
                    balles.remove(i);
                
            }
            
                if (nbBalle == 0){
                Score.saveScoreToFile(score);
                bestScore = Score.getBestScore(); // Mettre à jour le meilleur score
                gameStarted = false; // Fin de la partie
                gameOver = true; 
                for (int k = 0; k < brickRows; k++) {
                    for (int j = 0; j < brickCols; j++) {
                        bricks[k][j].isNotBreak = true; 
                        bricks[k][j].setHavePower(false);// Toutes les briques sont présentes au début
                    }
                }
                }
            
      
            }
            gc.setFill(Color.YELLOW);
            gc.fillRect(Racket.getXPos() - Racket.getWidth() / 2,
                         Racket.getYPos() - Racket.getHeight() / 2,
                          Racket.getWidth(), Racket.getHeight());

        } else {

           Affichage.MenuPrincipal(gc, rectX, rectY, rectWidth, rectHeight);

            
        }
        
    }

    public static boolean verifierCliqueRetour(double mouseX, double mouseY, double replayButtonX, double replayButtonY, double replayButtonWidth, double replayButtonHeight){
        boolean clicked = (mouseX >= replayButtonX && mouseX <= replayButtonX + replayButtonWidth && mouseY >= replayButtonY && mouseY <= replayButtonY + replayButtonHeight);
        System.out.println("Clicked " + clicked); //juste pour vérifier si le joueur a bien cliqué sur le bouton
        return clicked;
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
