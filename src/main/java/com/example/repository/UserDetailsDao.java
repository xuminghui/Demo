package com.example.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.example.entity.Person;
@Repository
public class UserDetailsDao implements UserDetailsService{
	@Autowired
	private PersonRepository personDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Person user = personDao.findByUserName(username);
		 System.out.println(user.getRole());
		    if (user == null) {
		      throw new UsernameNotFoundException(username + " not found");
		    }
		    return new UserDetails() {
		      @Override
		      public Collection<? extends GrantedAuthority> getAuthorities() {
		        List<SimpleGrantedAuthority> auths = new ArrayList<>();
		        auths.add(new SimpleGrantedAuthority(user.getRole()));
		        return auths;
		      }

		      @Override
		      public String getPassword() {
		        return user.getPassword();
		      }

		      @Override
		      public String getUsername() {
		        return username;
		      }

		      @Override
		      public boolean isAccountNonExpired() {
		        return true;
		      }

		      @Override
		      public boolean isAccountNonLocked() {
		        return true;
		      }

		      @Override
		      public boolean isCredentialsNonExpired() {
		        return true;
		      }

		      @Override
		      public boolean isEnabled() {
		        return true;
		      }
		    };
	}
	public enum ROLE {
	    ADMIN("ADMIN"), USER("USER");

	    private String role;

	    ROLE(String role) {
	      this.role = role;
	    }

	    public String getRole() {
	      return role;
	    }

	    public void setRole(String role) {
	      this.role = role;
	    }

	    @Override
	    public String toString() {
	      return "ROLE_" + role;
	    }

	  }

}
