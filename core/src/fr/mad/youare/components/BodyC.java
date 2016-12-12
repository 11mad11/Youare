package fr.mad.youare.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

public class BodyC implements Component {
	public BodyDef bodyDef = new BodyDef();
	public ObjectMap<FixtureDef,Shape2D> fixtureDefs = new ObjectMap<>();
	public Body body;
	public boolean keepDef = true;
	public boolean generateSpriteC;
	
	public void begin() {
		for (Entry<FixtureDef, Shape2D> entry : fixtureDefs) {
			Shape2D o = entry.value;
			FixtureDef fdef = entry.key;
			if (o instanceof Rectangle) {
				fdef.shape = rectToPoly((Rectangle) o);
			} else if (o instanceof Polygon) {
				PolygonShape p = new PolygonShape();
				p.set(((Polygon) o).getVertices());
				fdef.shape = p;
			} else if (o instanceof PolylineMapObject) {
				//TODO
			} else if (o instanceof CircleMapObject) {
				//TODO
			}
		}
	}
	
	public void end() {
		for (Entry<FixtureDef, Shape2D> entry : fixtureDefs) {
			FixtureDef fdef = entry.key;
			fdef.shape = null;
		}
	}
	
	public static PolygonShape rectToPoly(Rectangle r) {
		PolygonShape p = new PolygonShape();
		float v1 = r.x;
		float v2 = r.y;
		float v3 = r.x + r.width;
		float v4 = r.y + r.height;
		p.set(new float[] { v1, v2, v3, v2, v3, v4, v1, v4 });
		return p;
	}
	
	public static BodyC square(float x, float y, float width, float height) {
		BodyC c = new BodyC();
		c.bodyDef.type = BodyType.DynamicBody;
		c.bodyDef.position.set(x, y);
		FixtureDef f = new FixtureDef();
		Polygon p = new Polygon();
		float v1 = x-(width/2);
		float v2 = y-(height/2);
		float v3 = x + width/2;
		float v4 = y + height/2;
		p.setVertices(new float[] { v1, v2, v3, v2, v3, v4, v1, v4 });
		c.fixtureDefs.put(f,p);
		return c;
	}
}
