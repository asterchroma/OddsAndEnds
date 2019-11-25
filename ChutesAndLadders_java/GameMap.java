import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMap {
    public static int MAP_LENGTH = 40;
    public static int MAP_COMPLEXITY = 20;

    List<Box> boxes;

    GameMap() {
        boxes = new ArrayList<>();

        for(int i = 0; i < MAP_LENGTH; i++) {
            boxes.add(new Box());
        }

        for(int i = 0; i < MAP_COMPLEXITY; i++) {
            generateLink();
        }
    }

    private void generateLink() {
        Random rand = new Random();
        int from = rand.nextInt(MAP_LENGTH);
        Box fromBox = this.boxes.get(from);
        while(fromBox.hasChute || fromBox.hasLadder) {
            from = rand.nextInt(MAP_LENGTH);
            fromBox = this.boxes.get(from);
        }
        int to;
        boolean isChute = rand.nextBoolean();
        if(isChute) {
            to = from - rand.nextInt(from + 1);
            if (to > 0) {
                fromBox.setChuteTo(to);
            }
        } else {
            if(from == MAP_LENGTH) {
                return;
            }
            to = from + (rand.nextInt(MAP_LENGTH - from) + 1);
            if (to < MAP_LENGTH) {
                fromBox.setLadderTo(to);
            }
        }
    }

    public void printMap() {
        System.out.println("--------------------- current map ----------------------");
        for(int i = 0; i < MAP_LENGTH; i++) {
            Box atBox = this.boxes.get(i);
            System.out.printf(" | ");
            if(atBox.hasChute) {
                System.out.printf("%02d<-#%02d#    ", atBox.chuteTo, i);
            } else if(atBox.hasLadder) {
                System.out.printf("    #%02d#->%02d", i, atBox.ladderTo);
            } else {
                System.out.printf("    #%02d#    ", i);
            }
            if(atBox.hasPlayer) {
                System.out.printf(" <P%02d>", atBox.playerNo);
            } else {
                System.out.printf(" <   >");
            }
            System.out.printf(" | ");
            if((i+1) % 5 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("--------------------- *********** ----------------------");
    }

    public void removePlayer(int index) {
        this.boxes.get(index).hasPlayer = false;
    }

    public void addPlayer(int index, int id) {
        this.boxes.get(index).hasPlayer = true;
        this.boxes.get(index).playerNo = id;
    }

    public int checkChuteOrLadder(int index) {
        Box from = this.boxes.get(index);
        if(from.hasChute) {
            return from.chuteTo;
        }
        if(from.hasLadder) {
            return from.ladderTo;
        }
        return -1;
    }

    class Box {
        boolean hasChute;
        boolean hasLadder;
        boolean hasPlayer;
        int chuteTo;
        int ladderTo;
        int playerNo;

        Box() {
            this.hasChute = false;
            this.hasLadder = false;
            this.hasPlayer = false;
        }

        public void setChuteTo(int index) {
            this.hasChute = true;
            this.chuteTo = index;
        }

        public void setLadderTo(int index) {
            this.hasLadder = true;
            this.ladderTo = index;
        }
    }
}