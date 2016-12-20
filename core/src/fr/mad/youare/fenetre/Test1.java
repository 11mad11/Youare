package fr.mad.youare.fenetre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fr.mad.youare.fenetre.Frame.Pane;

public class Test1 extends Pane {
	ShapeRenderer sr = new ShapeRenderer();
	private Texture tex;
	private Array<String> l = new Array<>();
	
	public Test1() {
		tex = new Texture(ran());
	}
	
	private String ran() {
		FileHandle[] list = Gdx.files.absolute(Gdx.files.internal("").file().getAbsolutePath()).list();
		System.out.println();
		ran(list);
		l.shuffle();
		return l.first();
	}
	
	private void ran(FileHandle[] list) {
		for (FileHandle f : list) {
			System.out.println(f.path());
			ran(f.list());
			if(f.exists()&&!f.isDirectory()){
				//System.out.println(f.extension());
				if(f.extension().equals("png"))
					l.add(f.path());
			}
		}
	}
	
	@Override
	protected Viewport initVP() {
		return new ExtendViewport(1, 1);
	}
	
	@Override
	protected void render(float v) {
		batch.draw(tex, 0, 0, 1, 1);
	}

	@Override
	public void dispose() {
		tex.dispose();
	}
	
}
