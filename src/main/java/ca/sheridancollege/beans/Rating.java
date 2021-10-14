package ca.sheridancollege.beans;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Rating {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Lob
    private String comment;
	
	private float rating;
	
	@OneToOne
	@JsonIgnore
	private EndUser user;
	

	@OneToOne
	@JsonIgnore
	private Recipe recipe;
	
	private String userName;
	
	@Override
	public String toString() {
		return "Rating [id=" + id + ", comment=" + comment + ", rating=" + rating + ", user=" + user.getId() + ", recipe="
				+ recipe.getId() + "]";
	}

		
}
