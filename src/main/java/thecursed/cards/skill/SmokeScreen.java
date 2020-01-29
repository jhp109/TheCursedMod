package thecursed.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import thecursed.TheCursedMod;
import thecursed.actions.ExhaustCurseThenActivateAction;
import thecursed.cards.TheCursedCardTags;
import thecursed.enums.AbstractCardEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SmokeScreen extends CustomCard {
    public static final String ID = "TheCursedMod:SmokeScreen";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/smokescreen.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int BLOCK_UPGRADE_BONUS = 2;
    private static final int WEAK_DURATION = 2;
    private static final int WEAK_DURATION_UPGRADE_BONUS = 1;


    public SmokeScreen() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
        this.baseMagicNumber = this.magicNumber = WEAK_DURATION;
        this.tags.add(TheCursedCardTags.RITE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        List<AbstractGameAction> actions =
                AbstractDungeon.getMonsters().monsters.stream()
                        .filter(mo -> !mo.isDeadOrEscaped())
                        .map(mo -> new ApplyPowerAction(
                                mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber))
                        .collect(Collectors.toList());
        AbstractDungeon.actionManager.addToBottom(new ExhaustCurseThenActivateAction(new ArrayList(actions), NAME));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SmokeScreen();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(BLOCK_UPGRADE_BONUS);
            upgradeMagicNumber(WEAK_DURATION_UPGRADE_BONUS);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}