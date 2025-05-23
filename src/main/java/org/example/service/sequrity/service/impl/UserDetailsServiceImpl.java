package org.example.service.sequrity.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.entity.User;
import org.example.dao.mapper.UserMapper;
import org.example.dao.repository.UserRepository;
import org.example.service.sequrity.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepo;
    private final UserContext userContext;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        userContext.setUserDTO(userMapper.toResponseUserDTO(user));

        return UserDetailsImpl.build(user, email);
    }
}
