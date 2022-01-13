package kr.peopleware.util.database.interfaces;

public interface DatabaseManager <K,V>{	
	void insert(K key,V value);
	void delete(K key);
	void update(K key,V value);
	V get(K key);
	void open();
	void close();
}
