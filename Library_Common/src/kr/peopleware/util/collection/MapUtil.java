package kr.peopleware.util.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kr.peopleware.util.collection.data.Pair;

/**
 * 
 * @author shin285
 *
 */
public class MapUtil {
	public static int ASCENDING_ORDER = 1;
	public static int DESCENDING_ORDER = -1;	

	/**
	 * 맵을 value로 소팅하여 다시 맵으로 리턴
	 * @param map
	 * @param order Input MapUtil.ASCENDING_ORDER or MapUtil.DESCENDIN_ORDER
	 * @return Map
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> 
	sortByValue( Map<K, V> map ,final int order)
	{
		List<Map.Entry<K, V>> list =
				new LinkedList<Map.Entry<K, V>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>()
				{
			public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
			{            	
				if(order == ASCENDING_ORDER){
					return (o1.getValue()).compareTo( o2.getValue() );
				}
				else if(order == DESCENDING_ORDER){
					return (o2.getValue()).compareTo( o1.getValue() );
				}				
				return (o2.getValue()).compareTo( o1.getValue() );
			}
				} );

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list)
		{
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;
	}

	/**
	 * 맵의 키값을 리스트로 반환
	 * @param map
	 * @return List - 키 리스트
	 */
	public static <K, V> List<K> getKeys(Map<K,V> map){
		Iterator<K> it = map.keySet().iterator();
		List<K> result = new ArrayList<K>();
		while(it.hasNext()){
			result.add(it.next());
		}		
		return result;
	}

	/**
	 * 맵을 Pair 형태의 리스트로 반환
	 * @param map
	 * @return List<Pair>
	 */
	public static <K, V> List<Pair<K,V>> getListPair(Map<K,V> map){
		Iterator<K> it = map.keySet().iterator();
		List<Pair<K,V>> result = new ArrayList<Pair<K,V>>();
		while(it.hasNext()){
			K key = it.next();
			V value = map.get(key);
			result.add(new Pair<K, V>(key,value));
		}		
		return result;
	}


	/**
	 * 맵에 있는 데이터 출력
	 * @param map
	 */
	public static <V, K> void printMap(Map<K,V> map){
		Iterator<K> it = map.keySet().iterator();
		while(it.hasNext()){
			K key = it.next();
			V value = map.get(key);
			System.out.println("[key : "+key+", value : "+value);
		}
	}

	public static <K> Map<K,Double> scalingIntegerMap( Map<K, Integer> map){
		List<Map.Entry<K, Integer>> list =
				new LinkedList<Map.Entry<K, Integer>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, Integer>>()
				{
			public int compare( Map.Entry<K, Integer> o1, Map.Entry<K, Integer> o2 )
			{
				return (o2.getValue()).compareTo( o1.getValue() );	
			}
				} );

		Map<K, Double> result = new LinkedHashMap<K, Double>();
		
		int max = list.get(0).getValue();		
		int min = list.get(list.size()-1).getValue();
		
		for (Pair<K, Integer> pair : MapUtil.getListPair(map)) 
		{
			if(min < 0){
				result.put( pair.getFirst(), (pair.getSecond()-min)/(double)(max-min));
			}else{
				result.put( pair.getFirst(), pair.getSecond()/(double)(max+min));
			}
		}
		return result;
	}
	
	public static <K> Map<K,Double> scalingDoubleMap( Map<K, Double> map){
		List<Map.Entry<K, Double>> list =
				new LinkedList<Map.Entry<K, Double>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, Double>>()
				{
			public int compare( Map.Entry<K, Double> o1, Map.Entry<K, Double> o2 )
			{
				return (o2.getValue()).compareTo( o1.getValue() );	
			}
				} );

		Map<K, Double> result = new LinkedHashMap<K, Double>();
		
		Double max = list.get(0).getValue();		
		Double min = list.get(list.size()-1).getValue();		
		
		for (Pair<K, Double> pair : MapUtil.getListPair(map)) 
		{
			if(min < 0){				
				result.put( pair.getFirst(), (pair.getSecond()-min)/(double)(max-min));									
				
			}else{
				result.put( pair.getFirst(), pair.getSecond()/(double)(max+min));
			}
		}
		return result;
	}
	
	public static <K> Map<K,Integer> cutterIntegerMap(Map<K,Integer> map,int threshold){
		Map<K,Integer> resultMap = new HashMap<K,Integer>();
		List<Pair<K,Integer>> listPair = MapUtil.getListPair(map);
		for (Pair<K, Integer> pair : listPair) {
			if(pair.getSecond() >= threshold){
				resultMap.put(pair.getFirst(), pair.getSecond());
			}
		}
		return resultMap;
	}
	public static <K> Map<K,Double> cutterDoubleMap(Map<K,Double> map,double threshold){		
		Map<K,Double> resultMap = new HashMap<K,Double>();
		List<Pair<K,Double>> listPair = MapUtil.getListPair(map);
		for (Pair<K, Double> pair : listPair) {
			if(pair.getSecond() >= threshold){
				resultMap.put(pair.getFirst(), pair.getSecond());
			}
		}
		return resultMap;
	}
	
	public static <K> Map<K, Integer> getRankByIntegerValue(Map<K,Integer> map){
		List<Pair<K,Integer>> listPair = MapUtil.getListPair(MapUtil.sortByValue(map, DESCENDING_ORDER));
		Map<K,Integer> resultMap = new HashMap<K,Integer>();
		int rank = 1;
		int prevValue = 0;
		for(int i=0;i<listPair.size();i++){
			K key = listPair.get(i).getFirst();
			int value = listPair.get(i).getSecond();
			if(prevValue == value){
				resultMap.put(key, rank);
			}else{
				rank++;
				resultMap.put(key, rank);
			}
			prevValue = value;
		}
		return MapUtil.sortByValue(resultMap, ASCENDING_ORDER);
	}
	public static <K> Map<K, Integer> getRankByDoubleValue(Map<K,Double> map){
		List<Pair<K,Double>> listPair = MapUtil.getListPair(MapUtil.sortByValue(map, DESCENDING_ORDER));
		Map<K,Integer> resultMap = new HashMap<K,Integer>();
		int rank = 0;
		double prevValue = 0;
		for(int i=0;i<listPair.size();i++){
			K key = listPair.get(i).getFirst();
			double value = listPair.get(i).getSecond();
			if(prevValue == value){
				resultMap.put(key, rank);
			}else{
				rank++;
				resultMap.put(key, rank);
			}
			prevValue = value;
		}
		return MapUtil.sortByValue(resultMap, ASCENDING_ORDER);
	}
	
}
