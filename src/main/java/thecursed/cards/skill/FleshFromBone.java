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
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import thecursed.TheCursedMod;
import thecursed.actions.ExhaustCurseThenActivateAction;
import thecursed.cards.TheCursedCardTags;
import thecursed.enums.AbstractCardEnum;

public class FleshFromBone extends CustomCard {
    public static final String ID = "TheCursedMod:FleshFromBone";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/fleshfrombone.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private static final int PLATE_ARMOR = 4;
    private static final int PLATE_ARMOR_AMOUNT = 2;

    public FleshFromBone() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = PLATE_ARMOR;
        this.tags.add(TheCursedCardTags.RITUAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction targetAction =
                new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber);
        AbstractDungeon.actionManager.addToBottom(new ExhaustCurseThenActivateAction(targetAction, NAME));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FleshFromBone();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(PLATE_ARMOR_AMOUNT);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
