package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class RavingStaffAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private DamageInfo info;
    private int energyOnUse;

    public RavingStaffAction(AbstractPlayer p, DamageInfo info, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.info = info;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    @Override
    public void update() {
        int numTimes = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            numTimes = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            numTimes += 2;
            this.p.getRelic("Chemical X").flash();
        }


        if (numTimes > 0) {
            AbstractMonster randomMonster =
                    AbstractDungeon.getMonsters().getRandomMonster(
                            null, true, AbstractDungeon.cardRandomRng);
            AbstractDungeon.actionManager.addToTop(new RavingStaffHitAction(randomMonster, info, numTimes));
        }


        if (numTimes > 0 && !this.freeToPlayOnce) {
            this.p.energy.use(EnergyPanel.totalCount);
        }

        this.isDone = true;
    }
}
