package thecursed.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thecursed.TheCursedMod;
import thecursed.cards.CurseUtil;

public class BlackMagicAdvanced extends CustomRelic {
    public static final String ID = "TheCursedMod:BlackMagicAdvanced";
    public static final String IMG_PATH = "relics/blackmagicadvanced.png";
    public static final String IMG_PATH_LARGE = "relics/blackmagicadvanced_large.png";
    public static final String OUTLINE_IMG_PATH = "relics/blackmagicadvanced_outline.png";

    private static final RelicTier TIER = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int DRAW = 2;

    private boolean drawThisTurn = false;

    public BlackMagicAdvanced() {
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
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BlackMagic101.ID);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(BlackMagic101.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(BlackMagic101.ID)) {
                    this.instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BlackMagicAdvanced();
    }
}
