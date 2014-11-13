package edu.sjsu.cmpe.cache.client;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

public class Client {
	public static void main(String[] args) throws Exception {

		System.out.println("Starting Cache Client...");

		CacheServiceInterface cache1 = new DistributedCacheService(
				"http://localhost:3000");
		CacheServiceInterface cache2 = new DistributedCacheService(
				"http://localhost:3001");
		CacheServiceInterface cache3 = new DistributedCacheService(
				"http://localhost:3002");

		List<CacheServiceInterface> servers = Lists.newArrayList(cache1,
				cache2, cache3);

		HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
		hashmap.put(1, "a");
		hashmap.put(2, "b");
		hashmap.put(3, "c");
		hashmap.put(4, "d");
		hashmap.put(5, "e");
		hashmap.put(6, "f");
		hashmap.put(7, "g");
		hashmap.put(8, "h");
		hashmap.put(9, "i");
		hashmap.put(10, "j");

		for (int i = 1; i < hashmap.size() + 1; i++) {

			int bucket = Hashing.consistentHash(Hashing.md5().hashLong(i),
					servers.size());
			servers.get(bucket).put(i, hashmap.get(i));
			System.out.println("put " + i + ":" + hashmap.get(i)
					+ "=>servers.get(" + (bucket + 1) + ")");
		}

		for (int i = 1; i < hashmap.size() + 1; i++) {

			int bucket = Hashing.consistentHash(Hashing.md5().hashLong(i),
					servers.size());
			servers.get(bucket).get(i);

			System.out.println("get" + i + ":" + hashmap.get(i)
					+ "=>servers.get(" + (bucket+1) + ")");
		}

		System.out.println("Existing Cache Client...");
	}
}

/*
 * 
 * package edu.sjsu.cmpe.cache.client;
 * 
 * public class Client {
 * 
 * public static void main(String[] args) throws Exception {
 * System.out.println("Starting Cache Client..."); 
 * CacheServiceInterface cache = new DistributedCacheService( "http://localhost:3000");
 * 
 * cache.put(1, "foo"); System.out.println("put(1 => foo)");
 * 
 * String value = cache.get(1); System.out.println("get(1) => " + value);
 * 
 * System.out.println("Existing Cache Client..."); }
 * 
 * }
 * 
 * String value = cache.get(1); System.out.println("get(1) => " + value);
 * 
 * System.out.println("Existing Cache Client..."); }
 * 
 * }
 */

