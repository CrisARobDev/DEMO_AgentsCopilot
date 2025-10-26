package com.demo.jpacrud.service;

import com.demo.jpacrud.dto.UserRequest;
import com.demo.jpacrud.dto.UserResponse;
import com.demo.jpacrud.entity.User;
import com.demo.jpacrud.exception.DuplicateResourceException;
import com.demo.jpacrud.exception.ResourceNotFoundException;
import com.demo.jpacrud.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create a new user
     * @param userRequest the user data to create
     * @return the created user response
     * @throws DuplicateResourceException if username or email already exists
     */
    public UserResponse createUser(UserRequest userRequest) {
        // Check for duplicate username
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicateResourceException("User", "username", userRequest.getUsername());
        }

        // Check for duplicate email
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateResourceException("User", "email", userRequest.getEmail());
        }

        User user = new User(
            userRequest.getUsername(),
            userRequest.getEmail(),
            userRequest.getFirstName(),
            userRequest.getLastName()
        );

        User savedUser = userRepository.save(user);
        return UserResponse.fromUser(savedUser);
    }

    /**
     * Get all users
     * @return list of all users
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::fromUser)
                .collect(Collectors.toList());
    }

    /**
     * Get a user by ID
     * @param id the user ID
     * @return the user response
     * @throws ResourceNotFoundException if user not found
     */
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserResponse.fromUser(user);
    }

    /**
     * Get a user by username
     * @param username the username
     * @return the user response
     * @throws ResourceNotFoundException if user not found
     */
    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return UserResponse.fromUser(user);
    }

    /**
     * Update a user
     * @param id the user ID
     * @param userRequest the updated user data
     * @return the updated user response
     * @throws ResourceNotFoundException if user not found
     * @throws DuplicateResourceException if username or email already exists for another user
     */
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // Check if username is being changed and if it's already taken by another user
        if (!user.getUsername().equals(userRequest.getUsername())) {
            userRepository.findByUsername(userRequest.getUsername())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(id)) {
                        throw new DuplicateResourceException("User", "username", userRequest.getUsername());
                    }
                });
        }

        // Check if email is being changed and if it's already taken by another user
        if (!user.getEmail().equals(userRequest.getEmail())) {
            userRepository.findByEmail(userRequest.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(id)) {
                        throw new DuplicateResourceException("User", "email", userRequest.getEmail());
                    }
                });
        }

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        User updatedUser = userRepository.save(user);
        return UserResponse.fromUser(updatedUser);
    }

    /**
     * Delete a user
     * @param id the user ID
     * @throws ResourceNotFoundException if user not found
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(id);
    }
}
