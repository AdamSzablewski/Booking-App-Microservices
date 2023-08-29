package com.adamszablewski.users.employees;//package com.adamszablewski.Warehouse.Management.Application.users.employees;
//
//
//import com.adamszablewski.Warehouse.Management.Application.users.UserInfo;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@AllArgsConstructor
//@RestController
//public class EmployeeController {
//
//
//    EmployeeService employeeService;
//
//    @PostMapping("/register/employee")
//    public ResponseEntity<String> registerEmployee(@RequestBody UserInfo userInfo) {
//        return employeeService.registerNewEmployee(userInfo);
//    }
//
//    @PostMapping("/register/manager")
//    public ResponseEntity<String> registerManager(@RequestBody UserInfo userInfo) {
//        return employeeService.registerNewManager(userInfo);
//    }
//}
