import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Othello extends JFrame implements ActionListener {
    JButton[][] button = new JButton[8][8];
    boolean blacksTurn = true;
    static final int NORTH = 1;
    static final int NE = 2;
    static final int EAST = 3;
    static final int SE = 4;
    static final int SOUTH = 5;
    static final int SW = 6;
    static final int WEST = 7;
    static final int NW = 8;
    JTextField B;
    JTextField W;

    public static void main(String[] args) {
        new Othello();
    }

    public Othello() {
        super("Othello ");
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        panel.setSize(540, 540);
        this.setResizable(true);
        this.setDefaultCloseOperation(3);
        GridLayout grid = new GridLayout(8, 8);
        panel.setLayout(grid);

        for(int i = 0; i <= 7; ++i) {
            for(int j = 0; j <= 7; ++j) {
                this.button[i][j] = new JButton();
                this.button[i][j].addActionListener(this);
                panel.add(this.button[i][j]);
            }
        }

        this.B = new JTextField();
        this.B.setSize(100, 20);
        this.B.setLocation(40, 570);
        this.B.setText("2 BLACK");
        this.W = new JTextField();
        this.W.setSize(100, 20);
        this.W.setLocation(400, 570);
        this.W.setText("2 WHITE");
        this.add(panel);
        this.add(this.B);
        this.add(this.W);
        this.setSize(555, 650);
        this.add(panel2);
        this.setVisible(true);
        this.startGame();
    }

    public void startGame() {
        this.button[3][3].setBackground(Color.white);
        this.button[3][4].setBackground(Color.black);
        this.button[4][3].setBackground(Color.black);
        this.button[4][4].setBackground(Color.white);
        this.blacksTurn = true;
        this.markValidMoves();
    }

    public int markValidMoves() {
        int retVal = 0;

        for(int i = 0; i <= 7; ++i) {
            for(int j = 0; j <= 7; ++j) {
                if (this.isValidCell(i, j)) {
                    this.button[i][j].setBackground(this.blacksTurn ? Color.blue : Color.red);
                    ++retVal;
                } else if (this.button[i][j].getBackground() != Color.black && this.button[i][j].getBackground() != Color.white) {
                    this.button[i][j].setBackground(Color.green);
                }
            }
        }

        return retVal;
    }

    public boolean isValidCell(int i, int j) {
        Color backgroundColor = this.button[i][j].getBackground();
        if (backgroundColor != Color.black && backgroundColor != Color.white) {
            if (this.checkValidityInDirection(1, i, j)) {
                return true;
            } else if (this.checkValidityInDirection(2, i, j)) {
                return true;
            } else if (this.checkValidityInDirection(3, i, j)) {
                return true;
            } else if (this.checkValidityInDirection(4, i, j)) {
                return true;
            } else if (this.checkValidityInDirection(5, i, j)) {
                return true;
            } else if (this.checkValidityInDirection(6, i, j)) {
                return true;
            } else if (this.checkValidityInDirection(7, i, j)) {
                return true;
            } else {
                return this.checkValidityInDirection(8, i, j);
            }
        } else {
            return false;
        }
    }

    public boolean checkValidityInDirection(int direction, int i, int j) {
        boolean atleastOneFound = false;
        byte incrI;
        byte incrJ;
        switch (direction) {
            case 1:
                incrI = -1;
                incrJ = 0;
                break;
            case 2:
                incrI = -1;
                incrJ = 1;
                break;
            case 3:
                incrI = 0;
                incrJ = 1;
                break;
            case 4:
                incrI = 1;
                incrJ = 1;
                break;
            case 5:
                incrI = 1;
                incrJ = 0;
                break;
            case 6:
                incrI = 1;
                incrJ = -1;
                break;
            case 7:
                incrI = 0;
                incrJ = -1;
                break;
            case 8:
                incrI = -1;
                incrJ = -1;
                break;
            default:
                incrI = 0;
                incrJ = 0;
        }

        int initI = i + incrI;

        int initJ;
        for(initJ = j + incrJ; initI >= 0 && initI <= 7 && initJ >= 0 && initJ <= 7 && this.button[initI][initJ].getBackground() == (this.blacksTurn ? Color.white : Color.black); atleastOneFound = true) {
            initI += incrI;
            initJ += incrJ;
        }

        return initI >= 0 && initI <= 7 && initJ >= 0 && initJ <= 7 && atleastOneFound && this.button[initI][initJ].getBackground() == (this.blacksTurn ? Color.black : Color.white);
    }

    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();
        if (source.getBackground() == (this.blacksTurn ? Color.blue : Color.red)) {
            int foundI = 0;
            int foundJ = 0;

            int i;
            int j;
            for(i = 0; i <= 7; ++i) {
                for(j = 0; j <= 7; ++j) {
                    if (this.button[i][j] == source) {
                        foundI = i;
                        foundJ = j;
                        break;
                    }
                }
            }

            this.makeMove(foundI, foundJ);
            i = 0;
            j = 0;

            int n;
            for(n = 0; n <= 7; ++n) {
                for(int m = 0; m <= 7; ++m) {
                    if (this.button[n][m].getBackground() == Color.black) {
                        ++i;
                    } else if (this.button[n][m].getBackground() == Color.white) {
                        ++j;
                    }
                }
            }

            this.B.setText(String.valueOf(i) + " " + "BLACK");
            this.W.setText(String.valueOf(j) + " " + "WHITE");
            this.blacksTurn = !this.blacksTurn;
            n = this.markValidMoves();
            if (n == 0) {
                this.blacksTurn = !this.blacksTurn;
                n = this.markValidMoves();
                if (n == 0) {
                    this.declareWinner();
                    this.dispose();
                }
            }
        }

    }

    public void makeMove(int i, int j) {
        this.rePaintInDirection(1, i, j);
        this.rePaintInDirection(2, i, j);
        this.rePaintInDirection(3, i, j);
        this.rePaintInDirection(4, i, j);
        this.rePaintInDirection(5, i, j);
        this.rePaintInDirection(6, i, j);
        this.rePaintInDirection(7, i, j);
        this.rePaintInDirection(8, i, j);
        this.button[i][j].setBackground(this.blacksTurn ? Color.black : Color.white);
    }

    public void rePaintInDirection(int direction, int i, int j) {
        boolean atleastOneFound = false;
        byte incrI;
        byte incrJ;
        switch (direction) {
            case 1:
                incrI = -1;
                incrJ = 0;
                break;
            case 2:
                incrI = -1;
                incrJ = 1;
                break;
            case 3:
                incrI = 0;
                incrJ = 1;
                break;
            case 4:
                incrI = 1;
                incrJ = 1;
                break;
            case 5:
                incrI = 1;
                incrJ = 0;
                break;
            case 6:
                incrI = 1;
                incrJ = -1;
                break;
            case 7:
                incrI = 0;
                incrJ = -1;
                break;
            case 8:
                incrI = -1;
                incrJ = -1;
                break;
            default:
                incrI = 0;
                incrJ = 0;
        }

        int initI = i + incrI;

        int initJ;
        for(initJ = j + incrJ; initI >= 0 && initI <= 7 && initJ >= 0 && initJ <= 7 && this.button[initI][initJ].getBackground() == (this.blacksTurn ? Color.white : Color.black); atleastOneFound = true) {
            initI += incrI;
            initJ += incrJ;
        }

        if (initI >= 0 && initI <= 7 && initJ >= 0 && initJ <= 7 && atleastOneFound && this.button[initI][initJ].getBackground() == (this.blacksTurn ? Color.black : Color.white)) {
            while(initI != i || initJ != j) {
                initI -= incrI;
                initJ -= incrJ;
                this.button[initI][initJ].setBackground(this.blacksTurn ? Color.black : Color.white);
            }
        }

    }

    public void declareWinner() {
        int blacks1 = 0;
        int whites1 = 0;

        for(int i = 0; i <= 7; ++i) {
            for(int j = 0; j <= 7; ++j) {
                if (this.button[i][j].getBackground() == Color.black) {
                    ++blacks1;
                } else if (this.button[i][j].getBackground() == Color.white) {
                    ++whites1;
                }
            }
        }

        if (blacks1 > whites1) {
            JOptionPane.showMessageDialog((Component)null, "Black wins " + blacks1);
        } else {
            JOptionPane.showMessageDialog((Component)null, "White wins" + whites1);
        }

    }
}
