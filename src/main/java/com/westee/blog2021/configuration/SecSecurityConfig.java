package com.westee.blog2021.configuration;

import com.westee.blog2021.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.inject.Inject;
import javax.servlet.ServletContext;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
    @Inject
    private UserService userService;

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user1").password(bCryptPasswordEncoder().encode("user1Pass")).roles("USER")
//                .and()
//                .withUser("user2").password(bCryptPasswordEncoder().encode("user2Pass")).roles("USER")
//                .and()
//                .withUser("admin").password(bCryptPasswordEncoder().encode("adminPass")).roles("ADMIN");
//    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/anonymous*", "/auth").anonymous()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();
        // http builder configurations for authorize requests and form login (see below)
    }

    public class AppInitializer implements WebApplicationInitializer {

        @Override
        public void onStartup(ServletContext sc) {

            AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
            root.register(SecSecurityConfig.class);

            sc.addListener(new ContextLoaderListener(root));

            sc.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
                    .addMappingForUrlPatterns(null, false, "/*");
        }
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception{
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}