package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.MG;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.gun.Gun;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
public class MG extends Gun {
//기관총
    private boolean riot = false;
    private boolean shootAll = false;

    {
        max_round = 4;
        round = max_round;
        reload_time = 3f;
        shotPerShoot = 3;
        shootingAccuracy = 0.7f;
    }

    private static final String RIOT = "riot";
    private static final String SHOOTALL = "shootAll";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(RIOT, riot);
        bundle.put(SHOOTALL, shootAll);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        riot = bundle.getBoolean(RIOT);
        shootAll = bundle.getBoolean(SHOOTALL);
    }

    public static final String AC_RIOT	 = "RIOT";
    public static final String AC_SHOOTALL = "SHOOTALL";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);

        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

        if (action.equals(AC_RIOT)) {
            riot = !riot;
            hero.sprite.operate(hero.pos);
            Sample.INSTANCE.play(Assets.Sounds.UNLOCK);

        }
        if (action.equals(AC_SHOOTALL)) {
            shootAll = !shootAll;
            hero.sprite.operate(hero.pos);
            Sample.INSTANCE.play(Assets.Sounds.UNLOCK);

        }
    }

    @Override
    public int max(int lvl) {
        int damage = super.max(lvl);
        if (isEquipped(hero)) {
            damage *= 1f;
        }
        return damage;
    }

    @Override
    public int bulletMin(int lvl) {
        return tier +
                lvl/2 +
                RingOfSharpshooting.levelDamageBonus(hero);
    }

    @Override
    public int bulletMax(int lvl) {
        return 3 * (tier+1) +
                lvl * (tier+1) +
                RingOfSharpshooting.levelDamageBonus(hero);
    }

    @Override
    public float reloadTime() { //재장전에 소모하는 턴
        float amount = super.reloadTime();

        amount *= 1 ;

        return amount;
    }

    @Override
    public int STRReq(int lvl) {
        int req = super.STRReq(lvl);

        return req;
    }

    @Override
    public Bullet knockBullet(){
        return new MGBullet();
    }

    public class MGBullet extends Bullet {
        {
            image = ItemSpriteSheet.TRIPLE_BULLET;
        }

        @Override
        protected void onThrow( int cell ) {
            if (shootAll) {
                shotPerShoot = (round) * 3;
                Char ch = Actor.findChar(cell);

                super.onThrow(cell);
                if (ch != null && !ch.isAlive() && Random.Float() < 0.4f) {
                    round = maxRound();
                } else {
                    round = 0;
                }
                shotPerShoot = 3;
            } else {
                super.onThrow(cell);
            }
        }

        @Override
        public void cast(final Hero user, final int dst) {
            if (riot) {
                int cell = throwPos( user, dst );
                riot(cell);
            } else {
                super.cast(user, dst);
            }
        }

        @Override
        public float accuracyFactor(Char owner, Char target) {
            float ACC = super.accuracyFactor(owner, target);

            if (shootAll) {
                ACC *= 0.5f;
            }
            return ACC;
        }

        private void riot(int cell) {
            Ballistica aim = new Ballistica(hero.pos, cell, Ballistica.WONT_STOP);

            int maxDist = 6;
            int dist = Math.min(aim.dist, maxDist);

            ConeAOE cone = new ConeAOE(aim,
                    dist,
                    75,
                    Ballistica.STOP_SOLID | Ballistica.STOP_TARGET);

            Invisibility.dispel();
            hero.sprite.zap(cell);

            ArrayList<Char> affected = new ArrayList<>();
            for (int c : cone.cells){
                CellEmitter.get(c).burst(SmokeParticle.FACTORY, 2);
                CellEmitter.center(c).burst(BlastParticle.FACTORY, 2);
                Char ch = Actor.findChar(c);
                if (ch != null && ch.alignment != hero.alignment){
                    affected.add(ch);
                }
            }
            float multi = 1f;
            multi += 0.1f * affected.size();
            multi = Math.min(multi, 2.5f);


            for (Char ch : affected) {
                curUser.shoot(ch, MGBullet.this, multi, 0, multi);

                if (Random.Float() <  ((5-Dungeon.level.distance(hero.pos, ch.pos)) * 5) / 100f) { //(5-거리)*5*n%
                    Ballistica trajectory = new Ballistica(hero.pos, ch.pos, Ballistica.STOP_TARGET);
                    trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size()-1), Ballistica.PROJECTILE);
                    WandOfBlastWave.throwChar(ch,
                            trajectory,
                            1+dist-Dungeon.level.distance(hero.pos, ch.pos), //현재 위치로부터 발사 거리까지의 남은 타일 수
                            false,
                            true,
                            MGBullet.this);
                }
            }

            hero.spendAndNext(delayFactor(hero));
            updateQuickslot();
            round --;
            Sample.INSTANCE.play( Assets.Sounds.HIT_CRUSH, 1, Random.Float(0.33f, 0.66f) );
        }
    }
}
