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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;


public class BreakOut extends Application {

    private static final int width = 1024;
    private static final int height = 768;
    private int racketWidth = 50;
    private int racketHeight = 10;
    private double racketXPos = width / 2.;
    private double racketYPos = height - 40;
    private double ballXPos = width / 2; // ball at the center at game start
    private double ballYPos = height / 2;

    private int ballYSpeed = 4;
	private int ballXSpeed = 4;
    private int ballSize = 10;    

    private boolean gameStarted = false;
    private int diff = 1;
    private Niveau niv = new Niveau(5, 12, 1);
    private final int brickRows = niv.getBrickRows();
    private final int brickCols = 12;
    private Brick[][] bricks;
    private int score = 0;
    private double rectWidth = 100;
    private double rectHeight = 50;
    private double rectX = (width - rectWidth) / 2;
    private double rectY = (height - rectHeight) / 2;

    private MediaPlayer mediaPlayer; // La music du jeu
    private MediaPlayer paddleBoundPlayer; // Le son de rebond de balle
    private MediaPlayer brickBreakPlayer; // Le son des brick cassées

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
        this.ballXSpeed = 4;
        this.ballYSpeed = 4;
        this.gameOver = false;
        this.gameStarted = false;
        mediaPlayer.stop(); // La musique s'arrête
        
        for(int i=0; i<brickRows; i++){
            for(int j=0; j<brickCols; j++){
                bricks[i][j].isNotBreak = true;
            }
        }
        finDeJeu = new FinDeJeu(width, height, this.bestScore);
        mediaPlayer.play(); // Rejouer la musique
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
                    mediaPlayer.play(); // Rejour de la music
                }
            }else if (clickX >= rectX && clickX <= rectX + rectWidth && clickY >= rectY && clickY <= rectY + rectHeight) {
                gameStarted = true;
                mediaPlayer.play(); // Rejouer la musique
            }
        });

        initBackgroundMusic();//Initialiser la music 
        initPaddleBoundSound(); //Initialiser le son de rebond de balle
        initBrickBreakSound(); // Initialiser le son des briques cassées

        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Breakout");
        stage.setScene(new Scene(new StackPane(canvas)));

        // Initialise les briques
        bricks = niv.makeNiveau();
        /* 
        for (int i = 0; i < brickRows; i++) {
            for (int j = 0; j < brickCols; j++) {
                if(i%2 != 0){
                    bricks[i][j] = new BrickBleue(true, 80, 20);
                }
                else{
                    bricks[i][j] = new BrickRouge(true, 80, 20);
                }

        
            }
        }
        */
    
            

        // Charger le meilleur score au début
        bestScore = Score.getBestScore();

        finDeJeu = new FinDeJeu(width, height, this.bestScore); //Initialise finDeJeu après avoir récupéré le meilleur score

        stage.show();
        tl.play();
    }

    //Methode pour initialiser la music de fond
    private void initBackgroundMusic() {
        String musicFile = "src/main/resources/sounds/music.mp3"; // L'endroit du fichier audio
        Media sound = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // lecture en boucle
        mediaPlayer.setVolume(0.5); // Le volume（entre 0.0-1.0）
        //mediaPlayer.play();  
    }

   //Methode pour initialiser le son de la balle qui rebondit
    private void initPaddleBoundSound() {
        String paddleBoundFile = "src/main/resources/sounds/bound.wav"; // 
        Media paddleBoundSound = new Media(Paths.get(paddleBoundFile).toUri().toString());
        paddleBoundPlayer = new MediaPlayer(paddleBoundSound);
    }
    
    //Methode pour initialiser le son des briques cassées
    private void initBrickBreakSound() {
        String brickBreakFile = "src/main/resources/sounds/hit.wav"; 
        Media brickBreakSound = new Media(Paths.get(brickBreakFile).toUri().toString());
        brickBreakPlayer = new MediaPlayer(brickBreakSound);
    }
    
    private void run(GraphicsContext gc) {
        // Fond noir
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));
        gc.setFill(Color.WHITE);

        if(gameOver){
            mediaPlayer.pause(); // stop music
            finDeJeu.afficherFinDeJeu(gc, score); //Affiche la fin du jeu
            finDeJeu.afficherBoutonRetour(gc, replayButtonX, replayButtonY, replayButtonWidth, replayButtonHeight); //Affiche le bouton "Retour"
        }else 
        if (gameStarted) {
            if (!mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) { // Rejouer la music pendant que le jeu en cours
                mediaPlayer.play();
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
                            if(bricks[i][j].couleur() != Color.GRAY){
                                bricks[i][j].isNotBreak = false;
                                score += bricks[i][j].getScore(); 
                                brickBreakPlayer.stop(); // le son des briques cassées
                                brickBreakPlayer.play(); // 
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
                paddleBoundPlayer.stop(); 
                paddleBoundPlayer.play(); 
            }
            if (ballYPos > racketYPos){
                Score.saveScoreToFile(score); // Enregistrer le score dans un fichier
                bestScore = Score.getBestScore(); // Mettre à jour le meilleur score
                gameStarted = false; // Fin de la partie
                gameOver = true; 
                for (int i = 0; i < brickRows; i++) {
                    for (int j = 0; j < brickCols; j++) {
                        bricks[i][j].isNotBreak = true; // Toutes les briques sont présentes au début
                    }
                }
      
            }

            gc.fillRect(racketXPos - racketWidth / 2, racketYPos - racketHeight / 2, racketWidth, racketHeight);

        } else {
            gc.setStroke(Color.YELLOW);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font("Courier New",  FontWeight.LIGHT, 25));
			
			
			gc.strokeText("Jouer", width / 2, height / 2 + 6);
            gc.strokeRect(rectX, rectY, rectWidth, rectHeight);

            // Afficher le meilleur score
            gc.strokeText("Best Score: " + bestScore, width / 2, height / 2 + 60);

            ballXPos = width / 2;
            ballYPos = height / 2;
        }
        
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
