package thecursed.cards.power;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thecursed.TheCursedMod;
import thecursed.cards.CurseUtil;
import thecursed.enums.AbstractCardEnum;
import thecursed.powers.VoidFormPower;

public class VoidForm extends CustomCard {
    public static final String ID = "TheCursedMod:VoidForm";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/voidform.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 3;
    private static final int STRENGTH = 2;
    private static final int UPGRADE_STRENGTH_BONUS = 1;

    public VoidForm() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = STRENGTH;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInDrawPileAction(CurseUtil.getRandomCurseCard(), 1, true, true));
        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInDrawPileAction(CurseUtil.getRandomCurseCard(), 1, true, true));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new VoidFormPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new VoidForm();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_STRENGTH_BONUS);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
