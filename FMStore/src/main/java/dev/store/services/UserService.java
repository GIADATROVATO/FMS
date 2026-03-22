package dev.store.services;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.store.model.User;
import dev.store.repository.UserRepository;
@Service
public class UserService {
	private UserRepository repo;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository repo,PasswordEncoder passwordEncoder ) {
		this.repo=repo;
		this.passwordEncoder=passwordEncoder;
	}
	public Optional<User> authenticate(String username, String rawPass){
		return repo.findByUsername(username).filter(User::isEnabled)
					.filter(u-> passwordEncoder.matches(rawPass, u.getPassword()));
		
	}
}
