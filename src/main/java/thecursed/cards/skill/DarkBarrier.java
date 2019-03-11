package thecursed.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import thecursed.TheCursedMod;
import thecursed.actions.ExhaustCurseThenActivateAction;
import thecursed.cards.TheCursedCardTags;
import thecursed.enums.AbstractCardEnum;

public class DarkBarrier extends CustomCard {
    public static final String ID = "TheCursedMod:DarkBarrier";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/darkbarrier.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 3;
    private static final int INTANGIBLE = 1;
    private static final int UPGRADE_INTANGIBLE_AMOUNT = 1;

    public DarkBarrier() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = INTANGIBLE;
        this.exhaust = true;
        this.tags.add(TheCursedCardTags.RITUAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction targetAction =
                new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber), this.magicNumber);
        AbstractDungeon.actionManager.addToBottom(new ExhaustCurseThenActivateAction(targetAction, NAME));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DarkBarrier();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_INTANGIBLE_AMOUNT);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
