package thecursed.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thecursed.TheCursedMod;

public class SoulVessel extends CustomRelic {
    public static final String ID = "TheCursedMod:SoulVessel";
    public static final String IMG_PATH = "relics/soulvessel.png";
    public static final String IMG_PATH_LARGE = "relics/soulvessel_large.png";
    public static final String OUTLINE_IMG_PATH = "relics/soulvessel_outline.png";

    private static final RelicTier TIER = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.SOLID;

    public SoulVessel() {
        super(ID, new Texture(TheCursedMod.getResourcePath(IMG_PATH)),
                new Texture(TheCursedMod.getResourcePath(OUTLINE_IMG_PATH)), TIER, SOUND);
        this.largeImg = ImageMaster.loadImage(TheCursedMod.getResourcePath(IMG_PATH_LARGE));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void setCounter(int c) {
        this.counter = c;
        this.updateDescriptionWithCounter();
    }

    @Override
    public void atBattleStart() {
        this.setCounter(0);
    }

    @Override
    public void onExhaust(AbstractCard c) {
        if (c.type == AbstractCard.CardType.CURSE) {
            this.setCounter(this.counter + 1);
        }
    }

    @Override
    public void onVictory() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0 && this.counter > 0) {
            this.flash();
            p.heal(this.counter);
        }
        this.setCounter(-1);
    }

    private void updateDescriptionWithCounter() {
        if (this.counter == 0) {
            this.description = this.DESCRIPTIONS[0];
        } else if (this.counter == 1) {
            this.description =
                    this.DESCRIPTIONS[0] + this.DESCRIPTIONS[1];
        } else {
            this.description =
                    this.DESCRIPTIONS[0] + this.DESCRIPTIONS[2] + this.counter + this.DESCRIPTIONS[3];
        }

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SoulVessel();
    }

}
