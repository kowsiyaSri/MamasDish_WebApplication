package ca.sheridancollege.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EndUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    private String firstName;

    private String lastName;

    private String email;
    
    private String password;

	@OneToMany
	private List<Recent> recent;
	
	@OneToMany
	private List<Cuisine> cuisine;

	@OneToMany
	private List<Country> country;

	@OneToMany
	private List<Diet> diet;

	@OneToMany
	private List<Protein> protein;
		
}
