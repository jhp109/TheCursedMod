package thecursed.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class LuckyCharmPower extends AbstractTheCursedPower implements OnCardDrawPower {
    public static final String POWER_ID = "TheCursedMod:LuckyCharmPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "luckycharm.png";

    public LuckyCharmPower(AbstractCreature owner, int amount) {
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
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        this.maybeActivate(card);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        this.maybeActivate(card);
    }

    private void maybeActivate(AbstractCard card) {
        if (card.type == AbstractCard.CardType.CURSE) {
            this.flash();

            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
