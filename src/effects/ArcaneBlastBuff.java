package effects;
import combatants.Combatant;

public class ArcaneBlastBuff implements StatusEffect {
    private int attackBoost;
    
    public ArcaneBlastBuff(int attackBoost) {
        this.attackBoost = attackBoost;
    }

    @Override
    public void onApply(Combatant target) {
        target.setAttack(target.getAttack() + attackBoost);
    }

    @Override
    public void tick(Combatant target) {}

    @Override
    public void onRemove(Combatant target) {}

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public String getName() {
        return "Arcane Blast Buff";
    }

    @Override
    public boolean preventsAction() {
        return false;
    }
    
}
