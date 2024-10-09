package org.openjfx;



import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


public class BreakOut extends Application {

    private static final String GAME_TITLE = "BreakOut";
    private static final int width = 1024;
    private static final int height = 768;
    private int racketWidth = 50;
    private int racketHeight = 10;
    private double racketXPos = width / 2.;
    private double racketYPos = height - 40;
    private double ballXPos = width / 2; // ball at the center at game start
    private double ballYPos = height / 2;

    private int ballYSpeed = 3;
	private int ballXSpeed = 3;
    private int ballSize = 10;    

    private boolean gameStarted = false;
    private int diff = 1;
    private Niveau niv = new Niveau(5, 12, diff);
    private final int brickRows = niv.getBrickRows();
    private final int brickCols = 12;
    private Brick[][] bricks;
    private int score = 0;
    private double rectWidth = 100;
    private double rectHeight = 50;
    private double rectX = (width - rectWidth) / 2;
    private double rectY = (height - rectHeight) / 2;

 

    private int bestScore = 0; // Meilleur score

    private FinDeJeu finDeJeu; //pour gérer la fin de jeu
    private boolean gameOver = false;

    //coordonnées d'affichage du bouton "Retour"
    double replayButtonX = width/2-50;
    double replayButtonY = height/2+60; //position du bouton "Retour" sous le meilleur score
    double replayButtonWidth = 100;
    double replayButtonHeight = 40;

    //Réinitialise l'état du jeu pour recommencer une nouvelle partie
    private void resetGame(){
        this.ballXPos = width/2;
        this.ballXPos = width/2;
        this.score = 0;
        this.ballXSpeed = 3;
        this.ballYSpeed = 3;
        this.gameOver = false;
        this.gameStarted = false;
        Son.StopMusique();  // La musique s'arrête
        
        for(int i=0; i<brickRows; i++){
            for(int j=0; j<brickCols; j++){
                bricks[i][j].isNotBreak = true;
            }
        }
        //finDeJeu = new FinDeJeu(width, height, this.bestScore);
        Son.JouerMusique(); // Rejouer la musique
    }
        

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
		canvas.setOnMouseMoved(e ->  racketXPos  = e.getX());
		canvas.setOnMouseClicked(e -> {
            double clickX = e.getX();
            double clickY = e.getY();
            
            if(gameOver){
                if(finDeJeu.verifierCliqueRetour(clickX, clickY, replayButtonX, replayButtonY, replayButtonWidth, replayButtonHeight)){
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

        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Breakout");
        stage.setScene(new Scene(new StackPane(canvas)));

        // Initialise les briques
        bricks = niv.makeNiveau();
    
            

        // Charger le meilleur score au début
        bestScore = Score.getBestScore();

        finDeJeu = new FinDeJeu(width, height); //Initialise finDeJeu après avoir récupéré le meilleur score

        stage.show();
        tl.play();
    }


    
    private void run(GraphicsContext gc) {
        // Fond noir
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));
        gc.setFill(Color.WHITE);

        if(gameOver){
            Son.PauseMusique(); // stop music
            finDeJeu.afficherFinDeJeu(gc, score); //Affiche la fin du jeu
            finDeJeu.afficherBoutonRetour(gc, replayButtonX, replayButtonY, replayButtonWidth, replayButtonHeight); //Affiche le bouton "Retour"
        }else 
        if (gameStarted) {
            if (!Son.MusiqueEnCours()) { // Rejouer la music pendant que le jeu en cours
                Son.JouerMusique();
            }
            gc.fillText("Score: " + score, width / 2, height / 2);
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;
            gc.fillOval(ballXPos, ballYPos, ballSize, ballSize);

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
            
                        if (ballXPos + ballSize >= brickX &&
                            ballXPos <= brickX + bricks[i][j].getWidth() &&
                            ballYPos + ballSize >= brickY &&
                            ballYPos <= brickY + bricks[i][j].getHeight()) {
            
                            ballYSpeed = -ballYSpeed; 
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
            if (ballXPos <= 0 || ballXPos >= width - ballSize) {
                ballXSpeed = -ballXSpeed; // Rebond horizontal
            }

            if (ballYPos <= 0) {
                ballYSpeed = -ballYSpeed; // Rebond vertical
            }


            // Collision avec la raquette
            if (ballYPos + ballSize >= racketYPos - racketHeight / 2 &&
                    ballXPos + ballSize >= racketXPos - racketWidth / 2 &&
                    ballXPos <= racketXPos + racketWidth / 2) {

                ballYSpeed = -ballYSpeed; // Rebond de la balle
                 
                // Jouer le son de rebond de balle
                Son.StopSonRebond();
                Son.JouerSonRebond();
            }
            if (ballYPos > racketYPos){
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
            gc.fillRect(racketXPos - racketWidth / 2, racketYPos - racketHeight / 2, racketWidth, racketHeight);

        } else {
            gc.setStroke(Color.YELLOW);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font("Courier New",  FontWeight.LIGHT, 25));
			
			
			gc.strokeText("Jouer", width / 2, height / 2 + 6);
            gc.strokeRect(rectX, rectY, rectWidth, rectHeight);

            gc.setFill(Color.YELLOW);
            gc.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 105));
            gc.fillText(GAME_TITLE, width / 2,110);

            // Afficher le meilleur score
            gc.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
            gc.strokeText("Best Score: " + bestScore, width / 2, height / 2 + 60);

            // Afficher les 5 derniers scores à droite
            int[] lastFiveScores = Score.getLastFiveScores();

            // Déterminer la largeur et la hauteur du rectangle
            double scoresWidth = 200; // Largeur du rectangle
            double scoresHeight = 180; // Hauteur du rectangle
            double scoresX = width - scoresWidth - 20; // Position X (20 pixels à partir du bord droit)
            double scoresY = (height - scoresHeight) / 2; // Centré verticalement

            // Dessiner le contour jaune du rectangle
            gc.setStroke(Color.YELLOW); // Couleur des bords
            gc.strokeRect(scoresX, scoresY, scoresWidth, scoresHeight); // Dessiner le contour

            // Afficher les scores à l'intérieur du rectangle
            gc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
            gc.setFill(Color.YELLOW); // Couleur du texte
            gc.setTextAlign(TextAlignment.LEFT); // Alignement à gauche

            
            
            // affiche les 5 dernier scores
            gc.fillText("Last scores :", scoresX + 10, scoresY + 20);
            for (int i = 0; i < lastFiveScores.length; i++) {
                gc.fillText("" + lastFiveScores[i], scoresX + 10, scoresY + 50 + (i * 30));
            }

            ballXPos = width / 2;
            ballYPos = height / 2;
        }
        
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
