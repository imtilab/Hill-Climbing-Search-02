
import java.util.Arrays;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mti
 */

//heuristic function f(h) is highest distance tiles from its destination position
public class Eight_Puzzle {

    //solution state of the 8-puzzle game
    int goal_state[][] = {
        {1, 2, 3},
        {8, 0, 4},
        {7, 6, 5}
    };

    //problem board of 8-puzzle game
    int game_board[][] = {
        {2, 6, 3},
        {1, 0, 4},
        {8, 7, 5}
    };
    
    //initial empty tile position
    int emptyTile_row = 0;
    int emptyTile_col = 0;
    int stepCounter = 0;

    //int preState[][];
    int distance[] = new int[8];//distance sequence 1,2,3,4,5,6,7,8

    //initializations
    public void initializations() {

        locateEmptyTilePosition();//set empty tile position
        setDistanceToGoal();//set initial tile distance to goal

        System.out.println("=========================================");
        printState(game_board, "initial problem state");
        System.out.println("initial empty tile position: " + emptyTile_row + ", " + emptyTile_col);
        System.out.println("distances: " + Arrays.toString(distance));
        System.out.println("=========================================");

        //start hill climbing search
        hill_climbing_search();
    }

    //start hill climbing search for 8-puzzle problem
    public void hill_climbing_search() {

        while (true) {
            System.out.println(">========================================<");
            System.out.println("cost/steps: " + (++stepCounter));
            System.out.println("-------------");

            if (isAllDistancesZero()) {//Already solved puzzle
                System.out.println("-------------------------");
                System.out.println("8-Puzzle has been solved!");
                System.out.println("-------------------------");
                System.out.println("-------------------------------------");
                break;
            }

            Node highest_distance_tile_node = getHighest_distance_tile_node();//from all distance
//            if (preState != null) {
//                highest_distance_tile_node = Priority.getParentRemovedNodeArray(preState);//after parent remove
//            }
            highest_distance_tile_node = Priority.getBestTileNode();//ensure distance reducing

            //print all tile values
            System.out.print("all sorted tile distance of current state: ");
            for (int i = 0; i < Priority.neighbors_nodeArray.length; i++) {
                System.out.print(Priority.neighbors_nodeArray[i].distTile + " ");
            }
            System.out.println();

            Priority.preState = game_board;//change pre state
            game_board = highest_distance_tile_node.state;//lowest tile's state is the current state
            setDistanceToGoal();//update distance array 
            locateEmptyTilePosition();//locate empty tile for updated state

            System.out.println("distances: " + Arrays.toString(distance));

            if (isAllDistancesZero()) {
                printState(highest_distance_tile_node.state, "-------final state");
                System.out.println("-------------------------");
                System.out.println("8-Puzzle has been solved!");
                System.out.println("-------------------------");
                System.out.println("-------------------------------------");
                break;
            }

            System.out.println("highest tile: " + highest_distance_tile_node.distTile);
            printState(highest_distance_tile_node.state, "-------new state");
            System.out.println("empty tile position: " + emptyTile_row + ", " + emptyTile_col);
        }
    }

    private Node getHighest_distance_tile_node() {

        if (emptyTile_row == 0 && emptyTile_col == 0) {//0,0 position is empty tile
            //System.out.println("Empty 0,0");
            Node tile_array[] = {get_distance_down(), get_distance_right()};
            Node highest_distTile_node = Priority.sort(tile_array);
            return highest_distTile_node;

        } else if (emptyTile_row == 0 && emptyTile_col == 1) {//0,1 position is empty tile
            //System.out.println("Empty 0,1");
            Node tile_array[] = {get_distance_left(), get_distance_down(), get_distance_right()};
            Node highest_distTile_node = Priority.sort(tile_array);
            return highest_distTile_node;

        } else if (emptyTile_row == 0 && emptyTile_col == 2) {//0,2 position is empty tile
            //System.out.println("Empty 0,2");
            Node tile_array[] = {get_distance_left(), get_distance_down()};
            Node highest_distTile_node = Priority.sort(tile_array);
            return highest_distTile_node;

        } else if (emptyTile_row == 1 && emptyTile_col == 0) {//1,0 position is empty tile
            //System.out.println("Empty 1,0");
            Node tile_array[] = {get_distance_down(), get_distance_right(), get_distance_up()};
            Node highest_distTile_node = Priority.sort(tile_array);
            return highest_distTile_node;

        } else if (emptyTile_row == 1 && emptyTile_col == 1) {//1,1 position is empty tile
            //System.out.println("Empty 1,1");
            Node tile_array[] = {get_distance_left(), get_distance_down(), get_distance_right(), get_distance_up()};
            Node highest_distTile_node = Priority.sort(tile_array);
            return highest_distTile_node;

        } else if (emptyTile_row == 1 && emptyTile_col == 2) {//1,2 position is empty tile
            //System.out.println("Empty 1,2");
            Node tile_array[] = {get_distance_left(), get_distance_down(), get_distance_up()};
            Node highest_distTile_node = Priority.sort(tile_array);
            return highest_distTile_node;

        } else if (emptyTile_row == 2 && emptyTile_col == 0) {//2,0 position is empty tile
            //System.out.println("Empty 2,0");
            Node tile_array[] = {get_distance_right(), get_distance_up()};
            Node highest_distTile_node = Priority.sort(tile_array);
            return highest_distTile_node;

        } else if (emptyTile_row == 2 && emptyTile_col == 1) {//2,1 position is empty tile
            //System.out.println("Empty 2,1");
            Node tile_array[] = {get_distance_left(), get_distance_right(), get_distance_up()};
            Node highest_distTile_node = Priority.sort(tile_array);
            return highest_distTile_node;

        } else if (emptyTile_row == 2 && emptyTile_col == 2) {//2,2 position is empty tile
            //System.out.println("Empty 2,2");
            Node tile_array[] = {get_distance_left(), get_distance_up()};
            Node highest_distTile_node = Priority.sort(tile_array);
            return highest_distTile_node;

        }
        return null;
    }

    //return number of misplaced tiles for left state
    private Node get_distance_left() {

        int tile = game_board[emptyTile_row][emptyTile_col - 1];

        int left_state[][] = new int[game_board.length][game_board[0].length];
        for (int i = 0; i < game_board.length; i++) {
            for (int j = 0; j < game_board[0].length; j++) {

                if (i == emptyTile_row && j == emptyTile_col) {//empty tile, swap left
                    left_state[i][j] = game_board[i][j - 1];
                    left_state[i][j - 1] = game_board[i][j];
                } else {//normal copy
                    left_state[i][j] = game_board[i][j];
                }
            }
        }
        // printState(left_state, "left state");//print left state
        Node node = new Node(tile, distance[tile - 1], left_state);
        return node;
    }

    //return number of misplaced tiles for right state
    private Node get_distance_right() {

        int tile = game_board[emptyTile_row][emptyTile_col + 1];

        int right_state[][] = new int[game_board.length][game_board[0].length];
        for (int i = 0; i < game_board.length; i++) {
            for (int j = 0; j < game_board[0].length; j++) {

                if (i == emptyTile_row && j == emptyTile_col) {//empty tile, swap right
                    right_state[i][j] = game_board[i][j + 1];
                    right_state[i][j + 1] = game_board[i][j];
                    j++;//as j++ position already copied/updated 
                } else {//normal copy
                    right_state[i][j] = game_board[i][j];
                }
            }
        }

        // printState(right_state, "right state");//print right state
        Node node = new Node(tile, distance[tile - 1], right_state);
        return node;
    }

    //return number of misplaced tiles for up state
    private Node get_distance_up() {

        int tile = game_board[emptyTile_row - 1][emptyTile_col];
        int up_state[][] = new int[game_board.length][game_board[0].length];
        for (int i = 0; i < game_board.length; i++) {
            for (int j = 0; j < game_board[0].length; j++) {

                if (i == emptyTile_row && j == emptyTile_col) {//empty tile, swap up
                    up_state[i][j] = game_board[i - 1][j];
                    up_state[i - 1][j] = game_board[i][j];
                } else {//normal copy
                    up_state[i][j] = game_board[i][j];
                }
            }
        }
        // printState(up_state, "up state");//print up state
        Node node = new Node(tile, distance[tile - 1], up_state);
        return node;
    }

    //return number of misplaced tiles for down state
    private Node get_distance_down() {

        int tile = game_board[emptyTile_row + 1][emptyTile_col];
        int down_state[][] = new int[game_board.length][game_board[0].length];
        for (int i = 0; i < game_board.length; i++) {
            for (int j = 0; j < game_board[0].length; j++) {

                if ((i - 1) == emptyTile_row && j == emptyTile_col) {//down pos of empty tile, swap down
                    down_state[i][j] = game_board[i - 1][j];
                    down_state[i - 1][j] = game_board[i][j];
                } else {//normal copy
                    down_state[i][j] = game_board[i][j];
                }
            }
        }
        // printState(down_state, "down state");//print down state
        Node node = new Node(tile, distance[tile - 1], down_state);
        return node;
    }

    //takes state and a tile number 
    //calculate distance from current to goal and return distance
    public int getDistanceToReachGoal(int state[][], int tile) {
        int position[] = getTilePosition(state, tile);

        if (tile == 1) {//tile 1
            if (position[0] == 0 && position[0] == 0) {
                return 0;//distance 0 in (0,0)
            } else if ((position[0] == 0 && position[1] == 1)
                    || (position[0] == 1 && position[1] == 0)) {
                return 1;//distance 1 in (0,1), (1,0)
            } else if ((position[0] == 0 && position[1] == 2)
                    || (position[0] == 2 && position[1] == 0)
                    || (position[0] == 1 && position[1] == 1)) {
                return 2;//distance 2 in (0,2), (2,0), (1,1)
            } else if ((position[0] == 1 && position[1] == 2)
                    || (position[0] == 2 && position[1] == 1)) {
                return 3;//distance 3 in (1,2),(2,1)
            } else if (position[0] == 2 && position[1] == 2) {
                return 4;//distance 4 in (2,2)
            }

        } else if (tile == 2) {//tile 2
            if (Arrays.equals(position, new int[]{0, 1})) {
                return 0;//distance 0 in (0,1)
            } else if (Arrays.equals(position, new int[]{0, 0})
                    || Arrays.equals(position, new int[]{0, 2})
                    || Arrays.equals(position, new int[]{1, 1})) {
                return 1;//distance 1 in (0,0), (0,2), (1,1)
            } else if (Arrays.equals(position, new int[]{1, 0})
                    || Arrays.equals(position, new int[]{1, 2})
                    || Arrays.equals(position, new int[]{2, 1})) {
                return 2;//distance 2 in (1,0), (1,2), (2,1)
            } else if (Arrays.equals(position, new int[]{2, 0})
                    || Arrays.equals(position, new int[]{2, 2})) {
                return 3;//distance 3 in (2,0),(2,2)
            }

        } else if (tile == 3) {//tile 3
            if (Arrays.equals(position, new int[]{0, 2})) {
                return 0;//distance 0 in (0,2)
            } else if (Arrays.equals(position, new int[]{0, 1})
                    || Arrays.equals(position, new int[]{1, 2})) {
                return 1;//distance 1 in (0,1), (1,2)
            } else if (Arrays.equals(position, new int[]{0, 0})
                    || Arrays.equals(position, new int[]{1, 1})
                    || Arrays.equals(position, new int[]{2, 2})) {
                return 2;//distance 2 in (0,0), (1,1), (2,2)
            } else if (Arrays.equals(position, new int[]{1, 0})
                    || Arrays.equals(position, new int[]{2, 1})) {
                return 3;//distance 3 in (1,0),(2,1)
            } else if (Arrays.equals(position, new int[]{2, 0})) {
                return 4;//distance 4 in(2,0)
            }

        } else if (tile == 4) {//tile 4
            if (Arrays.equals(position, new int[]{1, 2})) {
                return 0;//distance 0 in (0,1)
            } else if (Arrays.equals(position, new int[]{0, 2})
                    || Arrays.equals(position, new int[]{2, 2})
                    || Arrays.equals(position, new int[]{1, 1})) {
                return 1;//distance 1 in (0,0), (0,2), (1,1)
            } else if (Arrays.equals(position, new int[]{0, 1})
                    || Arrays.equals(position, new int[]{1, 0})
                    || Arrays.equals(position, new int[]{2, 1})) {
                return 2;//distance 2 in (1,0), (1,2), (2,1)
            } else if (Arrays.equals(position, new int[]{0, 0})
                    || Arrays.equals(position, new int[]{2, 0})) {
                return 3;//distance 3 in (2,0),(2,2)
            }

        } else if (tile == 5) {//tile 5
            if (Arrays.equals(position, new int[]{2, 2})) {
                return 0;//distance 0 in (0,2)
            } else if (Arrays.equals(position, new int[]{1, 2})
                    || Arrays.equals(position, new int[]{2, 1})) {
                return 1;//distance 1 in (0,1), (1,2)
            } else if (Arrays.equals(position, new int[]{0, 2})
                    || Arrays.equals(position, new int[]{1, 1})
                    || Arrays.equals(position, new int[]{2, 0})) {
                return 2;//distance 2 in (0,0), (1,1), (2,2)
            } else if (Arrays.equals(position, new int[]{0, 1})
                    || Arrays.equals(position, new int[]{1, 0})) {
                return 3;//distance 3 in (1,0),(2,1)
            } else if (Arrays.equals(position, new int[]{0, 0})) {
                return 4;//distance 4 in(2,0)
            }

        } else if (tile == 6) {//tile 6
            if (Arrays.equals(position, new int[]{2, 1})) {
                return 0;//distance 0 in (0,1)
            } else if (Arrays.equals(position, new int[]{2, 0})
                    || Arrays.equals(position, new int[]{2, 2})
                    || Arrays.equals(position, new int[]{1, 1})) {
                return 1;//distance 1 in (0,0), (0,2), (1,1)
            } else if (Arrays.equals(position, new int[]{0, 1})
                    || Arrays.equals(position, new int[]{1, 0})
                    || Arrays.equals(position, new int[]{1, 2})) {
                return 2;//distance 2 in (1,0), (1,2), (2,1)
            } else if (Arrays.equals(position, new int[]{0, 0})
                    || Arrays.equals(position, new int[]{0, 2})) {
                return 3;//distance 3 in (2,0),(2,2)
            }

        } else if (tile == 7) {//tile 7
            if (Arrays.equals(position, new int[]{2, 0})) {
                return 0;//distance 0 in (0,2)
            } else if (Arrays.equals(position, new int[]{1, 0})
                    || Arrays.equals(position, new int[]{2, 1})) {
                return 1;//distance 1 in (0,1), (1,2)
            } else if (Arrays.equals(position, new int[]{0, 0})
                    || Arrays.equals(position, new int[]{1, 1})
                    || Arrays.equals(position, new int[]{2, 2})) {
                return 2;//distance 2 in (0,0), (1,1), (2,2)
            } else if (Arrays.equals(position, new int[]{0, 1})
                    || Arrays.equals(position, new int[]{1, 2})) {
                return 3;//distance 3 in (1,0),(2,1)
            } else if (Arrays.equals(position, new int[]{0, 2})) {
                return 4;//distance 4 in(2,0)
            }

        } else if (tile == 8) {//tile 8
            if (Arrays.equals(position, new int[]{1, 0})) {
                return 0;//distance 0 in (0,1)
            } else if (Arrays.equals(position, new int[]{0, 0})
                    || Arrays.equals(position, new int[]{2, 0})
                    || Arrays.equals(position, new int[]{1, 1})) {
                return 1;//distance 1 in (0,0), (0,2), (1,1)
            } else if (Arrays.equals(position, new int[]{0, 1})
                    || Arrays.equals(position, new int[]{1, 2})
                    || Arrays.equals(position, new int[]{2, 1})) {
                return 2;//distance 2 in (1,0), (1,2), (2,1)
            } else if (Arrays.equals(position, new int[]{0, 2})
                    || Arrays.equals(position, new int[]{2, 2})) {
                return 3;//distance 3 in (2,0),(2,2)
            }

        }
        return 0;
    }

    //takes int array and tile number then returns array of tile position
    private int[] getTilePosition(int[][] state, int tile) {
        int positionArray[] = new int[2];//pos 0 is row and 1 is col

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] == tile) {
                    positionArray[0] = i;
                    positionArray[1] = j;

                    return positionArray;
                }
            }
        }
        return null;
    }

    private void setDistanceToGoal() {
        distance[0] = getDistanceToReachGoal(game_board, 1);//tile 1 distance
        distance[1] = getDistanceToReachGoal(game_board, 2);//tile 2 distance
        distance[2] = getDistanceToReachGoal(game_board, 3);//tile 3 distance
        distance[3] = getDistanceToReachGoal(game_board, 4);//tile 4 distance
        distance[4] = getDistanceToReachGoal(game_board, 5);//tile 5 distance
        distance[5] = getDistanceToReachGoal(game_board, 6);//tile 6 distance
        distance[6] = getDistanceToReachGoal(game_board, 7);//tile 7 distance
        distance[7] = getDistanceToReachGoal(game_board, 8);//tile 8 distance

    }

    //find out the new empty tile position for current state
    private void locateEmptyTilePosition() {

        nestedloop://to break inner and outer loop
        for (int i = 0; i < game_board.length; i++) {
            for (int j = 0; j < game_board[0].length; j++) {
                if (game_board[i][j] == 0) {
                    emptyTile_row = i;
                    emptyTile_col = j;
                    break nestedloop;
                }
            }
        }
    }

    //check if all tile distances to goal are 0 or not
    //if all 0, return true, otherwise return false
    private boolean isAllDistancesZero() {
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] != 0) {
                return false;
            }
        }
        return true;
    }

    //print the current state of the game board
    private void printState(int[][] state, String message) {
        System.out.println(message);
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                System.out.print(state[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("--------");
    }
}
