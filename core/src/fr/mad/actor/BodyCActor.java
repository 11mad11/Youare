package fr.mad.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import fr.mad.youare.components.BodyC;

public class BodyCActor extends Actor {
	
	private BodyC bodyC;
	private ObjectMap<Fixture,Texture> texs = new ObjectMap<>();
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	public BodyCActor(BodyC bodyC) {
		this.bodyC = bodyC;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Array<Fixture> fs = bodyC.body.getFixtureList();
		for(Fixture f:fs){
			drawFisture(f);
		}
	}
	
	private void drawFisture(Fixture f) {
		if (!(f.getShape() instanceof PolygonShape))
			return;
		PolygonShape shape = (PolygonShape) f.getShape();
		Body box = f.getBody();
		Vector2[] vertices = new Vector2[shape.getVertexCount()];
		for (int i = 0; i < shape.getVertexCount(); i++) {
			vertices[i] = new Vector2();
			shape.getVertex(i, vertices[i]);
			box.getTransform().mul(vertices[i]);
		}
		for (int i = 0; i < shape.getVertexCount(); i++) {
			if (i + 1 < shape.getVertexCount())
				shapeRenderer.line(vertices[i], vertices[i + 1]);
		}
		shapeRenderer.line(vertices[shape.getVertexCount() - 1], vertices[0]);
	}
}
