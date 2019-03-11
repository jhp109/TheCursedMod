package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thecursed.powers.BleedPower;
import thecursed.relics.BloodyHarpoon;

import java.util.ArrayList;

public class ApplyBleedAction extends AbstractGameAction {
    public ApplyBleedAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.target = target;
        this.source = source;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
        this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (this.source == AbstractDungeon.player && AbstractDungeon.player.hasRelic(BloodyHarpoon.ID)) {
                AbstractDungeon.player.getRelic(BloodyHarpoon.ID).flash();
                this.amount += BloodyHarpoon.BLEED_ADD_AMOUNT;
            }

            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            this.target, this.source, new BleedPower(this.target, this.source, this.amount), this.amount));
        }
        this.tickDuration();
    }
}
