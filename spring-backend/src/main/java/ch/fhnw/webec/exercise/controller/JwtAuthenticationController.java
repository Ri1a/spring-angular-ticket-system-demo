//package ch.fhnw.webec.exercise.controller;
//
//import ch.nag.dts.entities.AuthentificationViewModel;
//import ch.nag.dts.entities.User;
//import ch.nag.dts.interfaces.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
//public class JwtAuthenticationController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private UserDetailsService jwtInMemoryUserDetailsService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @RequestMapping(value = "/api/authenticate", method = RequestMethod.POST)
//    public AuthentificationViewModel generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
//            throws Exception {
//
//        AuthentificationViewModel model = new AuthentificationViewModel();
//        try {
//            UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//            List<User> userList = userRepository.getAllUser();
//
//            User user = new User();
//
//            for (User userDetail : userList) {
//                if(userDetail.getUsername().equals(authenticationRequest.getUsername())) {
//                    user = userDetail;
//                }
//            }
//
//            model.setRole(user.getRole());
//            model.setUsername(authenticationRequest.getUsername());
//            model.setId(UUID.randomUUID().toString());
//            String token = jwtTokenUtil.generateToken(userDetails);
//            model.setToken(token);
//
//            return model;
//
//        } catch (Exception e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
//}
