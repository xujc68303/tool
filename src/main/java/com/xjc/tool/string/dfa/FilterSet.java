package com.xjc.tool.string.dfa;

public class FilterSet{

	private final long[] elements;
	
	public FilterSet() {
		 elements = new long[1 + (65535 >>> 6)];
	}
	
	public void add(final int no) {
		elements[no >>> 6] |= (1L << (no & 63));
	}
	
	public void add(final int... no) {
		for(int currNo : no)
			elements[currNo >>> 6] |= (1L << (currNo & 63));
	}
	
	public boolean contains(final int no) {
        return (elements[no >>> 6] & (1L << (no & 63))) != 0;
    }

	public int size() {
		int size = 0;
		for (long element : elements)
			size += Long.bitCount(element);
		return size;
	}

}