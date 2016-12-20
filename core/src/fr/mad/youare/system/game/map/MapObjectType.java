package fr.mad.youare.system.game.map;

public enum MapObjectType {
	NPC("npc"),Spawn("spawn"),Portal("portal");
	
	public final String type;

	private MapObjectType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
