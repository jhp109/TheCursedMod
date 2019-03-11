package thecursed.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thecursed.TheCursedMod;

public class DemonicMark extends CustomRelic {
    public static final String ID = "TheCursedMod:DemonicMark";
    public static final String IMG_PATH = "relics/demonicmark.png";
    public static final String IMG_PATH_LARGE = "relics/demonicmark_large.png";
    public static final String OUTLINE_IMG_PATH = "relics/demonicmark_outline.png";

    private static final RelicTier TIER = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.SOLID;

    private static final int STACK_AMOUNT = 3;
    private static final int STRENGTH = 1;

    public DemonicMark() {
        super(ID, new Texture(TheCursedMod.getResourcePath(IMG_PATH)),
                new Texture(TheCursedMod.getResourcePath(OUTLINE_IMG_PATH)), TIER, SOUND);
        this.largeImg = ImageMaster.loadImage(TheCursedMod.getResourcePath(IMG_PATH_LARGE));
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + STACK_AMOUNT + this.DESCRIPTIONS[1] + STRENGTH + this.DESCRIPTIONS[2];
    }


    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    public void increaseCountAndMaybeActivate() {
        this.counter++;
        if (this.counter > 0 && this.counter % STACK_AMOUNT == 0) {
            this.counter = 0;

            AbstractPlayer p = AbstractDungeon.player;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p, this));
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTH), STRENGTH));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DemonicMark();
    }

}
