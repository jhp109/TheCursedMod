package thecursed.cards.curse;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thecursed.TheCursedMod;

public class Dregs extends CustomCard {
    public static final String ID = "TheCursedMod:Dregs";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/dregs.png";

    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = -2;

    public Dregs() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.CURSE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        } else {
            AbstractDungeon.actionManager.addToBottom(new UseCardAction(this));
        }

    }

    public AbstractCard makeCopy() {
        return new Dregs();
    }

    public void upgrade() {
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
