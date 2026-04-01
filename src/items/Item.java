package items;

import combatants.Combatant;
import engine.BattleEngine;

public interface Item {
    void apply(Combatant user, Combatant target, BattleEngine engine);
    String getName();
}
