package nz.ac.vuw.ecs.swen225.gp22.domain;

public class Domain {
    private Tile[][] gameState;
    private Player   player;
    private int      requiredTreasureCount;

    public Domain(Tile[][] gameState) {
        this.gameState = gameState;
        this.player = new Player(this);
        countTreasures();
    }

    private void countTreasures() {
        for (int y=0; y<gameState.length; ++y)  {
            for (int x=0; x<gameState[y].length; ++x) {
                if (gameState[y][x].name() == "treasure") {
                    requiredTreasureCount++;
                }
            }
        }
    }

    public int requiredTreasureCount() {
        return requiredTreasureCount;
    }
}
