package org.openjfx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;

public class Son {

    private static MediaPlayer mediaPlayer; // La music du jeu
    private static MediaPlayer paddleBoundPlayer; // Le son de rebond de balle
    private static  MediaPlayer brickBreakPlayer; // Le son des brick cassées
    private static boolean isMuted = false; // mode Muet
    
    //Methode pour arret tous les sons
    public static void muteAllSounds() {
        isMuted = true;
        StopMusique(); 
        StopSonRebond(); 
        StopSonBrique();
    }

    // Methode pour rejouer les sons
    public static void unmuteAllSounds() {
        isMuted = false;
        JouerMusique(); 
    }


    //Methode pour initialiser la music de fond
    public static void initBackgroundMusic() {
        String musicFile = "src/main/resources/sounds/music.mp3"; // L'endroit du fichier audio
        Media sound = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // lecture en boucle
        mediaPlayer.setVolume(0.5); // Le volume（entre 0.0-1.0）
        //mediaPlayer.play();  
    }

   //Methode pour initialiser le son de la balle qui rebondit
    public static void initPaddleBoundSound() {
        String paddleBoundFile = "src/main/resources/sounds/bound.wav"; // 
        Media paddleBoundSound = new Media(Paths.get(paddleBoundFile).toUri().toString());
        paddleBoundPlayer = new MediaPlayer(paddleBoundSound);
    }
    
    //Methode pour initialiser le son des briques cassées
    public static void initBrickBreakSound() {
        String brickBreakFile = "src/main/resources/sounds/hit.wav"; 
        Media brickBreakSound = new Media(Paths.get(brickBreakFile).toUri().toString());
        brickBreakPlayer = new MediaPlayer(brickBreakSound);
    }

    public static void JouerMusique(){
        if (!isMuted) {
            mediaPlayer.play(); // Rejouer la musique
        }
    }

    public static void StopSonRebond(){
        if (paddleBoundPlayer != null) {
            paddleBoundPlayer.stop(); 
        }
    }

    public static void JouerSonRebond(){
        if (!isMuted) {
            paddleBoundPlayer.play(); 
        }
    }

    public static void JouerSonBrique(){
        if (!isMuted) {
            brickBreakPlayer.play();
        }
    }
    
    public static void StopSonBrique(){
        if (brickBreakPlayer != null) {
            brickBreakPlayer.stop();
        }
    }

    public static void StopMusique(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public static void PauseMusique(){
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public static boolean MusiqueEnCours(){
        return mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
    }

    
}
