package org.openjfx;



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
    private BoutonMusique boutonMusique;//bouton pour le mode muet et active
    private BoutonPause boutonPause; 
 

    

    private boolean gameStarted = false;
    private boolean isGamePaused = false;
    private boolean gameOver = false;


    public static int getWidth(){ return width; }
    public static int getHeight(){ return height; }
    public static String getTitre(){ return GAME_TITLE; }

    //Réinitialise l'état du jeu pour recommencer une nouvelle partie
    private void resetGame(){

        this.score = 0;

        Balle.reset();
  
        this.gameOver = false;
        this.gameStarted = true;
        this.isGamePaused = false; 
        boutonJouer.setText("Jouer"); // bouton de réinitialisation
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
        

        // Initialiser les boutons
        boutonRetour = new Bouton(width / 2 - 50, height / 2 + 60, 100, 40, "Retour");
        boutonJouer = new Bouton(rectX, rectY, rectWidth, rectHeight, "Jouer");
        boutonMusique = new BoutonMusique(10, height - 50, 100, 40, "Son activé");
        boutonPause = new BoutonPause(120, height - 50, 100, 40,"Pause"); 
       
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
		canvas.setOnMouseMoved(e ->  Racket.setXpos( e.getX() ) );
		canvas.setOnMouseClicked(e -> {
            double clickX = e.getX();
            double clickY = e.getY();
            if (boutonMusique.estClique(clickX, clickY)) {
                boutonMusique.toggleMuted(); // change le mode muet
        
                if (boutonMusique.isMuted()) {
                    Son.StopMusique();  
                    Son.muteAllSounds(); // arrete les sons
                } else {
                    Son.JouerMusique(); // rejouer
                    Son.unmuteAllSounds(); // rejouer les sons
                }
            }

            // parametre du bouton pause
            if (boutonPause.estClique(clickX, clickY)) {
                boutonPause.togglePaused(); // change le mode pause

                if (boutonPause.isPaused()) {
                    isGamePaused = true; // mode pause
                    gameStarted = false; // arret le jeu
                    boutonJouer.setText("ReJouer"); // bouton recommence le jeu
                    Son.PauseMusique(); // stop les sons
                } else {
                    gameStarted = true; // rejouer le jeu
                    isGamePaused = false; // mode en cours
                    Son.JouerMusique(); // rejouer les sons
                }
            }

            // bouton de réinitialisation/rejouer
            if (boutonJouer.estClique(clickX, clickY)) {
                if (isGamePaused) {
                    resetGame(); // recommence le jeu
                    isGamePaused = false; // 恢复未暂停模式
                    gameStarted = true; // 开始新游戏
                    boutonJouer.setText("Jouer");
                    boutonPause.setText("Pause"); // 重置暂停按钮文本为“Pause”
                    boutonPause.setPaused(false); // 重置暂停按钮状态为未暂停
                    Son.JouerMusique(); // 播放音乐
                } else if (!gameStarted || gameOver) {
                    resetGame(); // commence le jeu
                    boutonPause.setText("Pause"); // 重置暂停按钮文本为“Pause”
                    boutonPause.setPaused(false); // 重置暂停按钮状态为未暂停
                }
            }

            
            if(gameOver && boutonRetour.estClique(clickX, clickY)){
                resetGame(); //Réinitialise le jeu -> retour au menu
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

       

        stage.show();
        tl.play();
    }


    
    private void run(GraphicsContext gc) {

        
        Affichage.setGC(gc);
        boutonMusique.afficher(gc);// affichage le bouton
        //boutonPause.afficher(gc); // affichage le bouton

        /*ST */
        if (isGamePaused) { // 检查是否处于暂停状态
            boutonPause.afficher(gc); // 显示暂停按钮
        }    

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
                            Balle.getYPos() + Balle.getSize() >= brickY +5 &&
                            Balle.getYPos() <= brickY + bricks[i][j].getHeight() + 5) {
            
                            Balle.Rebond();

                            if(bricks[i][j].couleur() != Color.GRAY && bricks[i][j].getNbrVie() == 0){
                                bricks[i][j].isNotBreak = false;
                                score += bricks[i][j].getScore(); 

                                Son.StopSonBrique(); // le son des briques cassées
                                Son.JouerSonBrique(); // 

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

            boutonMusique.afficher(gc);// affichage le bouton
            boutonPause.afficher(gc); // affichage le bouton

            // Collision avec la raquette
            if (Balle.getYPos() + Balle.getSize() >= Racket.getYPos() - Racket.getHeight() /2 &&
                Balle.getXPos() + Balle.getSize() >= Racket.getXPos() - Racket.getWidth() / 2 &&
                Balle.getXPos() <= Racket.getXPos() + Racket.getWidth() / 2 && Balle.getYSpeed() > 0){

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
