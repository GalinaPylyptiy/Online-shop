package com.epam.electronic.service;
import com.epam.electronic.model.User;
import com.epam.electronic.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    public class UserServiceTest {

        @InjectMocks
        private UserService userService;

        @Mock
        private UserRepository userRepository;

        @BeforeEach
        public void init() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testCreateUser() {
            User userToCreate = new User();
            userToCreate.setLogin("testUser");
            userToCreate.setPassword("password");

            when(userRepository.save(userToCreate)).thenReturn(userToCreate);

            User createdUser = userService.createUser(userToCreate);

            verify(userRepository, times(1)).save(userToCreate);

            assertNotNull(createdUser);
            assertEquals("testUser", createdUser.getLogin());
            assertEquals("password", createdUser.getPassword());
        }

        @Test
        public void testGetUserById() {
            Long userId = 1L;
            User user = new User();
            user.setId(userId);
            user.setLogin("testUser");
            user.setPassword("password");

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));

            User retrievedUser = userService.getUserById(userId);

            verify(userRepository, times(1)).findById(userId);

            assertNotNull(retrievedUser);
            assertEquals(userId, retrievedUser.getId());
            assertEquals("testUser", retrievedUser.getLogin());
            assertEquals("password", retrievedUser.getPassword());
        }

        @Test
        public void testGetUserByIdNotFound() {
            Long userId = 1L;

            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> userService.getUserById(userId));
        }

        @Test
        public void testUpdateUser() {
            User userToUpdate = new User();
            userToUpdate.setId(1L);
            userToUpdate.setLogin("updatedUser");
            userToUpdate.setPassword("newPassword");

            when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

            User updatedUser = userService.updateUser(userToUpdate);

            verify(userRepository, times(1)).save(userToUpdate);

            assertNotNull(updatedUser);
            assertEquals("updatedUser", updatedUser.getLogin());
            assertEquals("newPassword", updatedUser.getPassword());
        }

        @Test
        public void testDeleteUser() {
            Long userId = 1L;
            userService.deleteUser(userId);
            verify(userRepository, times(1)).deleteById(userId);
        }
    }

