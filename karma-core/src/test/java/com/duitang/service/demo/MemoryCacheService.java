package com.duitang.service.demo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MemoryCacheService implements IDemoService {

	protected Map<String, String> memory = new HashMap<>();
	protected Map<String, byte[]> memoryB = new HashMap<>();
	protected Map<String, Map> mapA = new HashMap<>();

	protected boolean verbose = false;

	public MemoryCacheService() {
		this(false);
	}

	public MemoryCacheService(boolean verbose) {
		this.verbose = verbose;
	}

	@Override
	public String memory_getString(String key) {
		verbose("memory_getString: " + key);
		if (new Random().nextInt(10) > 7) {
			System.out.println("serve " + key);
		}
		return memory.get(key);
	}

	@Override
	public boolean memory_setString(String key, String value, int ttl) {
		memory.put(key, value); // mock, so ignore ttl
		verbose("memory_setString: " + key + " --> " + value);
		return true;
	}

	@Override
	public byte[] memory_getBytes(String key) {
		verbose("memory_getBytes: " + key);
		return memoryB.get(key);
	}

	@Override
	public boolean memory_setBytes(String key, byte[] value, int ttl) {
		memoryB.put(key, value);
		verbose("memory_setBytes: " + key + " --> " + new String(value));
		return true;
	}

	@Override
	public String trace_msg(String key, long ttl) {
		long ts = System.currentTimeMillis();
		try {
			Thread.sleep(ttl);
		} catch (InterruptedException e) {
			// ignored
		}
		long ela = System.currentTimeMillis() - ts;
		String ret = key + " => {" + ts + ", " + ela + "}";
		verbose("trace_msg: " + key);
		return ret;
	}

	@Override
	public Map getmap(String name) {
		verbose("getmap: " + name);
		return mapA.get(name);
	}

	@Override
	public boolean setmap(String name, Map data) {
		mapA.put(name, data);
		verbose("setmap: " + data.toString());
		return true;
	}

	protected void verbose(String data) {
		if (verbose) {
			System.err.println(data);
		}
	}

	@Override
	public Set<String> noparam() {
		HashSet<String> ret = new HashSet<>();
		ret.add("abcdef");
		ret.add("abcdef1");
		return ret;
	}

	@Override
	public Map getM(Set s) {
		System.out.println(s);
		return new HashMap();
	}

	@Override
	public void getError() {
		throw new NullPointerException("hello, by laurence");
	}

	@Override
	public void getExp() throws DemoException {
		throw new DemoException("i will i will rock u!");
	}

	@Override
	public void getTimeoutError(long timeout) {
		try {
			Thread.sleep(Double.valueOf(timeout * 1.5).longValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
