package com.xujialin.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author XuJiaLin
 * @date 2021/7/17 21:04
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder pw;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        if (!username.equals("admin")) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        String password = null;
        if (pw == null) {
            password = new BCryptPasswordEncoder().encode("123");
        } else {
            password = pw.encode("123");
        }


        return new User(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_Admin"));
    }


}
