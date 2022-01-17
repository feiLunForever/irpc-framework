
package org.idea.irpc.framework.core.serialize;

import java.io.Serializable;

public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private transient String gender;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Student() {}
	public Student(int id,String name,String gender){
		this.id = id;
		this.name = name;
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return "User(id="+id+",name="+name+",gender="+gender+")";
	}
}

