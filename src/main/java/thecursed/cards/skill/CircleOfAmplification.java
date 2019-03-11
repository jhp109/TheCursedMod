package thecursed.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import thecursed.TheCursedMod;

public class CircleOfAmplification extends AbstractCircle {
    public static final String ID = "TheCursedMod:CircleOfAmplification";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/circleofamplification.png";

    private static final CardStrings cardStrings;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int DRAW = 1;
    private static final int ENERGY = 1;

    public CircleOfAmplification() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), DESCRIPTION, EXTENDED_DESCRIPTION[0], RARITY, TARGET);
        this.isEthereal = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new CircleOfAmplification();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.type == CardType.POWER) {
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW));
            this.onActivateCircle(this.name);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }
}
