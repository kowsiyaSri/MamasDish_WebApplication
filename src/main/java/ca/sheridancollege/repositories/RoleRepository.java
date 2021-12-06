package ca.sheridancollege.repositories;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	//returns role object that its roleName field equals to the string parameter
	public Role findByRolename(String name);

}
