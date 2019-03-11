package thecursed.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import thecursed.cards.CurseUtil;
import thecursed.cards.curse.Dregs;

public class CollectTributePower extends AbstractTheCursedPower {
    public static final String POWER_ID = "TheCursedMod:CollectTributePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "collecttribute.png";

    public CollectTributePower(AbstractCreature owner, int amount) {
        super(IMG);
        this.owner = owner;
        this.amount = amount;

        this.name = NAME;
        this.ID = POWER_ID;
        this.type = POWER_TYPE;

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
        }
    }

    public void atStartOfTurn() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        for (int i = 0; i < this.amount; ++i) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(CurseUtil.getRandomCurseCard()));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
