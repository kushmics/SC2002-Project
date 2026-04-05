package actions;

import combatants.Combatant;
import engine.BattleEngine;
import effects.ArcaneBlastBuff;
import java.util.List;

public class ArcaneBlastAction implements Action {
    @Override
    public void execute(Combatant actor, Combatant target, BattleEngine engine) {
        System.out.println(actor.getName() + " casts Arcane Blast!");
        List<Combatant> enemies = engine.getAliveEnemies();
        int defeatedCount = 0;
        
        for (Combatant enemy : enemies) {
            int damage = Math.max(0, actor.getAttack() - enemy.getDefense());
            enemy.takeDamage(damage);
            System.out.println("-> Hits " + enemy.getName() + " for " + damage + " damage.");
            if (!enemy.isAlive()) {
                defeatedCount++;
                System.out.println("-> " + enemy.getName() + " was defeated by Arcane Blast!");
            }
        }
        
        if (defeatedCount > 0) {
            actor.addStatusEffect(new ArcaneBlastBuff(defeatedCount * 10));
        }
    }
    
    @Override
    public String getName() {
        return "Arcane Blast";
    }
}
