package com.xujialin.Config;

import com.xujialin.Handler.MyAccessDeniedHandler;
import com.xujialin.Repository.MyPersistentTokenRepository;
import com.xujialin.Service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author XuJiaLin
 * @date 2021/7/17 21:02
 */
@Configuration
public class SpringsecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyAccessDeniedHandler deniedHandler;

    @Autowired
    private MyPersistentTokenRepository repository;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置登录页面 第一个是自定义表单，第二个是定义的登录的处理请求路径
        http.formLogin().loginPage("/login").
                loginProcessingUrl("/login").
                successForwardUrl("/index").
                failureForwardUrl("/Error");

        //授权认证
        http.authorizeRequests().antMatchers("/vip").hasAuthority("admin");

        //所有程序需要认证才能访问
        http.authorizeRequests().antMatchers("/login").
                permitAll().antMatchers("/Error").permitAll().

                antMatchers("/vip").hasAuthority("admin").//权限判断
                /* antMatchers("/vip").hasRole("Admin").//角色判断*/
                        antMatchers("/vip2").hasIpAddress("127.0.0.1").
                anyRequest().authenticated();/*192.168.0.103*/

        //自定义异常处理
        /*http.exceptionHandling().accessDeniedHandler(deniedHandler);*/

        //开启自动登录
        http.rememberMe().tokenRepository(repository).
                userDetailsService(new UserDetailsServiceImpl());

        //关闭csrf防护
        http.csrf().disable();

        http.logout().logoutSuccessUrl("/login");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //对静态资源进行放行,要不然图片等css加载不出来
        web.ignoring().antMatchers("/img/**");
    }
}
