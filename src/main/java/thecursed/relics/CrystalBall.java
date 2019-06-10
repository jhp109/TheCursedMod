package thecursed.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thecursed.TheCursedMod;
import thecursed.cards.CurseUtil;

public class CrystalBall extends CustomRelic {
    public static final String ID = "TheCursedMod:CrystalBall";
    public static final String IMG_PATH = "relics/crystalball.png";
    public static final String IMG_PATH_LARGE = "relics/crystalball_large.png";
    public static final String OUTLINE_IMG_PATH = "relics/crystalball_outline.png";

    private static final RelicTier TIER = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int DAMAGE = 5;

    public CrystalBall() {
        super(ID, new Texture(TheCursedMod.getResourcePath(IMG_PATH)),
                new Texture(TheCursedMod.getResourcePath(OUTLINE_IMG_PATH)), TIER, SOUND);
        this.largeImg = ImageMaster.loadImage(TheCursedMod.getResourcePath(IMG_PATH_LARGE));
    }

    @Override
    public void onExhaust(AbstractCard c) {
        if (!CurseUtil.isCurse(c)) {
            return;
        }

        AbstractMonster m =
                AbstractDungeon.getMonsters().getRandomMonster(
                        null, true, AbstractDungeon.cardRandomRng);
        if (m == null) {
            return;
        }

        CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                        m, new DamageInfo(AbstractDungeon.player, DAMAGE, DamageInfo.DamageType.THORNS),
                        AbstractGameAction.AttackEffect.FIRE));

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + DAMAGE + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CrystalBall();
    }

}
