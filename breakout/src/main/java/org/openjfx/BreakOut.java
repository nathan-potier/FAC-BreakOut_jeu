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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


public class BreakOut extends Application {

    private static final String GAME_TITLE = "BreakOut";
    private static final int width = 1016;
    private static final int height = 700;


    private boolean isPaused = false;
    private boolean modeSelection = false;
    private boolean modeCréation = false;
    private boolean gameStarted = false;
    private boolean finDeJeu = false;
    private boolean type = true;
    private int diff = 1;
    private Niveau niv; //niveau classique
    private NiveauInput n = new NiveauInput(); //niveau perso
    private NiveauEditeur gen = new NiveauEditeur();

    private int brickRows;
    private final int brickCols = 12;
    private Brick[][] bricks;

    private int score = 0;
    private int bestScore = 0; // Meilleur score
    
    private double rectWidth = 100;
    private double rectHeight = 50;
    private double rectX = (width - rectWidth) / 2;
    private double rectY = (height - rectHeight) / 2;

    //Espace horizontal entre les boutons "Rejouer" et "Retour"
    private double espaceEntreBoutons = 40;

    private double rectRejouerX = (width - (2 * rectWidth + espaceEntreBoutons)) / 2;
    private double rectRetourX = rectRejouerX + rectWidth + espaceEntreBoutons;
    private double rectChangerModeJeuX = (width - rectWidth) / 2;
    private double rectChangerModeJeuY = rectY + 50 + rectHeight + 15;

    private Bouton boutonRejouer;
    private Bouton boutonRetour;
    private Bouton boutonChangerModeJeu;
    private Bouton boutonJouer;
    private Bouton boutonClassique;
    private Bouton boutonPerso;
    private Bouton boutonRetourSelec;
    private Bouton boutonProchainNiveau;
    private Bouton boutonRecommencer;
    private Bouton boutonCréation;
    private Bouton boutonEnregistrer;
    private Bouton boutonMute;
    private Bouton boutonPause;
    private Bouton boutonToggleBackgroundMusic; 
    private Bouton boutonToggleCollisionSound;
    private Bouton boutonToggleBounceSound;
    
    
    private FenetrePerso f1;
    private FenetreEditeur f2;

    private Bouton boutonSettings;
    private boolean drawSettingsRectangle = false; // Ajoute cet attribut



    


    private boolean gameOver = false;
    private boolean ouvert = false;




    public static int getWidth(){ return width; }
    public static int getHeight(){ return height; }
    public static String getTitre(){ return GAME_TITLE; }

    //Réinitialise l'état du jeu pour recommencer une nouvelle partie
    private void resetGame(GraphicsContext gc){

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

        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
        gc.setFill(Color.YELLOW);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Score: " + score, width / 2, height / 2); //score remise à 0
        
        Son.JouerMusique(); // Rejouer la musique
    }
    


    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        

        // Initialiser les boutons
        boutonRejouer = new Bouton(rectRejouerX, rectY + 50, rectWidth + 20, rectHeight, "Rejouer");
        boutonChangerModeJeu = new Bouton(rectChangerModeJeuX - 70, rectChangerModeJeuY, rectWidth + 160, rectHeight, "Changer de mode"); 
        boutonRetour = new Bouton(rectRetourX, rectY + 50, rectWidth + 20, rectHeight, "retour");
        boutonJouer = new Bouton(rectX, rectY, rectWidth, rectHeight, "Jouer");
        boutonClassique = new Bouton(rectX - 100, rectY, rectWidth + 200, rectHeight, "Mode classique");
        boutonPerso = new Bouton(rectX - 100, rectY + 80, rectWidth + 200, rectHeight, "Mode personnalisé");
        boutonRetourSelec = new Bouton(rectX, rectY + 250, 100, 40, "Retour");
        boutonProchainNiveau = new Bouton(rectX - 100, rectY, rectWidth + 200, rectHeight, "Prochain niveau");
        boutonRecommencer = new Bouton(rectX - 100, rectY, rectWidth + 200, rectHeight, "Recommencer");
        boutonCréation = new Bouton(rectX - 100, rectY + 160, rectWidth + 200, rectHeight, "Mode création");
        boutonEnregistrer = new Bouton(rectX - 100, rectY + 80, rectWidth + 200, rectHeight, "Enregistrer");

        boutonSettings = new Bouton(20, height - 80, 100, 40, "Settings");
        boutonMute = new Bouton(425, height - 80, 100, 40, "Muet");
        boutonPause = new Bouton(525, height - 80, 100, 40,"Pause");
        boutonToggleBackgroundMusic = new Bouton(100, height - 150, 130, 20, "BackgroundMusic: on");
        boutonToggleCollisionSound = new Bouton(100, height - 130, 130, 20, "BrickSound: on");
        boutonToggleBounceSound = new Bouton(100, height - 110, 130, 20, "BounceSound: on");


 

        // Initialiser la fenetre personnalisé
        
        f1 = new FenetrePerso(n);
        f2 = new FenetreEditeur();


        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
        
		canvas.setOnMouseMoved(e ->{
            double mouseX = e.getX();
            double mouseY = e.getY();

            //États visuels des boutons au survol
            boutonJouer.setHover(boutonJouer.isHover(mouseX, mouseY));
            boutonRejouer.setHover(boutonRejouer.isHover(mouseX, mouseY));
            boutonChangerModeJeu.setHover(boutonChangerModeJeu.isHover(mouseX, mouseY));
            boutonRetour.setHover(boutonRetour.isHover(mouseX, mouseY));
            boutonClassique.setHover(boutonClassique.isHover(mouseX, mouseY));
            boutonPerso.setHover(boutonPerso.isHover(mouseX, mouseY));
            boutonRetourSelec.setHover(boutonRetourSelec.isHover(mouseX, mouseY));
            boutonSettings.setHover(boutonSettings.isHover(mouseX,mouseY));

            Racket.setXpos(mouseX);
        });

		canvas.setOnMouseClicked(e -> {
            double clickX = e.getX();
            double clickY = e.getY();
            if (drawSettingsRectangle) {  // Traiter les clics sur les boutons uniquement lorsque le menu des paramètres est ouvert
                if (boutonToggleBackgroundMusic.estClique(clickX, clickY)) {
                    // Basculer la musique de fond
                    if (Son.isBackgroundMusicOn()) {
                        Son.setBackgroundMusicOn(false);  // Arrêter la musique de fond
                        boutonToggleBackgroundMusic.setText("BackgroundMusic: off");
                    } else {
                        Son.setBackgroundMusicOn(true); // Démarrer la musique de fond
                        boutonToggleBackgroundMusic.setText("BackgroundMusic: on");
                    }
                }
                
                if (boutonToggleCollisionSound.estClique(clickX, clickY)) {
                    // Basculer le son de collision
                    if (Son.isCollisionSoundOn()) {
                        Son.setCollisionSoundOn(false);  // Désactiver le son de collision
                        boutonToggleCollisionSound.setText("BrickSound: off");
                    } else {
                        Son.setCollisionSoundOn(true);   // Activer le son de collision
                        boutonToggleCollisionSound.setText("BrickSound: on");
                    }
                }
    
                if (boutonToggleBounceSound.estClique(clickX, clickY)) {
                    // Basculer le son de rebond
                    if (Son.isBounceSoundOn()) {
                        Son.setBounceSoundOn(false); // Désactiver le son de rebond
                        boutonToggleBounceSound.setText("BounceSound: off");
                    } else {
                        Son.setBounceSoundOn(true); // Activer le son de rebond
                        boutonToggleBounceSound.setText("BounceSound: on");
                    }
                }
            }
        
             //Paramètres de clic du bouton muet
             if (boutonMute.estClique(clickX, clickY)) {
                if(!Son.isMuted()){
                    Son.toggleMute(); // Changer l'état muet
                    boutonMute.setText("Son activé");
                } else {
                    Son.toggleMute(); // Changer l'état avec son
                    boutonMute.setText("Muet");
                }
            }

            //Paramètres de clic du bouton Pause
            if (boutonPause.estClique(clickX, clickY)) {
                if (isPaused) {
                    isPaused = false;
                } else {
                    isPaused = true; 
                    boutonPause.setText("Pause");
                }
            }
            

            if(gameOver){
                if(boutonRejouer.estClique(clickX, clickY)){
                    resetGame(gc);
                    gameStarted = true; //Rejouer le jeu
                    gameOver = false;   //sans retourner au menu principal
                    Son.JouerMusique();
                }else if(boutonChangerModeJeu.estClique(clickX, clickY)){
                    resetGame(gc);
                    modeSelection = true; //retourner sur la selection de mode
                }else if(boutonRetour.estClique(clickX, clickY)){
                    resetGame(gc); //Réinitialise le jeu -> retour au menu
                    Son.JouerMusique(); // Rejoue de la music
                }else if (boutonSettings.estClique(clickX, clickY)) { //bouton "Settings" à la fin de jeu 
                    drawSettingsRectangle = !drawSettingsRectangle; //l'affichage du rectangle
                }
            }else if (!modeCréation && !gameStarted && !modeSelection && !finDeJeu && boutonJouer.estClique(clickX, clickY)) {
                //bascule sur la selection de mode
                modeSelection = true;
                Affichage.enregistrerPseudo();  // Stocker le pseudo avant de commencer
                drawSettingsRectangle = false; 

            //confirme un input sur le bouton "Mode classique" ou "Prochain niveau"
            }else if(!modeCréation && !gameStarted && type && ((modeSelection && boutonClassique.estClique(clickX, clickY))
                    || (finDeJeu && boutonProchainNiveau.estClique(clickX, clickY)))){
                niv = new Niveau(diff);
                bricks = niv.makeNiveau();
                brickRows = niv.getBrickRows();
                modeSelection = false;
                finDeJeu = false;
                gameStarted = true;
                type = true;

            //confirme un input sur le bouton Personnalisé
            }else if(!modeCréation && !gameStarted && modeSelection && boutonPerso.estClique(clickX, clickY)){
                niv = n;
                n.setOuvert(true);
                f1.setVisible(true);
            }
            else if( !modeCréation && !gameStarted && modeSelection && boutonCréation.estClique(clickX, clickY)){
                modeSelection = false;
                modeCréation = true;
                brickRows = gen.getBrickRows();
                gen.setOuvert(true);
    

            }else if(!modeCréation && !gameStarted && 
                    ((modeSelection && boutonRetourSelec.estClique(clickX, clickY))||
                    finDeJeu && boutonRetourSelec.estClique(clickX, clickY))){
                modeSelection = false;
                finDeJeu = false;
            }else if(!modeCréation && !gameStarted && !type && boutonRecommencer.estClique(clickX, clickY)){
                resetGame(gc);
                gameStarted = true;
                niv.resetBrick();
            }
            else if(!gameStarted && modeCréation){

                if (boutonEnregistrer.estClique(clickX, clickY)){
                    f2.getNiv(gen);

                    f2.setVisible(true);
                    
                } 
                else if (boutonRetourSelec.estClique(clickX, clickY)){
                    modeCréation = false;

                }
                else{
                    gen.genNiveau(clickX, clickY);
                }
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
        stage.setResizable(false);


    
            

        // Charger le meilleur score au début
        bestScore = Score.getBestScore(Pseudo.getPseudo());

       

        stage.show();
        tl.play();
    }
    

    
    private void run(GraphicsContext gc) {

        //Si en mode Pause,le jeu s'arrete mais pas revenir le page de menu principal
        if (isPaused) {
            Affichage.afficherBoutonPause(gc,boutonPause);  // S'arrete le jeu
            return;  
        }        

        Affichage.setGC(gc);

        
        if (f1.isActive() || (f2 != null && f2.isActive())){

        }
        else{
            if (modeCréation){
                gen.afficheNiveau(gc);
                Affichage.affichageMenuProchainNiveau(gc, boutonEnregistrer, boutonRetourSelec);
                if (!gen.getOuvert()){
                    modeCréation = false;
                }
            }
            else
            if (!gameStarted && finDeJeu){
                if (type){
                    Affichage.affichageMenuProchainNiveau(gc, boutonProchainNiveau, boutonRetourSelec);
                }
                else{
                    Affichage.affichageMenuProchainNiveau(gc, boutonRecommencer, boutonRetourSelec);
                }
            }
            else
            if (gameStarted && niv.finDeJeu()){
                gameStarted = false;
                finDeJeu = niv.finDeJeu();
                if (type){
                    if (diff <10) diff++;
                }
            }
            else
            if(gameOver){
                Son.PauseMusique(); // stop music
                Affichage.afficherFinDeJeu(gc, score, boutonRejouer, boutonChangerModeJeu, boutonRetour, boutonSettings, drawSettingsRectangle);
            }else 
            if (modeSelection){
                Affichage.affichageMenuSelection(gc, boutonClassique, boutonPerso, boutonCréation, boutonRetourSelec);
                if (n.getPath() != null && n.getOuvert()){
                    bricks = niv.makeNiveau();
                    if (bricks == null){
                        Affichage.afficheTexte(gc, "format du ficher personnalisé invalide");
                    }
                    else{
                        type = false;
                        brickRows = niv.getBrickRows();
                        modeSelection = false;
                        gameStarted = true;
                        n.setOuvert(false);
                    }
                }
            }else
            if (gameStarted && !niv.finDeJeu()) {
                if (!Son.MusiqueEnCours()) { // Rejouer la music pendant que le jeu en cours
                    Son.JouerMusique();
                }
                gc.fillText("Score: " + score, width / 2, height / 2);

                 Affichage.afficherBoutonMute(gc, boutonMute); // afficher le bouton
                 Affichage.afficherBoutonPause(gc, boutonPause);

                Balle.MAJPosHorizontal();
                Balle.MAJPosVertical();
                
                 gc.fillOval(Balle.getXPos(), Balle.getYPos(), Balle.getSize(), Balle.getSize());

                // Dessiner les briques
                niv.afficheNiveau(gc);
            
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
                                    niv.brickDetruite();
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
                Affichage.afficherBoutonToggleBackgroundMusic(gc, boutonToggleBackgroundMusic);//Affiche dans le boutonSetting
                Affichage.afficherBoutonToggleCollisionSound(gc, boutonToggleCollisionSound);//Affiche dans le boutonSetting
                Affichage.afficherBoutonToggleBounceSound(gc, boutonToggleBounceSound);//Affiche dans le boutonSetting  
            }
        }        
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
