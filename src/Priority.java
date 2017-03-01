
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
public class Priority {
    static int[][] preState;//keeps the previous state
    static Node neighbors_nodeArray[];

    //takes an node array, sort nodes based on distance of tile from goal
    //and return highest distance tile's node
    public static Node sort(Node[] nodeArray) {
        
        if(preState!=null){//parent exists
            nodeArray = getParentRemovedNodeArray(nodeArray, preState);//remove parent
        }
        
        //sorting nodes based on distance of tile from goal
        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = nodeArray.length - 1; j > i; j--) {
                if (nodeArray[j].distTile < nodeArray[j - 1].distTile) {
                    Node temp = nodeArray[j];
                    nodeArray[j] = nodeArray[j - 1];
                    nodeArray[j - 1] = temp;
                }
            }
        }
        Priority.neighbors_nodeArray = nodeArray;
        return nodeArray[nodeArray.length - 1];
    }

    //takes node array and prestate 
    //remove the neighbor which same as prestate and return parent removed node array
    public static Node[] getParentRemovedNodeArray(Node []nodeArray, int[][] preState) {
        Node[] parentRemovedNodeArray = new Node[nodeArray.length - 1];
        int j = 0;
        for (int i = 0; i < nodeArray.length; i++) {
            if (Arrays.deepEquals(nodeArray[i].state, preState)) {
                //System.out.println("removed parent");
            } else {
                parentRemovedNodeArray[j] = nodeArray[i];
                j++;
            }
        }
        //Node highest_distTile_node = sort(parentRemovedNodeArray);
        //return highest_distTile_node;
        return parentRemovedNodeArray;
    }

    //pick the best to go from equal distance tiles and return node array
    public static Node getBestTileNode() {
        
        if (neighbors_nodeArray.length == 1) {
            return neighbors_nodeArray[0];
        }

        if (neighbors_nodeArray.length == 2) {
            if (neighbors_nodeArray[0].distTile == neighbors_nodeArray[1].distTile) {//both are equal
                int dist0 = new Eight_Puzzle().getDistanceToReachGoal(neighbors_nodeArray[0].state, neighbors_nodeArray[0].tile);
                int dist1 = new Eight_Puzzle().getDistanceToReachGoal(neighbors_nodeArray[1].state, neighbors_nodeArray[1].tile);
                
                if ((neighbors_nodeArray[0].distTile - dist0) >= (neighbors_nodeArray[1].distTile - dist1)){ //pos 0 tile is reducing dist
                    return neighbors_nodeArray[0];
                } else {//pos 1 tile is reducing dist
                    return neighbors_nodeArray[1];
                }
            }else{
                return neighbors_nodeArray[1];//return largest one
            }
        }
        if (neighbors_nodeArray.length == 3) {
            if (neighbors_nodeArray[1].distTile == neighbors_nodeArray[2].distTile) {//largest two equal
                int dist1 = new Eight_Puzzle().getDistanceToReachGoal(neighbors_nodeArray[1].state, neighbors_nodeArray[1].tile);
                int dist2 = new Eight_Puzzle().getDistanceToReachGoal(neighbors_nodeArray[2].state, neighbors_nodeArray[2].tile);

                if ((neighbors_nodeArray[1].distTile - dist1) >= (neighbors_nodeArray[2].distTile - dist2)) {//pos 1 tile reducing dist
                    return neighbors_nodeArray[1];
                } else {//pos 2 tile reducing dist
                    return neighbors_nodeArray[2];
                }
            }else{
                return neighbors_nodeArray[2];//return largest one
            }
        }
        if (neighbors_nodeArray.length == 4) {
            if (neighbors_nodeArray[2].distTile == neighbors_nodeArray[3].distTile) {//largest two equal
                int dist2 = new Eight_Puzzle().getDistanceToReachGoal(neighbors_nodeArray[2].state, neighbors_nodeArray[2].tile);
                int dist3 = new Eight_Puzzle().getDistanceToReachGoal(neighbors_nodeArray[3].state, neighbors_nodeArray[3].tile);

                if ((neighbors_nodeArray[2].distTile - dist2) >= (neighbors_nodeArray[3].distTile - dist3)) {//pos 1 tile reducing dist
                    return neighbors_nodeArray[2];
                } else {//pos 2 tile reducing dist
                    return neighbors_nodeArray[3];
                }
            }else{
                return neighbors_nodeArray[3];//return largest one
            }
        }

        return null;
    }
}

//Node class
class Node {

    int tile;//tile value
    int distTile;//distance of tile
    int[][] state;//states

    public Node(int tile, int distTile, int[][] state) {
        this.tile = tile;
        this.distTile = distTile;
        this.state = state;
    }
}
