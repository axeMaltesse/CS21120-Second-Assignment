package cs21120.assignment.solution;
/**
 * The aim of this assignment was to test my ability to implement and use some basic data structures.
 * I had to write a system for managing a single elimination style competition.
 * This is the type of approach used in tennis or football championships.
 * In each game, two teams or players compete. One is eliminated as a looser,
 * whilst the other continues to the next round or is declared as a winner.
 *
 * The provided code was able to:
 * Read a list of players or teams from .txt file,
 * draw the competition tree and handle based input and output.
 *
 * What I had to do:
 * I had to implement two interfaces, one for draw the tree and
 * second one for the competition management. The interfaces was also provided
 * without implementation. The next step was writing a main function which has to read
 * file with data and then after passing the scores for each teams as an input,
 * printing the competition tree.
 *
 * Self Evaluation:
 * I hope that my evaluation is worth 70% or more because I have filled up every part
 * of the assignment criteria including documentation as comments and description below.
 * The data.txt file is created by following the Appendix 1 in Assignment description.
 * I have spent more than week of the easter break doing this assignment so I am sure
 * it will pass future tests given through the marking process.
 *
 */

import cs21120.assignment.*;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by axeMaltesse on 2016-04-02.
 */
public class BTSingleElimluw19 {
    /**
     * Basic main function
     * @param args - needed to run the application
     */
    public static void main(String[] args) {
        //creating an object of the class by default constructor
        BTSingleElimluw19 singleElim = new BTSingleElimluw19();
        //The manager object
        IManager manager = singleElim.new Manager();
        //competition manager object to create a competition
        CompetitionManager competitionManager = new CompetitionManager(manager);
        //Loop to read data from file and print the tree
        try {
            competitionManager.runCompetition("C:\\Users\\axeMaltesse\\Desktop\\JavaFile\\data.txt");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Manager class which implements IManager interface.
     * In this class I had to fill missing implementations of interface and
     * write other functions if needed.
     */
    public class Manager implements IManager{

        private Tree node;
        private Tree competition;
        private Queue <Tree> queue = new ArrayDeque<Tree>();
        private Stack <Tree> stack = new Stack<Tree>();

        @Override
        public void setPlayers(ArrayList<String> players) {
            //creating a new tree
            competition = new Tree(players);
            //method described below
            find();
        }

        /**
         * method to find and add a team in tree
         */
        private void find(){
            queue.add(competition);
            //looping until all nodes are filled
            while (queue.size()>0){
                Tree t = queue.poll();

                if (t.left != null && t.right!=null){
                    queue.add(t.left);
                    queue.add(t.right);
                    stack.add(t);
                }
            }
        }

        /**
         * checking if the team is playing next match
         * @return true if stack is not empty, otherwise false
         */
        @Override
        public boolean hasNextMatch() {
            return !stack.empty();
        }

        /**
         * Method to traverse the tree and creating next events for the winners
         * @return - teams/players for the next match
         * @throws NoNextMatchException if stack is empty
         */
        @Override
        public Match nextMatch() throws NoNextMatchException {
            if(stack.empty()){
                throw new NoNextMatchException("Empty competition");
            }

            node = stack.pop();
            Match m = new Match(node.getLeft().getPlayer(),node.getRight().getPlayer());
            return m;
        }

        /**
         * method to setting the scores for the teams
         * @param p1 should be the score of player1
         * @param p2 should be the score of player2
         */
        @Override
        public void setMatchScore(int p1, int p2) {
            node.left.score=p1;
            node.right.score=p2;
            //described below
            setWinner(p1, p2);
        }

        /**
         * method to compare the score and choose the winner
         * @param s1 is score of the first team
         * @param s2 is score of the second team
         */
        public void setWinner (int s1, int s2){
            if (s1 > s2){
                node.playerName=node.left.getPlayer();
            }else{
                node.playerName=node.left.getPlayer();
            }
        }

        /**
         * method to getting the position
         * @param n the position to return
         * @return a position of the node
         */
        @Override
        public String getPosition(int n) {
            return queue.element().toString();
        }

        /**
         * Method to construct the tree
         * @return a tree object
         */
        @Override
        public IBinaryTree getCompetitionTree() {
            return competition;
        }

    }

    /**
     * Class which implements a IBinaryTree interface
     * to construct a competition tree
     */
    public class Tree implements IBinaryTree {

        private String playerName;
        private Tree left;
        private Tree right;
        int score;

        /**
         * default constructor for the competition tree
         * @param players list of the players
         */
        public Tree (ArrayList<String> players){
            //simple loop to check the amount of the players/teams
            if (players.size() == 0){
                System.out.print("There is no teams \n");
            }else if (players.size()== 1){
                playerName = players.get(0);
            } else {
                //for >2 teams creating a 2 array lists to hold the teams for each root
                ArrayList<String> LeftNode = new ArrayList<>();
                ArrayList<String> RightNode = new ArrayList<>();
                //splitting the amount of the teams by two
                int split = players.size() /2;
                //adding firs part to the left root
                for (int i=0; i<split; i++){
                    LeftNode.add(players.get(i));
                }
                left = new Tree(LeftNode);
                //adding second part to the right root
                for (int i = split; i < players.size(); i++){
                    RightNode.add(players.get(i));
                }
                right = new Tree(RightNode);
            }
        }

        /**
         * method to get left node
         * @return left node
         */
        @Override
        public IBinaryTree getLeft() {
            return left;
        }

        /**
         * method to get right node
         * @return right node
         */
        @Override
        public IBinaryTree getRight() {
            return right;
        }

        /**
         * method to get player
         * @return playerName
         */
        @Override
        public String getPlayer() {
            return playerName;
        }

        /**
         * method to get the score of the player
         * @return score
         */
        @Override
        public int getScore() {
            return score;
        }
    }
}
