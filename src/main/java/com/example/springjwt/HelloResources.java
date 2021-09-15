package com.example.springjwt;

import com.example.springjwt.models.AuthenticationRequest;
import com.example.springjwt.models.AutheticationResponse;
import com.example.springjwt.services.MyUserDetailsService;
import com.example.springjwt.util.jwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api")
public class HelloResources {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private jwtUtil  jwtTokenUtil;
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @RequestMapping(value = "/authenticate" , method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
        throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password");
        }
        final UserDetails userDetails=userDetailsService.
                loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AutheticationResponse(jwt));
    }
//    @PostMapping(value = "/authenticate")
}
