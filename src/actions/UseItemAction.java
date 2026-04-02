package actions;

import combatants.Combatant;
import combatants.Player;
import engine.BattleEngine;
import items.Item;

public class UseItemAction implements Action {
    private Item item;

    public UseItemAction(Item item) {
        this.item = item;
    }

    @Override
    public void execute(Combatant actor, Combatant target, BattleEngine engine) {
        if (actor instanceof Player) {
            System.out.println(actor.getName() + " uses item: " + item.getName());
            item.apply(actor, target, engine);
            ((Player) actor).removeItem(item);
        }
    }

    @Override
    public String getName() {
        return "Use Item (" + item.getName() + ")";
    }
}
