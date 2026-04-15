package items;

import combatants.Combatant;
import engine.BattleEngine;
import effects.PoisonCloudEffect;

public class PoisonCloud implements Item {
    @Override
    public void apply(Combatant user, Combatant target, BattleEngine engine) {
        target.addStatusEffect(new PoisonCloudEffect(3)); // Poison for 3 turns
    }
    
    @Override
    public String getName() {
        return "Poison Cloud";
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }
}
