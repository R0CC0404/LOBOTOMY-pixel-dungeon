package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Magicbullet extends Gun {
//저격소총
    {
        max_round = 1;
        round = max_round;
        shootingAccuracy = 2f;

        image = ItemSpriteSheet.EGO_WEB_MAGIC_BULLET;
        tier = 4;
        grade = "Waw";
    }

    @Override
    public int bulletMin(int lvl) {
        return tier +
                lvl +
                RingOfSharpshooting.levelDamageBonus(hero);
    }

    @Override
    public int bulletMax(int lvl) {
        return 6 * (tier+1) +
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

        return amount;
    }

    @Override
    public Bullet knockBullet(){
        return new SRBullet();
    }

    public class SRBullet extends Bullet {
        {
            image = ItemSpriteSheet.SNIPER_BULLET;
        }

        @Override
        public int damageRoll(Char owner) {
            if (owner instanceof Hero) {
                Hero hero = (Hero)owner;
                Char enemy = hero.enemy();
                if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
                    //deals 25% toward max to max on surprise, instead of min to max.
                    int diff = bulletMax() - bulletMin();
                    int damage = augment.damageFactor(Random.NormalIntRange(
                            bulletMin(),
                            bulletMax()));
                    int exStr = hero.STR() - STRReq();
                    if (exStr > 0) {
                        damage += Random.IntRange(0, exStr);
                    }
                    return damage;
                }
            }
            return super.damageRoll(owner);
        }
    }


}
