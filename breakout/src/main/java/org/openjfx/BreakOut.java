package org.openjfx;




import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
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
    
    private boolean isMouseMode = true;
    private boolean isKeyboardMode =true;

    private boolean isPaused = false;
    private boolean modeSelection = false;
    private boolean modeCréation = false;
    private boolean selectionNiveaux = false;
    private boolean gameStarted = false;
    private boolean finDeJeu = false;
    private boolean type = true;
    private int diff = 1;
    private Niveau niv; 
    private NiveauInput n = new NiveauInput(); 
    private NiveauEditeur gen = new NiveauEditeur();

    private int brickRows;
    private final int brickCols = 12;
    private Brick[][] bricks;

    private int score = 0;
    private int bestScore = 0; // Meilleur score
    public static ArrayList<Balle> balles = new ArrayList<>();
    private static double timePU1 = 0;
    private static double  timePU2 = 0;
    private static double timeMU1 = 0;
    private static double timeMU2 = 0;
    public static int nbBalle = 1;
    private final Balle balle1 = new Balle(BreakOut.getWidth() / 2,BreakOut.getHeight() / 2);
    
    private double rectWidth = 100;
    private double rectHeight = 50;
    private double rectX = (width - rectWidth) / 2;
    private double rectY = (height - rectHeight) / 2;

    private double espacementHorizontalSelectNiv = 60; 
    private double espacementVerticalSelectNiv = 90;
    double totalWidthNiv = rectWidth * 5 + espacementHorizontalSelectNiv * 4;
    double rectXniveau = (width - totalWidthNiv) / 2;
    double rectYniveau = (height - rectHeight * 2 - espacementVerticalSelectNiv) / 2;

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
    private Bouton boutonKeyboard;
    private Bouton boutonMouse;
    private Bouton boutonRetourAuMode;
    private List<Bouton> boutonsNiveaux;
    
    private FenetrePerso f1;
    private FenetreEditeur f2;

    private Bouton boutonSettings;
    private boolean drawSettingsRectangle = false;



    


    private boolean gameOver = false;
    private boolean ouvert = false;




    public static int getWidth(){ return width; }
    public static int getHeight(){ return height; }
    public static String getTitre(){ return GAME_TITLE; }

    private void resetBalles(){
        balle1.reset();
        balles = new ArrayList<>();
        balles.add(balle1);
        nbBalle = 1;
    }

    //Réinitialise l'état du jeu pour recommencer une nouvelle partie
    private void resetGame(GraphicsContext gc){

        this.score = 0;
        resetBalles();
        Racket.resetSize();
        Brick.resetBrickTab(bricks, brickRows, brickCols);
  
        this.gameOver = false;
        this.gameStarted = false;
        Son.StopMusique();  // La musique s'arrête
        

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
        boutonRetour = new Bouton(rectRetourX, rectY + 50, rectWidth + 20, rectHeight, "Retour");
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
        boutonToggleBackgroundMusic = new Bouton(30, height - 150, 130, 20, "BackgroundMusic: on");
        boutonToggleCollisionSound = new Bouton(30, height - 130, 130, 20, "BrickSound: on");
        boutonToggleBounceSound = new Bouton(30, height - 110, 130, 20, "BounceSound: on");
       
        boutonKeyboard = new Bouton(180, height - 150, 130, 20,"Clavier: on");
        boutonMouse = new Bouton(180, height - 130, 130, 20,"Souris: on");
    
        //Initialiser les boutons de niveaux
        boutonRetourAuMode = new Bouton(rectX, rectY + 250, 100, 40, "Retour");
        boutonsNiveaux = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            double x = rectXniveau + (i - 1) * (rectWidth + espacementHorizontalSelectNiv); // Position horizontale
            Bouton bouton = new Bouton(x, rectY, rectWidth+40, rectHeight, "Niveau " + i);
            boutonsNiveaux.add(bouton);
        }
        for (int i = 6; i <= 10; i++) {
            double x = rectXniveau + (i - 6) * (rectWidth + espacementHorizontalSelectNiv); // Position horizontale
            double y = rectYniveau + rectHeight + espacementVerticalSelectNiv; // Position verticale (sous la première ligne)
            Bouton bouton = new Bouton(x, y, rectWidth+40, rectHeight, "Niveau " + i);
            boutonsNiveaux.add(bouton);
        }
 

        // Initialiser la fenetre personnalisé
        
        f1 = new FenetrePerso(n);
        f2 = new FenetreEditeur();


        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
        
		canvas.setOnMouseMoved(e ->{
            double mouseX = e.getX();
            double mouseY = e.getY();

            for (Bouton bouton : Bouton.getAllButtons()) {
                bouton.setHover(bouton.isHover(mouseX, mouseY));
            }
            
            if (isMouseMode) {
                Racket.setXpos(mouseX);
            }
        });

		canvas.setOnMouseClicked(e -> {
            double clickX = e.getX();
            double clickY = e.getY();
            if (drawSettingsRectangle) {  // Traiter les clics sur les boutons uniquement lorsque le menu des paramètres est ouvert
                // Gérer le basculement entre le mode souris et le mode clavier
                if (boutonMouse.estClique(clickX, clickY)) {
                    // Basculer l'état du mode souris
                    isMouseMode = !isMouseMode;// Inverser l'état du mode souris
                    boutonMouse.setText(isMouseMode ? "Souris: on" : "Souris: off");// Définir le texte en fonction de l'état
                }
                if (boutonKeyboard.estClique(clickX, clickY)) {
                    // Basculer l'état du mode clavier
                    isKeyboardMode=!isKeyboardMode;// Inverser l'état du mode clavier
                    boutonKeyboard.setText(isKeyboardMode ? "Clavier: on" : "Clavier: off");// Définir le texte en fonction de l'état
                }                

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
            if (gameStarted && boutonPause.estClique(clickX, clickY)) {
                if (isPaused) {
                    isPaused = false;
                    //Son.toggleMute(); // Changer l'état avec son
                } else {
                    isPaused = true; 
                    boutonPause.setText("Pause");
                    //Son.toggleMute(); // Changer l'état muet
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

            }else if(modeSelection && boutonClassique.estClique(clickX, clickY)){
                modeSelection = false;
                selectionNiveaux = true;
            }else if(selectionNiveaux && !boutonRetourAuMode.estClique(clickX, clickY)){
                for (int i = 0; i < boutonsNiveaux.size(); i++) {
                    Bouton bouton = boutonsNiveaux.get(i);
                    if (bouton.estClique(clickX, clickY)) {
                        niv = new Niveau(i + 1); //Charge le niveau sélectionné
                        bricks = niv.makeNiveau();
                        brickRows = niv.getBrickRows();
                        gameStarted = true; 
                        selectionNiveaux = false; 
                    }
                }
            //Permet de retourner au sélection de mode lorsqu'on se trouve dans le menu des niveaux
            }else if(selectionNiveaux && boutonRetourAuMode.estClique(clickX, clickY)){
                modeSelection = true;
                selectionNiveaux = false;

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
        balles.add(balle1);// Ajout de la balle initiale a la liste

        // Gestion des touches clavier pour entrer le pseudo
        if (!Pseudo.estDef()){
            canvas.setOnKeyPressed(Affichage::handleKeyPress);
            canvas.setFocusTraversable(true);  // Nécessaire pour capter les événements de clavier
        }else{
            canvas.setOnKeyPressed(null);
            canvas.setFocusTraversable(false);
        }
        
        // Événement du clavier pour mettre le jeu en pause avec la touche ESC
        canvas.setOnKeyPressed(e -> {
            if (isKeyboardMode || (isMouseMode && isKeyboardMode)) { // mode clavier ou les deux mode qui sont ouvert
                if (gameStarted && e.getCode() == KeyCode.ESCAPE) {
                    isPaused = !isPaused; // Bascule l'état de la pause
                }
                // Flèche droite (déplacer vers la droite)
                if (e.getCode() == KeyCode.RIGHT) {
                    if (Racket.getXPos() + Racket.getWidth() < width) { // Empêcher la palette de dépasser la limite droite de l'écran
                        Racket.setXpos(Racket.getXPos() + 50); // Déplacer vers la droite
                    }
                // Flèche gauche (déplacer vers la gauche)
                }else if (e.getCode() == KeyCode.LEFT) {
                    if (Racket.getXPos() > 0) { // Empêcher la palette de dépasser la limite gauche de l'écran
                        Racket.setXpos(Racket.getXPos() - 50); // Déplacer vers la gauche
                    }
                }
            }    
        });

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
                    if (diff < 8) diff++;
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
            }else if(selectionNiveaux){
                Affichage.affichageSelectionNiveaux(gc, boutonsNiveaux, boutonRetourAuMode);
            }else
            if (gameStarted && !niv.finDeJeu()) {
                if (!Son.MusiqueEnCours()) { // Rejouer la music pendant que le jeu en cours
                    Son.JouerMusique();
                }
                gc.fillText("Score: " + score, width / 2, height / 2);

                Affichage.afficherBoutonMute(gc, boutonMute); // afficher le bouton
                Affichage.afficherBoutonPause(gc, boutonPause);

                for(int i = 0; i<balles.size(); i++){
                    balles.get(i).MAJPosHorizontal();
                    balles.get(i).MAJPosVertical();
                    gc.fillOval(balles.get(i).getXPos(), balles.get(i).getYPos(), Balle.getSize(), Balle.getSize());
                }
    

                // Dessiner les briques
                niv.afficheNiveau(gc);
            
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
                    
                                    balles.get(k).rebondVertical();
        
                                           
                                    if(bricks[i][j].couleur() != Color.GRAY && bricks[i][j].getNbrVie() == 0){
                                        bricks[i][j].isNotBreak = false;
                                        niv.brickDetruite();
                                        score += bricks[i][j].getScore(); 
                                        bricks[i][j].getPowerUp().setTombe(true);


                                        Son.StopSonBrique(); // le son des briques cassées
                                        Son.JouerSonBrique(); // 

                                    }else{
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
                            if (bricks[i][j].getPowerUp().getMalus()){
                                gc.setFill(Color.RED);
                                gc.fillOval(bricks[i][j].getPowerUp().getPux() + bricks[i][j].getWidth()/2, bricks[i][j].getPowerUp().getPuy(), PowerUp.getSize(), PowerUp.getSize());
                            }else{
                                gc.setFill(Color.BLUE);
                                gc.fillOval(bricks[i][j].getPowerUp().getPux() + bricks[i][j].getWidth()/2, bricks[i][j].getPowerUp().getPuy(), PowerUp.getSize(), PowerUp.getSize());
                            }
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
            if (Balle.getSize() == 50){
                timePU1++;
                if (timePU1 >= 700){
                    Balle.resetSize();
                    timePU1 = 0;
                }

            }

            //Verif powerUp RacketSize actif si oui augmente le temps passé
            if (Racket.getWidth() > 100){
                timePU2++;
                if (timePU2 >= 700){
                    Racket.resetSize();
                    timePU2 = 0;
                }

            }

            //verif Malus RacketSize actif si oui augmente le temps passé
            if (Racket.getWidth() < 100){
                timeMU1++;
                if (timeMU1 >= 700){
                    Racket.resetSize();
                    timeMU1 = 0;
                }

            }
            //verif Malus BALLSPEED actif si oui augmente le temps passé
            for (int i = 0; i < balles.size(); i++) {
            if (balles.get(i).getXSpeed() > 3){
                timeMU2++;
                if (timeMU2 >= 700){
                    Balle.resetSpeed();
                    timeMU2 = 0;
                }
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
    
                    balles.get(i).rebondVertical();
                     
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
                }
    
    
                if (nbBalle == 0){
                    gameOver = true; 
                    gameStarted = false; // Fin de la partie
                    Score.saveScoreToFile(Pseudo.getPseudo(), score);
                    bestScore = Score.getBestScore(Pseudo.getPseudo()); // Mettre à jour le meilleur score
    

                }
                gc.setFill(Color.YELLOW);
                gc.fillRect(Racket.getXPos() - Racket.getWidth() / 2,
                Racket.getYPos() - Racket.getHeight() / 2,
                Racket.getWidth(), Racket.getHeight());

            }else {

                Affichage.afficherMenuPrincipal(gc, boutonJouer, boutonSettings, drawSettingsRectangle);
               
            }

            if (drawSettingsRectangle) {
                Affichage.dessinerRectangle(gc, boutonSettings);
                Affichage.afficherBoutonToggleBackgroundMusic(gc, boutonToggleBackgroundMusic);//Affiche dans le boutonSetting
                Affichage.afficherBoutonToggleCollisionSound(gc, boutonToggleCollisionSound);//Affiche dans le boutonSetting
                Affichage.afficherBoutonToggleBounceSound(gc, boutonToggleBounceSound);//Affiche dans le boutonSetting  
                Affichage.afficherBoutonKeyboard(gc, boutonKeyboard);//Affiche dans le boutonSetting
                Affichage.afficherBoutonMouse(gc, boutonMouse);//Affiche dans le boutonSetting
            }
            
        }        
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
