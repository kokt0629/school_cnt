package kr.peopleware.util.model;

public class Syllable {
	public static final int CHOSUNG = 0,JUNGSUNG = 1, JONGSUNG = 2, ETC = 3;
	
	private String syllabel;
	private int type;
	private int index;
	
	public Syllable(String syllabel,int type,int index){
		setSyllabel(syllabel);
		setType(type);
		setIndex(index);
	}
	
	public String getSyllabel() {
		return syllabel;
	}
	public void setSyllabel(String syllabel) {
		this.syllabel = syllabel;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Syllable [syllabel=" + syllabel + ", type=" + type + "]";
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
