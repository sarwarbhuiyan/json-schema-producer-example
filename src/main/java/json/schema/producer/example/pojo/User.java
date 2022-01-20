package json.schema.producer.example.pojo;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	@JsonProperty(required = true)
	public String firstName;

	@JsonProperty (required = true)
	public String lastName;

	@JsonProperty (required = true)
	public Integer age;
	
	public String email;

	public User() {
	}

	public User(String firstName, String lastName, Integer age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

}
