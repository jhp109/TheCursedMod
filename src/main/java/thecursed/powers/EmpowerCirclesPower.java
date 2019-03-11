package thecursed.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import thecursed.cards.TheCursedCardTags;

public class EmpowerCirclesPower extends AbstractTheCursedPower implements OnCardDrawPower {
    public static final String POWER_ID = "TheCursedMod:EmpowerCirclesPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "empowercircles.png";

    public EmpowerCirclesPower(AbstractCreature owner, int amount) {
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

    @Override
    public void onCardDraw(AbstractCard c) {
        if (c.hasTag(TheCursedCardTags.CIRCLE)) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.amount));
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.owner, this.amount));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
