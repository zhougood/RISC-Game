package ece651.riskgame.shared;

public class PlaceAction implements Action {
  private Unit toPlace;
  private String placeTerritory;
  public PlaceAction(Unit toPlace, String placeTerritory) {
    this.toPlace = toPlace;
    this.placeTerritory = placeTerritory;
  }

  @Override
  public void apply(Actable world) {
    Board board = world.getBoard();
    board.getTerritory(placeTerritory).addUnit(toPlace);
  }
  @Override
  public void clientApply(Actable game) {
    apply(game);
  }

}
