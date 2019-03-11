package thecursed.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thecursed.actions.ApplyBleedAction;

public class HemoplaguePower extends AbstractTheCursedPower {
    public static final String POWER_ID = "TheCursedMod:HemoplaguePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "hemoplague.png";

    public HemoplaguePower(AbstractCreature owner, int amount) {
        super(IMG);
        this.owner = owner;
        this.amount = amount;

        this.name = NAME;
        this.ID = POWER_ID;
        this.type = POWER_TYPE;

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractDungeon.getMonsters().monsters.stream()
                    .filter(m -> !m.isDead && !m.isDying)
                    .forEach(this::applyBleedPower);
        }
    }

    private void applyBleedPower(AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyBleedAction(m, this.owner, this.amount));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
