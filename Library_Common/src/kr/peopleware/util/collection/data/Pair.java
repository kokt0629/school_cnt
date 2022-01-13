package kr.peopleware.util.collection.data;

/**
 * 
 * @author shin
 *
 * @param <F> Object type of first value;
 * @param <S> Object type of second value;
 */
public class Pair<F,S>{

	private F first;
	private S second;	
	
	public Pair(){
		;
	}
	
	public Pair(F first,S second){
		this.first = first;
		this.second = second;
	}
	
	public F getFirst() {
		return first;
	}
	public void setFirst(F first) {
		this.first = first;
	}
	public S getSecond() {
		return second;
	}
	public void setSecond(S second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "Pair [first=" + first + ", second=" + second + "]";
	}
	
}
