package io.github.pikaq.serialize;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 8078998547887165812L;
	private String name;
	private int age;
}
