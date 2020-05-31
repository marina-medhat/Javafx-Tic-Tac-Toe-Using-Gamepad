/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package try_minimax;

import animatefx.animation.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.java.games.input.*;
import static try_minimax.FXMLDocumentController.*;
import static javafx.application.Application.launch;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Mark
 */
public class Try_MiniMax extends Application
{
    public static Controller[] ca;
    public static Controller gamepad;
    public static Controller gamepad2;
    public static Component[] components;
    public static Component[] components2;
    public static Thread th;
    public static Thread th2;
    public static Scene scene;
    public static Parent root;
    public static Integer key;
    public static Boolean pressed;
    public static Stage myStage;
    public static FXMLLoader fl;
    public static IntegerProperty xB;
    public static IntegerProperty yB;
    public static IntegerProperty eB;
    public static IntegerProperty bB;
    public static IntegerProperty sB;
    public static IntegerProperty xB2;
    public static IntegerProperty yB2;
    public static IntegerProperty eB2;
    public static IntegerProperty bB2;
    public static IntegerProperty sB2;
    public static Boolean duplicated;
    public static Boolean duplicated2;
    public static int block;
    public static int exitGame;
    

    public static String pathClick = System.getProperty("user.dir")+"\\src\\try_minimax\\Click.mp3";
    public static Media mediaClick = new Media(new File(pathClick).toURI().toString());
    public static MediaPlayer click = new MediaPlayer(mediaClick);
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        p1s = 0;
        p2s = 0;
        mode = 0;
        dif = 0;
        playerStart = true;
        exitGame = 0;
        errorGame = 0;
        multi = false;
        player12 = 0;
        duplicated = false;
        duplicated2 = false;
        FXMLLoader fl = new FXMLLoader();
        root = fl.load(getClass().getResource("FXMLDocument.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        myStage = stage;
        ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
        int tmpFirst = 0;
        for(int i = 0; i < ca.length; i++)
        {
            if(ca[i].getType().equals(Controller.Type.STICK))
            {
               if(tmpFirst == 0)
               {
                   gamepad2 = ca[i];
                   tmpFirst++;
               }
               else
               {
                   gamepad = ca[i];
               }
            }
        }
        key = 0;
        
        FXMLDocumentController.AP.heightProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) 
            {
                double height = (double)newValue;
                GP.setPrefHeight(height);
                IV.setFitHeight(height);
                GIV.setFitHeight(height);
                MG.setPrefHeight(height);
                IVW.setFitHeight(height);
            
            }
        });
        
        FXMLDocumentController.AP.widthProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) 
            {
                double width = (double)newValue;
                GP.setPrefWidth(width);
                IV.setFitWidth(width);
                GIV.setFitWidth(width);
                MG.setPrefWidth(width);
                IVW.setFitWidth(width);
            }
        });
        
        stage.setFullScreen(true);
        
        scene.setOnKeyPressed(e->{
            if(errorGame == 1)
                System.exit(0);
        });
        
        try
        {
            components = gamepad.getComponents();
            components2 = gamepad2.getComponents();
        }
        catch(Exception e)
        {
            System.out.println("Loading Controllers Warning!!");
        }
        
        xB = new SimpleIntegerProperty(0);
        yB = new SimpleIntegerProperty(0);
        eB = new SimpleIntegerProperty(0);
        bB = new SimpleIntegerProperty(0);
        sB = new SimpleIntegerProperty(0);
        
        xB2 = new SimpleIntegerProperty(0);
        yB2 = new SimpleIntegerProperty(0);
        eB2 = new SimpleIntegerProperty(0);
        bB2 = new SimpleIntegerProperty(0);
        sB2 = new SimpleIntegerProperty(0);
        
        final ChangeListener changeListener = new ChangeListener() 
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(duplicated == false)
                {
                    player12 = 1;
                    initInputThread();
                }
                duplicated = !duplicated;
            }
        };
        
        final ChangeListener changeListener2 = new ChangeListener() 
        {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(duplicated2 == false)
                {
                    player12 = 2;
                    initInputThread();
                }
                duplicated2 = !duplicated2;
            }
        };
        
        
        block = 0;
        xB.addListener(changeListener);
        yB.addListener(changeListener);
        eB.addListener(changeListener);
        bB.addListener(changeListener);
        sB.addListener(changeListener);
        
        xB2.addListener(changeListener2);
        yB2.addListener(changeListener2);
        eB2.addListener(changeListener2);
        bB2.addListener(changeListener2);
        sB2.addListener(changeListener2);
        
        th2 = new Thread(new Runnable()
        {
            public void run()
            {
                while(true)
                {
                    if(gamepad == null)
                    {
                        errorGame = 1;
                        initInputThread();
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Try_MiniMax.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            saveGame();
                        } catch (IOException ex) {
                            System.out.println("Saving Game Failed");
                        }
                        try{
                            Process proc = Runtime.getRuntime().exec("java -jar "+System.getProperty("user.dir")+"\\Try_MiniMax.jar");
                            mediaplayer.stop();
                            mediaplayer2.stop();
                            Thread.sleep(10000);
                        } catch (Exception ex) {
                            System.out.println("Couldn't Run The Game Again!!");
                        }
                        System.exit(0);
                    }
                    if(gamepad.poll())
                    {
                        Try_MiniMax.xB.set((int) Try_MiniMax.components[0].getPollData());
                        Try_MiniMax.yB.set((int) Try_MiniMax.components[1].getPollData());
                        Try_MiniMax.eB.set((int) Try_MiniMax.components[7].getPollData());
                        Try_MiniMax.bB.set((int) Try_MiniMax.components[6].getPollData());
                        Try_MiniMax.sB.set((int) Try_MiniMax.components[8].getPollData());
                    }
                    else
                    {
                        errorGame = 1;
                        initInputThread();
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            saveGame();
                        } catch (IOException ex) {
                            System.out.println("Couldn't Save Game!!");
                        }
                        try{
                            Process proc = Runtime.getRuntime().exec("java -jar C:\\Users\\Mark\\Desktop\\Java_XO_Game\\Try_MiniMax.jar");
                            mediaplayer.stop();
                            mediaplayer2.stop();
                            Thread.sleep(10000);
                        } catch (Exception ex) {
                        System.out.println("Couldn't Run The Game Again!!");
                        }
                        System.exit(0);
                    }

                    if(gamepad2.poll() == false && multi == true)
                    {
                        errorGame = 1;
                        initInputThread();
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            saveGame();
                        } catch (IOException ex) {
                        System.out.println("Couldn't Save Game!!");
                        }
                        try{
                            Process proc = Runtime.getRuntime().exec("java -jar C:\\Users\\Mark\\Desktop\\Java_XO_Game\\Try_MiniMax.jar");
                            mediaplayer.stop();
                            mediaplayer2.stop();
                            Thread.sleep(10000);
                        } catch (Exception ex) {
                        System.out.println("Couldn't Run The Game Again!!");
                        }
                        System.exit(0);
                    }
                    else if(gamepad2.poll())
                    {
                        Try_MiniMax.xB2.set((int) Try_MiniMax.components2[0].getPollData());
                        Try_MiniMax.yB2.set((int) Try_MiniMax.components2[1].getPollData());
                        Try_MiniMax.eB2.set((int) Try_MiniMax.components2[7].getPollData());
                        Try_MiniMax.bB2.set((int) Try_MiniMax.components2[6].getPollData());
                        Try_MiniMax.sB2.set((int) Try_MiniMax.components2[8].getPollData());
                    }
                }
            }
        });
        
        th2.start();
    }
    
    public static void initInputThread() 
    {
        mediaClick = new Media(new File(pathClick).toURI().toString());
        click = new MediaPlayer(mediaClick);
        click.play();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() 
                    {
                        if(errorGame == 1)
                        {
                            initMode();
                            exitGame = 1;
                            return;
                        }
                        if (block == 1)
                            return;
                        if(mode == 4)
                        {
                            if(multi)
                                mode = 5;
                            else
                                mode = 2;
                            initMode();
                            return;
                        }
                        else if(mode == 0 && player12 == 1)
                        {
                            if(components[7].getPollData() > 0.7)
                            {
                                block = 1;
                                Tada tmp = new Tada(BM[item]);
                                tmp.setOnFinished(e -> 
                                        {
                                            block = 0;
                                            if(item == 1) mode = 1;
                                            else if(item == 2) mode = 6;
                                            else if(item == 3) System.exit(0);
                                            initMode();
                                        });
                                tmp.play();
                                return;
                            }
                            if(components[6].getPollData() > 0.7)
                            {
                                System.exit(0);
                            }
                            BM[item].setStyle("-fx-background-color:  #00000090;");
                            if(components[0].getPollData() > 0.5)
                            {
                                item++;
                            }
                            if(components[0].getPollData() < -0.5)
                            {
                                item--;
                            }
                            if(item < 1)
                            {
                                item +=3;
                            }
                            if(item > 3)
                            {
                                item -= 3;
                            }
                            BM[item].setStyle("-fx-background-color:  #00000090; -fx-border-color:   #FFFFFF;  -fx-border-width:  10;");
                            new Pulse(BM[item]).play();
                        }
                        else if(mode == 1 && player12 == 1)
                        {
                            if(components[7].getPollData() > 0.7)
                            {
                                block = 1;
                                Tada tmp = new Tada(BM[item]);
                                tmp.setOnFinished(e -> 
                                        {
                                            block  = 0;
                                            if(item == 0)
                                            {
                                                mode = 3;
                                                dif = 0;
                                            }
                                            else if(item == 1) 
                                            {
                                                mode = 3;
                                                dif = 1;
                                            }
                                            else if(item == 2) 
                                            {
                                                mode = 3;
                                                dif = 2;
                                            }
                                            else if(item == 3 && gamepad2 != null && gamepad2.poll()) //Multiplayer
                                            {
                                                p1s = 0;
                                                p2s = 0;
                                                playerStart = true;
                                                mode = 5;
                                            }
                                            initMode();
                                        });
                                tmp.play();
                                return;
                            }
                            if(components[6].getPollData() > 0.7)
                            {
                                mode = 0;
                                initMode();
                                return;
                            }
                            BM[item].setStyle("-fx-background-color:  #00000090;");
                            if(components[0].getPollData() > 0.5)
                            {
                                item++;
                            }
                            if(components[0].getPollData() < -0.5)
                            {
                                item--;
                            }
                            if(item < 0)
                            {
                                item +=4;
                            }
                            if(item > 3)
                            {
                                item -= 4;
                            }
                            BM[item].setStyle("-fx-background-color:  #00000090; -fx-border-color:   #FFFFFF;  -fx-border-width:  10;");
                            new Pulse(BM[item]).play();
                        }
                        else if(mode == 3 && player12 == 1)
                        {
                            if(components[7].getPollData() > 0.7)
                            {
                                block = 1;
                                Tada tmp = new Tada(BM[item]);
                                tmp.setOnFinished(e -> 
                                        {
                                            block = 0;
                                            if(item == 1) 
                                            {
                                                mode = 2;
                                                playerStart = true;
                                            }
                                            else if(item == 2) 
                                            {
                                                mode = 2;
                                                playerStart = false;
                                            }
                                            p1s = 0; p2s = 0;
                                            initMode();
                                        });
                                tmp.play();
                                return;
                            }
                            if(components[6].getPollData() > 0.7)
                            {
                                mode = 0;
                                initMode();
                                return;
                            }
                            BM[item].setStyle("-fx-background-color:  #00000090;");
                            if(components[0].getPollData() > 0.5)
                            {
                                item++;
                            }
                            if(components[0].getPollData() < -0.5)
                            {
                                item--;
                            }
                            if(item < 1)
                            {
                                item += 2;
                            }
                            if(item > 2)
                            {
                                item -= 2;
                            }
                            BM[item].setStyle("-fx-background-color:  #00000090; -fx-border-color:   #FFFFFF;  -fx-border-width:  10;");
                            new Pulse(BM[item]).play();
                        }
                        else if(mode == 2 && player12 == 1)
                        {
                            if(components[8].getPollData() > 0.5)
                            {
                                try {
                                    saveGame();
                                } catch (IOException ex) {
                                    System.out.println("Couldn't Save Game!!");
                                }
                                return;
                            }
                            new BounceIn(B[iPos][jPos]).play();
                            B[iPos][jPos].setStyle(orange);
                            if(components[0].getPollData() > 0.5)
                            {
                                iPos++;
                            }
                            if(components[0].getPollData() < -0.5)
                            {
                                iPos--;
                            }
                            if(components[1].getPollData() > 0.5)
                            {
                                jPos++;
                            }
                            if(components[1].getPollData() < -0.5)
                            {
                                jPos--;
                            }
                            if(components[6].getPollData() > 0.7)
                            {
                                mode = 0;
                                initMode();
                                return;
                            }
                            if(iPos > 2)
                                iPos -= 3;
                            if(iPos < 0)
                                iPos += 3;
                            if(jPos > 2)
                                jPos -= 3;
                            if(jPos < 0)
                                jPos += 3;
                            if(components[7].getPollData() > 0.7)
                            {
                                if(b[iPos][jPos] != 'E')
                                {
                                    B[iPos][jPos].setStyle(red);
                                    new Jello(B[iPos][jPos]).play();
                                    return;
                                }
                                b[iPos][jPos] = player;
                                B[iPos][jPos].setText(player.toString());
                                
                                if(checkWin(b, player))
                                {
                                    winLose = 1;
                                    p1s++;
                                    PL2.setText(p2s.toString());
                                    playerStart = true;
                                    mode = 4;
                                    Tada tmp = new Tada(B[iPos][jPos]);
                                    tmp.setSpeed(0.6);
                                    block = 1;
                                    tmp.setOnFinished(e -> 
                                            {
                                                block = 0;
                                                initMode();
                                            });
                                    tmp.play();
                                    return;
                                }
                                if(checkFull(b))
                                {
                                    winLose = 0;
                                    mode = 4;
                                    Tada tmp = new Tada(B[iPos][jPos]);
                                    tmp.setSpeed(0.7);
                                    block = 1;
                                    tmp.setOnFinished(e -> 
                                            {
                                                block = 0;
                                                initMode();
                                            });
                                    tmp.play();
                                    return;
                                }
                                switchPlayer();
                                Pos tmp ;
                                if(dif == 0)
                                {
                                    tmp = computerMedium();
                                }
                                else if(dif == 1)
                                {
                                    tmp = computerHard();
                                }
                                else
                                {
                                    tmp = firstMiniMax();
                                }
                                b[tmp.i][tmp.j] = player;
                                B[tmp.i][tmp.j].setText(player.toString());
                                new BounceIn(B[tmp.i][tmp.j]).play();
                                if(checkWin(b, player))
                                {
                                    winLose = -1;
                                    p2s++;
                                    PL2.setText(p2s.toString());
                                    mode = 4;
                                    playerStart = false;
                                    Tada tmp1 = new Tada(B[tmp.i][tmp.j]);
                                    tmp1.setSpeed(0.7);
                                    block = 1;
                                    tmp1.setOnFinished(e -> 
                                            {
                                                block = 0;
                                                initMode();
                                            });
                                    tmp1.play();
                                    return;
                                }
                                if(checkFull(b))
                                {
                                    winLose = 0;
                                    mode = 4;
                                    Tada tmp1 = new Tada(B[tmp.i][tmp.j]);
                                    tmp1.setSpeed(0.7);
                                    block = 1;
                                    tmp1.setOnFinished(e -> 
                                            {
                                                block = 0;
                                                initMode();
                                            });
                                    tmp1.play();
                                    return;
                                }
                                switchPlayer();
                            }
                            B[iPos][jPos].setStyle(red);
                            new Jello(B[iPos][jPos]).play();
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else if(mode == 5 && player12 == 1 && player == 'X')      //Multiplayer
                        {
                            if(components[8].getPollData() > 0.5)
                            {
                                try {
                                    saveGame();
                                } catch (IOException ex) {
                                    System.out.println("Couldn't Save Game!!");
                                }
                                return;
                            }
                            new BounceIn(B[iPos][jPos]).play();
                            B[iPos][jPos].setStyle(orange);
                            if(components[0].getPollData() > 0.5)
                            {
                                iPos++;
                            }
                            if(components[0].getPollData() < -0.5)
                            {
                                iPos--;
                            }
                            if(components[1].getPollData() > 0.5)
                            {
                                jPos++;
                            }
                            if(components[1].getPollData() < -0.5)
                            {
                                jPos--;
                            }
                            if(components[6].getPollData() > 0.7)
                            {
                                mode = 0;
                                initMode();
                                return;
                            }
                            if(iPos > 2)
                                iPos -= 3;
                            if(iPos < 0)
                                iPos += 3;
                            if(jPos > 2)
                                jPos -= 3;
                            if(jPos < 0)
                                jPos += 3;
                            if(components[7].getPollData() > 0.7)
                            {
                                if(b[iPos][jPos] != 'E')
                                {
                                    B[iPos][jPos].setStyle(red);
                                    new Jello(B[iPos][jPos]).play();
                                    return;
                                }
                                b[iPos][jPos] = player;
                                B[iPos][jPos].setText(player.toString());
                                
                                if(checkWin(b, player))
                                {
                                    winLose = 1;
                                    p1s++;
                                    playerStart = true;
                                    PL1.setText(p1s.toString());
                                    mode = 4;
                                    Tada tmp = new Tada(B[iPos][jPos]);
                                    tmp.setSpeed(0.7);
                                    block = 1;
                                    tmp.setOnFinished(e -> 
                                            {
                                                block = 0;
                                                initMode();
                                            });
                                    tmp.play();
                                    return;
                                }
                                if(checkFull(b))
                                {
                                    winLose = 0;
                                    mode = 4;
                                    Tada tmp = new Tada(B[iPos][jPos]);
                                    tmp.setSpeed(0.7);
                                    block = 1;
                                    tmp.setOnFinished(e -> 
                                            {
                                                block = 0;
                                                initMode();
                                            });
                                    tmp.play();
                                    return;
                                }
                                switchPlayer();
                            }
                            B[iPos][jPos].setStyle(red);
                            new Jello(B[iPos][jPos]).play();
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else if(mode == 5 && player12 == 2 && player == 'O')      //Multiplayer
                        {
                            if(components2[8].getPollData() > 0.5)
                            {
                                try {
                                    saveGame();
                                } catch (IOException ex) {
                                    Logger.getLogger(Try_MiniMax.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                return;
                            }
                            new BounceIn(B[iPos][jPos]).play();
                            B[iPos][jPos].setStyle(orange);
                            if(components2[0].getPollData() > 0.5)
                            {
                                iPos++;
                            }
                            if(components2[0].getPollData() < -0.5)
                            {
                                iPos--;
                            }
                            if(components2[1].getPollData() > 0.5)
                            {
                                jPos++;
                            }
                            if(components2[1].getPollData() < -0.5)
                            {
                                jPos--;
                            }
                            if(components2[6].getPollData() > 0.7)
                            {
                                mode = 0;
                                initMode();
                                return;
                            }
                            if(iPos > 2)
                                iPos -= 3;
                            if(iPos < 0)
                                iPos += 3;
                            if(jPos > 2)
                                jPos -= 3;
                            if(jPos < 0)
                                jPos += 3;
                            if(components2[7].getPollData() > 0.7)
                            {
                                if(b[iPos][jPos] != 'E')
                                {
                                    B[iPos][jPos].setStyle(red);
                                    new Jello(B[iPos][jPos]).play();
                                    return;
                                }
                                b[iPos][jPos] = player;
                                B[iPos][jPos].setText(player.toString());
                                
                                if(checkWin(b, player))
                                {
                                    winLose = -1;
                                    p2s++;
                                    playerStart = false;
                                    PL2.setText(p2s.toString());
                                    mode = 4;
                                    Tada tmp = new Tada(B[iPos][jPos]);
                                    tmp.setSpeed(0.7);
                                    block = 1;
                                    tmp.setOnFinished(e -> 
                                            {
                                                block = 0;
                                                initMode();
                                            });
                                    tmp.play();
                                    return;
                                }
                                if(checkFull(b))
                                {
                                    winLose = 0;
                                    mode = 4;
                                    Tada tmp = new Tada(B[iPos][jPos]);
                                    tmp.setSpeed(0.7);
                                    block = 1;
                                    tmp.setOnFinished(e -> 
                                            {
                                                block = 0;
                                                initMode();
                                            });
                                    tmp.play();
                                    return;
                                }
                                switchPlayer();
                            }
                            B[iPos][jPos].setStyle(red);
                            new Jello(B[iPos][jPos]).play();
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                return null;
            }
        };

        th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
}

