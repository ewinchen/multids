package dev.ewin.multids;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class TypeCastTest {

	@Test
	void test() {
		Map<String, Object> hashMap = new HashMap<>();
		LinkedHashMap<String, Object> linkedHashMapd = (LinkedHashMap<String, Object>) hashMap;
	}
	
	@Test
	void test2() {
		Map<String, Object> linkedHashMap = new LinkedHashMap<>();
		HashMap<String, Object> hashMap = (HashMap<String, Object>) linkedHashMap;
	}

}
