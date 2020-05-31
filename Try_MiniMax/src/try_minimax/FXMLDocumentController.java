package try_minimax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Mark
 */
public class FXMLDocumentController implements Initializable//, Runnable 
{
    @FXML
    private ImageView IView;
    
    public static ImageView IV;
    
    @FXML
    private ImageView GameImageView;
    
    public static ImageView GIV;
    
    @FXML
    private ImageView IVWin;
    
    public static ImageView IVW;
    
    @FXML
    private GridPane GPane;
    
    public static GridPane GP;
    
    @FXML
    private AnchorPane APane;
    
    public static AnchorPane AP;
    
    @FXML
    private Button B00;

    @FXML
    private Button B10;

    @FXML
    private Button B20;

    @FXML
    private Button B01;

    @FXML
    private Button B11;

    @FXML
    private Button B21;

    @FXML
    private Button B02;

    @FXML
    private Button B12;

    @FXML
    private Button B22;
    
    @FXML
    private Button lW;
    
    public static Button lWin;
    
    @FXML
    private GridPane MenuGrid;

    public static GridPane MG;
    
    @FXML
    private Button P1;
    
    public static Button PL1;
    public static Button PL2;
    
    @FXML
    private Button P2;
    
    @FXML
    private Button B1;

    @FXML
    private Button B2;

    @FXML
    private Button B3;

    @FXML
    private Button B4;
    
    public static Button[] BM;

    
    public static Button[][] B;
    
    @FXML
    private Label p2Label;
    
    public static Label p2l;
    
    public static Boolean playerStart;
    
    public static Integer player12;
    
    public static Boolean multi;
    
    public static Character b[][];
    
    public static Character player;
    
    public static Integer iPos,jPos;
    
    public static Thread th;
    
    public static Integer Nrow, Ncol;
    
    public static Integer mode, p1s, p2s;
    
    public Character whoIsFirst;
    
    public static Integer item, dif, winLose;
    
    public static Integer errorGame;
    
    public static String orange = "-fx-background-color: #FD9466; -fx-background-radius: 30;";
    public static String red = "-fx-background-color: red; -fx-background-radius: 30;";
    
    public static String path=System.getProperty("user.dir")+"\\src\\try_minimax\\timon.mp3";
    public static Media media=new Media(new File(path).toURI().toString());
    public static MediaPlayer mediaplayer=new MediaPlayer(media);
    
    public static String path2=System.getProperty("user.dir")+"\\src\\try_minimax\\Lion.mp3";
    public static Media media2=new Media(new File(path2).toURI().toString());
    public static MediaPlayer mediaplayer2=new MediaPlayer(media2);
    
    static void initMode()
    {
        if(errorGame == 1)   //Error
        {
            MG.setVisible(false);
            IV.setVisible(false);
            IVW.setVisible(true);
            Image i = new Image(new File(System.getProperty("user.dir")+"\\src\\try_minimax\\Error.jpg").toURI().toString());
            IVW.setImage(i);
            GIV.setVisible(false);
            GP.setVisible(false);
            return;
        }
        if(mode == 0)   // Menu
        {
            multi = false;
            mediaplayer2.pause();
            mediaplayer.setAutoPlay(true);
            mediaplayer.play();
            MG.setVisible(true);
            IV.setVisible(true);
            IVW.setVisible(false);
            for(int i=1; i<4; i++)
            {
                BM[i].setVisible(true);
                BM[i].setStyle("-fx-background-color:  #00000090");
            }
            BM[0].setVisible(false);
            BM[1].setText("New Game");
            BM[2].setText("Load Game");
            BM[3].setText("Exit");
            BM[1].setStyle("-fx-background-color:  #00000090; -fx-border-color:   #FFFFFF;  -fx-border-width:  10;");
            GIV.setVisible(false);
            GP.setVisible(false);
            item = 1;
        }
        else if(mode == 1)       //Choose
        {
            multi = false;
            mediaplayer2.pause();
            mediaplayer.setAutoPlay(true);
            mediaplayer.play();
            MG.setVisible(true);
            IV.setVisible(true);
            IVW.setVisible(false);
            for(int i=0; i<4; i++)
            {
                BM[i].setVisible(true);
                BM[i].setStyle("-fx-background-color:  #00000090");
            }
            BM[0].setText("Medium");
            BM[1].setText("Hard");
            BM[2].setText("Unbeatable");
            BM[3].setText("Multiplayer");
            BM[0].setStyle("-fx-background-color:  #00000090; -fx-border-color:   #FFFFFF;  -fx-border-width:  10;");
            GIV.setVisible(false);
            GP.setVisible(false);
            item = 0;
        }
        else if(mode == 2)       //Start Game
        {
            multi = false;
            mediaplayer.pause();
            mediaplayer2.setAutoPlay(true);
            mediaplayer2.play();
            MG.setVisible(false);
            IV.setVisible(false);
            IVW.setVisible(false);
            for(int i=0; i<3; i++)
            {
                for(int j=0; j<3; j++)
                {
                    b[i][j] = 'E';
                    B[i][j].setText("");
                    B[i][j].setStyle(orange);
                }
            }
            B[0][0].setStyle(red);
            GIV.setVisible(true);
            GP.setVisible(true);
            iPos = 0; jPos = 0;
            if(playerStart)
            {
                player = 'X';
            }
            else
            {
                Random random = new Random();
                if(dif == 0 || dif == 1)
                {
                    int tmpi = random.nextInt(2);
                    int tmpj = random.nextInt(2);
                    b[tmpi][tmpj] = 'O';
                    B[tmpi][tmpj].setText("O");
                    player = 'X';
                }
                else
                {
                    b[1][1] = 'O';
                    B[1][1].setText("O");
                    System.out.println("1");
                    player = 'X';
                }
            }
            PL1.setText(p1s.toString());
            PL2.setText(p2s.toString());
        }
        if(mode == 3)   // Start First
        {
            multi = false;
            mediaplayer2.pause();
            p2l.setText("Computer");
            mediaplayer.setAutoPlay(true);
            mediaplayer.play();
            MG.setVisible(true);
            IV.setVisible(true);
            IVW.setVisible(false);
            for(int i=1; i<3; i++)
            {
                BM[i].setVisible(true);
                BM[i].setStyle("-fx-background-color:  #00000090");
            }
            BM[0].setVisible(false);
            BM[1].setText("Start First");
            BM[2].setText("Let Computer Start");
            BM[3].setVisible(false);
            BM[1].setStyle("-fx-background-color:  #00000090; -fx-border-color:   #FFFFFF;  -fx-border-width:  10;");
            GIV.setVisible(false);
            GP.setVisible(false);
            item = 1;
        }
        if(mode == 4)   // Win Lose Draw
        {
            MG.setVisible(false);
            IV.setVisible(false);
            IVW.setVisible(true);
            if(winLose == 0)
            {
                Image i = new Image(new File(System.getProperty("user.dir")+"\\src\\try_minimax\\Draw.gif").toURI().toString());
                IVW.setImage(i);
            }
            else if(winLose == -1)
            {
                Image i = new Image(new File(System.getProperty("user.dir")+"\\src\\try_minimax\\Loser.gif").toURI().toString());
                IVW.setImage(i);
            }
            else
            {
                Image i = new Image(new File(System.getProperty("user.dir")+"\\src\\try_minimax\\Win.gif").toURI().toString());
                IVW.setImage(i);
            }
            GIV.setVisible(false);
            GP.setVisible(false);
        }
        else if(mode == 5)       //Multiplayer
        {
            multi = true;
            mediaplayer.pause();
            mediaplayer2.setAutoPlay(true);
            mediaplayer2.play();
            MG.setVisible(false);
            IV.setVisible(false);
            IVW.setVisible(false);
            for(int i=0; i<3; i++)
            {
                for(int j=0; j<3; j++)
                {
                    b[i][j] = 'E';
                    B[i][j].setText("");
                    B[i][j].setStyle(orange);
                }
            }
            B[0][0].setStyle(red);
            GIV.setVisible(true);
            GP.setVisible(true);
            iPos = 0; jPos = 0;
            player = 'O';
            p2l.setText("Player 2");
            PL1.setText(p1s.toString());
            PL2.setText(p2s.toString());
            if(playerStart)
            {
                player = 'X';
            }
        }
        else if(mode == 6)  //Load Game
        {
            try {
                FileReader file = new FileReader("SavedGame");
                BufferedReader bf = new BufferedReader(file);
                String st = bf.readLine();
                StringTokenizer stn = new StringTokenizer(st);
                mode = Integer.parseInt(stn.nextToken());
                st = bf.readLine();
                StringTokenizer stn1 = new StringTokenizer(st);
                dif = Integer.parseInt(stn1.nextToken());
                st = bf.readLine();
                StringTokenizer stn2 = new StringTokenizer(st);
                p1s = Integer.parseInt(stn2.nextToken());
                st = bf.readLine();
                StringTokenizer stn3 = new StringTokenizer(st);
                p2s = Integer.parseInt(stn3.nextToken());
                st = bf.readLine();
                StringTokenizer stn4 = new StringTokenizer(st);
                playerStart = Boolean.parseBoolean(stn4.nextToken());
                if(mode == 5)
                {
                    p2l.setText("Player 2");
                    multi = true;
                }
                else
                {
                    p2l.setText("Computer");
                    multi = false;
                }
                file.close();
            } catch (Exception ex) {
                System.out.println("Couldn't Load Game");
                mode = 0;
            }
            finally
            {
                initMode();
            }
        }
    }
    
    public static void saveGame() throws IOException
    {
        FileWriter wr = new FileWriter("SavedGame");
        wr.write(new Integer(mode).toString());
        wr.write("\n");
        wr.write(new Integer(dif).toString());
        wr.write("\n");
        wr.write(new Integer(p1s).toString());
        wr.write("\n");
        wr.write(new Integer(p2s).toString());
        wr.write("\n");
        wr.write(new Boolean(playerStart).toString());
        wr.write("\n");
        wr.close();
    }
    
    static boolean checkFull(Character[][] board)
    {
        for(int i = 0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                if(board[i][j] == 'E')
                    return false;
            }
        }
        return true;
    }
    
    public static void setLab(String s)
    {
        lWin.setText(s);
    }
    
    static Boolean checkWin(Character[][] board, Character p)
    {
        for(int i = 0; i<3; i++)
        {
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] == p)
                return true;
        }
        for(int i = 0; i<3; i++)
        {
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] == p)
                return true;
        }
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] == p)
                return true;
        if(board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[1][1] == p)
                return true;
        return false;
    }
    
    public static void switchPlayer()
    {
        if(player == 'X')
            player = 'O';
        else
            player = 'X';
    }
    
    public static Integer miniMax(Character tmp[][], Character p) 
    {
        Character notP = 'X';
        if(p == 'X') notP = 'O';
        if(checkWin(tmp, notP) == true)
        {
            if(notP == 'O') return 1;
            else return -1;
        }
        if(checkFull(tmp) == true)
        {
            return 0;
        }
        
        Vector<Integer> res = new Vector<Integer>();
        
        for(int i = 0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                if(tmp[i][j] == 'E')
                {
                    res.add(miniMax(arrayGen(tmp, i, j, p),notP));
                }
            }
        }
        if(p == 'X') return findMin(res);
        else return findMax(res);
    }
    public static Character[][] arrayGen(Character[][] board, int x, int y, Character p)
    {
        Character[][] A = new Character[3][3];
        for(int i = 0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                A[i][j] = board[i][j];
            }
        }
        A[x][y] = p;
        return A;
    }
    
    public static Integer findMin(Vector<Integer> v)
    {
        Integer min = v.get(0);
        Integer ind = 0;
        for(int i=0; i<v.size(); i++)
        {
            if(v.get(i) < min)
            {
                min = v.get(i);
                ind = i;
            }
        }
        return min;
    }
    
    public static Integer findMax(Vector<Integer> v)
    {
        Integer max = v.get(0);
        Integer ind = 0;
        for(int i=0; i<v.size(); i++)
        {
            if(v.get(i) > max)
            {
                max = v.get(i);
                ind = i;
            }
        }
        return max;
    }
    
    public static Integer findMaxIndex(Vector<Integer> v)
    {
        Integer max = v.get(0);
        Integer ind = 0;
        
        for(int i=0; i<v.size(); i++)
        {
            if(v.get(i) > max)
            {
                max = v.get(i);
                ind = i;
            }
        }
        return ind;
    }
    
    static Pos firstMiniMax()
    {
        Vector<Integer> res = new Vector<Integer>();
        Vector<Pos> pos = new Vector<Pos>();
        
        Character notP = 'X';
        if(player == 'X') notP = 'O';
        
        for(int i = 0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                if(b[i][j] == 'E')
                {
                    res.add(miniMax(arrayGen(b, i, j, player),notP));
                    pos.add(new Pos(i,j));
                }
            }
        }
        Integer  tmp = findMaxIndex(res);
        return pos.get(tmp);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        p1s = 0;
        p2s = 0;
        mode = 0;
        p2l = p2Label;
        lWin = lW;
        IV = IView;
        GP = GPane;
        AP = APane;
        GIV = GameImageView;
        MG = MenuGrid;
        IVW = IVWin;
        PL1 = P1;
        PL2 = P2;
        BM = new Button[4];
        BM[0] = B1;
        BM[1] = B2;
        BM[2] = B3;
        BM[3] = B4;
        
        B = new Button[3][3];
        B[0][0] = B00;
        B[0][1] = B01;
        B[0][2] = B02;
        B[1][0] = B10;
        B[1][1] = B11;
        B[1][2] = B12;
        B[2][0] = B20;
        B[2][1] = B21;
        B[2][2] = B22;
        item = 0;
        B[0][0].setStyle(red);
        iPos = 0;
        jPos = 0;
        player = 'X';
        b = new Character[3][3];
        for(int i = 0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                b[i][j] = 'E';
            }
        }
        
        b[1][1] = 'O';
        B[1][1].setText(b[1][1].toString());
        Try_MiniMax.pressed = false;
        Try_MiniMax.key = 0;
        initMode();
    }
    public static void printb(Character[][] board)
    {
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
    
    public static boolean nextPlace() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b[i][j] == 'E') {
                    Nrow = i;
                    Ncol = j;
                    return true;
                }
            }
        }
        return false;
    }
    
    public static Boolean isEmpty()
    {
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                if(b[i][j] != 'E')
                    return false;
            }
        }
        return true;
    }
    
    public static Pos computerHard() {

        Pos p = new Pos(0,0);
        Character playerCh = 'X';
        Character computerCh = player;
        if(player == 'X')
             playerCh= 'O';
        if (isEmpty()) 
        {
            p.i = 1;
            p.j = 1;
        } 
        else 
        {
            if (b[0][0] == b[0][1] && b[0][0] == computerCh && b[0][2] == 'E') {
                p.i = 0;
                p.j = 2;

            } else if (b[0][0] == b[0][2] && b[0][0] == computerCh && b[0][1] == 'E') {
                p.i = 0;
                p.j = 1;

            } else if (b[0][1] == b[0][2] && b[0][1] == computerCh && b[0][0] == 'E') {
                p.i = 0;
                p.j = 0;

            } else if (b[1][0] == b[1][1] && b[1][0] == computerCh && b[1][2] == 'E') {
                p.i = 1;
                p.j = 2;

            } else if (b[1][0] == b[1][2] && b[1][0] == computerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;

            } else if (b[1][1] == b[1][2] && b[1][1] == computerCh && b[1][0] == 'E') {
                p.i = 1;
                p.j = 0;

            } else if (b[2][0] == b[2][1] && b[2][0] == computerCh && b[2][2] == 'E') {
                p.i = 2;
                p.j = 2;

            } else if (b[2][0] == b[2][2] && b[2][0] == computerCh && b[2][1] == 'E') {
                p.i = 2;
                p.j = 1;

            } else if (b[2][1] == b[2][2] && b[2][1] == computerCh && b[2][0] == 'E') {
                p.i = 2;
                p.j = 0;

            } else if (b[0][0] == b[1][0] && b[0][0] == computerCh && b[2][0] == 'E') {
                p.i = 2;
                p.j = 0;

            } else if (b[0][0] == b[2][0] && b[0][0] == computerCh && b[1][0] == 'E') {
                p.i = 1;
                p.j = 0;

            } else if (b[1][0] == b[2][0] && b[1][0] == computerCh && b[0][0] == 'E') {
                p.i = 0;
                p.j = 0;

            } else if (b[0][1] == b[1][1] && b[0][1] == computerCh && b[2][1] == 'E') {
                p.i = 2;
                p.j = 1;

            } else if (b[0][1] == b[2][1] && b[0][1] == computerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;

            } else if (b[1][1] == b[2][1] && b[1][1] == computerCh && b[0][1] == 'E') {
                p.i = 0;
                p.j = 1;

            } else if (b[0][2] == b[1][2] && b[0][2] == computerCh && b[2][2] == 'E') {
                p.i = 2;
                p.j = 2;
            } else if (b[0][2] == b[2][2] && b[0][2] == computerCh && b[1][2] == 'E') {
                p.i = 1;
                p.j = 2;

            } else if (b[1][2] == b[2][2] && b[1][2] == computerCh && b[0][2] == 'E') {
                p.i = 0;
                p.j = 2;

            } else if (b[0][0] == b[1][1] && b[0][0] == computerCh && b[2][2] == 'E') {
                p.i = 2;
                p.j = 2;

            } else if (b[0][0] == b[2][2] && b[0][0] == computerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;

            } else if (b[1][1] == b[2][2] && b[1][1] == computerCh && b[0][0] == 'E') {
                p.i = 0;
                p.j = 0;

            } else if (b[0][2] == b[1][1] && b[0][2] == computerCh && b[2][0] == 'E') {
                p.i = 2;
                p.j = 0;

            } else if (b[0][2] == b[2][0] && b[0][2] == computerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;

            } else if (b[1][1] == b[2][0] && b[1][1] == computerCh && b[0][2] == 'E') {
                p.i = 0;
                p.j = 2;

            } else if (b[0][0] == b[0][1] && b[0][0] == playerCh && b[0][2] == 'E') {
                p.i = 0;
                p.j = 2;

            } else if (b[0][0] == b[0][2] && b[0][0] == playerCh && b[0][1] == 'E') {
                p.i = 0;
                p.j = 1;

            } else if (b[0][1] == b[0][2] && b[0][1] == playerCh && b[0][0] == 'E') {
                p.i = 0;
                p.j = 0;

            } else if (b[1][0] == b[1][1] && b[1][0] == playerCh && b[1][2] == 'E') {
                p.i = 1;
                p.j = 2;

            } else if (b[1][0] == b[1][2] && b[1][0] == playerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;

            } else if (b[1][1] == b[1][2] && b[1][1] == playerCh && b[1][0] == 'E') {
                p.i = 1;
                p.j = 0;

            } else if (b[2][0] == b[2][1] && b[2][0] == playerCh && b[2][2] == 'E') {
                p.i = 2;
                p.j = 2;

            } else if (b[2][0] == b[2][2] && b[2][0] == playerCh && b[2][1] == 'E') {
                p.i = 2;
                p.j = 1;

            } else if (b[2][1] == b[2][2] && b[2][1] == playerCh && b[2][0] == 'E') {
                p.i = 2;
                p.j = 0;

            } else if (b[0][0] == b[1][0] && b[0][0] == playerCh && b[2][0] == 'E') {
                p.i = 2;
                p.j = 0;

            } else if (b[0][0] == b[2][0] && b[0][0] == playerCh && b[1][0] == 'E') {
                p.i = 1;
                p.j = 0;

            } else if (b[1][0] == b[2][0] && b[1][0] == playerCh && b[0][0] == 'E') {
                p.i = 0;
                p.j = 0;

            } else if (b[0][1] == b[1][1] && b[0][1] == playerCh && b[2][1] == 'E') {
                p.i = 2;
                p.j = 1;

            } else if (b[0][1] == b[2][1] && b[0][1] == playerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;

            } else if (b[1][1] == b[2][1] && b[1][1] == playerCh && b[0][1] == 'E') {
                p.i = 0;
                p.j = 1;

            } else if (b[0][2] == b[1][2] && b[0][2] == playerCh && b[2][2] == 'E') {
                p.i = 2;
                p.j = 2;

            } else if (b[0][2] == b[2][2] && b[0][2] == playerCh && b[1][2] == 'E') {
                p.i = 1;
                p.j = 2;

            } else if (b[1][2] == b[2][2] && b[1][2] == playerCh && b[0][2] == 'E') {
                p.i = 0;
                p.j = 2;

            } else if (b[0][0] == b[1][1] && b[0][0] == playerCh && b[2][2] == 'E') {
                p.i = 2;
                p.j = 2;

            } else if (b[0][0] == b[2][2] && b[0][0] == playerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;

            } else if (b[1][1] == b[2][2] && b[1][1] == playerCh && b[0][0] == 'E') {
                p.i = 0;
                p.j = 0;

            } else if (b[0][2] == b[1][1] && b[0][2] == playerCh && b[2][0] == 'E') {
                p.i = 2;
                p.j = 0;

            } else if (b[0][2] == b[2][0] && b[0][2] == playerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;
            } else if (b[1][1] == b[2][0] && b[1][1] == playerCh && b[0][2] == 'E') {
                p.i = 0;
                p.j = 2;
            } else if (b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;
            } else if (nextPlace()) {
                p.i = Nrow;
                p.j = Ncol;
            }
        }
        return p;
    }

    public static Pos computerMedium() {
        Pos p = new Pos(0,0);
        Character playerCh = 'X';
        Character computerCh = player;
        if(player == 'X')
             playerCh= 'O';
        if (isEmpty()) {
            p.i = 1;
            p.j = 1;
        } else {
            if (b[0][0] == b[0][1] && b[0][0] == playerCh && b[0][2] == 'E') {
                p.i = 0;
                p.j = 2;

            } else if (b[0][0] == b[0][2] && b[0][0] == playerCh && b[0][1] == 'E') {
                p.i = 0;
                p.j = 1;

            } else if (b[0][1] == b[0][2] && b[0][1] == playerCh && b[0][0] == 'E') {
                p.i = 0;
                p.j = 0;

            } else if (b[1][0] == b[1][1] && b[1][0] == playerCh && b[1][2] == 'E') {
                p.i = 1;
                p.j = 2;

            } else if (b[1][0] == b[1][2] && b[1][0] == playerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;

            } else if (b[1][1] == b[1][2] && b[1][1] == playerCh && b[1][0] == 'E') {
                p.i = 1;
                p.j = 0;

            } else if (b[2][0] == b[2][1] && b[2][0] == playerCh && b[2][2] == 'E') {
                p.i = 2;
                p.j = 2;

            } else if (b[2][0] == b[2][2] && b[2][0] == playerCh && b[2][1] == 'E') {
                p.i = 2;
                p.j = 1;
            } else if (b[2][1] == b[2][2] && b[2][1] == playerCh && b[2][0] == 'E') {
                p.i = 2;
                p.j = 0;

            } else if (b[0][0] == b[1][0] && b[0][0] == playerCh && b[2][0] == 'E') {
                p.i = 2;
                p.j = 0;
            } else if (b[0][0] == b[2][0] && b[0][0] == playerCh && b[1][0] == 'E') {
                p.i = 1;
                p.j = 0;

            } else if (b[1][0] == b[2][0] && b[1][0] == playerCh && b[0][0] == 'E') {
                p.i = 0;
                p.j = 0;

            } else if (b[0][1] == b[1][1] && b[0][1] == playerCh && b[2][1] == 'E') {
                p.i = 2;
                p.j = 1;
            } else if (b[0][1] == b[2][1] && b[0][1] == playerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;

            } else if (b[1][1] == b[2][1] && b[1][1] == playerCh && b[0][1] == 'E') {
                p.i = 0;
                p.j = 1;

            } else if (b[0][2] == b[1][2] && b[0][2] == playerCh && b[2][2] == 'E') {
                p.i = 2;
                p.j = 2;

            } else if (b[0][2] == b[2][2] && b[0][2] == playerCh && b[1][2] == 'E') {
                p.i = 1;
                p.j = 2;

            } else if (b[1][2] == b[2][2] && b[1][2] == playerCh && b[0][2] == 'E') {
                p.i = 0;
                p.j = 2;

            } else if (b[0][0] == b[1][1] && b[0][0] == playerCh && b[2][2] == 'E') {
                p.i = 2;
                p.j = 2;

            } else if (b[0][0] == b[2][2] && b[0][0] == playerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;

            } else if (b[1][1] == b[2][2] && b[1][1] == playerCh && b[0][0] == 'E') {
                p.i = 0;
                p.j = 0;

            } else if (b[0][2] == b[1][1] && b[0][2] == playerCh && b[2][0] == 'E') {
                p.i = 2;
                p.j = 0;

            } else if (b[0][2] == b[2][0] && b[0][2] == playerCh && b[1][1] == 'E') {
                p.i = 1;
                p.j = 1;

            } else if (b[1][1] == b[2][0] && b[1][1] == playerCh && b[0][2] == 'E') {
                p.i = 0;
                p.j = 2;

            } else if (nextPlace()) {
                p.i = Nrow;
                p.j = Ncol;
            }
        }
        return p;
    }
}

class Pos
{
    public Integer i;
    public Integer j;
    public Pos(Integer x, Integer y)
    {
        i = x;
        j = y;
    }
}
