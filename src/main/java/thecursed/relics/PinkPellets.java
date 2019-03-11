package thecursed.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thecursed.TheCursedMod;

public class PinkPellets extends CustomRelic {
    public static final String ID = "TheCursedMod:PinkPellets";
    public static final String IMG_PATH = "relics/pinkpellets.png";
    public static final String IMG_PATH_LARGE = "relics/pinkpellets_large.png";
    public static final String OUTLINE_IMG_PATH = "relics/pinkpellets_outline.png";

    private static final RelicTier TIER = RelicTier.SHOP;
    private static final LandingSound SOUND = LandingSound.CLINK;

    private static final int STACK_AMOUNT = 5;

    public PinkPellets() {
        super(ID, new Texture(TheCursedMod.getResourcePath(IMG_PATH)),
                new Texture(TheCursedMod.getResourcePath(OUTLINE_IMG_PATH)), TIER, SOUND);
        this.largeImg = ImageMaster.loadImage(TheCursedMod.getResourcePath(IMG_PATH_LARGE));
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + STACK_AMOUNT + this.DESCRIPTIONS[1];
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
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(AbstractDungeon.player));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PinkPellets();
    }

}
