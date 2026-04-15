package actions;

import combatants.Combatant;
import combatants.Player;
import engine.BattleEngine;

public class ExecuteSpecialSkillAction implements Action {
    @Override
    public void execute(Combatant actor, Combatant target, BattleEngine engine) {
        if (actor instanceof Player) {
            Player p = (Player) actor;
            if (p.canUseSpecialSkill()) {
                p.getSpecialSkill().execute(actor, target, engine);
                p.setCooldown(p.getMaxCooldown());
            } else {
                System.out.println("Special skill is on cooldown!");
            }
        }
    }

    @Override
    public String getName() {
        return "Execute Special Skill";
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }
}
