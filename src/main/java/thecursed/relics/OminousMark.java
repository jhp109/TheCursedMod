package thecursed.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thecursed.TheCursedMod;
import thecursed.cards.CurseUtil;

public class OminousMark extends CustomRelic {
    public static final String ID = "TheCursedMod:OminousMark";
    public static final String IMG_PATH = "relics/ominousmark.png";
    public static final String IMG_PATH_LARGE = "relics/ominousmark_large.png";
    public static final String OUTLINE_IMG_PATH = "relics/ominousmark_outline.png";

    private static final RelicTier TIER = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int CURSES_ADD_AMOUNT = 2;


    public OminousMark() {
        super(ID, new Texture(TheCursedMod.getResourcePath(IMG_PATH)),
                new Texture(TheCursedMod.getResourcePath(OUTLINE_IMG_PATH)), TIER, SOUND);
        this.largeImg = ImageMaster.loadImage(TheCursedMod.getResourcePath(IMG_PATH_LARGE));
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        for (int i = 0; i < CURSES_ADD_AMOUNT; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new MakeTempCardInDrawPileAction(
                            CurseUtil.getRandomCurseCard(), 1, true, true));
        }
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CURSES_ADD_AMOUNT + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OminousMark();
    }

}
