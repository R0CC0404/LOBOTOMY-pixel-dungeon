package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.AR;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
public class AR extends Gun {

    {
        max_round = 4;
        round = max_round;
    }

    public int maxRound() { //최대 장탄수
        int amount = super.maxRound();

        return amount;
    }

    @Override
    public int bulletMax(int lvl) {
        return 4 * (tier+1) +
                lvl * (tier+1) +
                RingOfSharpshooting.levelDamageBonus(hero);
    }

    @Override
    public int STRReq(int lvl) {
        int req = super.STRReq(lvl);

        return req;
    }

    @Override
    public float reloadTime() { //재장전에 소모하는 턴
        float amount = super.reloadTime();

        amount *= 1 ;

        return amount;
    }

    @Override
    public Bullet knockBullet(){
        return new ARBullet();
    }

    public class ARBullet extends Bullet {
        {
            image = ItemSpriteSheet.SINGLE_BULLET;
        }

        @Override
        public float accuracyFactor(Char owner, Char target) {
            float ACC = super.accuracyFactor(owner, target);

            return ACC;
        }

        @Override
        protected void onThrow( int cell ) {
            super.onThrow(cell);

        }
    }
}
