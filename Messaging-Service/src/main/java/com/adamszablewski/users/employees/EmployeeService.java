package com.adamszablewski.users.employees;//package com.adamszablewski.Warehouse.Management.Application.users.employees;
//
//import com.adamszablewski.Warehouse.Management.Application.role.Role;
//import com.adamszablewski.Warehouse.Management.Application.role.RoleRepository;
//import com.adamszablewski.Warehouse.Management.Application.users.UserInfo;
//import com.adamszablewski.Warehouse.Management.Application.users.repository.UserInfoRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class EmployeeService {
//
//    private UserInfoRepository UserInfoRepository;
//    private RoleRepository roleRepository;
//    private PasswordEncoder passwordEncoder;
//
//    public ResponseEntity<String> registerNewEmployee(UserInfo userInfo) {
//        if (UserInfoRepository.existsByUsername(userInfo.getUsername())){
//            return new ResponseEntity<>("username is taken", HttpStatus.BAD_REQUEST);
//        }
//        UserInfo user = new UserInfo();
//        user.setUsername(userInfo.getUsername());
//        user.setName(userInfo.getName());
//        user.setLastName(userInfo.getLastName());
//
//        Role role = roleRepository.findByName("EMPLOYEE").get();
//        user.setRoles(List.of(role));
//        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
//        user.setPhoneNumber(userInfo.getPhoneNumber());
//
//        UserInfoRepository.save(user);
//        return new ResponseEntity<>("User registered", HttpStatus.OK);
//    }
//
//    public ResponseEntity<String> registerNewManager(UserInfo userInfo) {
//        if (UserInfoRepository.existsByUsername(userInfo.getUsername())){
//            return new ResponseEntity<>("username is taken", HttpStatus.BAD_REQUEST);
//        }
//        UserInfo user = new UserInfo();
//        user.setUsername(userInfo.getUsername());
//        user.setName(userInfo.getName());
//        user.setLastName(userInfo.getLastName());
//        Role role = roleRepository.findByName("MANAGER").get();
//        user.setRoles(List.of(role));
//        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
//        user.setPhoneNumber(userInfo.getPhoneNumber());
//
//        UserInfoRepository.save(user);
//        return new ResponseEntity<>("User registered", HttpStatus.OK);
//    }
//    }
//
