package fr.mad.youare.components;

import com.badlogic.ashley.core.ComponentMapper;

public class Components {
	public static final ComponentMapper<TransformC> transformC = ComponentMapper.getFor(TransformC.class);
	public static final ComponentMapper<EffectsC> effectsC = ComponentMapper.getFor(EffectsC.class);
	public static final ComponentMapper<BodyC> bodyC = ComponentMapper.getFor(BodyC.class);
	public static final ComponentMapper<SpriteC> spriteC = ComponentMapper.getFor(SpriteC.class);
	public static final ComponentMapper<AnimtionC> animationC = ComponentMapper.getFor(AnimtionC.class);
	public static final ComponentMapper<ActionC> actionC = ComponentMapper.getFor(ActionC.class);
	public static final ComponentMapper<MovementC> movementC = ComponentMapper.getFor(MovementC.class);
	public static final ComponentMapper<InputsC> inputsC = ComponentMapper.getFor(InputsC.class);
	public static final ComponentMapper<IDC> idC = ComponentMapper.getFor(IDC.class);
}
