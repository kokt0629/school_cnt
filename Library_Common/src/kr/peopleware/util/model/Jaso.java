package kr.peopleware.util.model;

public class Jaso {
	private String cho,jung,jong;
	private int choIndex,jungIndex,jongIndex;
	public Jaso(char cho,int choIndex, char jung,int jungIndex, char jong, int jongIndex){
		setCho("");
		setJung("");
		setJong("");
		
		this.cho += cho;
		this.jung += jung;
		this.jong += jong;
		
		setChoIndex(choIndex);
		setJungIndex(jungIndex);
		setJongIndex(jongIndex);
	}	
	public String getJong() {
		return jong;
	}

	public void setJong(String jong) {
		this.jong = jong;
	}

	public String getJung() {
		return jung;
	}

	public void setJung(String jung) {
		this.jung = jung;
	}

	public String getCho() {
		return cho;
	}

	public void setCho(String cho) {
		this.cho = cho;
	}	
	public int getChoIndex() {
		return choIndex;
	}
	public void setChoIndex(int choIndex) {
		this.choIndex = choIndex;
	}
	public int getJungIndex() {
		return jungIndex;
	}
	public void setJungIndex(int jungIndex) {
		this.jungIndex = jungIndex;
	}
	public int getJongIndex() {
		return jongIndex;
	}
	public void setJongIndex(int jongIndex) {
		this.jongIndex = jongIndex;
	}	
}
