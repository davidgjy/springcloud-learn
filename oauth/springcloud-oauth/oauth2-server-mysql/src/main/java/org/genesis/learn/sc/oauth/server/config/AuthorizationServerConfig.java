package org.genesis.learn.sc.oauth.server.config;

import org.genesis.learn.sc.oauth.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务器配置
 * Created by KG on 2019/9/30
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    //在配置文件中定义数据库信息
    @Autowired
    private DataSource dataSource;

    /**
     * 声明TokenStore实现
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 声明ClientDetails实现，使用JdbcClientDetailsService客户端详情服务
     * @return ClientDetailsService
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 使用用户服务来控制认证服务器的访问
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 使用oauth2的密码模式时需要配置authenticationManager
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(tokenStore())
                // 配置tokenStore,需要配置userDetailsService，否则refresh_token会报错
                .userDetailsService(userService);

        // 配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 从数据库查询Token
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(false);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        // token过期30天
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));
        endpoints.tokenServices(tokenServices);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // oauthServer.checkTokenAccess("isAuthenticated()");
        // 开启oauth、token_key验证端口无权限访问
        security.checkTokenAccess("permitAll()");
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 从JDBC查出数据，如客户端ID、密码、授权方式等
        clients.withClientDetails(clientDetails());
    }
}
