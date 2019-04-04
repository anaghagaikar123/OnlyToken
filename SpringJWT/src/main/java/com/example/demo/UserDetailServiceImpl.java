package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


	@Autowired
	private StudentDAO studentDAO;
	
	@Override
	public UserDetails loadUserByUsername(String studentnm) throws UsernameNotFoundException {
		System.out.println("############### Fetching data credentials for " + studentnm + " ###############");
		Student student = studentDAO.byName(studentnm);
		if(student==null){
			System.out.println("Exception ");
			throw new UsernameNotFoundException(studentnm);
		}
		System.out.println("studentnm "+studentnm);
		System.out.println("studentid "+student.getId());
		System.out.println("studentnm "+student.getName());

		UserCreds accountCredentials = new UserCreds();
		
		
		UserPrincipal userPrincipal = new UserPrincipal(accountCredentials);
		return userPrincipal;
		}

}