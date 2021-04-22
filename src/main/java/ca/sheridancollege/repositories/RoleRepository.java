package ca.sheridancollege.repositories;

import org.springframework.data.repository.CrudRepository;

import ca.sheridancollege.beans.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	public Role findByRolename(String name);

}
