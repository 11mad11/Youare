package fr.mad.youare.system.game.map;

public enum MapOrientation {
	Staggered("staggered"),Orthogonal("orthogonal"),Isometric("isometric");
	
	public final String type;

	private MapOrientation(String type) {
		this.type = type;
	}
}
