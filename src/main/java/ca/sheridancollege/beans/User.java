package ca.sheridancollege.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String encryptedpassword;
	private Byte enabled;

	public User(String username, String encryptedpassword) {
		this.username = username;
		this.encryptedpassword = encryptedpassword;
		enabled = 1;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<Role>();
	


}
