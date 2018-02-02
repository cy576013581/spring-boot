package com.cy.example.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cy.example.filter.ShiroPermissionsFilter;
import com.cy.example.supplement.shiro.AuthRealm;

/*
 * Shiro 配置
 */
@Configuration
public class ShiroConfig {

	private static final Logger logger = LoggerFactory
			.getLogger(ShiroConfig.class);

	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();//获取filters  
        //将自定义 的ShiroFilterFactoryBean注入shiroFilter
        filters.put("perms", new ShiroPermissionsFilter());
        
        // 必须设置SecuritManager  
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/courtBusiness/nonStandardProductAttr/api.sdo", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/lib/**", "anon");

		filterChainDefinitionMap.put("/index", "anon");
		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/system/user/validate", "anon");
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		// 这里自定义的权限拦截规则
		filterChainDefinitionMap.put("/system/*/add","authc,perms[add]");
		filterChainDefinitionMap.put("/system/*/delete","authc,perms[del]");
		filterChainDefinitionMap.put("/system/*/update","authc,perms[update]");
		filterChainDefinitionMap.put("/system/*/list","authc,perms[list]");
		// filterChainDefinitionMap.put("/system/*/list", "perms[list]");
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/**", "authc");
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/index");
		// 登录成功后要跳转的链接
//		shiroFilterFactoryBean.setSuccessUrl("/main");

		// 未授权界面;
		// shiroFilterFactoryBean.setUnauthorizedUrl("/menu/403");
		shiroFilterFactoryBean
				.setFilterChainDefinitionMap(filterChainDefinitionMap);

		logger.info("--------------Shiro拦截器工厂类注入成功----------------");
		return shiroFilterFactoryBean;
	}
	
	/*
	 * 配置自定义的权限登录器
	 */
	@Bean
	public AuthRealm authRealm() {
		AuthRealm authRealm = new AuthRealm();
		return authRealm;
	}

	/*
	 * 配置核心安全事务管理器
	 */
	@Bean
	public SecurityManager securityManager() {
		logger.info("--------------shiro安全事务管理器已经加载----------------");
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(authRealm());
		//注入记住我管理器;
		manager.setRememberMeManager(rememberMeManager());
		return manager;
	}
	
	/**
	 * cookie对象;
	 * 设置Cookie的生成模版，比如cookie的name，cookie的有效时间
	 * @return
	 */
	public SimpleCookie rememberMeCookie(){
	   //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
	   SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
	   //<!-- 记住我cookie生效时间30天 ,单位秒;-->
	   simpleCookie.setMaxAge(2592000);
	   return simpleCookie;
	}

	/**
	 * cookie管理对象;记住我功能
	 * @return
	 */
	public CookieRememberMeManager rememberMeManager(){
	   CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
	   cookieRememberMeManager.setCookie(rememberMeCookie());
	   //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
	   cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
	   return cookieRememberMeManager;
	}
}
