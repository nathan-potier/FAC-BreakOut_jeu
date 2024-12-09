package org.openjfx;

public class BoutonListe{

    private static final int width = 1016;
    private static final int height = 700;
    private static final double rectWidth = 100;
    private static final double rectHeight = 50;
    private static final double rectX = (width - rectWidth) / 2;
    private static final double rectY = (height - rectHeight) / 2;
    
    private static double espaceEntreBoutons = 40;

    private static double rectRejouerX = (width - (2 * rectWidth + espaceEntreBoutons)) / 2;
    private static double rectRetourX = rectRejouerX + rectWidth + espaceEntreBoutons;
    private static double rectChangerModeJeuX = (width - rectWidth) / 2;
    private static double rectChangerModeJeuY = rectY + 50 + rectHeight + 15;

    static Bouton bRejouer = new Bouton(rectRejouerX, rectY + 50, rectWidth + 20, rectHeight, "Rejouer");
    static Bouton bChangerModeJeu = new Bouton(rectChangerModeJeuX - 70, rectChangerModeJeuY, rectWidth + 160, rectHeight, "Changer de mode");
    static Bouton bRetour = new Bouton(rectRetourX, rectY + 50, rectWidth + 20, rectHeight, "Retour");
    static Bouton bJouer = new Bouton(rectX, rectY, rectWidth, rectHeight, "Jouer");
    static Bouton bClassique = new Bouton(rectX - 100, rectY - 80, rectWidth + 200, rectHeight, "Mode classique");
    static Bouton bExperimental = new Bouton(rectX - 100, rectY, rectWidth + 200, rectHeight, "Mode expérimental");
    static Bouton bPerso = new Bouton(rectX - 100, rectY + 80, rectWidth + 200, rectHeight, "Mode personnalisé");
    static Bouton bRetourSelec = new Bouton(rectX, rectY + 250, 100, 40, "Retour");
    static Bouton bProchainNiveau = new Bouton(rectX - 100, rectY, rectWidth + 200, rectHeight, "Prochain niveau");
    static Bouton bRecommencer = new Bouton(rectX - 100, rectY, rectWidth + 200, rectHeight, "Recommencer");
    static Bouton bCréation = new Bouton(rectX - 100, rectY + 160, rectWidth + 200, rectHeight, "Mode création");
    static Bouton bEnregistrer = new Bouton(rectX - 100, rectY + 80, rectWidth + 200, rectHeight, "Enregistrer");
    static Bouton bMute = new Bouton(425, height - 80, 100, 40, "Muet");
    static Bouton bPause = new Bouton(525, height - 80, 100, 40,"Pause");
    static Bouton bToggleBackgroundMusic = new Bouton(30, height - 150, 130, 20, "BackgroundMusic: on");; 
    static Bouton bToggleCollisionSound = new Bouton(30, height - 130, 130, 20, "BrickSound: on");
    static Bouton bToggleBounceSound = new Bouton(30, height - 110, 130, 20, "BounceSound: on");
    static Bouton bKeyboard = new Bouton(180, height - 150, 130, 20,"Clavier: on");
    static Bouton bMouse = new Bouton(180, height - 130, 130, 20,"Souris: on");
    static Bouton bSetting = new Bouton(20, height - 80, 100, 40, "Settings");

    static Bouton bMultijoueur = new Bouton(rectX - 100, rectY -160, rectWidth + 200, rectHeight, "Mode Multijoueur");
    static Bouton bRetourAuMode = new Bouton(rectX, rectY + 250, 100, 40, "Retour");
}
