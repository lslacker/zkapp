package info.thatngo.zkapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String ZUL_FILES = "/zkau/web/**/*.zul";
	private static final String STATIC_FILES = "/static/**";
	private static final String[] ZK_RESOURCES = {
			"/zkau/web/**/js/**",
			"/zkau/web/**/zul/css/**",
			"/zkau/web/**/font/**",
			"/zkau/web/**/img/**",
	};
	
	// allow desktop cleanup after logout or when reloading login page
	private static final String REMOVE_DESKTOP_REGEX = "/zkau\\?dtid=.*&cmd_0=rmDesktop&.*";
	
	
	protected void configure(HttpSecurity http) throws Exception {
		// ZK already sends a AJAX request with a built-in CSRF token,
        // please refer to https://www.zkoss.org/wiki/ZK%20Developer's%20Reference/Security%20Tips/Cross-site%20Request%20Forgery
		
		http.csrf().disable();
		
		http.authorizeRequests()
		    .antMatchers(ZUL_FILES).denyAll()
		    .antMatchers(HttpMethod.POST, "/zkau").permitAll()
		    .antMatchers(HttpMethod.GET, ZK_RESOURCES).permitAll()
		    .antMatchers(HttpMethod.GET, STATIC_FILES).permitAll()
		    .regexMatchers(HttpMethod.GET, REMOVE_DESKTOP_REGEX).permitAll()
		    .requestMatchers(req -> "removeDesktop".equals(req.getParameter("cmd_0"))).permitAll()
		    .mvcMatchers("/login", "/logout").permitAll()
		    .anyRequest().authenticated()
		    .and()
		    .formLogin()
		    .loginPage("/login").defaultSuccessUrl("/", true)
		    .and()
		    .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
	  throws Exception {
	    auth.inMemoryAuthentication()
	      .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
	      .and()
	      .withUser("admin").password(passwordEncoder().encode("password")).roles("USER", "ADMIN");
	}
}
