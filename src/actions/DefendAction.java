package actions;

import combatants.Combatant;
import engine.BattleEngine;
import effects.DefendBuff;

public class DefendAction implements Action {
    @Override
    public void execute(Combatant actor, Combatant target, BattleEngine engine) {
        System.out.println(actor.getName() + " defends! Defense increased.");
        actor.addStatusEffect(new DefendBuff(2));
    }
    
    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }
}
