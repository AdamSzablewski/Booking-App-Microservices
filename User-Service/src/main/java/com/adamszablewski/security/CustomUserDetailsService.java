//package com.adamszablewski.security;
//
//
//
//import com.adamszablewski.exceptions.NoSuchUserException;
//import com.adamszablewski.users.UserClass;
//import com.adamszablewski.users.repository.UserRepository;
//import com.adamszablewski.users.role.Role;
//import lombok.AllArgsConstructor;
//
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private UserRepository userRepository;
//
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserClass user = userRepository.findByEmail(username)
//                .orElseThrow(NoSuchUserException::new);
//
//        return new User(
//                user.getEmail()
//                ,user.getPassword()
//                ,mapRolesTOAuthorities(new ArrayList<>())
//        );
//    }
//
//
//    private Collection<GrantedAuthority> mapRolesTOAuthorities(List<Role> roles){
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
//}
