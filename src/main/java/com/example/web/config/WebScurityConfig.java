package com.example.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.repository.UserDetailsDao;

@Configuration
@EnableWebSecurity
public class WebScurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsDao  userDetailDao;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().csrfTokenRepository(csrfTokenRepository());
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/testAjax").permitAll();
		http.authorizeRequests().antMatchers("/testAjax2").permitAll();
		http.authorizeRequests().antMatchers("/console/**").permitAll();
		
		http.authorizeRequests().antMatchers("/writers").permitAll();
		http.authorizeRequests().antMatchers("/books/**").permitAll();
		http.authorizeRequests().antMatchers("/internal/**").permitAll();
		http.authorizeRequests().antMatchers("/registration").permitAll();
		http.authorizeRequests().antMatchers("/test").permitAll();
	//	http.authorizeRequests().antMatchers("/").hasRole(ROLE.ADMIN.getRole()); 
		http.authorizeRequests().anyRequest().authenticated();
		http.formLogin().failureUrl("/login?error").defaultSuccessUrl("/").loginPage("/login").permitAll().and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
				.permitAll();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
		web.ignoring().antMatchers("/webjars/**");
		web.ignoring().antMatchers("/css/**");
		web.ignoring().antMatchers("/img/**");
		web.ignoring().antMatchers("/js/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin")
				.password("password").roles("USER", "ADMIN");*/
		auth.userDetailsService(userDetailDao);
	}
	
	//实现方式http.csrf().csrfTokenRepository(csrfTokenRepository());
	private CsrfTokenRepository csrfTokenRepository() 
	{ 
	    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository(); 
	    repository.setSessionAttributeName("_csrf");
	    return repository; 
	}

}
