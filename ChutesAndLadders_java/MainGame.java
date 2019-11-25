import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MainGame {
    public static int PLAYER_NUMBER = 3;

    private GameMap gameMap;
    private List<Player> players;
    private Random rand;

    public MainGame() {
        // initialize dice
        // could have a dice class for better abstraction but I'm lazy
        this.rand = new Random();

        // initialize map
        this.gameMap = new GameMap();
        this.gameMap.printMap();
        this.players = new ArrayList<>();

        // initialize players
        for(int i = 0; i < PLAYER_NUMBER; i++) {
            Player player = new Player(i);
            movePlayerTo(0, player);
            this.players.add(player);
        }

        // TODO: put players on map
    }

    private void oneRound() {
        int diceNum;
        for(Player p: players) {
            System.out.printf(">> Player %d%n", p.getId());

            // roll dice
            diceNum = this.rand.nextInt(6) + 1;
            System.out.printf("      Rolled: %d%n", diceNum);
            System.out.printf("      Player %d currently at: %d%n", p.getId(), p.getPosition());

            // move player
            movePlayerForward(diceNum, p);

            // check for win
            if(p.getPosition() >= GameMap.MAP_LENGTH - 1) {
                System.out.printf("      Player %d Wins !!%n", p.getId());
                System.exit(0);
            }

            // check for chutes or ladders and move
            chutesOrLadder(p);

            // check for win again, shit this is ugly
            if(p.getPosition() >= GameMap.MAP_LENGTH - 1) {
                System.out.printf("      Player %d Wins !!%n", p.getId());
                System.exit(0);
            }
        }
        System.out.println("Current Map: ");
        this.gameMap.printMap();
    }

    private void chutesOrLadder(Player p) {
        int position = p.getPosition();
        int to = gameMap.checkChuteOrLadder(position);
        if(to != -1) {
            System.out.printf("      !!!");
            movePlayerTo(to, p);
        }
    }

    private void movePlayerTo(int index, Player player) {
        if(index > GameMap.MAP_LENGTH - 1) {
            index = GameMap.MAP_LENGTH - 1;
        }
        System.out.printf("      Moving Player %d to %d%n", player.getId(), index);
        gameMap.removePlayer(player.getPosition());
        player.moveTo(index);
        gameMap.addPlayer(index, player.getId());
    }

    private void movePlayerForward(int blocks, Player player) {
        int index = blocks + player.getPosition();
        movePlayerTo(index, player);
    }

    private static void pressEnterToContinue() { 
        System.out.println("Press Enter key to roll dice...");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}  
    }

    public static void main(String[] args) {
        // initialize game
        System.out.println("Initializing game....");
        MainGame mainGame = new MainGame();
        System.out.println("Game initialized....");

        // play
        int roundsCount = 0;
        while(true) {
            System.out.println();
            System.out.printf("---------------------- Round %d... ----------------------%n", roundsCount);
            pressEnterToContinue();
            mainGame.oneRound();
            roundsCount++;
            System.out.printf("-------------------- Round %d ended ---------------------%n", roundsCount);
        }
    }
}