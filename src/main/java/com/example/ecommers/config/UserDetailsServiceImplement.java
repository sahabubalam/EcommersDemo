package com.example.ecommers.config;

import com.example.ecommers.model.User;
import com.example.ecommers.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImplement implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepo.getUserByUserName(username);
      if(user==null)
      {
          throw new UsernameNotFoundException("Could not found user !");
      }
      CustomUserDetails customUserDetails = new CustomUserDetails(user);
        return customUserDetails;
    }
}
