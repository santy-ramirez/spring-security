package com.santy.disney.controller;

import com.santy.disney.domain.Movies;
import com.santy.disney.repository.MoviesRepository;
import com.santy.disney.security.responseAndRequest.RequestJwt;
import com.santy.disney.security.responseAndRequest.ResponseJWT;
import com.santy.disney.security.jwt.JwtUtil;
import com.santy.disney.security.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MoviesController {
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MoviesRepository moviesRepository;

    @PostMapping()
    public Movies createMovie(@RequestBody  Movies movies){
        return  moviesRepository.save(movies);
    }

    @GetMapping
    public List<Movies> getAllMovies(){
        return moviesRepository.findAll();
    }

    @PostMapping("/authenticate")
    public ResponseJWT authenticate(@RequestBody RequestJwt jwtRequest) throws Exception {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials", e);
        }
        final UserDetails userDetails
                = myUserDetailService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        return new ResponseJWT(token);
    }
}
