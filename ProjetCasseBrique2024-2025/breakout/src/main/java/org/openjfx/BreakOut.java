package org.openjfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


public class BreakOut extends Application {

    private static final int width = 1024;
    private static final int height = 768;
    private int racketWidth = 50;
    private int racketHeight = 10;
    private double racketXPos = width / 2.;
    private double racketYPos = height - 40;
    private double ballXPos = width / 2; // ball at the center at game start
    private double ballYPos = height / 2;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private int ballSize = 10;
    private boolean gameStarted = false;
    private final int brickRows = 5;
    private final int brickCols = 12;
    private final double brickWidth = 80;
    private final double brickHeight = 20;
    private boolean[][] bricks = new boolean[brickRows][brickCols];
    private int score = 0;
    private int bestScore = 0; // Meilleur score

    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);
        canvas.setOnMouseMoved(e -> racketXPos = e.getX());
        canvas.setOnMouseClicked(e -> gameStarted = true);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Breakout");
        stage.setScene(new Scene(new StackPane(canvas)));

        // Initialise les briques
        for (int i = 0; i < brickRows; i++) {
            for (int j = 0; j < brickCols; j++) {
                bricks[i][j] = true; // Toutes les briques sont présentes au début
            }
        }

        // Charger le meilleur score au début
        bestScore = Score.getBestScore();

        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        // Fond noir
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));
        gc.setFill(Color.WHITE);

        if (gameStarted) {
            gc.fillText("Score: " + score, width / 2, height / 2);
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;
            gc.fillOval(ballXPos, ballYPos, ballSize, ballSize);

            // Dessiner les briques
            for (int i = 0; i < brickRows; i++) {
                for (int j = 0; j < brickCols; j++) {
                    if (bricks[i][j]) {
                        double brickX = j * (brickWidth + 5);
                        double brickY = i * (brickHeight + 5);
                        gc.setFill(Color.BLUE);
                        gc.fillRect(brickX, brickY, brickWidth, brickHeight);
                    }
                }
            }

            // Détection de collision avec les briques
            for (int i = 0; i < brickRows; i++) {
                for (int j = 0; j < brickCols; j++) {
                    if (bricks[i][j]) {
                        double brickX = j * (brickWidth + 5);
                        double brickY = i * (brickHeight + 5);

                        if (ballXPos + ballSize >= brickX &&
                                ballXPos <= brickX + brickWidth &&
                                ballYPos + ballSize >= brickY &&
                                ballYPos <= brickY + brickHeight) {

                            ballYSpeed = -ballYSpeed;
                            bricks[i][j] = false;
                            score += 10;
                        }
                    }
                }
            }

            // Collision avec les murs
            if (ballXPos <= 0 || ballXPos >= width - ballSize) {
                ballXSpeed = -ballXSpeed; // Rebond horizontal
            }

            if (ballYPos <= 0) {
                ballYSpeed = -ballYSpeed; // Rebond vertical
            }

            // Fin de la partie si la balle tombe
            if (ballYPos > height) {
                Score.saveScoreToFile(score); // Enregistrer le score dans un fichier
                bestScore = Score.getBestScore(); // Mettre à jour le meilleur score
                gameStarted = false; // Fin de la partie
            }

            // Collision avec la raquette
            if (ballYPos + ballSize >= racketYPos - racketHeight / 2 &&
                    ballXPos + ballSize >= racketXPos - racketWidth / 2 &&
                    ballXPos <= racketXPos + racketWidth / 2) {

                ballYSpeed = -ballYSpeed; // Rebond de la balle
            }

        } else {
            gc.setStroke(Color.YELLOW);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click to Start", width / 2, height / 2);

            // Afficher le meilleur score
            gc.strokeText("Best Score: " + bestScore, width / 2, height / 2 + 30);

            ballXPos = width / 2;
            ballYPos = height / 2;
        }

        gc.fillRect(racketXPos - racketWidth / 2, racketYPos - racketHeight / 2, racketWidth, racketHeight);
    }




    
    public static void main(String[] args) {
        launch(args);
    }
}
