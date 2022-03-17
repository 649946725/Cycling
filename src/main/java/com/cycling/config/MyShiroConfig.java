package com.cycling.config;

import com.cycling.cache.RedisCacheManger;
import com.cycling.filter.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: MyShiroConfig
 * @Description: TODO
 * @Author: qyz
 * @date: 2021/10/20 17:48
 * @Version: V1.0
 */

@Configuration
public class MyShiroConfig {


    //创建一个shiro过滤器
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {

        //构建一个shiro过滤器工厂对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //把jwt过滤器添加在shiro过滤器中
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwt", new JwtFilter());
        //设置过滤器
        shiroFilterFactoryBean.setFilters(filterMap);
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //设置放行和不放行的资源
        Map<String, String> sourceMap = new LinkedHashMap<>();
        //放行的资源
        sourceMap.put("/ride/prepare", "anon");
        sourceMap.put("/login", "anon");
        sourceMap.put("/login2", "anon");
        sourceMap.put("/code", "anon");
        //anon代表可以匿名访问的过滤器的名字缩写是shiro自带的
        sourceMap.put("/", "anon");
        sourceMap.put("/cycling/**", "anon");
        //释放swagger资源
        sourceMap.put("/swagger-ui/index.html", "anon");
        sourceMap.put("/swagger-ui/**.js", "anon");
        sourceMap.put("/swagger-ui/**.css", "anon");
        sourceMap.put("/swagger-ui/**.png", "anon");
        sourceMap.put("/swagger-resources/**/**", "anon");
        sourceMap.put("/v2/api-docs", "anon");
        sourceMap.put("/webjars/springfox-swagger-ui/**", "anon");
        sourceMap.put("/configuration/security", "anon");
        sourceMap.put("/configuration/ui", "anon");
        //拦截的资源
        //拦截所有jwt不放行的资源  jwt是我们自定义的过滤器名字的缩写
        sourceMap.put("/**", "jwt");
//        sourceMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(sourceMap);
        return shiroFilterFactoryBean;
    }

    //创建一个安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(CustomerRealm customRealm) {

        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //设置自定义的realm
        //开启缓存管理
        customRealm.setCacheManager(new RedisCacheManger());
        //开启全局缓存
        customRealm.setCachingEnabled(true);
        //开启认证缓存
        customRealm.setAuthenticationCachingEnabled(true);
        //给认证缓存设置名字
        customRealm.setAuthenticationCacheName("AuthenticationCache");
        //开启授权缓存
        customRealm.setAuthorizationCachingEnabled(true);
        //给授权缓存设置名字
        customRealm.setAuthorizationCacheName("AuthorizationCache");
        defaultWebSecurityManager.setRealm(customRealm);
        //关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);
        return defaultWebSecurityManager;
    }


    /**
     * 授权属性源配置
     *
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;

    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
