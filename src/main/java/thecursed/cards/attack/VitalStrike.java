package thecursed.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thecursed.TheCursedMod;
import thecursed.enums.AbstractCardEnum;
import thecursed.powers.BleedPower;

public class VitalStrike extends CustomCard {
    public static final String ID = "TheCursedMod:VitalStrike";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/vitalstrike.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_BONUS = 3;


    public VitalStrike() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);

        this.baseDamage = this.damage = DAMAGE;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(p, this.damage, this.damageTypeForTurn);
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, info, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        if (mo.hasPower(BleedPower.POWER_ID)) {
            this.damage *= 2;
            this.isDamageModified = true;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new VitalStrike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}