package com.nelioalves.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.workshopmongo.domain.User;
import com.nelioalves.workshopmongo.dto.UserDTO;
import com.nelioalves.workshopmongo.repository.UserRepository;
import com.nelioalves.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> findAll() {
		return repo.findAll();
	}
	
	public User findById(String id) {
		Optional<User> optionalUser = repo.findById(id);
	    if (optionalUser.isPresent()) {
	        return optionalUser.get();
	    } else {
	        throw new ObjectNotFoundException("Object not found");
	    }
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	
	public User update(User obj) {
		 Optional<User> optionalUser = repo.findById(obj.getId());

	        optionalUser.ifPresent(existingUser -> {
	            updateData(existingUser, obj);
	            repo.save(existingUser);
	        });

	        return optionalUser.orElseThrow(() -> new ObjectNotFoundException("User not found with ID: " + obj.getId()));
	    }

	    private void updateData(User existingUser, User obj) {
	        existingUser.setName(obj.getName());
	        existingUser.setEmail(obj.getEmail());
	}
	
	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
