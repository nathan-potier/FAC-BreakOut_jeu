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
import javafx.scene.text.FontWeight;
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
    private int ballYSpeed = 4;
	private int ballXSpeed = 4;
    private int ballSize = 10;    
    private boolean gameStarted = false;
    private final int brickRows = 5;
    private final int brickCols = 12;
    private final double brickWidth = 80;
    private final double brickHeight = 20;
    private Brick[][] bricks = new Brick[brickRows][brickCols];
    private int score = 0;
    private double rectWidth = 100;
    private double rectHeight = 50;
    private double rectX = (width - rectWidth) / 2;
    private double rectY = (height - rectHeight) / 2;




    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
		canvas.setOnMouseMoved(e ->  racketXPos  = e.getX());
		canvas.setOnMouseClicked(e -> {
            double clickX = e.getX();
            double clickY = e.getY();
        
            if (clickX >= rectX && clickX <= rectX + rectWidth && clickY >= rectY && clickY <= rectY + rectHeight) {
                gameStarted = true;
            }
        });
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Breakout");
        stage.setScene(new Scene(new StackPane(canvas)));
        for (int i = 0; i < brickRows; i++) {
            for (int j = 0; j < brickCols; j++) {
                if(i%2 != 0){
                    bricks[i][j] = new BrickBleue(true);
                }
                else{
                    bricks[i][j] = new BrickRouge(true);
                }

        
            }
        }
        
        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        // black background: TODO add better graphics using layers.
		gc.setFill(Color.BLACK); 
		gc.fillRect(0, 0, width, height);
		gc.setFill(Color.WHITE);
		gc.setFont(Font.font(25));
        gc.setFill(Color.WHITE);
        

		if(gameStarted) {
            gc.fillText("Score: " + score, width/2, height/2);
			ballXPos+=ballXSpeed;
			ballYPos+=ballYSpeed;
			gc.fillOval(ballXPos, ballYPos, ballSize, ballSize);
            // collision mur
            for (int i = 0; i < brickRows; i++) {
                for (int j = 0; j < brickCols; j++) {
                    if (bricks[i][j].isNotBreak) {
                        double brickX = j * (brickWidth + 5);
                        double brickY = i * (brickHeight + 5);
                        gc.setFill(bricks[i][j].couleur());
                        gc.fillRect(brickX, brickY, brickWidth, brickHeight);
                    }
                }
            }
            for (int i = 0; i < brickRows; i++) {
                for (int j = 0; j < brickCols; j++) {
                    if (bricks[i][j].isNotBreak) {
                        double brickX = j * (brickWidth + 5);
                        double brickY = i * (brickHeight + 5);
            
                        if (ballXPos + ballSize >= brickX &&
                            ballXPos <= brickX + brickWidth &&
                            ballYPos + ballSize >= brickY &&
                            ballYPos <= brickY + brickHeight) {
            
                            ballYSpeed = -ballYSpeed; 
                            if(bricks[i][j].couleur() != Color.GRAY){
                                bricks[i][j].isNotBreak = false;
                                score += 10; 
                            }
                             
                        }
                    }
                }
            }
            
            if (ballXPos <= 0 || ballXPos >= width - ballSize) {
                ballXSpeed = -ballXSpeed; // Rebond horizontal
            }
            
            if (ballYPos <= 0) {
                ballYSpeed = -ballYSpeed; // Rebond vertical en haut de l'écran
            }
            
            if (ballYPos > height) {
                gameStarted = false; // Fin de partie si la balle dépasse l'écran en bas
            }
            // collision raquette
            if (ballYPos + ballSize >= racketYPos - racketHeight / 2 &&
                ballXPos + ballSize >= racketXPos - racketWidth / 2 &&
                ballXPos <= racketXPos + racketWidth / 2) {
    
                ballYSpeed = -ballYSpeed; // Rebond de la balle
            }
            if (ballYPos > racketYPos)
                {
                    gameStarted=false;
                    for (int i = 0; i < brickRows; i++) {
                        for (int j = 0; j < brickCols; j++) {
                            bricks[i][j].isNotBreak = true; // Toutes les briques sont présentes au début
                        }
                    }
                    score = 0;
                }
                gc.fillRect(racketXPos - racketWidth/2, racketYPos - racketHeight/2, racketWidth, racketHeight);

		} else {
			gc.setFont(Font.font("Courier New",  FontWeight.LIGHT, 25));
			gc.setStroke(Color.YELLOW);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.strokeText("Jouer", width / 2, height / 2 + 3);
            gc.strokeRect(rectX, rectY, rectWidth, rectHeight);
			ballXPos = width / 2;
			ballYPos = height / 2;
		}
		
	}

    public static void main(String[] args) {
        launch(args);
    }

}
