package com.home.ppmtool.services;


import com.home.ppmtool.domain.User;
import com.home.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.home.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){
        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setUsername(newUser.getUsername());
            newUser.setConfirmedPassword("");
            return  userRepository.save(newUser);
        }
        catch(Exception e){
            throw new UsernameAlreadyExistsException("User with username" + newUser.getUsername()+"already exists.");
        }

    }

}
