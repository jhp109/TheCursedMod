package thecursed.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thecursed.TheCursedMod;
import thecursed.cards.CurseUtil;

public class BlackMagic101 extends CustomRelic {
    public static final String ID = "TheCursedMod:BlackMagic101";
    public static final String IMG_PATH = "relics/blackmagic101.png";
    public static final String IMG_PATH_LARGE = "relics/blackmagic101_large.png";
    public static final String OUTLINE_IMG_PATH = "relics/blackmagic101_outline.png";

    private static final RelicTier TIER = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int DRAW = 1;

    private boolean drawThisTurn = false;

    public BlackMagic101() {
        super(ID, new Texture(TheCursedMod.getResourcePath(IMG_PATH)),
                new Texture(TheCursedMod.getResourcePath(OUTLINE_IMG_PATH)), TIER, SOUND);
        this.largeImg = ImageMaster.loadImage(TheCursedMod.getResourcePath(IMG_PATH_LARGE));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        if (CurseUtil.hasCurse(AbstractDungeon.player.drawPile.group)) {
            this.flash();
            this.drawThisTurn = true;
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        if (this.drawThisTurn) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, DRAW));
            this.drawThisTurn = false;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BlackMagic101();
    }
}
