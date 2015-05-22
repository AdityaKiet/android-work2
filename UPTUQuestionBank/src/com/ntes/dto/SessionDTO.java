package com.ntes.dto;

public class SessionDTO {

	private String name;
	private String email;
	private Integer id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SessionDTO [name=" + name + ", email=" + email + ", id=" + id
				+ "]";
	}

}
