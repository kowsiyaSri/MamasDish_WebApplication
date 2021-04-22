package ca.sheridancollege.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	
	@Autowired
	private LoginAccessDeniedHandler accessDeniedHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//****************************************************************
		// This must removed when creating production level code
		http.csrf().disable();
		http.headers().frameOptions().disable();
		//******************************************************************
		
		http.authorizeRequests()
			.antMatchers("/viewAllRecipe", "/viewRecipe").hasAnyRole("USER", "CHEF")
			.antMatchers("/uploadRecipe","/addRecipe").hasRole("CHEF")
			.antMatchers("/h2-console/**","/register/**").permitAll()
			.antMatchers("/","/css/**","/images/**","/js/**","/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/viewAllRecipe")
			.permitAll()
		.and()
			.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout")
			.permitAll()
		.and()
			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler);
			
		
	}
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		
		return new BCryptPasswordEncoder();
	}

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception {		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

}
