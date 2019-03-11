package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import thecursed.powers.BleedPower;

public class ManuallyActivateBleedAction extends AbstractGameAction {
    public ManuallyActivateBleedAction(AbstractCreature target, int times) {
        this.target = target;
        this.amount = times;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (this.target.hasPower(BleedPower.POWER_ID)) {
            BleedPower bleedPower = (BleedPower) this.target.getPower(BleedPower.POWER_ID);
            bleedPower.activateBleed(this.amount);
        }
        this.isDone = true;
    }
}
