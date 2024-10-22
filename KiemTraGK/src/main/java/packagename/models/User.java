package packagename.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;
	
	@Column(name = "name")
	@NotNull(message = "Name must be not null")
	@NotEmpty(message = "Name must be not empty")
	@Size(min = 8, max = 50, message = "Name must between 8 - 50 char")
	protected String name;
	
	@Column(name = "email")
	@NotEmpty(message = "Email must be not empty")
	@Email(message = "Email should be valid")
	protected String email;
	
	@Column(name="country")
	protected String country;
	
	
	public User() {
		super();
	}


	public User(int id, String name, String email, String country) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.country = country;
	}

	public User( String name, String email, String country) {
		super();
		this.name = name;
		this.email = email;
		this.country = country;
	}

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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", country=" + country + "]";
	}
	
	

}
