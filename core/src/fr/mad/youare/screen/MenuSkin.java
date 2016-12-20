package fr.mad.youare.screen;

import javax.swing.JFileChooser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

import fr.mad.youare.ressource.Ressource;

public class MenuSkin {
	public static final ObjectMap<String, Color> colors = new ObjectMap<>();
	public static final ObjectMap<String, BitmapFont> fonts = new ObjectMap<>();
	
	public static Skin getSkin(Ressource r) {
		Skin skin = new Skin();
		colors.put("white", Color.WHITE);
		color(skin);
		fonts.put("default", new BitmapFont());
		font(skin);
		defaut(skin);
		r.addToDispose(custom(skin));
		r.addToDispose(skin);
		return skin;
	}
	
	public static TextureAtlas custom(Skin skin) {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/menu/test.pack"));
		
		skin.addRegions(atlas);
		
		skin.add("default",new Touchpad.TouchpadStyle(skin.getDrawable("blank_silver"),skin.getDrawable("white")));
		
		return atlas;
	}

	private static void defaut(Skin skin) {
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		
		LabelStyle ls = new LabelStyle(skin.getFont("default"), Color.WHITE);

		TextFieldStyle tfs = new TextFieldStyle();
		tfs.font = skin.getFont("default");
		tfs.fontColor = Color.WHITE;
		
		skin.add("default", textButtonStyle);
		skin.add("default", ls);
		skin.add("default", tfs);
		
	}
	
	private static void font(Skin skin) {
		for (Entry<String, BitmapFont> e : fonts) {
			skin.add(e.key, e.value);
		}
	}
	
	private static void color(Skin skin) {
		for (Entry<String, Color> e : colors) {
			Pixmap pixmap = new Pixmap(10, 10, Format.RGBA8888);
			pixmap.setColor(e.value);
			pixmap.fill();
			skin.add(e.key, new Texture(pixmap));
		}
	}
	
}
