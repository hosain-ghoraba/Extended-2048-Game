package hahaaa;
import java.awt.*;
import java.util.*;

public class Game
{
    int maxWidth;
    int maxHeight;
    int[][] board;
    boolean[][] fresh;
    boolean hit_2048;
    boolean hit_2048_Once;
    boolean noChange;
    ArrayList<Point> emptyCells;
    
    public Game(final int maxHeight, final int maxWidth) {
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.board = new int[maxHeight][maxWidth];
        this.fresh = new boolean[maxHeight][maxWidth];
        this.emptyCells = new ArrayList<Point>();
        for (int i = 0; i < maxHeight; ++i) {
            for (int j = 0; j < maxWidth; ++j) {
                this.emptyCells.add(new Point(i, j));
            }
        }
        this.putNumber();
        this.putNumber();
    }
    
    public void putNumber() {
        final Point target = this.emptyCells.get(new Random().nextInt(this.emptyCells.size()));
        this.board[target.x][target.y] = (new Random().nextBoolean() ? 2 : 4);
        this.emptyCells.remove(target);
    }
    
    public void moveVertical(final Direction d) {
        int init_firstOccupiedcounter = 0;
        int firstOccupiedcounterInc = 0;
        int init_innerLoopVariable = 0;
        int innerLoopCounterInc = 0;
        int cornerValueX = 0;
        int puttingBesideInc = 0;
        switch (d) {
            case UP: {
                init_firstOccupiedcounter = this.maxHeight - 1;
                firstOccupiedcounterInc = -1;
                init_innerLoopVariable = 0;
                innerLoopCounterInc = 1;
                cornerValueX = 0;
                puttingBesideInc = 1;
                break;
            }
            case DOWN: {
                init_firstOccupiedcounter = 0;
                firstOccupiedcounterInc = 1;
                init_innerLoopVariable = this.maxHeight - 1;
                innerLoopCounterInc = -1;
                cornerValueX = this.maxHeight - 1;
                puttingBesideInc = -1;
                break;
            }
        }
        this.noChange = true;
        for (int y = 0; y < this.maxWidth; ++y) {
            Point firstOccupied = null;
            int firstOccupiedcounter = init_firstOccupiedcounter;
            while (true) {
                final boolean firstOccupiedCondition = (d == Direction.UP) ? (firstOccupiedcounter >= 0) : (firstOccupiedcounter < this.maxHeight);
                if (!firstOccupiedCondition) {
                    break;
                }
                if (this.board[firstOccupiedcounter][y] != 0) {
                    firstOccupied = new Point(firstOccupiedcounter, y);
                    break;
                }
                firstOccupiedcounter += firstOccupiedcounterInc;
            }
            Point lastOccupied = null;
            int innerLoopVariable = init_innerLoopVariable;
            while (true) {
                final boolean innerLoopCondition = (d == Direction.UP) ? (innerLoopVariable < this.maxHeight) : (innerLoopVariable >= 0);
                if (!innerLoopCondition) {
                    break;
                }
                final Point currentLocation = new Point(innerLoopVariable, y);
                final int currentValue = this.board[innerLoopVariable][y];
                if (currentValue != 0) {
                    this.board[currentLocation.x][currentLocation.y] = 0;
                    this.emptyCells.add(currentLocation);
                    if (lastOccupied == null) {
                        this.board[cornerValueX][y] = currentValue;
                        lastOccupied = new Point(cornerValueX, y);
                        removePoint(lastOccupied, this.emptyCells);
                    }
                    else if (this.board[lastOccupied.x][lastOccupied.y] == currentValue && !this.fresh[lastOccupied.x][lastOccupied.y]) {
                        final int[] array = this.board[lastOccupied.x];
                        final int y2 = lastOccupied.y;
                        array[y2] *= 2;
                        this.fresh[lastOccupied.x][lastOccupied.y] = true;
                        if (this.board[lastOccupied.x][lastOccupied.y] == 2048) {
                            this.hit_2048 = true;
                        }
                    }
                    else {
                        this.board[lastOccupied.x + puttingBesideInc][y] = currentValue;
                        lastOccupied = new Point(lastOccupied.x + puttingBesideInc, y);
                        removePoint(new Point(lastOccupied.x, y), this.emptyCells);
                    }
                }
                innerLoopVariable += innerLoopCounterInc;
            }
            if (firstOccupied != null && !firstOccupied.equals(lastOccupied)) {
                this.noChange &= false;
            }
        }
        this.fresh = new boolean[this.maxHeight][this.maxWidth];
    }
    
    public void moveHorizontal(final Direction d) {
        int firstOccupiedcounter = 0;
        int init_firstOccupiedcounter = 0;
        boolean firstOccupiedCondition = false;
        int firstOccupiedcounterInc = 0;
        int innerLoopVariable = 0;
        int init_innerLoopVariable = 0;
        boolean innerLoopCondition = false;
        int innerLoopCounterInc = 0;
        int cornerValueY = 0;
        int puttingBesideInc = 0;
        switch (d) {
            case RIGHT: {
                init_firstOccupiedcounter = 0;
                firstOccupiedcounterInc = 1;
                init_innerLoopVariable = this.maxWidth - 1;
                innerLoopCounterInc = -1;
                cornerValueY = this.maxWidth - 1;
                puttingBesideInc = -1;
                break;
            }
            case LEFT: {
                init_firstOccupiedcounter = this.maxWidth - 1;
                firstOccupiedcounterInc = -1;
                init_innerLoopVariable = 0;
                innerLoopCounterInc = 1;
                cornerValueY = 0;
                puttingBesideInc = 1;
                break;
            }
        }
        this.noChange = true;
        for (int x = 0; x < this.maxHeight; ++x) {
            Point firstOccupied = null;
            firstOccupiedcounter = init_firstOccupiedcounter;
            while (true) {
                firstOccupiedCondition = ((d == Direction.RIGHT) ? (firstOccupiedcounter < this.maxWidth) : (firstOccupiedcounter >= 0));
                if (!firstOccupiedCondition) {
                    break;
                }
                if (this.board[x][firstOccupiedcounter] != 0) {
                    firstOccupied = new Point(x, firstOccupiedcounter);
                    break;
                }
                firstOccupiedcounter += firstOccupiedcounterInc;
            }
            Point lastOccupied = null;
            innerLoopVariable = init_innerLoopVariable;
            while (true) {
                innerLoopCondition = ((d == Direction.RIGHT) ? (innerLoopVariable >= 0) : (innerLoopVariable < this.maxWidth));
                if (!innerLoopCondition) {
                    break;
                }
                final Point currentLocation = new Point(x, innerLoopVariable);
                final int currentValue = this.board[x][innerLoopVariable];
                if (currentValue != 0) {
                    this.board[currentLocation.x][currentLocation.y] = 0;
                    this.emptyCells.add(currentLocation);
                    if (lastOccupied == null) {
                        this.board[x][cornerValueY] = currentValue;
                        lastOccupied = new Point(x, cornerValueY);
                        removePoint(lastOccupied, this.emptyCells);
                    }
                    else if (this.board[lastOccupied.x][lastOccupied.y] == currentValue && !this.fresh[lastOccupied.x][lastOccupied.y]) {
                        final int[] array = this.board[lastOccupied.x];
                        final int y = lastOccupied.y;
                        array[y] *= 2;
                        this.fresh[lastOccupied.x][lastOccupied.y] = true;
                        if (this.board[lastOccupied.x][lastOccupied.y] == 2048) {
                            this.hit_2048 = true;
                        }
                    }
                    else {
                        this.board[x][lastOccupied.y + puttingBesideInc] = currentValue;
                        lastOccupied = new Point(x, lastOccupied.y + puttingBesideInc);
                        removePoint(new Point(x, lastOccupied.y), this.emptyCells);
                    }
                }
                innerLoopVariable += innerLoopCounterInc;
            }
            if (firstOccupied != null && !firstOccupied.equals(lastOccupied)) {
                this.noChange &= false;
            }
        }
        this.fresh = new boolean[this.maxHeight][this.maxWidth];
    }
    
    public boolean GameOver() {
        if (this.emptyCells.size() != 0) {
            return false;
        }
        for (int i = 0; i < this.maxHeight - 1; ++i) {
            for (int j = 0; j < this.maxWidth; ++j) {
                if (this.board[i][j] == this.board[i + 1][j]) {
                    return false;
                }
            }
        }
        for (int k = 0; k < this.maxWidth - 1; ++k) {
            for (int l = 0; l < this.maxHeight; ++l) {
                if (this.board[l][k] == this.board[l][k + 1]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void removePoint(final Point point, final ArrayList<Point> list) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).equals(point)) {
                list.remove(list.get(i));
                return;
            }
        }
    }
    
    public void showBoard() {
        for (int i = 0; i < this.maxHeight; ++i) {
            for (int j = 0; j < this.maxWidth; ++j) {
                System.out.print(String.valueOf(this.board[i][j]) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public static void main(final String[] args) {
        new Welcome();
    }
}
