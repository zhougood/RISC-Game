package ece651.riskgame.shared;

import java.io.Serializable;
import java.util.Map;

// TODO: Rename
public class Move extends MigrationAction implements Serializable {

    public Move(Unit moveUnit, String fromTerritory, String toTerritory, String color) {
        super(moveUnit, fromTerritory, toTerritory, color);
    }

    @Override
    public void apply(Actable world) {
        Board board = world.getBoard();

        board.getTerritory(fromTerritory).decUnit(Unit);
        board.getTerritory(toTerritory).addUnit(Unit);

        int unitCost = board.getUnitMoveCost(fromTerritory).get(toTerritory);
        System.out.println(world.getClans().get(color));
        world.getClans().get(color).getResource().costFood(unitCost * Unit.getNum());
    }
    @Override
    public void clientApply(Actable game) {
      apply(game);
    }
  
}
