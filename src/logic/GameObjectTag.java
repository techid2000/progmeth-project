package logic;

public class GameObjectTag {
	
	public static final long UNDEFINED = 0;
	public static final long OVERLAY = 1;
	public static final long BLOCK = 2;
	
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