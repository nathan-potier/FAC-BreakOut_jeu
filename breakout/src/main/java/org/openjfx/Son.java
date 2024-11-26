package org.openjfx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;

/**
 * La classe Son permet de gérer les effets sonores du jeu, y compris la musique de fond,
 * le son des rebonds de balle et le son des briques cassées. Elle inclut également une fonctionnalité
 * pour activer ou désactiver le mode silencieux.
 * 
 * <p>
 * Cette classe fournit les fonctionnalités suivantes :
 * </p>
 * 
 * <ul>
 * <li>Initialiser et jouer la musique de fond avec {@link #initBackgroundMusic()} et {@link #jouerMusique()}.</li>
 * <li>Initialiser et jouer le son de rebond de balle avec {@link #initPaddleBoundSound()} et {@link #jouerSonRebond()}.</li>
 * <li>Initialiser et jouer le son des briques cassées avec {@link #initBrickBreakSound()} et {@link #jouerSonBrique()}.</li>
 * <li>Contrôler l'état de la musique et des effets sonores (mettre en pause, arrêter, ou basculer le mode silencieux) avec {@link #StopMusique()}, {@link #PauseMusique()}, {@link #s()}, {@link #StopSonBrique()}, et {@link #toggleMute()}.</li>
 * <li>Coupe puis rejoue la musique {@link #rejouerMusique()}.<\li>
 * </ul>
 * 
 * <p>
 * Le mode silencieux permet de couper tous les effets sonores, et les sons peuvent être réactivés
 * via la méthode {@link #toggleMute()}.
 * </p>
 * 
 * @author Siting ZHENG
 * @version 1.0
 */
public class Son {

    private static MediaPlayer mediaPlayer; // La music du jeu
    private static MediaPlayer paddleBoundPlayer; // Le son de rebond de balle
    private static  MediaPlayer brickBreakPlayer; // Le son des brick cassées
    
    private static boolean isMuted = false; // État de silence
    private static boolean backgroundMusicOn = true; // Musique de fond activée/désactivée
    private static boolean collisionSoundOn = true; // Son de collision (briques cassées) activé/désactivé
    private static boolean bounceSoundOn = true; // Son de rebond de balle activé/désactivé

    /**
     * Retourne l'état actuel de la musique de fond.
     * @return <code>true</code> si la musique de fond est activée, <code>false</code> sinon
     */
    public static boolean isBackgroundMusicOn() {
        return backgroundMusicOn;
    }

    /**
     * Retourne l'état actuel du son des rebonds de balle.
     * @return <code>true</code> si le son des rebonds est activé, <code>false</code> sinon
     */
    public static boolean isBounceSoundOn() {
        return bounceSoundOn;
    }

    /**
     * Retourne l'état actuel du son des collisions (briques cassées).
     * @return <code>true</code> si le son des collisions est activé, <code>false</code> sinon
     */
    public static boolean isCollisionSoundOn() {
        return collisionSoundOn;
    }


     /**
     * Retourne l'état actuel du mode silencieux.
     * @return <code>true</code> si le mode silencieux est activé, <code>false</code> sinon
     */
    public static boolean isMuted() { return isMuted;}
    
    /**
     * Bascule l'état du mode silencieux. Si le mode silencieux est activé, tous les sons seront arrêtés,
     * sinon la musique de fond sera jouée.
     */
    // Méthode pour gérer tous les effets sonores en mode silencieux
    public static void toggleMute() {
        isMuted = !isMuted; //Basculer l'état muet
        if (isMuted) {
            stopAllSounds();
        } else {
            jouerMusique(); 
        }
    }

    /**
     * Active ou désactive la musique de fond.
     * 
     * @param enabled <code>true</code> pour activer la musique de fond, <code>false</code> pour la désactiver
     */
    //Méthode pour active ou désactive la musique de fond
    public static void setBackgroundMusicOn(boolean enabled) {
        backgroundMusicOn = enabled;
        if (!isMuted) {
            jouerMusique();
        } else if(!enabled){
            stopMusique();
        }
    }

     /**
     * Active ou désactive le son des rebonds de balle.
     * 
     * @param enabled <code>true</code> pour activer le son de rebond, <code>false</code> pour le désactiver
     */
    //Méthode pour active ou désactive le son de rebond de balle.
    public static void setBounceSoundOn(boolean enabled) {
        bounceSoundOn = enabled;
    }

    /**
     * Active ou désactive le son des briques cassées.
     * 
     * @param enabled <code>true</code> pour activer le son des briques cassées, <code>false</code> pour le désactiver
     */
    //Méthode pour active ou désactive le son des briques cassées.
    public static void setCollisionSoundOn(boolean enabled) {
        collisionSoundOn = enabled;
    }

    
    /**
     * Initialise la musique de fond du jeu. Le fichier audio de la musique est spécifié à l'emplacement
     * <code>src/main/resources/sounds/music.mp3</code> et la musique est configurée pour être lue en boucle.
     */
    //Methode pour initialiser la music de fond
    public static void initBackgroundMusic() {
        try {
            String musicFile = "src/main/resources/sounds/music.mp3"; // L'endroit du fichier audio
            Media sound = new Media(Paths.get(musicFile).toUri().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // lecture en boucle
            mediaPlayer.setVolume(0.5); // Le volume（entre 0.0-1.0）
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation de la musique de fond: " + e.getMessage());
        }
    }

    /**
     * Initialise le son du rebond de la balle contre la palette. Le fichier audio du son est situé
     * à l'emplacement <code>src/main/resources/sounds/bound.wav</code>.
     */
    // Méthode pour initialiser le son de la balle qui rebondit
    public static void initPaddleBoundSound() {
        try{
            String paddleBoundFile = "src/main/resources/sounds/bound.wav"; // 
            Media paddleBoundSound = new Media(Paths.get(paddleBoundFile).toUri().toString());
            paddleBoundPlayer = new MediaPlayer(paddleBoundSound);
        } catch(Exception e){
            System.err.println("Erreur lors de l'initialisation du son de rebond: " + e.getMessage());
        }
    }
    
    /**
     * Initialise le son des briques cassées. Le fichier audio du son est situé à
     * l'emplacement <code>src/main/resources/sounds/hit.wav</code>.
     */
    // Méthode pour initialiser le son des briques cassées
    public static void initBrickBreakSound() {
        try {
            String brickBreakFile = "src/main/resources/sounds/hit.wav"; 
            Media brickBreakSound = new Media(Paths.get(brickBreakFile).toUri().toString());
            brickBreakPlayer = new MediaPlayer(brickBreakSound);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation du son des briques cassées: " + e.getMessage());
        }
    }

    /**
     * Joue la musique de fond si le mode silencieux est désactivé.
     */
    // Méthode pour jouer la musique de fond
    public static void jouerMusique(){
        if (!isMuted && backgroundMusicOn && mediaPlayer != null && mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) { //Si ce n'est pas en mode silencieux
            mediaPlayer.play(); //Rejouer la musique
        }
    }

    
    /**
     * Arrête le son du rebond de la balle.
     */
    // Méthode pour arrêter le son de rebond de la balle
    public static void stopSonRebond(){
        if (paddleBoundPlayer != null) {
            paddleBoundPlayer.stop(); 
        }
    }

    /**
     * Joue le son du rebond de la balle si le mode silencieux est désactivé.
     */
    // Méthode pour jouer le son de rebond de la balle
    public static void jouerSonRebond(){
        if (!isMuted && bounceSoundOn && paddleBoundPlayer != null) { //Si ce n'est pas en mode silencieux
            paddleBoundPlayer.stop();
            paddleBoundPlayer.play(); 
        }
    }

     /**
     * Joue le son des briques cassées si le mode silencieux est désactivé.
     */
    // Méthode pour jouer le son des briques cassées
    public static void jouerSonBrique(){
        if (!isMuted && collisionSoundOn && brickBreakPlayer != null) { //Si ce n'est pas en mode silencieux
           brickBreakPlayer.stop();
            brickBreakPlayer.play();
        }
    }
    
    /**
     * Arrête le son des briques cassées.
     */
    // Méthode pour arrêter le son des briques cassées
    public static void stopSonBrique(){
        if (brickBreakPlayer != null) {
            brickBreakPlayer.stop();
        }
    }

    /**
     * Arrête la musique de fond.
     */
    // Méthode pour arrêter la musique de fond
    public static void stopMusique(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Arrête tous les sons et la music.
     */
    public static void stopAllSounds() {
        stopMusique();
        stopSonRebond();
        stopSonBrique();
    }

    /**
     * Met en pause la musique de fond.
     */
    // Méthode pour mettre la musique en pause
    public static void pauseMusique(){
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * Vérifie si la musique de fond est en cours de lecture.
     * 
     * @return <code>true</code> si la musique est en cours de lecture, <code>false</code> sinon
     */
    // Méthode pour vérifier si la musique est en cours
    public static boolean musiqueEnCours(){
        return mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
    }

    /**
     * rejoue la musique depuis le début en coupant la musique courante
     */
    public static void rejouerMusique(){
        stopMusique();
        jouerMusique();
    }
}