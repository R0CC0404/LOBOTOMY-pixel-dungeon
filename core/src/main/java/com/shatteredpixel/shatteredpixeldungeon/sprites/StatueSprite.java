/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.TorchHalo;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.GameMath;

public class StatueSprite extends MobSprite {

	private TorchHalo light;

	private static TextureFilm nums;

	public StatueSprite() {
		super();
		
		texture( Assets.Sprites.OFFICER );

		TextureFilm film = new TextureFilm( nums(), Dungeon.hero.num(), 12, 15 );
		
		idle = new Animation( 1, true );
		idle.frames( film, 0, 0, 0, 1, 0, 0, 1, 1 );
		
		run = new Animation( 20, true );
		run.frames( film, 2, 3, 4, 5, 6, 7 );
		
		attack = new Animation( 15, false );
		attack.frames( film, 13, 14, 15, 0 );
		
		die = new Animation( 20, false );
		die.frames( film, 8, 9, 10, 11, 12, 11 );
		
		play( idle );
	}

	public static TextureFilm nums() {
		if (nums == null) {
			SmartTexture texture = TextureCache.get( Assets.Sprites.OFFICER );
			nums = new TextureFilm( texture, texture.width, 15 );
		}

		return nums;
	}

	private static int[] tierFrames = {0, 21, 32, 43, 54, 65};

	public void setArmor( int num ){
		int c = tierFrames[(int)GameMath.gate(0, num, 5)];

		TextureFilm frames = new TextureFilm( texture, 12, 15 );

		idle.frames( frames, 0+c, 0+c, 0+c, 1+c, 0+c, 0+c, 1+c, 1+c );
		run.frames( frames, 2+c, 3+c, 4+c, 5+c, 6+c, 7+c );
		attack.frames( frames, 8+c, 9+c, 10+c );
		//death animation is always armorless

		play( idle, true );

	}

	@Override
	public void link(Char ch) {
		super.link(ch);
		light = new TorchHalo( this );
		light.hardlight(blood() & 0x00FFFFFF);
		light.alpha(0.3f);
		light.radius(10);

		GameScene.effect(light);
	}

	@Override
	public int blood() {
		return 0xFFcdcdb7;
	}



}
