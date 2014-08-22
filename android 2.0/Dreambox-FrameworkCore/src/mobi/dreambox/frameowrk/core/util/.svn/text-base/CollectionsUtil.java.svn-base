package mobi.dreambox.frameowrk.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Title: Collections Util</p>
 *
 * <p>Description: Some common collections util methods.</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wonders Group</p>
 *
 * @author Ryan
 * @version 1.0
 */
public class CollectionsUtil {
	/**
	 * If a object array is null or every object is null of array, this array is null.
	 * 
	 * @param objects
	 * @return
	 */
	public static boolean isNull(Object objects[]) {
		if(objects == null || objects.length == 0)
			return true;
		
		boolean isNull = true;
		for(int i = 0; i < objects.length; i++)
			if(objects[i] != null) {
				isNull = false;
				break;
			}
		
		return isNull;
	}
	
    /**
     * If a collection is null or hasn't any element, it's null.
     *
     * @param collection Collection
     * @return boolean
     */
    public static boolean isNull(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * If a map is null or hasn't any element, it's null.
     *
     * @param map Map
     * @return boolean
     */
    public static boolean isNull(Map map) {
        return map == null || map.size() == 0;
    }

	/**
	 * If a object array is not null or any object is not null of array, this array is not null.
	 * 
	 * @param objects
	 * @return
	 */
	public static boolean isNotNull(Object objects[]) {
		if(objects == null || objects.length == 0)
			return false;
		
		for(int i = 0; i < objects.length; i++)
			if(objects[i] != null)
				return true;
		
		return false;
	}

    /**
     * If a collection is not null and has more than one element, it's not null.
     *
     * @param collection Collection
     * @return boolean
     */
    public static boolean isNotNull(Collection collection) {
        return collection != null && collection.size() > 0;
    }

    /**
     * If a map is not null and has more than one element, it's not null.
     *
     * @param map Map
     * @return boolean
     */
    public static boolean isNotNull(Map map) {
        return map != null && map.size() > 0;
    }

    /**
     * Check two collections has intersection or not.
     *
     * @param collection1 Collection
     * @param collection2 Collection
     * @return boolean
     */
    public static boolean hasIntersection(Collection collection1, Collection collection2) {
        return CollectionsUtil.intersect(collection1, collection2).size() > 0;
    }

    /**
     * Generate the intersection of two collections.
     *
     * @param collection1 Collection
     * @param collection2 Collection
     * @return Collection
     */
    public static Collection intersect(Collection collection1, Collection collection2) {
        Collection collection = new LinkedList();
        if(collection1 == null || collection2 == null ||
           collection1.isEmpty() || collection2.isEmpty())
            return collection;

        Iterator iterator = collection1.iterator();
        Object object = null;
        while(iterator.hasNext()){
            object = iterator.next();
            if(collection2.contains(object))
                collection.add(object);
        }

        return collection;
    }

    /**
     * Remove elements from a list by positions.
     *
     * @param list List A integer list.
     * @param positions List
     */
    public static void removeFromList(List list, List positions) {
        if(list == null || list.size() == 0 ||
           positions == null || positions.size() == 0)
            return;

        List tempList = list;
        list = new LinkedList();
        for(int i = 0; i < tempList.size(); i++)
            if(!positions.contains(new Integer(i)))
                list.add(tempList.get(i));
    }

    /**
     * Convert a map to string in format "key1 = value1, key2 = values...".
     * @param m Map
     * @return String
     */
    public static String mapToString(Map m) {
        String returnString = "";
        if(m == null || m.size() == 0)
            return returnString;

        Object keys[] = m.keySet().toArray(), value = null;
        for(int i = 0; i < keys.length; i++){
            value = m.get(keys[i]);
            if(value != null)
                returnString += keys[i] + " = " + value;
        }

        return returnString;
    }
    
    /**
     * Add all elements of minorCollection into mainCollection.
     * 
     * @param mainCollection
     * @param minorCollection
     */
    public static void addAllIfNotNull(Collection mainCollection, Collection minorCollection) {
    	if(mainCollection != null && minorCollection != null)
    		mainCollection.addAll(minorCollection);
    }

    /**
     * Put all objects into a new list, and the null values won't been ignored.
     *
     * @param objects Object[]
     * @return List
     */
    public static  List toList(Object objects[]) {
    	if(objects == null)
    		return new ArrayList();
    	else
    		return Arrays.asList(objects);
    }

    /**
     * Put all objects into a new list, and the null values won't been ignored.
     *
     * @param object Object
     * @return List
     */
    public static List toList(Object object) {
        List returnList = new LinkedList();
        returnList.add(object);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values won't been ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @return List
     */
    public static List toList(Object object1, Object object2) {
        List returnList = new LinkedList();
        returnList.add(object1);
        returnList.add(object2);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values won't been ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @return List
     */
    public static List toList(Object object1, Object object2, Object object3) {
        List returnList = new LinkedList();
        returnList.add(object1);
        returnList.add(object2);
        returnList.add(object3);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values won't been ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @return List
     */
    public static List toList(Object object1, Object object2, Object object3, Object object4) {
        List returnList = new LinkedList();
        returnList.add(object1);
        returnList.add(object2);
        returnList.add(object3);
        returnList.add(object4);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values won't been ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @param object5 Object
     * @return List
     */
    public static List toList(Object object1, Object object2, Object object3, Object object4, Object object5) {
        List returnList = new LinkedList();
        returnList.add(object1);
        returnList.add(object2);
        returnList.add(object3);
        returnList.add(object4);
        returnList.add(object5);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values won't been ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @param object5 Object
     * @param object6 Object
     * @return List
     */
    public static List toList(Object object1, Object object2, Object object3, Object object4, Object object5, Object object6) {
        List returnList = new LinkedList();
        returnList.add(object1);
        returnList.add(object2);
        returnList.add(object3);
        returnList.add(object4);
        returnList.add(object5);
        returnList.add(object6);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values will be ignored.
     *
     * @param objects Object[]
     * @return List
     */
    public static List toListNoNull(Object objects[]) {
        List returnList = new LinkedList();
        for(int i = 0; i < objects.length; i++)
            if(objects[i] != null)
                returnList.add(objects[i]);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values will be ignored.
     *
     * @param object Object
     * @return List
     */
    public static List toListNoNull(Object object) {
        List returnList = new LinkedList();
        if(object != null)
            returnList.add(object);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values will be ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @return List
     */
    public static List toListNoNull(Object object1, Object object2) {
        List returnList = new LinkedList();
        if(object1 != null)
            returnList.add(object1);
        if(object2 != null)
            returnList.add(object2);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values will be ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @return List
     */
    public static List toListNoNull(Object object1, Object object2, Object object3) {
        List returnList = new LinkedList();
        if(object1 != null)
            returnList.add(object1);
        if(object2 != null)
            returnList.add(object2);
        if(object3 != null)
            returnList.add(object3);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values will be ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @return List
     */
    public static List toListNoNull(Object object1, Object object2, Object object3, Object object4) {
        List returnList = new LinkedList();
        if(object1 != null)
            returnList.add(object1);
        if(object2 != null)
            returnList.add(object2);
        if(object3 != null)
            returnList.add(object3);
        if(object4 != null)
            returnList.add(object4);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values will be ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @param object5 Object
     * @return List
     */
    public static List toListNoNull(Object object1, Object object2, Object object3, Object object4, Object object5) {
        List returnList = new LinkedList();
        if(object1 != null)
            returnList.add(object1);
        if(object2 != null)
            returnList.add(object2);
        if(object3 != null)
            returnList.add(object3);
        if(object4 != null)
            returnList.add(object4);
        if(object5 != null)
            returnList.add(object5);

        return returnList;
    }

    /**
     * Put all objects into a new list, and the null values will be ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @param object5 Object
     * @param object6 Object
     * @return List
     */
    public static List toListNoNull(Object object1, Object object2, Object object3, Object object4, Object object5, Object object6) {
        List returnList = new LinkedList();
        if(object1 != null)
            returnList.add(object1);
        if(object2 != null)
            returnList.add(object2);
        if(object3 != null)
            returnList.add(object3);
        if(object4 != null)
            returnList.add(object4);
        if(object5 != null)
            returnList.add(object5);
        if(object6 != null)
            returnList.add(object6);

        return returnList;
    }

    /**
     * Put all objects into a new set, and the null values won't been ignored.
     *
     * @param objects Object[]
     * @return Set
     */
    public static Set toSet(Object objects[]) {
        return new HashSet(Arrays.asList(objects));
    }

    /**
     * Put all objects into a new set, and the null values won't been ignored.
     *
     * @param object Object
     * @return Set
     */
    public static Set toSet(Object object) {
        Set returnSet = new HashSet();
        returnSet.add(object);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values won't been ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @return Set
     */
    public static Set toSet(Object object1, Object object2) {
        Set returnSet = new HashSet();
        returnSet.add(object1);
        returnSet.add(object2);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values won't been ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @return Set
     */
    public static Set toSet(Object object1, Object object2, Object object3) {
        Set returnSet = new HashSet();
        returnSet.add(object1);
        returnSet.add(object2);
        returnSet.add(object3);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values won't been ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @return Set
     */
    public static Set toSet(Object object1, Object object2, Object object3, Object object4) {
        Set returnSet = new HashSet();
        returnSet.add(object1);
        returnSet.add(object2);
        returnSet.add(object3);
        returnSet.add(object4);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values won't been ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @param object5 Object
     * @return Set
     */
    public static Set toSet(Object object1, Object object2, Object object3, Object object4, Object object5) {
        Set returnSet = new HashSet();
        returnSet.add(object1);
        returnSet.add(object2);
        returnSet.add(object3);
        returnSet.add(object4);
        returnSet.add(object5);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values won't been ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @param object5 Object
     * @param object6 Object
     * @return Set
     */
    public static Set toSet(Object object1, Object object2, Object object3, Object object4, Object object5, Object object6) {
        Set returnSet = new HashSet();
        returnSet.add(object1);
        returnSet.add(object2);
        returnSet.add(object3);
        returnSet.add(object4);
        returnSet.add(object5);
        returnSet.add(object6);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values will be ignored.
     *
     * @param objects Object[]
     * @return Set
     */
    public static Set toSetNoNull(Object objects[]) {
        Set returnSet = new HashSet();
        for(int i = 0; i < objects.length; i++)
            if(objects[i] != null)
                returnSet.add(objects[i]);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values will be ignored.
     *
     * @param object Object
     * @return Set
     */
    public static Set toSetNoNull(Object object) {
        Set returnSet = new HashSet();
        if(object != null)
            returnSet.add(object);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values will be ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @return Set
     */
    public static Set toSetNoNull(Object object1, Object object2) {
        Set returnSet = new HashSet();
        if(object1 != null)
            returnSet.add(object1);
        if(object2 != null)
            returnSet.add(object2);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values will be ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @return Set
     */
    public static Set toSetNoNull(Object object1, Object object2, Object object3) {
        Set returnSet = new HashSet();
        if(object1 != null)
            returnSet.add(object1);
        if(object2 != null)
            returnSet.add(object2);
        if(object3 != null)
            returnSet.add(object3);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values will be ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @return Set
     */
    public static Set toSetNoNull(Object object1, Object object2, Object object3, Object object4) {
        Set returnSet = new HashSet();
        if(object1 != null)
            returnSet.add(object1);
        if(object2 != null)
            returnSet.add(object2);
        if(object3 != null)
            returnSet.add(object3);
        if(object4 != null)
            returnSet.add(object4);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values will be ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @param object5 Object
     * @return Set
     */
    public static Set toSetNoNull(Object object1, Object object2, Object object3, Object object4, Object object5) {
        Set returnSet = new HashSet();
        if(object1 != null)
            returnSet.add(object1);
        if(object2 != null)
            returnSet.add(object2);
        if(object3 != null)
            returnSet.add(object3);
        if(object4 != null)
            returnSet.add(object4);
        if(object5 != null)
            returnSet.add(object5);

        return returnSet;
    }

    /**
     * Put all objects into a new set, and the null values will be ignored.
     *
     * @param object1 Object
     * @param object2 Object
     * @param object3 Object
     * @param object4 Object
     * @param object5 Object
     * @param object6 Object
     * @return Set
     */
    public static Set toSetNoNull(Object object1, Object object2, Object object3, Object object4, Object object5, Object object6) {
        Set returnSet = new HashSet();
        if(object1 != null)
            returnSet.add(object1);
        if(object2 != null)
            returnSet.add(object2);
        if(object3 != null)
            returnSet.add(object3);
        if(object4 != null)
            returnSet.add(object4);
        if(object5 != null)
            returnSet.add(object5);
        if(object6 != null)
            returnSet.add(object6);

        return returnSet;
    }

    /**
     * Put all key-values into a new map, and the null key-values won't be ignored.
     *
     * @param key String
     * @param value Object
     * @return Map
     */
    public static Map toMap(String key, Object value) {
        Map returnMap = new HashMap();
        returnMap.put(key, value);

        return returnMap;
    }

    /**
     * Put all key-values into a new map, and the null key-values won't be ignored.
     *
     * @param key1 String
     * @param value1 Object
     * @param key2 String
     * @param value2 Object
     * @return Map
     */
    public static Map toMap(String key1, Object value1, String key2, Object value2) {
        Map returnMap = new HashMap();
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);

        return returnMap;
    }

    /**
     * Put all key-values into a new map, and the null key-values won't be ignored.
     *
     * @param key1 String
     * @param value1 Object
     * @param key2 String
     * @param value2 Object
     * @param key3 String
     * @param value3 Object
     * @return Map
     */
    public static Map toMap(String key1, Object value1, String key2, Object value2, String key3, Object value3) {
        Map returnMap = new HashMap();
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);

        return returnMap;
    }

    /**
     * Put all key-values into a new map, and the null key-values won't be ignored.
     *
     * @param key1 String
     * @param value1 Object
     * @param key2 String
     * @param value2 Object
     * @param key3 String
     * @param value3 Object
     * @param key4 String
     * @param value4 Object
     * @return Map
     */
    public static Map toMap(String key1, Object value1, String key2, Object value2, String key3, Object value3,
                            String key4, Object value4) {
        Map returnMap = new HashMap();
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);

        return returnMap;
    }

    /**
     * Put all key-values into a new map, and the null key-values won't be ignored.
     *
     * @param key1 String
     * @param value1 Object
     * @param key2 String
     * @param value2 Object
     * @param key3 String
     * @param value3 Object
     * @param key4 String
     * @param value4 Object
     * @param key5 String
     * @param value5 Object
     * @return Map
     */
    public static Map toMap(String key1, Object value1, String key2, Object value2, String key3, Object value3,
                            String key4, Object value4, String key5, Object value5) {
        Map returnMap = new HashMap();
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);

        return returnMap;
    }

    /**
     * Put all key-values into a new map, and the null key-values won't be ignored.
     *
     * @param key1 String
     * @param value1 Object
     * @param key2 String
     * @param value2 Object
     * @param key3 String
     * @param value3 Object
     * @param key4 String
     * @param value4 Object
     * @param key5 String
     * @param value5 Object
     * @param key6 String
     * @param value6 Object
     * @return Map
     */
    public static Map toMap(String key1, Object value1, String key2, Object value2, String key3, Object value3,
                            String key4, Object value4, String key5, Object value5, String key6, Object value6) {
        Map returnMap = new HashMap();
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);
        returnMap.put(key6, value6);

        return returnMap;
    }

    /**
     * Put all key-values into a new map, and the null key-values will be ignored.
     *
     * @param key String
     * @param value Object
     * @return Map
     */
    public static Map toMapNoNull(String key, Object value) {
        Map returnMap = new HashMap();
        if(key != null && value != null)
            returnMap.put(key, value);

        return returnMap;
    }

    /**
     * Put all key-values into a new map, and the null key-values will be ignored.
     *
     * @param key1 String
     * @param value1 Object
     * @param key2 String
     * @param value2 Object
     * @return Map
     */
    public static Map toMapNoNull(String key1, Object value1, String key2, Object value2) {
        Map returnMap = new HashMap();
        if(key1 != null && value1 != null)
            returnMap.put(key1, value1);
        if(key2 != null && value2 != null)
            returnMap.put(key2, value2);

        return returnMap;
    }

    /**
     * Put all key-values into a new map, and the null key-values will be ignored.
     *
     * @param key1 String
     * @param value1 Object
     * @param key2 String
     * @param value2 Object
     * @param key3 String
     * @param value3 Object
     * @return Map
     */
    public static Map toMapNoNull(String key1, Object value1, String key2, Object value2, String key3, Object value3) {
        Map returnMap = new HashMap();
        if(key1 != null && value1 != null)
            returnMap.put(key1, value1);
        if(key2 != null && value2 != null)
            returnMap.put(key2, value2);
        if(key3 != null && value3 != null)
            returnMap.put(key3, value3);

        return returnMap;
    }

    /**
     * Put all key-values into a new map, and the null key-values will be ignored.
     *
     * @param key1 String
     * @param value1 Object
     * @param key2 String
     * @param value2 Object
     * @param key3 String
     * @param value3 Object
     * @param key4 String
     * @param value4 Object
     * @return Map
     */
    public static Map toMapNoNull(String key1, Object value1, String key2, Object value2, String key3, Object value3,
                            String key4, Object value4) {
        Map returnMap = new HashMap();
        if(key1 != null && value1 != null)
            returnMap.put(key1, value1);
        if(key2 != null && value2 != null)
            returnMap.put(key2, value2);
        if(key3 != null && value3 != null)
            returnMap.put(key3, value3);
        if(key4 != null && value4 != null)
            returnMap.put(key4, value4);

        return returnMap;
    }

    /**
     * Put all key-values into a new map, and the null key-values will be ignored.
     *
     * @param key1 String
     * @param value1 Object
     * @param key2 String
     * @param value2 Object
     * @param key3 String
     * @param value3 Object
     * @param key4 String
     * @param value4 Object
     * @param key5 String
     * @param value5 Object
     * @return Map
     */
    public static Map toMapNoNull(String key1, Object value1, String key2, Object value2, String key3, Object value3,
                            String key4, Object value4, String key5, Object value5) {
        Map returnMap = new HashMap();
        if(key1 != null && value1 != null)
            returnMap.put(key1, value1);
        if(key2 != null && value2 != null)
            returnMap.put(key2, value2);
        if(key3 != null && value3 != null)
            returnMap.put(key3, value3);
        if(key4 != null && value4 != null)
            returnMap.put(key4, value4);
        if(key5 != null && value5 != null)
            returnMap.put(key5, value5);

        return returnMap;
    }

    /**
     * Put all key-values into a new map, and the null key-values will be ignored.
     *
     * @param key1 String
     * @param value1 Object
     * @param key2 String
     * @param value2 Object
     * @param key3 String
     * @param value3 Object
     * @param key4 String
     * @param value4 Object
     * @param key5 String
     * @param value5 Object
     * @param key6 String
     * @param value6 Object
     * @return Map
     */
    public static Map toMapNoNull(String key1, Object value1, String key2, Object value2, String key3, Object value3,
                            String key4, Object value4, String key5, Object value5, String key6, Object value6) {
        Map returnMap = new HashMap();
        if(key1 != null && value1 != null)
            returnMap.put(key1, value1);
        if(key2 != null && value2 != null)
            returnMap.put(key2, value2);
        if(key3 != null && value3 != null)
            returnMap.put(key3, value3);
        if(key4 != null && value4 != null)
            returnMap.put(key4, value4);
        if(key5 != null && value5 != null)
            returnMap.put(key5, value5);
        if(key6 != null && value6 != null)
            returnMap.put(key6, value6);

        return returnMap;
    }
    public static String[] toStringArray(List list){
    	if(list == null) 
    		return null;
    	String [] arr = new String [list.size()];
    	for(int i=0;i<list.size();i++){
    		arr[i] = (String)list.get(i);
    	}
    	return arr;
    }
}
