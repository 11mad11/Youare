package fr.mad.youare.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;

import fr.mad.youare.damage.Damage.Effect;

public class EffectsC implements Component {
	public Array<Effect> effects = new Array<>();
}
