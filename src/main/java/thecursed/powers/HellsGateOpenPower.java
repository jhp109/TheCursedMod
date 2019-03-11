package thecursed.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import thecursed.cards.attack.*;
import thecursed.cards.skill.CursedAmulet;
import thecursed.cards.skill.CursedArmor;
import thecursed.cards.skill.CursedSpellbook;

import java.util.ArrayList;

public class HellsGateOpenPower extends AbstractTheCursedPower {
    public static final String POWER_ID = "TheCursedMod:HellsGateOpenPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "hellsgateopen.png";

    private ArrayList<AbstractCard> cursedCardPool;

    public HellsGateOpenPower(AbstractCreature owner, int amount) {
        super(IMG);
        this.owner = owner;
        this.amount = amount;

        this.name = NAME;
        this.ID = POWER_ID;
        this.type = POWER_TYPE;

        this.cursedCardPool = new ArrayList<>();
        this.cursedCardPool.add(new CursedBlade());
        this.cursedCardPool.add(new CursedBoomerang());
        this.cursedCardPool.add(new CursedMace());
        this.cursedCardPool.add(new CursedRelics());
        this.cursedCardPool.add(new CursedShiv());
        this.cursedCardPool.add(new CursedStaff());
        this.cursedCardPool.add(new CursedWand());
        this.cursedCardPool.add(new CursedAmulet());
        this.cursedCardPool.add(new CursedArmor());
        this.cursedCardPool.add(new CursedSpellbook());

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void atStartOfTurn() {
        this.flash();
        for (int i = 0; i < this.amount; ++i) {
            AbstractCard card =
                    this.cursedCardPool
                            .get(AbstractDungeon.cardRandomRng.random(this.cursedCardPool.size() - 1))
                            .makeCopy();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
