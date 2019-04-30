package logic;

public class GameObjectTag {
	
	public static final long EVERYTHING = Long.MAX_VALUE;
	public static final long OVERLAY = 1;
	public static final long BLOCK = 2;
	public static final long LOOT = 4;
	
	private long tag;
	
	public void addTag(long tag) {
		this.tag |= tag;
	}
	
	public void removeTag(long tag) {
		if((this.tag & tag) > 0) {
			this.tag -= tag;
		}
	}
	
	public boolean contains(long tag) {
		return (this.tag & tag) > 0;
	}
}