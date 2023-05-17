package com.exam.service.impl;

import com.exam.helper.UserFoundExpection;
import com.exam.helper.UserNotFoundException;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {

         User local = this.userRepository.findByUsername(user.getUsername());
        if(local != null)
        {
            System.out.println("User is already there!!");
            throw new UserFoundExpection();
        }
        else {

            for(UserRole ur:userRoles)
            {
                roleRepository.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);
        }
        return local;
    }

    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public void updateUser(Long UserId,User user1) throws Exception{

       Optional<User> optional= this.userRepository.findById(UserId);
       User user2 = optional.get();
        System.out.println(user2);
        if(user2 != null)
        {
            user2.setUsername(user1.getUsername());
            user2.setFirstName(user1.getFirstName());
            user2.setLastName(user1.getLastName());
            user2.setPhone(user1.getPhone());
            user2.setProfile(user1.getProfile());
            user2.setEmail(user1.getEmail());
            user2.setUserRoles(user1.getUserRoles());
            this.userRepository.save(user2);
        }
        else
        {
            System.out.println("UserId not found!!");
            throw new Exception("UserId not exist");
        }



    }


}
