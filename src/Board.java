import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class Board extends JFrame {
    private static final int MineRows = 10;
    private static final int MineColumns = 10;

    JPanel jPanel = new JPanel(new GridLayout(10,10,5,5));
    public static final Cell[][] cells = new Cell[MineRows][MineColumns];
    public static final ImageIcon[] Resources = new ImageIcon[10];
    public Board(){
        this.setContentPane(jPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GetImages();

        jPanel.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));
        initCells();
        SetBombs();
        CheckCellBombs();
        this.setSize(500,500);
        this.setResizable(false);
        this.setVisible(true);
    }
    private void GetImages(){
        for (int i = 0; i < 9; i++) {
            URL systemResource = ClassLoader.getSystemResource("Resources/"+i+".png");
            ImageIcon icon = new ImageIcon(systemResource);

            Resources[i] = Cell.resizeIcon(icon,40,30);
        }
    }

    private void initCells() {
        URL systemResource = ClassLoader.getSystemResource("Resources/facingDown.png");
        ImageIcon icon = new ImageIcon(systemResource);
        Icon resizedIcon = Cell.resizeIcon(icon,50,30);
        for (int i = 0 ; i<10;i++){
            for (int j = 0 ; j<10;j++){
                cells[i][j] = new Cell(i,j,resizedIcon);
                this.add(cells[i][j]);
            }
        }

    }
    public void CheckCellBombs(){
        for (int i = 0 ; i<10;i++){
            for (int j = 0 ; j<10;j++){
                CheckCell(i,j);

            }
        }
    }
    public void CheckCell(int row,int column){
        Cell cell = cells[row][column];
        if(cell.GetState().equals(CellState.Bomb)) return;

        for (int i = row-1;i<row+2;i++){
            for (int j = column-1;j<column+2;j++){
                if ((i < 0 || i >= 10) || (j < 0 || j >= 10)) continue;
                if (i == row && column == j) continue;
                CellState state = cells[i][j].GetState();
                if (state.equals(CellState.Bomb)){
                    cell.HowManyBombsAround++;
            }

            }
        }

        if (cell.HowManyBombsAround>0&& !cell.GetState().equals(CellState.Bomb)){
            cell.SetState(CellState.NearBomb);
        }
    }
    private void SetBombs(){
        Random random = new Random();
        for (int i = 0; i<20;i++){
            int row = random.nextInt(0,10);
            int column = random.nextInt(0,10);
            cells[row][column].SetState(CellState.Bomb);


        }

    }

}
