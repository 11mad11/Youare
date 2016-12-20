package fr.mad.youare.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Transform;

public class SpriteC implements Component {
	
	public Sprite sprite;
	private float offx = 0;
	private float offy = 0;
	private float offr = 0;
	
	public SpriteC(Sprite s) {
		sprite = s;
	}
	
	public SpriteC(Sprite s,float offx,float offy,float offr) {
		this(s);
		this.offx = offx;
		this.offy = offy;
		this.offr = offr;
	}
	
	public void apply(Transform t) {
		sprite.setPosition(t.getPosition().x+offx, t.getPosition().y+offy);
		sprite.setRotation(t.getRotation()+offr );
	}
	
	public static SpriteC debug(Body b) {
		
		return null;
	}
	
	public static SpriteC debug(Fixture f) {
		Shape shape = f.getShape();
		//TODO other shape (not necessary for now)
		if (shape instanceof PolygonShape) {
			Vector2 l = null;
			Vector2 v = new Vector2();
			float minx = 0,miny = 0,maxx = 0,maxy = 0;
			
			for (int i = 0; i < ((PolygonShape) shape).getVertexCount(); i++) {
				((PolygonShape) shape).getVertex(i, v);
				minx = v.x<minx?v.x:minx;
				miny = v.y<miny?v.y:miny;
				maxx = v.x>maxx?v.x:maxx;
				maxy = v.y>maxy?v.y:maxy;
			}
			float rwidth = (float) Math.ceil(maxx-minx);
			int width = (int) Math.abs(Math.log10(rwidth)*10);
			rwidth = width/rwidth;
			float rheight = (float) Math.ceil(maxy-miny);
			int height = (int) Math.abs(Math.log10(rheight)*10);
			rheight = height/rheight;
			
			Pixmap p = new Pixmap(width, height, Format.RGBA8888);
			p.setColor(1, 0, 0, 1);
			
			((PolygonShape) shape).getVertex(0, v);
			for (int i = 1; i < ((PolygonShape) shape).getVertexCount(); i++) {
				l = v.cpy();
				((PolygonShape) shape).getVertex(i, v);
				p.drawLine((int)(l.x*rwidth), (int)(l.y*rheight), (int)(v.x*rwidth), (int)(v.y*rheight));
			}
			((PolygonShape) shape).getVertex(0, l);
			p.drawLine((int)(l.x*rwidth), (int)(l.y*rheight), (int)(v.x*rwidth), (int)(v.y*rheight));
			
			return new SpriteC(new Sprite(new Texture(p, false)));
		}
		return null;
	}
	
}
