package actions;

import combatants.Combatant;
import engine.BattleEngine;

public interface Action {
    void execute(Combatant actor, Combatant target, BattleEngine engine);
    String getName();
}
