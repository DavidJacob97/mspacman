package pacman;

import pacman.controllers.Controller;

import pacman.game.Constants;
import pacman.game.Game;


import javax.swing.*;


public class MyMCTSPacMan extends Controller<Constants.MOVE> {

    private static final int minDistance = 10;



    public Constants.MOVE getMove(Game game, long timeDue) {
        int current = game.getPacmanCurrentNodeIndex();
        int distance = 100;


        Constants.MOVE move = null;

        //(game.getGhostEdibleTime(ghost)==0 && game.getGhostLairTime(ghost)==0)
        // if(game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost))<MIN_DISTANCE)


        for (Constants.GHOST ghost : Constants.GHOST.values())

            //Strategu 1: run away from non edible ghosts
            if (game.getGhostEdibleTime(ghost) == 0 ) {


                if (game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost)) < minDistance) {
                    //
                    int[] PowerPills = game.getActivePowerPillsIndices();
                    for (int i = 0; i < PowerPills.length; i++) {

                        if (game.getShortestPathDistance(current, PowerPills[i]) >= 10) {
                            //System.out.println("Moving away");
                            return   game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost), Constants.DM.PATH);
                        } else {
                            return  game.getNextMoveTowardsTarget(current, PowerPills[i], Constants.DM.PATH);
                            //  System.out.println("getting power pill");
                        }
                    }
                }
            }
        //move = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getClosestNodeIndexFromNodeIndex(current, PowerPills, Constants.DM.PATH), Constants.DM.PATH);

        //Strategy 2 eat pills
        for (Constants.GHOST ghost : Constants.GHOST.values())
            if (game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost)) > minDistance && game.getGhostEdibleTime(ghost) == 0) {

//System.out.println("Eating");
                 int[] allPills = game.getActivePillsIndices();
                int[] targetNodeIndices=new int[allPills.length];
                for(int i=0;i<allPills.length;i++)
                    targetNodeIndices[i]=allPills[i];
                return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getClosestNodeIndexFromNodeIndex(current, targetNodeIndices, Constants.DM.PATH), Constants.DM.PATH);

            }

        for (Constants.GHOST ghost : Constants.GHOST.values())
            if (game.getGhostEdibleTime(ghost) > 0) {
                //int[] PowerPills = game.getActivePowerPillsIndices();
                if (game.getManhattanDistance(current, game.getGhostCurrentNodeIndex(ghost)) < 10) {

                    return game.getNextMoveTowardsTarget(current, game.getGhostCurrentNodeIndex(ghost), Constants.DM.PATH);
                }


                int[] allPills = game.getActivePillsIndices();
                int[] targetNodeIndices=new int[allPills.length];
                for(int i=0;i<allPills.length;i++)
                    targetNodeIndices[i]=allPills[i];
                return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getClosestNodeIndexFromNodeIndex(current, targetNodeIndices, Constants.DM.PATH), Constants.DM.PATH);
                // System.out.println(game.getShortestPathDistance(current, PowerPills[i]));



            }
        return null; }
}

