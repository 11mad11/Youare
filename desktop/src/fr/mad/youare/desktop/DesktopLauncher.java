package fr.mad.youare.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryonet.KryoSerialization;
import com.esotericsoftware.minlog.Log;

import fr.mad.youare.SpeedBox2DTest;
import fr.mad.youare.YouAre;
import fr.mad.youare.components.BodyC;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		new LwjglApplication(new YouAre(), config);
	}
}
