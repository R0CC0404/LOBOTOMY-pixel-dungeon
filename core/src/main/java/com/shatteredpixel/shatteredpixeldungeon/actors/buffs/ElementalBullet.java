package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.elementals.APBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.elementals.ElectricBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.elementals.ExplosiveBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.elementals.IceBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.elementals.IncendiaryBullet;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.HeroIcon;


import com.watabou.utils.Bundle;
public abstract class ElementalBullet extends Buff implements ActionIndicator.Action {

    float duration = 0;

    {
        type = buffType.NEUTRAL;
        announced = false;
    }

    @Override
    public boolean act() {
        ActionIndicator.setAction(ElementalBullet.this);
        spend(TICK);
        duration--;
        if (duration < 0) {
            detach();
        }
        return true;
    }

    public void set(float amount) {
        duration = amount;
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        ActionIndicator.setAction(ElementalBullet.this);
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public void detach() {
        super.detach();
        ActionIndicator.clearAction();
    }

    public enum ElementalReload {
        AP_BULLET		    (0xC7C4C9),
        INCENDIARY_BULLET   (0xFFAA33),
        EXPLOSIVE_BULLET    (0xFF2A00),
        ICE_BULLET  	    (0x5674B9),
        ELECTRIC_BULLET	    (0xFFFFFF);

        public int tintColor;

        ElementalReload(int tintColor){
            this.tintColor = tintColor;
        }

        public String title(){
            return Messages.get(this, name() + ".name");
        }

        public String desc(){
            return Messages.get(this, name() + ".desc");
        }

    }




    //액션인디케이터 내용
    @Override
    public String actionName() {
        return Messages.get(this, "action_name");
    }



    @Override
    public int indicatorColor() {
        return 0xC7C4C9;
    }


}