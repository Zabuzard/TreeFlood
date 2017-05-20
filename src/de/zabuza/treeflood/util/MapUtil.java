package de.zabuza.treeflood.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utility class for {@link Map}s.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class MapUtil {
	/**
	 * Reverses the given map. That is it turns values to keys and keys to
	 * values. For correct execution the values must be distinct. Entries are
	 * not backed by the original map.
	 * 
	 * @param <K>
	 *            The class of the keys
	 * @param <V>
	 *            The class of the values
	 * @param map
	 *            The map to reverse
	 * @return A reveres map, that is values are keys and keys are values.
	 */
	public static <K, V> Map<V, K> reverseMap(final Map<K, V> map) {
		final HashMap<V, K> reversedMap = new HashMap<>();

		for (final Entry<K, V> entry : map.entrySet()) {
			reversedMap.put(entry.getValue(), entry.getKey());
		}

		return reversedMap;
	}

	/**
	 * Utility class. No implementation.
	 */
	private MapUtil() {

	}
}
