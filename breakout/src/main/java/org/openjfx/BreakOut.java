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



    private boolean modeSelection = false;
    private boolean gameStarted = false;
    private int diff = 1;
    private Niveau niv; //niveau classique
    private NiveauInput n = new NiveauInput(); //niveau perso

    private int brickRows;
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
    private Bouton boutonClassique;
    private Bouton boutonPerso;
    private Bouton boutonRetourSelec;
    private Bouton invalide;

    private Fenetre f;

    private Bouton boutonSettings;
    private boolean drawSettingsRectangle = false; // Ajoute cet attribut



    


    private boolean gameOver = false;
    private boolean ouvert = false;




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
        
        Son.JouerMusique(); // Rejouer la musique
    }
    


    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        

        // Initialiser les boutons
        boutonRetour = new Bouton(width / 2 - 50, height / 2 + 60, 100, 40, "Retour");
        boutonJouer = new Bouton(rectX, rectY, rectWidth, rectHeight, "Jouer");
        boutonClassique = new Bouton(rectX - 100, rectY, rectWidth + 200, rectHeight, "Mode classique");
        boutonPerso = new Bouton(rectX - 100, rectY + 80, rectWidth + 200, rectHeight, "Mode personnalisé");
        boutonRetourSelec = new Bouton(rectX, rectY + 250, 100, 40, "Retour");

        boutonSettings = new Bouton(20, height - 80, 100, 40, "Settings");




        // Initialiser la fenetre personnalisé
        
        f = new Fenetre(n);


        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
		canvas.setOnMouseMoved(e ->  Racket.setXpos( e.getX() ) );
		canvas.setOnMouseClicked(e -> {
            double clickX = e.getX();
            double clickY = e.getY();
            
            if(gameOver){
                if(boutonRetour.estClique(clickX, clickY)){
                    resetGame(); //Réinitialise le jeu -> retour au menu
                    Son.JouerMusique(); // Rejoue de la music
                }
            }else if (!gameStarted && !modeSelection && boutonJouer.estClique(clickX, clickY)) {
                //bascule sur la selection de mode
                modeSelection = true;
                Affichage.enregistrerPseudo();  // Stocker le pseudo avant de commencer
                drawSettingsRectangle = false; 

            //confirme un input sur le bouton "Mode classique"
            }else if(!gameStarted && modeSelection && boutonClassique.estClique(clickX, clickY)){
                niv = new Niveau(diff);
                bricks = niv.makeNiveau();
                brickRows = niv.getBrickRows();
                modeSelection = false;
                gameStarted = true;
            }else if(!gameStarted && modeSelection && boutonPerso.estClique(clickX, clickY)){
                niv = n;
                n.setOuvert(true);
                f.setVisible(true);


            }else if(modeSelection && boutonRetourSelec.estClique(clickX, clickY)){
                modeSelection = false;
            }
            // Vérifier si le bouton Settings a été cliqué
            if (!gameStarted && !gameOver && boutonSettings.estClique(clickX, clickY)) {
                drawSettingsRectangle = !drawSettingsRectangle; // Pour ouvrir et fermer les parametres
            }
        });

        Son.initBackgroundMusic();//Initialiser la music 
        Son.initPaddleBoundSound(); //Initialiser le son de rebond de balle
        Son.initBrickBreakSound(); // Initialiser le son des briques cassées

        // Gestion des touches clavier pour entrer le pseudo
        if (!Pseudo.estDef()){
            canvas.setOnKeyPressed(Affichage::handleKeyPress);
            canvas.setFocusTraversable(true);  // Nécessaire pour capter les événements de clavier
        }else{
            canvas.setOnKeyPressed(null);
            canvas.setFocusTraversable(false);
        }
        


        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Breakout");
        stage.setScene(new Scene(new StackPane(canvas)));

    
            

        // Charger le meilleur score au début
        bestScore = Score.getBestScore(Pseudo.getPseudo());

       

        stage.show();
        tl.play();
    }
    

    
    private void run(GraphicsContext gc) {

        
        Affichage.setGC(gc);

        if (f.isActive()){

        }
        else{
            if(gameOver){
                Son.PauseMusique(); // stop music
                Affichage.afficherFinDeJeu(gc, score, boutonRetour);
                boutonRetour.afficher(gc); // Afficher le bouton "Retour"
            }else 
            if (modeSelection){
                Affichage.affichageMenuSelection(gc, boutonClassique, boutonPerso, boutonRetourSelec);
                if (n.getPath() != null && n.getOuvert()){
                    bricks = niv.makeNiveau();
                    if (bricks == null){
                        Affichage.afficheTexte(gc, "format du ficher personnalisé invalide");
                    }
                    else{
                        brickRows = niv.getBrickRows();
                        modeSelection = false;
                        gameStarted = true;
                        n.setOuvert(false);
                    }

                }
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
                    Score.saveScoreToFile(Pseudo.getPseudo(), score);
                    bestScore = Score.getBestScore(Pseudo.getPseudo()); // Mettre à jour le meilleur score
                    gameStarted = false; // Fin de la partie
                    gameOver = true; 

                }
                gc.setFill(Color.YELLOW);
                gc.fillRect(Racket.getXPos() - Racket.getWidth() / 2,
                Racket.getYPos() - Racket.getHeight() / 2,
                Racket.getWidth(), Racket.getHeight());

            } else {

                Affichage.afficherMenuPrincipal(gc, boutonJouer, boutonSettings, drawSettingsRectangle);
                Balle.reset();
            }

            if (drawSettingsRectangle) {
                Affichage.dessinerRectangle(gc, boutonSettings);
            }
        }        
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
