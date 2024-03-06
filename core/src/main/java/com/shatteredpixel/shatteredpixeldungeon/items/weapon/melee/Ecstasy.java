package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Ecstasy extends Gun {
//권총
    {
        max_round = 4;
        round = max_round;
        shootingSpeed = 0.5f;
        image = ItemSpriteSheet.EGO_WEB_ECSTASY;

        tier = 4;
        grade = "Waw";
    }

    @Override
    public int buffedLvl() {    //드워프 흑마법사의 디버프가 정상 작동하는지 확인 필수
        int lvl = super.buffedLvl();

        return lvl;
    }

    @Override
    public int maxRound() {
        int amount = super.maxRound();

        return amount;
    }

    @Override
    public int bulletMax(int lvl) {
        return 3 * (tier + 1) +
                lvl * (tier + 1) +
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
        return new HGBullet();
    }

    public class HGBullet extends Bullet {
        {
            image = ItemSpriteSheet.SINGLE_BULLET;
        }

        @Override
        public float accuracyFactor(Char owner, Char target) {
            float ACC = super.accuracyFactor(owner, target);

            return ACC;
        }
    }
}
