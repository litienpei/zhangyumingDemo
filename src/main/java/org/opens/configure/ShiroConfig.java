package org.opens.configure;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.opens.shiro.Realm.UserRealm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 简介
 *      特别注意的是, 如果你给一个方法配置了@Bean注解, 如果没有指定name属性, 则被@Bean修饰的方法产生的bean的name默认为方法名称.
 */
@Configuration
public class ShiroConfig {

    /**
     * 简介:
     *      配置shiro的Shiro的拦截器的工厂
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier(value = "securityManager") SecurityManager securityManager
    ) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/page/login");
        shiroFilter.setSuccessUrl("/page/success");
        //如果一个角色登录之后去访问他没有权限的url, 就会跳转到这个页面.
        shiroFilter.setUnauthorizedUrl("/page/403");

        Map<String, String> filterMap = new LinkedHashMap<String, String>();
        filterMap.put("/lg/lgin", "anon");
        filterMap.put("/lg/lgout", "logout");
        filterMap.put("/user/selectUserDemoById", "authc");
        //这个是角色拦截器, roles[admin]表示只有admin角色可以访问当前url, roles[admin,test]表示admin,test两个角色都可以访问
        filterMap.put("/user/selectUserDemoByIds", "roles[admin]");
        //filterMap.put("/page/**", "anon");
        //filterMap.put("/**", "anon");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }


    @Bean(name = "securityManager")
    public SecurityManager getSecurityManager(
            @Qualifier(value = "userRealm") UserRealm userRealm
    ) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean(name = "userRealm")
    public UserRealm getUserRealm(
            @Qualifier(value = "hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher
    ) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }

    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //设置使用什么哈希算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //指明加密次数, 此处设置为2表示设置2次
        hashedCredentialsMatcher.setHashIterations(2);
        //表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 简介:
     *      测试手动生产md5加密后的值
     * 输出:
     *      0b4778d00284b057c5d5bb6820b08440
     *      {"algorithmName":"MD5","bytes":"C0d40AKEsFfF1btoILCEQA==","empty":false,"iterations":2,"salt":{"bytes":"ZXdheXRlaw==","empty":false}}
     * @param args
     */
    public static void main(String[] args) {
        SimpleHash hash = new SimpleHash("md5","test1", ByteSource.Util.bytes("ewaytek"),2);
        System.out.println(hash);
        System.out.println(JSON.toJSONString(hash));
    }

    /**
     * 加入注解的使用，不加入这个注解不生效
     *
     * AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
     * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
     */
    @Bean(name = "authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier(value = "securityManager") SecurityManager securityManager
    ) {
        AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
        aASA.setSecurityManager(securityManager);
        return aASA;
    }

}
