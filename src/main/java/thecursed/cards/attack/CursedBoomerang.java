package thecursed.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thecursed.TheCursedMod;
import thecursed.cards.CurseUtil;
import thecursed.enums.AbstractCardEnum;

public class CursedBoomerang extends CustomCard {
    public static final String ID = "TheCursedMod:CursedBoomerang";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/cursedboomerang.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int NUM_ATTACKS = 3;
    private static final int UPGRADE_NUM_ATTACKS_BONUS = 1;

    public CursedBoomerang() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);

        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = NUM_ATTACKS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster randomMonster =
                AbstractDungeon.getMonsters().getRandomMonster(
                        null, true, AbstractDungeon.cardRandomRng);
        AbstractDungeon.actionManager.addToBottom(
                new SwordBoomerangAction(
                        randomMonster, new DamageInfo(p, this.baseDamage), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(CurseUtil.DREGS));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CursedBoomerang();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_NUM_ATTACKS_BONUS);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}