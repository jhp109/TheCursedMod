package thecursed.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ManaCirculationPower extends AbstractTheCursedPower {
    public static final String POWER_ID = "TheCursedMod:ManaCirculationPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "manacirculation.png";

    public ManaCirculationPower(AbstractCreature owner, int energyGain) {
        super(IMG);
        this.owner = owner;
        this.amount = energyGain;
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
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card.type == AbstractCard.CardType.CURSE) {
            this.flash();

            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
