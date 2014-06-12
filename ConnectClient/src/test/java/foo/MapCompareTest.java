package foo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

@Deprecated
public class MapCompareTest {
	// method to deep compare two maps
	private boolean compareMaps(Map<String, Map<String, String>> domainStore1,
			Map<String, Map<String, String>> domainStore2) {
		return domainStore1.equals(domainStore2);
	}

	// test cases for verifying the compareMaps method in this class
	@Test
	public void testCompareMaps() {
		Map map1 = new HashMap();
		Map map2 = new HashMap();

		Assert.assertTrue(this.compareMaps(map1, map2));
	}

	@Test
	public void testCompareMaps2() {
		Map map1 = new HashMap();
		map1.put("key1", null);

		Map map2 = new HashMap();

		Assert.assertFalse(this.compareMaps(map1, map2));
	}

	@Test
	public void testCompareMaps3() {
		Map map1 = new HashMap();
		map1.put("key", "1");

		Map map2 = new HashMap();
		map2.put("key", "1");

		Assert.assertTrue(this.compareMaps(map1, map2));
	}

	@Test
	public void testCompareMaps4() {
		Map map1 = new HashMap();
		Map map1a = new HashMap();
		map1.put("key", map1a);

		Map map2 = new HashMap();
		Map map2a = new HashMap();
		map2.put("key", map2a);

		Assert.assertTrue(this.compareMaps(map1, map2));
	}

	@Test
	public void testCompareMaps5() {
		Map map1 = new HashMap();
		Map map1a = new HashMap();
		map1.put("key", map1a);

		Map map2 = new HashMap();
		map2.put("key", null);

		Assert.assertFalse(this.compareMaps(map1, map2));
	}

	@Test
	public void testCompareMaps6() {
		Map map1 = new HashMap();
		Map map1a = new HashMap();
		map1a.put("1a", "v1a");
		map1.put("key", map1a);

		Map map2 = new HashMap();
		Map map2a = new HashMap();
		map2a.put("1a", "v1a");
		map2.put("key", map2a);

		Assert.assertTrue(this.compareMaps(map1, map2));
	}
}
