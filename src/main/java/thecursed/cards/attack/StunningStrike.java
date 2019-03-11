package thecursed.cards.attack;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
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

public class StunningStrike extends CustomCard {
    public static final String ID = "TheCursedMod:StunningStrike";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/stunningstrike.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 3;
    private static final int DAMAGE = 20;
    private static final int UPGRADE_BONUS = 8;
    private static final int STUN_DURATION = 1;

    public StunningStrike() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);

        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = STUN_DURATION;
        this.exhaust = true;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (m.intent == AbstractMonster.Intent.ATTACK
                || m.intent == AbstractMonster.Intent.ATTACK_BUFF
                || m.intent == AbstractMonster.Intent.ATTACK_DEBUFF
                || m.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
            AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m, p, this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new StunningStrike();
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