package items;

import combatants.Combatant;
import combatants.Player;
import engine.BattleEngine;
import actions.Action;

public class PowerStone implements Item {
  @Override
  public void apply(Combatant user, Combatant target, BattleEngine engine) {
    if (user instanceof Player) {
      Player p = (Player) user;
      Action specialSkill = p.getSpecialSkill();
      System.out.println(user.getName() + " channelled the Power Stone to trigger Special Skill early!");
      specialSkill.execute(user, target, engine);
    }
  }

  @Override
  public String getName() {
    return "Power Stone";
  }
}
