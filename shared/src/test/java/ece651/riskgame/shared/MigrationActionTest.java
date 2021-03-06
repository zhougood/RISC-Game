package ece651.riskgame.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MigrationActionTest {

  public class DemoMigrationAction extends MigrationAction {
    public DemoMigrationAction(Unit moveUnit, String fromTerritory, String toTerritory, String color) {
      super((List<Unit>) moveUnit, fromTerritory, toTerritory, color);
    }

    @Override
    public void apply(Actable world) {

    }

    @Override
    public void clientApply(Actable game) {

    }
  }
  
  @Test
  public void test_all() {
    MigrationAction ma = Mockito.spy(new DemoMigrationAction(null, "from", "to", "color"));
    assertEquals(null, ma.getUnit());
    assertEquals("from", ma.getFromTerritory());
    assertEquals("to", ma.getToTerritory());

    // irrevalent test for coverage
    ma.apply(null);
    ma.clientApply(null);
  }

}
