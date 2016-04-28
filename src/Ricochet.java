import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.awt.Point;

public class Ricochet {

    public static void main(String[] args) throws IOException {
        Board board = new Ricochet().loadBoard();

        // Debug code - uncomment to test that robot 0 is moved as expected
//        System.out.print(board);
//        System.out.println("Possible endpoints for robot 0: " + board.possibleEndpointsForRobot(0));
//        board.moveRobot(0, Direction.Left);
//        System.out.print(board);
        board.computeSolution();
    }

    public class Board {
        private static final char GOAL_CHAR = 'G';
        private static final char WALL_CHAR = '#';
        private static final char EMPTY_CHAR = ' ';

        private char[][] board;
        private int size;
        private Point[] robots;
        private Point goal;

        public Board(char[][] board, Point[] robots, Point goal) {
            this.board = board;
            this.size = board.length;
            this.robots = robots;
            this.goal = goal;
        }

        public void computeSolution() {
            // TODO: Implement really clever stuff here!


        }
        /*
    	public int[][] distanceMap(Point start) {	
    		int[][] distMap = new int[size][size];
    		
    		for (int[] row: distMap){	// Fylder hele array'et med -1
    			Arrays.fill(row, -1);
    		}
    		
    		if(board[start.x][start.y] != '#'){	// Checker om startpositionen er en væg
    			distMap[start.x][start.y] = 0;
    		} else {
    			distMap[start.x][start.y] = -1;
    		}
    		
    		int currentPosContent = 0;
    		int nextStepNumber = 0;
    		int stepsTaken = 0;
    		
    		while(nextStepNumber <= stepsTaken){
    			
    			for(int i = 0; i <= size-1; i++){							
    				for(int j = 0; j <= size-1; j++){
    					//For-løkke der summer igennem hvert tegn i vores distancemap
    						
    					Point currentPos = new Point(i,j); 	//Den nuværende position
    					currentPosContent = distMap[i][j];			//Den nuværende position's indhold
    					
    					if(currentPosContent == nextStepNumber){
    						for(int k=0; k<=3; k++){
    							// Checker om vi må incremente på naboværdierne rundt om den "nuværende" position
    							
    							if(checkValue(getNeighbors(currentPos)[k]) && distMap[getNeighbors(currentPos)[k].x][getNeighbors(currentPos)[k].y] == -1){
    								// Checker om det er ok at ændre til næste step, og om vi allerede har været her før.
    								
    								distMap[getNeighbors(currentPos)[k].x][getNeighbors(currentPos)[k].y] = nextStepNumber + 1;
    								stepsTaken++;
    								// Den endelige increment af naboen til current position
    							}		
    						}
    					}
    				}
    			}
    			nextStepNumber++;
    			// Vi incrementer til næste "skridt"
    		}
    		return distMap;	
    	}
    	
    	public boolean checkValue(Point inputValue){
    		if(inputValue.x >= size || inputValue.y >= size || inputValue.x < 0 || inputValue.y < 0){
    			// checker om inputvalue er uden for vores map (rows x cols)
    			return false;
    		}
    		
    		if(board[inputValue.x][inputValue.y] == ' ' || board[inputValue.x][inputValue.y] == 'G'){
    			// checker i mapData om der er en ledig plads (ikke en væg)
    			return true;
    		} else {
    			return false;
    		}
    	}
    	
    	public Point[] getNeighbors(Point currentPos) {
    		Point[] neighbors = { new Point(currentPos.x-1, currentPos.y),
    								 new Point(currentPos.x+1, currentPos.y),
    								 new Point(currentPos.x, currentPos.y-1),
    								 new Point(currentPos.x, currentPos.y+1),
    							   };
    		return neighbors;
    	}
    	*/

        public Endpoints possibleEndpointsForRobot(int robot) {
            assert robot < robots.length;

            Point up = pointAfterMovingRobot(robot, Direction.Up);
            Point down = pointAfterMovingRobot(robot, Direction.Down);
            Point left = pointAfterMovingRobot(robot, Direction.Left);
            Point right = pointAfterMovingRobot(robot, Direction.Right);

            return new Endpoints(up, down, left, right);
        }

        public Point pointAfterMovingRobot(int robot, Direction m) {
            assert robot < robots.length;

            Point pos = new Point(robots[robot]);
            int drow = 0, dcol = 0;

            if (m == Direction.Up) {
                drow = -1;
            } else if (m == Direction.Down) {
                drow = 1;
            }

            if (m == Direction.Left) {
                dcol = -1;
            } else if (m == Direction.Right) {
                dcol = 1;
            }

            while (withinBoard(pos.x+drow, pos.y+dcol) &&
                   (board[pos.x+drow][pos.y+dcol] == EMPTY_CHAR
                    || board[pos.x+drow][pos.y+dcol] == GOAL_CHAR)) {
                pos.translate(drow, dcol);
            }

            return pos;
        }

        public void moveRobot(int robot, Direction d) {
            assert robot < robots.length;

            Point from = robots[robot];
            Point to = pointAfterMovingRobot(robot, d);
            board[from.x][from.y] = EMPTY_CHAR;
            robots[robot] = to;
            board[to.x][to.y] = (char) ('0' + robot);
        }

        private boolean withinBoard(int x, int y) {
            return x >= 0 && x < size && y >= 0 && y < size;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(size * size);

            for (int i = 0; i < board.length; i++) {
                sb.append(board[i]);
                sb.append(System.getProperty("line.separator"));
            }

            return sb.toString();
        }
    }

    public enum Direction {
        Up, Down, Left, Right;

        public String toString() {
            switch (this) {
            case Up: return "U";
            case Down: return "D";
            case Left: return "L";
            case Right: return "R";
            default: throw new AssertionError();
            }
        }
    }

    public class Endpoints {
        public final Point up, down, left, right;

        public Endpoints(Point up, Point down, Point left, Point right) {
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return "Endpoints (Up: " + up + ", Down: " + down + ", Left: " + left + ", Right: " + right + ")";
        }
    }


    private Board loadBoard() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int size = Integer.parseInt(in.readLine());
        int numberOfRobots = Integer.parseInt(in.readLine());

        char[][] b = new char[size][size];
        Point[] robots = new Point[numberOfRobots];

        Point goal = null;

        for (int i = 0; i < size; i++) {
            char[] line = in.readLine().toCharArray();
            for (int j = 0; j < line.length; j++) {
                if (line[j] == Board.GOAL_CHAR) {
                    goal = new Point(i, j);
                } else if (Character.isDigit(line[j])) {
                    int number = Character.getNumericValue(line[j]);
                    robots[number] = new Point(i, j);
                } else if (line[j] != Board.EMPTY_CHAR &&
                           line[j] != Board.WALL_CHAR) {
                    System.err.println("Error: Unknown character '" + line[j] + "' on line " + (i+1) + ", column " + (j+1));
                }

                b[i][j] = line[j];
            }
        }

        if (goal == null) {
            System.err.println("Error: No goal found on board");
        }

        return new Board(b, robots, goal);
    }
}
