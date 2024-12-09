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

    private boolean type = true;
    private Niveau niv; 
    private NiveauInput n = new NiveauInput(); 
    private NiveauEditeur gen = new NiveauEditeur();
    private String modeActif;

    private int brickRows;
    private final int brickCols = 12;
    private Brick[][] bricks;

    private int score = 0;
    private int bestScore = 0; // Meilleur score
    public static ArrayList<Balle> balles = new ArrayList<>();
    public static int nbBalle = 1;
    private final Balle balle1 = new Balle(BreakOut.getWidth() / 2,BreakOut.getHeight() / 2);
    
    
    private FenetrePerso f1;
    private FenetreEditeur f2;

    private boolean drawSettingsRectangle = false;



    


    private boolean IsGameOver = false;
    private boolean ouvert = false;




    public static int getWidth(){ return GameSpec.width; }
    public static int getHeight(){ return GameSpec.height; }
    public static String getTitre(){ return GameSpec.GAME_TITLE; }


    //Réinitialise l'état du jeu pour recommencer une nouvelle partie
    private void resetGame(GraphicsContext gc){

        this.IsGameOver = false;
        GameState.isGameStarted = false;
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

        Canvas canvas = new Canvas(GameSpec.width, GameSpec.height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        //Initialiser les boutons de niveaux
        
 

        // Initialiser la fenetre personnalisé
        
        f1 = new FenetrePerso(n);
        f2 = new FenetreEditeur();
        BoutonListe.makeBoutons();


        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
        
		canvas.setOnMouseMoved(e ->{
            double mouseX = e.getX();
            double mouseY = e.getY();

            for (Bouton bouton : Bouton.getAllButtons()) {
                bouton.setHover(bouton.isHover(mouseX, mouseY));
            }
            
            if (GameState.isMouseMode) {
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
                    GameState.isMouseMode = !GameState.isMouseMode;// Inverser l'état du mode souris
                    BoutonListe.bMouse.setText(GameState.isMouseMode ? "Souris: on" : "Souris: off");// Définir le texte en fonction de l'état
                }
                if (BoutonListe.bKeyboard.estClique(clickX, clickY)) {
                    // Basculer l'état du mode clavier
                    GameState.isKeyboardMode=!GameState.isKeyboardMode;// Inverser l'état du mode clavier
                    BoutonListe.bKeyboard.setText(GameState.isKeyboardMode ? "Clavier: on" : "Clavier: off");// Définir le texte en fonction de l'état
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
            if (GameState.isGameStarted && BoutonListe.bPause.estClique(clickX, clickY)) {
                if (GameState.isPaused) {
                    GameState.isPaused = false;
                    //Son.toggleMute(); // Changer l'état avec son
                } else {
                    GameState.isPaused = true; 
                    BoutonListe.bPause.setText("Pause");
                    //Son.toggleMute(); // Changer l'état muet
                }
            }
            
            //confirme un input sur le bouton "Mode multijoueur"
            if (!GameState.isCreatingNiv && !GameState.isGameStarted && GameState.isSelectingMode && BoutonListe.bMultijoueur.estClique(clickX, clickY)){
                GameState.isSelectingMode = false;
                GameState.isPlayingMulti = true;
                GameState.isSelectingNiv = true;
            }

            if(IsGameOver){
                if(BoutonListe.bRejouer.estClique(clickX, clickY)){
                    resetGame(gc);
                    GameState.isGameStarted = true; //Rejouer le jeu
                    IsGameOver = false;   //sans retourner au menu principal
                    Son.jouerMusique();
                }else if(BoutonListe.bChangerModeJeu.estClique(clickX, clickY)){
                    resetGame(gc);
                    GameState.isSelectingMode = true; //retourner sur la selection de mode
                }else if(BoutonListe.bRetour.estClique(clickX, clickY)){
                    resetGame(gc); //Réinitialise le jeu -> retour au menu
                    Son.jouerMusique(); // Rejoue de la music
                }else if (BoutonListe.bSetting.estClique(clickX, clickY)) { //bouton "Settings" à la fin de jeu 
                    drawSettingsRectangle = !drawSettingsRectangle; //l'affichage du rectangle
                }
            }else if (!GameState.isCreatingNiv && !GameState.isGameStarted && !GameState.isSelectingMode && !GameState.isGameOver && BoutonListe.bJouer.estClique(clickX, clickY)) {
                //bascule sur la selection de mode
                GameState.isSelectingMode = true;
                Affichage.enregistrerPseudo();  // Stocker le pseudo avant de commencer
                drawSettingsRectangle = false; 


            //confirme un input sur le bouton "Mode classique" ou "Mode expérimental"
            }else if(!GameState.isCreatingNiv && !GameState.isGameStarted && !GameState.isGameOver && GameState.isSelectingMode && BoutonListe.bClassique.estClique(clickX, clickY)){
                GameState.isSelectingMode = false;
                GameState.isSelectingNiv = true;
                modeActif = "classique";
            }else if(!GameState.isCreatingNiv && !GameState.isGameStarted && !GameState.isGameOver && GameState.isSelectingMode && BoutonListe.bExperimental.estClique(clickX, clickY)){
                GameState.isSelectingMode = false;
                GameState.isSelectingNiv = true;
                modeActif = "experimental";

            //Retourne à la sélection de mode lorsqu'on se trouve dans le menu des niveaux
            }else if (!GameState.isSelectingMode && GameState.isSelectingNiv && BoutonListe.bRetourAuMode.estClique(clickX, clickY)){
                GameState.isSelectingMode = true;
                GameState.isSelectingNiv = false;

            }else if(GameState.isSelectingNiv && !GameState.isGameStarted){ 
                for (int i = 0; i < BoutonListe.boutonsNiveaux.size(); i++) {
                    Bouton bouton = BoutonListe.boutonsNiveaux.get(i);
                    if (bouton.estClique(clickX, clickY) && !GameState.isSelectingMode) {
                        if(modeActif.equals("classique")){
                            niv = new Niveau(i + 1); //Charger le niveau sélectionné
                            bricks = niv.makeNiveau();
                        }else if(modeActif.equals("experimental")){ 
                            niv = new Niveau();
                            bricks = niv.makeNiveauExperimental("Niveau" + (i+1) + ".txt"); //Crée les briques en fonction du niveau sélectionné à partir d'un fichier existant
                        }
                        brickRows = niv.getBrickRows();
                        GameState.isGameStarted = true;
                        GameState.isSelectingNiv = false; 
                        break; 
                    }
                }
            }

            //confirme un input sur le bouton Personnalisé
            else if(!GameState.isCreatingNiv && !GameState.isGameStarted && GameState.isSelectingMode && BoutonListe.bPerso.estClique(clickX, clickY)){
                niv = n;
                n.setOuvert(true);
                f1.setVisible(true);
            }
            else if( !GameState.isCreatingNiv && !GameState.isGameStarted && GameState.isSelectingMode && BoutonListe.bCréation.estClique(clickX, clickY)){
                GameState.isSelectingMode = false;
                GameState.isCreatingNiv = true;
                brickRows = gen.getBrickRows();
                gen.setOuvert(true);
    

            }else if(!GameState.isCreatingNiv && !GameState.isGameStarted && 
                    ((GameState.isSelectingMode && BoutonListe.bRetourSelec.estClique(clickX, clickY))||
                    GameState.isGameOver && BoutonListe.bRetourSelec.estClique(clickX, clickY))){
                GameState.isSelectingMode = false;
                GameState.isGameOver = false;
            }else if(!GameState.isCreatingNiv && !GameState.isGameStarted && !type && BoutonListe.bRecommencer.estClique(clickX, clickY)){
                resetGame(gc);
                GameState.isGameStarted = true;
                niv.resetBrick();
            }
            else if(!GameState.isGameStarted && GameState.isCreatingNiv){

                if (BoutonListe.bEnregistrer.estClique(clickX, clickY)){
                    f2.getNiv(gen);

                    f2.setVisible(true);
                    
                } 
                else if (BoutonListe.bRetourSelec.estClique(clickX, clickY)){
                    GameState.isCreatingNiv = false;

                }
                else{
                    gen.genNiveau(clickX, clickY);
                }
            }
            



            // Vérifier si le bouton Settings a été cliqué
            if (!GameState.isGameStarted && !IsGameOver && BoutonListe.bSetting.estClique(clickX, clickY)) {
                drawSettingsRectangle = !drawSettingsRectangle; // Pour ouvrir et fermer les parametres
            }

        });

        Son.initBackgroundMusic();//Initialiser la music 
        Son.initPaddleBoundSound(); //Initialiser le son de rebond de balle
        Son.initBrickBreakSound(); // Initialiser le son des briques cassées
        balles.add(balle1);// Ajout de la balle initiale a la liste
        
        canvas.setFocusTraversable(true);
        canvas.requestFocus();
        canvas.setOnKeyPressed(e -> {

        
            // Gestion de la saisie du pseudo
            if (!Pseudo.estDef()) {
                Affichage.handleKeyPress(e); // Appel de la méthode pour la gestion du pseudo
                
            }
        
            // Événement du clavier pour mettre le jeu en pause avec la touche ESC
            if (GameState.isKeyboardMode || (GameState.isMouseMode && GameState.isKeyboardMode)) {
                if (GameState.isGameStarted && e.getCode() == KeyCode.ESCAPE) {
                    GameState.isPaused = !GameState.isPaused; // Bascule l'état de la pause
                }
                // Flèche droite (déplacer vers la droite)
                if (e.getCode() == KeyCode.RIGHT) {
                    if (Racket.getXPos() + Racket.getWidth() < GameSpec.width) { // Empêcher la palette de dépasser la limite droite de l'écran
                        Racket.setXpos(Racket.getXPos() + 50); // Déplacer vers la droite
                    }
                // Flèche gauche (déplacer vers la gauche)
                } else if (e.getCode() == KeyCode.LEFT) {
                    if (Racket.getXPos() > 0) { // Empêcher la palette de dépasser la limite gauche de l'écran
                        Racket.setXpos(Racket.getXPos() - 50); // Déplacer vers la gauche
                    }
                }
            }
        });
        

        stage.setWidth(GameSpec.width);
        stage.setHeight(GameSpec.height);
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
        if (GameState.isPaused) {
            Affichage.afficherBoutonPause(gc,BoutonListe.bPause);  // S'arrete le jeu
            return;  
        }        

        Affichage.setGC(gc);

        
        if (f1.isActive() || (f2 != null && f2.isActive())){

        }
        else{
            if (GameState.isCreatingNiv){
                gen.afficheNiveau(gc);
                Affichage.affichageMenuProchainNiveau(gc, BoutonListe.bEnregistrer, BoutonListe.bRetourSelec);
                if (!gen.getOuvert()){
                    GameState.isCreatingNiv = false;
                }
            }
            else
            if (!GameState.isGameStarted && GameState.isGameOver){
                if (type){
                    Affichage.affichageMenuProchainNiveau(gc, BoutonListe.bProchainNiveau, BoutonListe.bRetourSelec);
                }
                else{
                    Affichage.affichageMenuProchainNiveau(gc, BoutonListe.bRecommencer, BoutonListe.bRetourSelec);
                }
            }
            else
            if (GameState.isGameStarted && niv.finDeJeu()){
                GameState.isGameStarted = false;
                GameState.isGameOver = niv.finDeJeu();
            }
            else
            if(IsGameOver){
                Son.pauseMusique(); // stop music
                Affichage.afficherFinDeJeu(gc, score, BoutonListe.bRejouer, BoutonListe.bChangerModeJeu, BoutonListe.bRetour, BoutonListe.bSetting, drawSettingsRectangle);
            }else 
            if (GameState.isSelectingMode){

                Affichage.affichageMenuSelection(gc, BoutonListe.bClassique, BoutonListe.bExperimental, BoutonListe.bPerso, BoutonListe.bCréation, BoutonListe.bMultijoueur, BoutonListe.bRetourSelec);
                if (n.getPath() != null && n.getOuvert()){
                    bricks = niv.makeNiveau();
                    if (bricks == null){
                        Affichage.afficheTexte(gc, "format du ficher personnalisé invalide");
                    }
                    else{
                        type = false;
                        brickRows = niv.getBrickRows();
                        GameState.isSelectingMode = false;
                        GameState.isGameStarted = true;
                        n.setOuvert(false);
                    }
                }
            }else if(GameState.isSelectingNiv){
                Affichage.affichageSelectionNiveaux(gc, BoutonListe.boutonsNiveaux, BoutonListe.bRetourAuMode);

            }else if (GameState.isGameStarted && !niv.finDeJeu()) {
                if (!Son.musiqueEnCours()) { // Rejouer la music pendant que le jeu en cours
                    Son.jouerMusique();
                }
                gc.fillText("Score: " + score, GameSpec.width / 2, GameSpec.height / 2);

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
            TimerListe.timePU1 = balle1.updateTimer(TimerListe.timePU1);

            //Verif powerUp RacketSize actif si oui augmente le temps passé
            if (Racket.getWidth() > 100){
                TimerListe.timePU2++;
                if (TimerListe.timePU2 >= 700){
                    Racket.resetSize();
                    TimerListe.timePU2 = 0;
                }

            }

            //verif Malus RacketSize actif si oui augmente le temps passé
            if (Racket.getWidth() < 100){
                TimerListe.timeMU1++;
                if (TimerListe.timeMU1 >= 700){
                    Racket.resetSize();
                    TimerListe.timeMU1 = 0;
                }

            }
            //verif Malus BALLSPEED actif si oui augmente le temps passé
            for (int i = 0; i < balles.size(); i++) {
            if (balles.get(i).getXSpeed() > 3){
                TimerListe.timeMU2++;
                if (TimerListe.timeMU2 >= 700){
                    Balle.resetSpeed();
                    TimerListe.timeMU2 = 0;
                }
            }
        }


            for (int i = 0; i < balles.size(); i++) {
                
            
                // Collision avec les murs
                if (balles.get(i).getXPos() <= 0 || balles.get(i).getXPos() >= GameSpec.width - Balle.getSize()) {
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
                    IsGameOver = true; 
                    GameState.isGameStarted = false; // Fin de la partie
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
