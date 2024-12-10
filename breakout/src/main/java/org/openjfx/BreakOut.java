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


    private int bestScore = 0; // Meilleur score
    


    private boolean ouvert = false;




    public static int getWidth(){ return GameSpec.width; }
    public static int getHeight(){ return GameSpec.height; }
    public static String getTitre(){ return GameSpec.GAME_TITLE; }
    


    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas(GameSpec.width, GameSpec.height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        //Initialiser les boutons de niveaux
        
 

        // Initialiser la fenetre personnalisé
        
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

        EventMouseClick.actOnInput(canvas, gc);
		

        Son.initBackgroundMusic();//Initialiser la music 
        Son.initPaddleBoundSound(); //Initialiser le son de rebond de balle
        Son.initBrickBreakSound(); // Initialiser le son des briques cassées
        NiveauActuel.balles.add(NiveauActuel.balle1);// Ajout de la balle initiale a la liste
        
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

        
        if (FenetrePossible.f1.isActive() || (FenetrePossible.f2 != null && FenetrePossible.f2.isActive())){

        }
        else{
            if (GameState.isCreatingNiv){
                NiveauPossible.gen.afficheNiveau(gc);
                Affichage.affichageMenuProchainNiveau(gc, BoutonListe.bEnregistrer, BoutonListe.bRetourSelec);
                if (!NiveauPossible.gen.getOuvert()){
                    GameState.isCreatingNiv = false;
                }
            }
            else
            if (!GameState.isGameStarted && GameState.isGameOver){
                if (NiveauActuel.type){
                    Affichage.affichageMenuProchainNiveau(gc, BoutonListe.bProchainNiveau, BoutonListe.bRetourSelec);
                }
                else{
                    Affichage.affichageMenuProchainNiveau(gc, BoutonListe.bRecommencer, BoutonListe.bRetourSelec);
                }
            }
            else
            if (GameState.isGameStarted && NiveauActuel.niv.finDeJeu()){
                GameState.isGameStarted = false;
                GameState.isGameOver
 = NiveauActuel.niv.finDeJeu();
            }
            else
            if(GameState.isGameOver){
                Son.pauseMusique(); // stop music
                Affichage.afficherFinDeJeu(gc, NiveauActuel.score, BoutonListe.bRejouer, BoutonListe.bChangerModeJeu, BoutonListe.bRetour, BoutonListe.bSetting, GameState.isSettingOpen);
            }else 
            if (GameState.isSelectingMode){

                Affichage.affichageMenuSelection(gc, BoutonListe.bClassique, BoutonListe.bExperimental, BoutonListe.bPerso, BoutonListe.bCréation, BoutonListe.bMultijoueur, BoutonListe.bRetourSelec);
                if (NiveauPossible.n.getPath() != null && NiveauPossible.n.getOuvert()){
                    NiveauActuel.tabBrick = NiveauActuel.niv.makeNiveau();
                    if (NiveauActuel.tabBrick == null){
                        Affichage.afficheTexte(gc, "format du ficher personnalisé invalide");
                    }
                    else{
                        NiveauActuel.type = false;
                        NiveauActuel.brickRows = NiveauActuel.niv.getBrickRows();
                        GameState.isSelectingMode = false;
                        GameState.isGameStarted = true;
                        NiveauPossible.n.setOuvert(false);
                    }
                }
            }else if(GameState.isSelectingNiv){
                Affichage.affichageSelectionNiveaux(gc, BoutonListe.boutonsNiveaux, BoutonListe.bRetourAuMode);

            }else if (GameState.isGameStarted && !NiveauActuel.niv.finDeJeu()) {
                if (!Son.musiqueEnCours()) { // Rejouer la music pendant que le jeu en cours
                    Son.jouerMusique();
                }
                gc.fillText("Score: " + NiveauActuel.score, GameSpec.width / 2, GameSpec.height / 2);

                Affichage.afficherBoutonMute(gc, BoutonListe.bMute); // afficher le bouton
                Affichage.afficherBoutonPause(gc, BoutonListe.bPause);

                Affichage.afficheBalles(gc, NiveauActuel.balles);
    

                // Dessiner les briques
                NiveauActuel.niv.afficheNiveau(gc);
            
                // Détection de collision avec les briques et incrémente le score
                NiveauActuel.score += Collision.checkCollision(NiveauActuel.balles, NiveauActuel.niv);

            //actualisation powerUp
            for (int i = 0; i < NiveauActuel.brickRows; i++) {
                for (int j = 0; j < NiveauActuel.brickCols; j++) {
                    if (NiveauActuel.tabBrick[i][j].couleur() == Color.RED){
                        if (NiveauActuel.tabBrick[i][j].getPowerUp().getTombe()){
                            if (NiveauActuel.tabBrick[i][j].getPowerUp().getMalus()){
                                gc.setFill(Color.RED);
                                gc.fillOval(NiveauActuel.tabBrick[i][j].getPowerUp().getPux() + NiveauActuel.tabBrick[i][j].getWidth()/2, NiveauActuel.tabBrick[i][j].getPowerUp().getPuy(), PowerUp.getSize(), PowerUp.getSize());
                            }else{
                                gc.setFill(Color.BLUE);
                                gc.fillOval(NiveauActuel.tabBrick[i][j].getPowerUp().getPux() + NiveauActuel.tabBrick[i][j].getWidth()/2, NiveauActuel.tabBrick[i][j].getPowerUp().getPuy(), PowerUp.getSize(), PowerUp.getSize());
                            }
                            NiveauActuel.tabBrick[i][j].getPowerUp().actuPUy();
                            if (NiveauActuel.tabBrick[i][j].getPowerUp().getPuy() + PowerUp.getSize() >= Racket.getYPos() - Racket.getHeight() /2 &&
                            NiveauActuel.tabBrick[i][j].getPowerUp().getPux() + PowerUp.getSize() >= Racket.getXPos() - Racket.getWidth() / 2 &&
                            NiveauActuel.tabBrick[i][j].getPowerUp().getPux() <= Racket.getXPos() + Racket.getWidth() / 2){
                                NiveauActuel.tabBrick[i][j].getPowerUp().applyEffect();
                                NiveauActuel.tabBrick[i][j].getPowerUp().setTombe(false);
                            }
                            if(NiveauActuel.tabBrick[i][j].getPowerUp().getPuy() > Racket.getYPos() +10){
                                NiveauActuel.tabBrick[i][j].getPowerUp().setTombe(false);
                            }
                        
    
                        }
                    }
                    
                }
            }
            //verif powerUp BALLSIZE actif si oui augmente le temps passé
            TimerListe.timePU1 = NiveauActuel.balle1.updateTimer(TimerListe.timePU1);

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
            for (int i = 0; i < NiveauActuel.balles.size(); i++) {
            if (NiveauActuel.balles.get(i).getXSpeed() > 3){
                TimerListe.timeMU2++;
                if (TimerListe.timeMU2 >= 700){
                    Balle.resetSpeed();
                    TimerListe.timeMU2 = 0;
                }
            }
        }


            for (int i = 0; i < NiveauActuel.balles.size(); i++) {
                
            
                // Collision avec les murs
                if (NiveauActuel.balles.get(i).getXPos() <= 0 || NiveauActuel.balles.get(i).getXPos() >= GameSpec.width - Balle.getSize()) {
                    NiveauActuel.balles.get(i).rebondHorizontal();
                }
    
                if (NiveauActuel.balles.get(i).getYPos() <= 0) {
                    NiveauActuel.balles.get(i).rebondVertical();
                }
    
    
                // Collision avec la raquette
                if (NiveauActuel.balles.get(i).getYPos() + Balle.getSize() >= Racket.getYPos() - Racket.getHeight() /2 &&
                    NiveauActuel.balles.get(i).getXPos() + Balle.getSize() >= Racket.getXPos() - Racket.getWidth() / 2 &&
                    NiveauActuel.balles.get(i).getXPos() <= Racket.getXPos() + Racket.getWidth() / 2 && NiveauActuel.balles.get(i).getYSpeed() > 0){
    
                    NiveauActuel.balles.get(i).rebondSurRaquette(Racket.getXPos(), Racket.getWidth());
                     
                    // Jouer le son de rebond de balle
                    Son.stopSonRebond();
                    Son.jouerSonRebond();
                }
    
                }

                for (int i = 0; i < NiveauActuel.balles.size(); i++) {
                    if (NiveauActuel.balles.get(i).getYPos() > Racket.getYPos()){
                        NiveauActuel.nbBalle--;
                        NiveauActuel.balles.remove(i);
                    
                    }
                }
    
    
                if (NiveauActuel.nbBalle == 0){
                    GameState.isGameOver
     = true; 
                    GameState.isGameStarted = false; // Fin de la partie
                    Score.saveScoreToFile(Pseudo.getPseudo(), NiveauActuel.score);
                    bestScore = Score.getBestScore(Pseudo.getPseudo()); // Mettre à jour le meilleur score
    

                }
                gc.setFill(Color.YELLOW);
                gc.fillRect(Racket.getXPos() - Racket.getWidth() / 2,
                Racket.getYPos() - Racket.getHeight() / 2,
                Racket.getWidth(), Racket.getHeight());

            }else {

                Affichage.afficherMenuPrincipal(gc, BoutonListe.bJouer, BoutonListe.bSetting, GameState.isSettingOpen);
               
            }

            if (GameState.isSettingOpen) {
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
