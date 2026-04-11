package items;

import combatants.Combatant;
import engine.BattleEngine;
import effects.SmokeBombEffect;

public class SmokeBomb implements Item {
  @Override
  public void apply(Combatant user, Combatant target, BattleEngine engine) {
    user.addStatusEffect(new SmokeBombEffect(2));
  }

  @Override
  public String getName() {
    return "Smoke Bomb";
  }
}
