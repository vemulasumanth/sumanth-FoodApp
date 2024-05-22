package com.sumanth.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumanth.config.JwtProvider;
import com.sumanth.model.Cart;
import com.sumanth.model.USER_ROLE;
import com.sumanth.model.User;
import com.sumanth.repository.CartRepository;
import com.sumanth.repository.UserRepository;
import com.sumanth.request.LoginRequest;
import com.sumanth.response.AuthResponse;
import com.sumanth.service.CustomerUserDetailsService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private CartRepository cartRepository;
	
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception
	{
		User isEmailExist=userRepository.findByEmail(user.getEmail());
		if(isEmailExist!=null)
		{
			throw new Exception("Email is already used with another account");
		}
		
		User createdUser=new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullname(user.getFullname());
		createdUser.setRole(user.getRole());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser=userRepository.save(createdUser);
		
		Cart cart=new Cart();
		cart.setCustomer(savedUser);
		cartRepository.save(cart);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Register success");
		authResponse.setRole(savedUser.getRole());
		return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req)
	{
		String username=req.getEmail();
		String password=req.getPassword();
		Authentication authentication=authenticate(username,password);
        String jwt=jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login success");
		Collection<?extends GrantedAuthority> authorities=authentication.getAuthorities();
		String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
		authResponse.setRole(USER_ROLE.valueOf(role));
		return new ResponseEntity<>(authResponse,HttpStatus.OK);
	}

	private Authentication authenticate(String username, String password) {
		// TODO Auto-generated method stub
		UserDetails userdetails=customerUserDetailsService.loadUserByUsername(username);
		if(userdetails==null)
		{
			throw new BadCredentialsException("invalid username...");
		}
		if(!passwordEncoder.matches(password, userdetails.getPassword()))
		{
			throw new BadCredentialsException("invalid password...");
		}
		return new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
	}
	
}
