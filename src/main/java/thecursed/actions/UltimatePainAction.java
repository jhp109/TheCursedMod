package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class UltimatePainAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private AbstractMonster m;
    private int energyOnUse;

    public UltimatePainAction(AbstractPlayer p, AbstractMonster m, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.m = m;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        AbstractDungeon.actionManager.addToBottom(new ManuallyActivateBleedAction(m, effect));

        if (effect > 0 && !this.freeToPlayOnce) {
            this.p.energy.use(EnergyPanel.totalCount);
        }

        this.isDone = true;
    }
}
