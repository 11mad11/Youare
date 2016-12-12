package fr.mad.youare.screen;

import static com.esotericsoftware.minlog.Log.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.FrameworkMessage.DiscoverHost;

import fr.mad.actor.Map;
import fr.mad.youare.YouAre;

public class Menu implements Screen {
	
	private Skin skin;
	private Stage stage;
	private Table table;
	private YouAre youAre;
	private ScreenViewport vp;
	private Thread tlan;
	private TextField ip;
	//private Map map;
	
	public Menu(final YouAre youAre) {
		this.youAre = youAre;
		vp = new ScreenViewport();
		skin = MenuSkin.getSkin(youAre.ressource);
		stage = new Stage();
		table = new Table();
		ip = new TextField("ip:port", skin);
		TextButton play = new TextButton("Play", skin);
		TextButton exit = new TextButton("Exit", skin);
		
		play.getCell(play.getLabel()).pad(5);
		exit.getCell(exit.getLabel()).pad(5);
		
		table.setFillParent(true);
		table.setDebug(true);
		table.center();
		table.add(ip).pad(5).fillX().row();
		table.add(play).pad(5).fillX().row();
		table.add(exit).pad(5).fillX().row();
		
		//table.add(new Touchpad(10, skin)).size(100);
		
		//stage.addActor(map = new Map(new TmxMapLoader().load("home.tmx")));
		stage.addActor(table);
		stage.setViewport(vp);
		Gdx.input.setInputProcessor(stage);
		
		play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tlan.stop();
				InetAddress addr;
				int port;
				try {
					addr = InetAddress.getByName(ip.getText().split(":")[0]);
					try {
						port = Integer.parseInt(ip.getText().split(":")[1]);
					} catch (Throwable e) {
						port = 1535;
					}
				} catch (Exception e) {
					addr=null;
					port=0;
				}
				youAre.setScreen(new PlayScreen(youAre,addr,port));
			}
		});
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				youAre.dispose();
				tlan.stop();
				System.exit(0);
			}
		});
		listenServerLocal();
	}
	
	private void listenServerLocal() {
		tlan = new Thread() {
			@Override
			public void run() {
				while (ip.getText().equals("ip:port")) {
					InetAddress a = discoverHost(1536, 1000);
					if (a != null) {
						if (ip.getText().equals("ip:port"))
							ip.setText(a.getHostAddress());
						break;
					}
				}
			}
			
			private Client c = new Client();
			
			private InetAddress discoverHost(int port, int timeout) {
				DatagramSocket socket = null;
				try {
					socket = new DatagramSocket();
					broadcast(port, socket);
					socket.setSoTimeout(timeout);
					DatagramPacket packet = new DatagramPacket(new byte[0], 0);
					try {
						socket.receive(packet);
					} catch (SocketTimeoutException ex) {
						if (INFO) info("mkryonet", "Host discovery timed out.");
						return null;
					}
					if (INFO) info("mkryonet", "Discovered server: " + packet.getAddress());
					return packet.getAddress();
				} catch (IOException ex) {
					if (ERROR) error("mkryonet", "Host discovery failed.", ex);
					return null;
				} finally {
					if (socket != null) socket.close();
				}
			}

			private void broadcast(int port, DatagramSocket socket) throws SocketException {
				ByteBuffer dataBuffer = ByteBuffer.allocate(64);
				c.getSerialization().write(null, dataBuffer, new DiscoverHost());
				dataBuffer.flip();
				byte[] data = new byte[dataBuffer.limit()];
				dataBuffer.get(data);
				for (NetworkInterface iface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
					for (InterfaceAddress address : iface.getInterfaceAddresses()) {
						if(address.getBroadcast()==null)
							continue;
						try {
							socket.send(new DatagramPacket(data, data.length,address.getBroadcast(), port));
						} catch (Exception ignored) {
						}
					}
				}
				if (DEBUG) debug("mkryonet", "Broadcasted host discovery on port: " + port);
			}
		};
		tlan.start();
	}

	@Override
	public void show() {
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		vp.apply();
		stage.act();
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		vp.update(width, height, true);
		//map.setSize(stage.getWidth(), stage.getHeight());
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
