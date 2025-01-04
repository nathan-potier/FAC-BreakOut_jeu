package org.openjfx;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

class EventMouseClick implements EventTrigger{

    public static void actOnInput(Canvas canvas, GraphicsContext gc){
        canvas.setOnMouseClicked(e -> {
            double clickX = e.getX();
            double clickY = e.getY();
            if (GameState.isSettingOpen) {  // Traiter les clics sur les boutons uniquement lorsque le menu des paramètres est ouvert
                // Gérer le basculement entre le mode souris et le mode clavier
                if (BoutonListe.bMouse.estClique(clickX, clickY)) {
                    // Basculer l'état du mode souris
                    GameState.isMouseMode = !GameState.isMouseMode;// Inverser l'état du mode souris
                    // Si les deux modes sont off, réactiver le mode clavier
                    if (!GameState.isMouseMode && !GameState.isKeyboardMode) {
                        GameState.isKeyboardMode = true;
                        BoutonListe.bKeyboard.setText("Clavier: on");
                    }
                    BoutonListe.bMouse.setText(GameState.isMouseMode ? "Souris: on" : "Souris: off");// Définir le texte en fonction de l'état
                }
                if (BoutonListe.bKeyboard.estClique(clickX, clickY)) {
                    // Basculer l'état du mode clavier
                    GameState.isKeyboardMode=!GameState.isKeyboardMode;// Inverser l'état du mode clavier
                    if (!GameState.isMouseMode && !GameState.isKeyboardMode) {
                        GameState.isMouseMode = true;
                        BoutonListe.bMouse.setText("Souris: on");
                    }
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

            if(GameState.isGameOver){
                BoutonListe.bSetting.enable();
                if(BoutonListe.bRejouer.estClique(clickX, clickY)){
                    NiveauActuel.resetGame(gc);
                    GameState.isGameStarted = true; //Rejouer le jeu
                    GameState.isGameOver = false;   //sans retourner au menu principal
                    Son.jouerMusique();
                    GameState.isSettingOpen = false;
                    BoutonListe.bSetting.disable();
                }else if(BoutonListe.bChangerModeJeu.estClique(clickX, clickY)){
                    NiveauActuel.resetGame(gc);
                    GameState.modeSelection = true; //retourner sur la selection de mode
                    GameState.isSelectingMode = false; //retourner sur la selection de mode
                }else if(BoutonListe.bRetour.estClique(clickX, clickY)){
                    NiveauActuel.resetGame(gc); //Réinitialise le jeu -> retour au menu
                    Son.jouerMusique(); // Rejoue de la music
                    BoutonListe.bJouer.enable();
                    GameState.isSettingOpen = false;

                }else if (BoutonListe.bSetting.estClique(clickX, clickY)) { //bouton "Settings" à la fin de jeu 
                    GameState.isSettingOpen = !GameState.isSettingOpen; //l'affichage du rectangle
                }
            }else if (!GameState.isCreatingNiv && !GameState.isGameStarted && !GameState.isSelectingMode && !GameState.isGameOver && BoutonListe.bJouer.estClique(clickX, clickY)) {
                //bascule sur la selection de mode
                GameState.isSelectingMode = true;
                Affichage.enregistrerPseudo();  // Stocker le pseudo avant de commencer
                GameState.isSettingOpen = false; 
                BoutonListe.bJouer.disable();
                BoutonListe.bSetting.disable();

            //confirme un input sur le bouton "Mode classique" ou "Mode expérimental"
            }else if(!GameState.isCreatingNiv && !GameState.isGameStarted && !GameState.isGameOver && GameState.isSelectingMode && BoutonListe.bClassique.estClique(clickX, clickY)){
                GameState.isSelectingMode = false;
                GameState.isSelectingNiv = true;
                NiveauActuel.modeActif = "classique";
            }else if(!GameState.isCreatingNiv && !GameState.isGameStarted && !GameState.isGameOver && GameState.isSelectingMode && BoutonListe.bExperimental.estClique(clickX, clickY)){
                GameState.isSelectingMode = false;
                GameState.isSelectingNiv = true;
                NiveauActuel.modeActif = "experimental";

            //Retourne à la sélection de mode lorsqu'on se trouve dans le menu des niveaux
            }else if (!GameState.isSelectingMode && GameState.isSelectingNiv && BoutonListe.bRetourAuMode.estClique(clickX, clickY)){
                GameState.isSelectingMode = true;
                GameState.isSelectingNiv = false;

            }else if(GameState.isSelectingNiv && !GameState.isGameStarted){ 
                for (int i = 0; i < BoutonListe.boutonsNiveaux.size(); i++) {
                    Bouton bouton = BoutonListe.boutonsNiveaux.get(i);
                    if (bouton.estClique(clickX, clickY) && !GameState.isSelectingMode) {
                        if(NiveauActuel.modeActif.equals("classique")){
                            NiveauActuel.niv = new Niveau(i + 1); //Charger le niveau sélectionné
                            NiveauActuel.tabBrick = NiveauActuel.niv.makeNiveau();
                        }else if(NiveauActuel.modeActif.equals("experimental")){ 
                            NiveauActuel.niv = new Niveau();
                            NiveauActuel.tabBrick = NiveauActuel.niv.makeNiveauExperimental("Niveau" + (i+1) + ".txt"); //Crée les briques en fonction du niveau sélectionné à partir d'un fichier existant
                        }
                        NiveauActuel.brickRows = NiveauActuel.niv.getBrickRows();
                        GameState.isGameStarted = true;
                        GameState.isSelectingNiv = false; 
                        break; 
                    }
                }
            }

            //confirme un input sur le bouton Personnalisé
            else if(!GameState.isCreatingNiv && !GameState.isGameStarted 
                    && GameState.isSelectingMode && BoutonListe.bPerso.estClique(clickX, clickY)){
                NiveauActuel.niv = NiveauPossible.n;
                NiveauPossible.n.setOuvert(true);
                FenetrePossible.f1.setVisible(true);
            }
            else if( !GameState.isCreatingNiv && !GameState.isGameStarted && GameState.isSelectingMode && BoutonListe.bCréation.estClique(clickX, clickY)){
                GameState.isSelectingMode = false;
                GameState.isCreatingNiv = true;
                NiveauActuel.brickRows = NiveauPossible.gen.getBrickRows();
                NiveauPossible.gen.setOuvert(true);
    

            }else if(!GameState.isCreatingNiv && !GameState.isGameStarted && 
                    ((GameState.isSelectingMode && BoutonListe.bRetourSelec.estClique(clickX, clickY))||
                    GameState.isGameOver && BoutonListe.bRetourSelec.estClique(clickX, clickY))){
                GameState.isSelectingMode = false;
                GameState.isGameOver = false;
                BoutonListe.bJouer.enable();
                BoutonListe.bSetting.enable();
            }else if(!GameState.isCreatingNiv && !GameState.isGameStarted && !NiveauActuel.type && BoutonListe.bRecommencer.estClique(clickX, clickY)){
                NiveauActuel.resetGame(gc);
                GameState.isGameStarted = true;
                NiveauActuel.niv.resetBrick();
            }
            else if(!GameState.isGameStarted && GameState.isCreatingNiv){

                if (BoutonListe.bEnregistrer.estClique(clickX, clickY)){
                    FenetrePossible.f2.getNiv(NiveauPossible.gen);

                    FenetrePossible.f2.setVisible(true);
                    BoutonListe.bJouer.enable();
                    BoutonListe.bSetting.enable();
                    
                } 
                else if (BoutonListe.bRetourSelec.estClique(clickX, clickY)){
                    GameState.isCreatingNiv = false;
                    BoutonListe.bJouer.enable();
                    BoutonListe.bSetting.enable();

                }
                else{
                    NiveauPossible.gen.genNiveau(clickX, clickY);
                }
            }

            // Vérifier si le bouton Settings a été cliqué
            if (!GameState.isGameStarted && !GameState.isGameOver && BoutonListe.bSetting.estClique(clickX, clickY)) {
                GameState.isSettingOpen = !GameState.isSettingOpen; // Pour ouvrir et fermer les parametres
            }
        });
    }
}
