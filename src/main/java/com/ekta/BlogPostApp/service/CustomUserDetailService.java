package com.ekta.BlogPostApp.service;

import com.ekta.BlogPostApp.models.CustomUserDetails;
import com.ekta.BlogPostApp.models.Users;
import com.ekta.BlogPostApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = userRepository.findByUsername(username);
        optionalUsers.orElseThrow(() -> new UsernameNotFoundException("Username invalid!!"));

        return optionalUsers.map(CustomUserDetails::new).get();
    }
}
