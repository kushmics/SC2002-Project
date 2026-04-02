package actions;

import combatants.Combatant;
import engine.BattleEngine;

public class BasicAttack implements Action {
    @Override
    public void execute(Combatant actor, Combatant target, BattleEngine engine) {
        int damage = Math.max(0, actor.getAttack() - target.getDefense());
        System.out.println(actor.getName() + " attacks " + target.getName() + "!");
        target.takeDamage(damage);
    }
    
    @Override
    public String getName() {
        return "Basic Attack";
    }
}
