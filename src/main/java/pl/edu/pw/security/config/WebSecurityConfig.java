package pl.edu.pw.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import pl.edu.pw.app.api.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    private UserServiceImpl userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .csrf().disable()
               .authorizeRequests().antMatchers("/api/registration/**").permitAll()
               .and()
               .authorizeRequests().antMatchers("/api/team/**").permitAll() .anyRequest()
//               .antMatchers("/api/registration/**").permitAll()
               .authenticated().and()
               .formLogin()
//               .loginPage("/login")
               .permitAll();
//               .and()
//               .exceptionHandling() // 1
//               .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)); // 1;



    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
}
