package com.eMurasu.config;

import com.eMurasu.enums.UserState;
import com.eMurasu.model.UserCredential;
import com.eMurasu.repo.UserCredentialRepo;
import com.eMurasu.repo.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserCredentialRepo userCredentialRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepo.findByUsername(username);
        //Only Active users can generate an access token.
        if (userCredential != null&& (UserState.ACTIVE.equals(userCredential.getStatus()))) {
            CustomUserDetails customUserDetails = new CustomUserDetails();
            customUserDetails.setUserName(userCredential.getUsername());
            customUserDetails.setPassword(userCredential.getPassword());
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            //need to check existence of UserRole
            authorities.add(new CustomGrantedAuthority(userRoleRepo.findById(userCredential.getRoleId()).getTitle()) );
            customUserDetails.setGrantedAuthorities(authorities);
            return customUserDetails;
        }
        throw new UsernameNotFoundException(username);
    }

}