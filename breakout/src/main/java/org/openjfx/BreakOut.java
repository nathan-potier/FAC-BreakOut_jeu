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


    private Bouton boutonRetourAuMode;
    private List<Bouton> boutonsNiveaux;
    
    private FenetrePerso f1;
    private FenetreEditeur f2;

    private boolean drawSettingsRectangle = false;



    


    private boolean gameOver = false;
    private boolean ouvert = false;




    public static int getWidth(){ return width; }
    public static int getHeight(){ return height; }
    public static String getTitre(){ return GAME_TITLE; }


    //Réinitialise l'état du jeu pour recommencer une nouvelle partie
    private void resetGame(GraphicsContext gc){

        this.gameOver = false;
        this.gameStarted = false;
        this.score = 0;
        nbBalle = 1;
        Balle.resetBalles(balle1);
        Racket.resetSize();
        Brick.resetBrickTab(bricks, brickRows, brickCols);
        Son.rejouerMusique();
        Affichage.afficherDefFont(gc);
    }
    


    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
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
                if (BoutonListe.bMouse.estClique(clickX, clickY)) {
                    // Basculer l'état du mode souris
                    isMouseMode = !isMouseMode;// Inverser l'état du mode souris
                    BoutonListe.bMouse.setText(isMouseMode ? "Souris: on" : "Souris: off");// Définir le texte en fonction de l'état
                }
                if (BoutonListe.bKeyboard.estClique(clickX, clickY)) {
                    // Basculer l'état du mode clavier
                    isKeyboardMode=!isKeyboardMode;// Inverser l'état du mode clavier
                    BoutonListe.bKeyboard.setText(isKeyboardMode ? "Clavier: on" : "Clavier: off");// Définir le texte en fonction de l'état
                }                

                if (BoutonListe.bToggleBackgroundMusic.estClique(clickX, clickY)) {
                    // Basculer la musique de fond
                    if (Son.isBackgroundMusicOn()) {
                        Son.setBackgroundMusicOn(false);  // Arrêter la musique de fond
                        BoutonListe.bToggleBackgroundMusic.setText("BackgroundMusic: off");
                    } else {
                        Son.setBackgroundMusicOn(true); // Démarrer la musique de fond
                        BoutonListe.bToggleBackgroundMusic.setText("BackgroundMusic: on");
                    }
                }
                
                if (BoutonListe.bToggleCollisionSound.estClique(clickX, clickY)) {
                    // Basculer le son de collision
                    if (Son.isCollisionSoundOn()) {
                        Son.setCollisionSoundOn(false);  // Désactiver le son de collision
                        BoutonListe.bToggleCollisionSound.setText("BrickSound: off");
                    } else {
                        Son.setCollisionSoundOn(true);   // Activer le son de collision
                        BoutonListe.bToggleCollisionSound.setText("BrickSound: on");
                    }
                }
    
                if (BoutonListe.bToggleBounceSound.estClique(clickX, clickY)) {
                    // Basculer le son de rebond
                    if (Son.isBounceSoundOn()) {
                        Son.setBounceSoundOn(false); // Désactiver le son de rebond
                        BoutonListe.bToggleBounceSound.setText("BounceSound: off");
                    } else {
                        Son.setBounceSoundOn(true); // Activer le son de rebond
                        BoutonListe.bToggleBounceSound.setText("BounceSound: on");
                    }
                }
            }
        
             //Paramètres de clic du bouton muet
             if (BoutonListe.bMute.estClique(clickX, clickY)) {
                if(!Son.isMuted()){
                    Son.toggleMute(); // Changer l'état muet
                    BoutonListe.bMute.setText("Son activé");
                }else {
                    Son.toggleMute(); // Changer l'état avec son
                    BoutonListe.bMute.setText("Muet");
                }
            }

            //Paramètres de clic du bouton Pause
            if (gameStarted && BoutonListe.bPause.estClique(clickX, clickY)) {
                if (isPaused) {
                    isPaused = false;
                    //Son.toggleMute(); // Changer l'état avec son
                } else {
                    isPaused = true; 
                    BoutonListe.bPause.setText("Pause");
                    //Son.toggleMute(); // Changer l'état muet
                }
            }
            

            if(gameOver){
                if(BoutonListe.bRejouer.estClique(clickX, clickY)){
                    resetGame(gc);
                    gameStarted = true; //Rejouer le jeu
                    gameOver = false;   //sans retourner au menu principal
                    Son.jouerMusique();
                }else if(BoutonListe.bChangerModeJeu.estClique(clickX, clickY)){
                    resetGame(gc);
                    modeSelection = true; //retourner sur la selection de mode
                }else if(BoutonListe.bRetour.estClique(clickX, clickY)){
                    resetGame(gc); //Réinitialise le jeu -> retour au menu
                    Son.jouerMusique(); // Rejoue de la music
                }else if (BoutonListe.bSetting.estClique(clickX, clickY)) { //bouton "Settings" à la fin de jeu 
                    drawSettingsRectangle = !drawSettingsRectangle; //l'affichage du rectangle
                }
            }else if (!modeCréation && !gameStarted && !modeSelection && !finDeJeu && BoutonListe.bJouer.estClique(clickX, clickY)) {
                //bascule sur la selection de mode
                modeSelection = true;
                Affichage.enregistrerPseudo();  // Stocker le pseudo avant de commencer
                drawSettingsRectangle = false; 

            //confirme un input sur le bouton "Mode classique" ou "Prochain niveau"
            }else if(!modeCréation && !gameStarted && type && ((modeSelection && BoutonListe.bClassique.estClique(clickX, clickY))
                    || (finDeJeu && BoutonListe.bProchainNiveau.estClique(clickX, clickY)))){
                niv = new Niveau(diff);
                bricks = niv.makeNiveau();
                brickRows = niv.getBrickRows();
                Balle.resetBalles(balle1);
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
            }else if(!modeCréation && !gameStarted && modeSelection && BoutonListe.bPerso.estClique(clickX, clickY)){
                niv = n;
                n.setOuvert(true);
                f1.setVisible(true);
            }
            else if( !modeCréation && !gameStarted && modeSelection && BoutonListe.bCréation.estClique(clickX, clickY)){
                modeSelection = false;
                modeCréation = true;
                brickRows = gen.getBrickRows();
                gen.setOuvert(true);
    

            }else if(!modeCréation && !gameStarted && 
                    ((modeSelection && BoutonListe.bRetourSelec.estClique(clickX, clickY))||
                    finDeJeu && BoutonListe.bRetourSelec.estClique(clickX, clickY))){
                modeSelection = false;
                finDeJeu = false;
            }else if(!modeCréation && !gameStarted && !type && BoutonListe.bRecommencer.estClique(clickX, clickY)){
                resetGame(gc);
                gameStarted = true;
                niv.resetBrick();
            }
            else if(!gameStarted && modeCréation){

                if (BoutonListe.bEnregistrer.estClique(clickX, clickY)){
                    f2.getNiv(gen);

                    f2.setVisible(true);
                    
                } 
                else if (BoutonListe.bRetourSelec.estClique(clickX, clickY)){
                    modeCréation = false;

                }
                else{
                    gen.genNiveau(clickX, clickY);
                }
            }
            



            // Vérifier si le bouton Settings a été cliqué
            if (!gameStarted && !gameOver && BoutonListe.bSetting.estClique(clickX, clickY)) {
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
            Affichage.afficherBoutonPause(gc,BoutonListe.bPause);  // S'arrete le jeu
            return;  
        }        

        Affichage.setGC(gc);

        
        if (f1.isActive() || (f2 != null && f2.isActive())){

        }
        else{
            if (modeCréation){
                gen.afficheNiveau(gc);
                Affichage.affichageMenuProchainNiveau(gc, BoutonListe.bEnregistrer, BoutonListe.bRetourSelec);
                if (!gen.getOuvert()){
                    modeCréation = false;
                }
            }
            else
            if (!gameStarted && finDeJeu){
                if (type){
                    Affichage.affichageMenuProchainNiveau(gc, BoutonListe.bProchainNiveau, BoutonListe.bRetourSelec);
                }
                else{
                    Affichage.affichageMenuProchainNiveau(gc, BoutonListe.bRecommencer, BoutonListe.bRetourSelec);
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
                Son.pauseMusique(); // stop music
                Affichage.afficherFinDeJeu(gc, score, BoutonListe.bRejouer, BoutonListe.bChangerModeJeu, BoutonListe.bRetour, BoutonListe.bSetting, drawSettingsRectangle);
            }else 
            if (modeSelection){
                Affichage.affichageMenuSelection(gc, BoutonListe.bClassique, BoutonListe.bPerso, BoutonListe.bCréation, BoutonListe.bRetourSelec);
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
                if (!Son.musiqueEnCours()) { // Rejouer la music pendant que le jeu en cours
                    Son.jouerMusique();
                }
                gc.fillText("Score: " + score, width / 2, height / 2);

                Affichage.afficherBoutonMute(gc, BoutonListe.bMute); // afficher le bouton
                Affichage.afficherBoutonPause(gc, BoutonListe.bPause);

                Affichage.afficheBalles(gc, balles);
    

                // Dessiner les briques
                niv.afficheNiveau(gc);
            
                // Détection de collision avec les briques et incrémente le score
                score += Collision.checkCollision(balles, niv);

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
            timePU1 = balle1.updateTimer(timePU1);

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
    
                    balles.get(i).rebondSurRaquette(Racket.getXPos(), Racket.getWidth());
                     
                    // Jouer le son de rebond de balle
                    Son.stopSonRebond();
                    Son.jouerSonRebond();
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

                Affichage.afficherMenuPrincipal(gc, BoutonListe.bJouer, BoutonListe.bSetting, drawSettingsRectangle);
               
            }

            if (drawSettingsRectangle) {
                Affichage.dessinerRectangle(gc, BoutonListe.bSetting);
                Affichage.afficherBoutonToggleBackgroundMusic(gc, BoutonListe.bToggleBackgroundMusic);//Affiche dans le boutonSetting
                Affichage.afficherBoutonToggleCollisionSound(gc, BoutonListe.bToggleCollisionSound);//Affiche dans le boutonSetting
                Affichage.afficherBoutonToggleBounceSound(gc, BoutonListe.bToggleBounceSound);//Affiche dans le boutonSetting  
                Affichage.afficherBoutonKeyboard(gc, BoutonListe.bKeyboard);//Affiche dans le boutonSetting
                Affichage.afficherBoutonMouse(gc, BoutonListe.bMouse);//Affiche dans le boutonSetting
            }
            
        }        
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
