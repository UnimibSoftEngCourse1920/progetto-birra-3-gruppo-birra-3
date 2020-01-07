package main;

import java.io.Serializable;

public class Test implements Serializable {
	
	public int id;

	public Test(int id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + "]";
	}
}
