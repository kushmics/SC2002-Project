package items;

import combatants.Combatant;
import engine.BattleEngine;

public class Potion implements Item {
  @Override
  public void apply(Combatant user, Combatant target, BattleEngine engine) {
    user.heal(100);
  }

  @Override 
  public String getName() {
    return "Potion";
  }

  @Override
  public boolean requiresTarget() {
    return false;
  }
}
