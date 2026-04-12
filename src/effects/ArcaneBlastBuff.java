package effects;
import combatants.Combatant;

public class ArcaneBlastBuff implements StatusEffect {

    @Override
    public void onApply(Combatant target) {}

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
