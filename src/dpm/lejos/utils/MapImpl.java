package dpm.lejos.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * @author David Lavoie-Boutin
 * @version v1.0
 */
public class MapImpl<K,V> implements java.util.Map {

    private ArrayList<K> keyList;
    private ArrayList<V> valueList;


    public MapImpl(){
        keyList = new ArrayList<K>();
        valueList = new ArrayList<V>();
    }

    /**
     * Removes all mappings from this map (optional operation).
     */
    @Override
    public void clear() {
        keyList = null;
        valueList = null;
        keyList = new ArrayList<K>();
        valueList = new ArrayList<V>();
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(Object key) {
        return keyList.contains((K)key);
    }

    /**
     * Returns true if this map maps one or more keys to the specified value.
     *
     * @param value
     */
    @Override
    public boolean containsValue(Object value) {
        return valueList.contains(value);
    }

    /**
     * Returns a set view of the mappings contained in this map.
     */
    @Override
    public Set<Entry> entrySet() {
        return null;
    }

    /**
     * Returns the value to which this map maps the specified key.
     *
     * @param key the key of the element
     */
    @Override
    public V get(Object key) {
        return valueList.get(keyList.indexOf(key));
    }

    /**
     * Returns true if this map contains no key-value mappings.
     */
    @Override
    public boolean isEmpty() {
        return keyList.isEmpty() && valueList.isEmpty();
    }

    /**
     * Returns a set view of the keys contained in this map.
     */
    @Override
    public Set keySet() {
        return null;
    }

    /**
     * Associates the specified value with the specified key in this map (optional operation).
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public Object put(Object key, Object value) {
            if (containsKey(key)){
                valueList.set(keyList.indexOf(key),(V) value);
            }
            else {
                valueList.add((V)value);
                keyList.add((K)key);
            }
        return value;
    }

    /**
     * Copies all of the mappings from the specified map to this map (optional operation).
     *
     * @param t
     */
    @Override
    public void putAll(java.util.Map t) {

    }

    /**
     * Removes the mapping for this key from this map if it is present (optional operation).
     *
     * @param key
     */
    @Override
    public Object remove(Object key) {
        return null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return keyList.size();
    }

    /**
     * Returns a collection view of the values contained in this map.
     */
    @Override
    public Collection values() {
        return null;
    }
}
