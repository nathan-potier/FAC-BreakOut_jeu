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
 * <li>Initialiser et jouer la musique de fond avec {@link #initBackgroundMusic()} et {@link #JouerMusique()}.</li>
 * <li>Initialiser et jouer le son de rebond de balle avec {@link #initPaddleBoundSound()} et {@link #JouerSonRebond()}.</li>
 * <li>Initialiser et jouer le son des briques cassées avec {@link #initBrickBreakSound()} et {@link #JouerSonBrique()}.</li>
 * <li>Contrôler l'état de la musique et des effets sonores (mettre en pause, arrêter, ou basculer le mode silencieux) avec {@link #StopMusique()}, {@link #PauseMusique()}, {@link #StopSonRebond()}, {@link #StopSonBrique()}, et {@link #toggleMute()}.</li>
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

     /**
     * Retourne l'état actuel du mode silencieux.
     * 
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
            StopMusique(); 
            StopSonRebond();
            StopSonBrique();
        } else {
            JouerMusique(); 
        }
    }

   

    
    /**
     * Initialise la musique de fond du jeu. Le fichier audio de la musique est spécifié à l'emplacement
     * <code>src/main/resources/sounds/music.mp3</code> et la musique est configurée pour être lue en boucle.
     */
    //Methode pour initialiser la music de fond
    public static void initBackgroundMusic() {
        String musicFile = "src/main/resources/sounds/music.mp3"; // L'endroit du fichier audio
        Media sound = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // lecture en boucle
        mediaPlayer.setVolume(0.5); // Le volume（entre 0.0-1.0）
        //mediaPlayer.play();  
    }

    /**
     * Initialise le son du rebond de la balle contre la palette. Le fichier audio du son est situé
     * à l'emplacement <code>src/main/resources/sounds/bound.wav</code>.
     */
    // Méthode pour initialiser le son de la balle qui rebondit
    public static void initPaddleBoundSound() {
        String paddleBoundFile = "src/main/resources/sounds/bound.wav"; // 
        Media paddleBoundSound = new Media(Paths.get(paddleBoundFile).toUri().toString());
        paddleBoundPlayer = new MediaPlayer(paddleBoundSound);
    }
    
    /**
     * Initialise le son des briques cassées. Le fichier audio du son est situé à
     * l'emplacement <code>src/main/resources/sounds/hit.wav</code>.
     */
    // Méthode pour initialiser le son des briques cassées
    public static void initBrickBreakSound() {
        String brickBreakFile = "src/main/resources/sounds/hit.wav"; 
        Media brickBreakSound = new Media(Paths.get(brickBreakFile).toUri().toString());
        brickBreakPlayer = new MediaPlayer(brickBreakSound);
    }

    /**
     * Joue la musique de fond si le mode silencieux est désactivé.
     */
    // Méthode pour jouer la musique de fond
    public static void JouerMusique(){
        if (!isMuted) { //Si ce n'est pas en mode silencieux
            mediaPlayer.play(); //Rejouer la musique
        }
    }

    /**
     * Arrête le son du rebond de la balle.
     */
    // Méthode pour arrêter le son de rebond de la balle
    public static void StopSonRebond(){
        paddleBoundPlayer.stop(); 
    }

    /**
     * Joue le son du rebond de la balle si le mode silencieux est désactivé.
     */
    // Méthode pour jouer le son de rebond de la balle
    public static void JouerSonRebond(){
        if (!isMuted) { //Si ce n'est pas en mode silencieux
            paddleBoundPlayer.play(); 
        }
    }

     /**
     * Joue le son des briques cassées si le mode silencieux est désactivé.
     */
    // Méthode pour jouer le son des briques cassées
    public static void JouerSonBrique(){
        if (!isMuted) { //Si ce n'est pas en mode silencieux
            brickBreakPlayer.play();
        }
    }
    
    /**
     * Arrête le son des briques cassées.
     */
    // Méthode pour arrêter le son des briques cassées
    public static void StopSonBrique(){
        brickBreakPlayer.stop();
    }

    /**
     * Arrête la musique de fond.
     */
    // Méthode pour arrêter la musique de fond
    public static void StopMusique(){
        mediaPlayer.stop();
    }

    /**
     * Met en pause la musique de fond.
     */
    // Méthode pour mettre la musique en pause
    public static void PauseMusique(){
        mediaPlayer.pause();
    }

    /**
     * Vérifie si la musique de fond est en cours de lecture.
     * 
     * @return <code>true</code> si la musique est en cours de lecture, <code>false</code> sinon
     */
    // Méthode pour vérifier si la musique est en cours
    public static boolean MusiqueEnCours(){
        return mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
    }
}