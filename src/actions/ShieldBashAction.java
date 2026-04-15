package actions;

import combatants.Combatant;
import engine.BattleEngine;
import effects.Stun;

public class ShieldBashAction implements Action{
    @Override
    public void execute(Combatant actor, Combatant target, BattleEngine engine) {
        int damage = Math.max(0, actor.getAttack() - target.getDefense());
        System.out.println(actor.getName() + " uses Shield Bash on " + target.getName() + "!");
        target.takeDamage(damage);
        target.addStatusEffect(new Stun(2));
    }
    
    @Override
    public String getName() {
        return "Shield Bash";
    } 

    @Override
    public boolean requiresTarget() {
        return true;
    }
}
