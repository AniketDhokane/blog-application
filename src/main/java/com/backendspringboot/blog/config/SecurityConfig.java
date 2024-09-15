package com.backendspringboot.blog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.backendspringboot.blog.security.CustomUserDetailService;
import com.backendspringboot.blog.security.JwtAuthenticationEntryPoint;
import com.backendspringboot.blog.security.JwtAuthenticationFilter;




@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig   { 
	
	public static final String[] PUBLIC_URL= {
			
			"/api/v1/auth/**",
			"/error",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-ui/**",
		    "/swagger-ui.html",
		    "/swagger-resources/**",
		    "/webjars/**"
	};
	
	 	@Autowired
	    private JwtAuthenticationEntryPoint point;
	 	
	    @Autowired
	    private JwtAuthenticationFilter filter;
	    

	    @Autowired
	    private CustomUserDetailService customUserDetailService;

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


	    	 http
	            .csrf(csrf -> csrf.disable())
	            .cors(cors -> cors.disable())
	            .authorizeHttpRequests(auth -> auth
	            	.requestMatchers(PUBLIC_URL).permitAll()
	                .requestMatchers(HttpMethod.GET).permitAll()
	                       // Then secure all other API endpoints
	                .anyRequest().authenticated())
	            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	        // Add JWT filter before the UsernamePasswordAuthenticationFilter
	        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }        
	        @Bean
		    public PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		    }

		    @Bean
		    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		        return builder.getAuthenticationManager();
		    }
		    
		    @Bean
		    public DaoAuthenticationProvider daoAuthenticationProvider() {
		    	
		    	DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
		    	
		    	provider.setUserDetailsService(this.customUserDetailService);
		    	provider.setPasswordEncoder(passwordEncoder());
		    	
		    	return provider;
		    }
		    
		    @Bean
		    public CorsFilter corsFilter() {
		        CorsConfiguration config = new CorsConfiguration();
		        config.setAllowCredentials(true);  // Enable credentials for CORS
		        config.addAllowedOriginPattern("*");  // Allows any domain
		        config.addAllowedHeader("*");  // Allows any header
		        config.addAllowedMethod("*");  // Allows all HTTP methods (GET, POST, etc.)
		        config.setMaxAge(3000L);
		        
		        // Apply CORS configuration to all paths
		        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		        source.registerCorsConfiguration("/**", config);

		        return new CorsFilter(source);  // Use CorsFilter with configured source
		    }
		    
		    
	 
}
	    
	   
	  
	   
	

