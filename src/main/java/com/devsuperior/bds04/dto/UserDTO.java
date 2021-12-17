package com.devsuperior.bds04.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.devsuperior.bds04.entities.User;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 5923400412599996862L;
	
	private Long id;
	@NotEmpty(message="Campo obrigatório")
	@Email(message="Favor entrar um email válido")
	private String email;
	
	private Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO() {
	}
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.roles.clear();
		user.getRoles().forEach(x -> this.roles.add(new RoleDTO(x)));
	}
	
	public UserDTO(Long id, String email, String password) {
		super();
		this.id = id;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}
}
