package com.sfac.geniusdirecruit.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: Shiro Config
 * @author: HymanHu
 * @date: 2020年11月29日
 */
@Configuration
public class ShiroConfig {
	/**
	 * 配置密码匹配器
	 * @return
	 */
	@Autowired
	private MyRealm myRealm;

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myRealm);
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(){

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		shiroFilterFactoryBean.setLoginUrl("/manager/login");
		shiroFilterFactoryBean.setSuccessUrl("/common/dashboard");

		// 自定义过滤器
		FormAuthenticationFilter managerAuthc = new FormAuthenticationFilter();
		managerAuthc.setLoginUrl("/user/login");
		managerAuthc.setSuccessUrl("/common/dashboard");
		UserFilter managerUser = new UserFilter();
		managerUser.setLoginUrl("/user/login");

		FormAuthenticationFilter tmallAuthc = new FormAuthenticationFilter();
		tmallAuthc.setLoginUrl("/user/login");
		tmallAuthc.setSuccessUrl("/common/dashboard");
		UserFilter tmallUser = new UserFilter();
		tmallUser.setLoginUrl("/user/login");

		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		filters.put("managerAuthc", managerAuthc);
		filters.put("managerUser", managerUser);
		filters.put("tmallAuthc", tmallAuthc);
		filters.put("tmallUser", tmallUser);
		shiroFilterFactoryBean.setFilters(filters);

		// 设置访问规则
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 匿名访问规则
		// ---- 静态资源文件 ----
		map.put("/backstage/css/**", "anon");
		map.put("/backstage/images/**", "anon");
		map.put("/backstage/manager/js/**", "anon");
		map.put("/backstage/manager/images/**", "anon");
		map.put("/backstage/manager/css/**", "anon");
		map.put("/backstage/js/**", "anon");
		map.put("/backstage/plugin/**", "anon");
		map.put("/backstage/vendors/**", "anon");

		map.put("/frontdesk/css/**", "anon");
		map.put("/frontdesk/images/**", "anon");
		map.put("/frontdesk/js/**", "anon");
		map.put("/frontdesk/vendors/**", "anon");
		map.put("/frontdesk/**", "anon");


		// ---- 登录、注册、测试、api ----
		map.put("/user/login", "anon");
		map.put("/user/register", "anon");
		map.put("/user/logout", "anon");
		/*map.put("/tmall/login", "anon");
		map.put("/tmall/register", "anon");
		map.put("/tmall/logout", "anon");
		map.put("/tmall/home", "anon");
		map.put("/tmall/searchResults", "anon");
		map.put("/tmall/category/*", "anon");
		map.put("/tmall/product/*", "anon");*/
		map.put("/test/**", "anon");
		map.put("/user/**", "anon");

		// 登录访问规则
		/*map.put("/user/**", "tmallAuthc");*/
		map.put("/user/**", "tmallAuthc");

		// 记住我访问规则
		map.put("/common/dashboard", "managerUser");
		map.put("/jobs/jobsPage", "managerUser");
		map.put("/newses/**", "managerUser");
		map.put("/blogrolls/**", "managerUser");
		/*map.put("/shopping/**", "managerUser");*/

		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

		return shiroFilterFactoryBean;
	}

	@Bean
	public ShiroDialect shiroDialect(){
		return new ShiroDialect();
	}

	/**
	 * --自动代理类，支持Shiro的注解
	 */
	@Bean
	@DependsOn({"lifecycleBeanPostProcessor"})
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	/**
	 * --开启Shiro的注解
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * --记住我之rememberMeCookie
	 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		//这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		//如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，
		//使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
		simpleCookie.setHttpOnly(true);
		//记住我cookie生效时间,单位是秒
		simpleCookie.setMaxAge(1 * 24 * 60 * 60);
		return simpleCookie;
	}

	/**
	 * --记住我之cookie管理器
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
		cookieRememberMeManager.setCipherService(new AesCipherService());
		cookieRememberMeManager.setCipherKey(cipherKey);
		return cookieRememberMeManager;
	}
}
