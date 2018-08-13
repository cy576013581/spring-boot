package com.cy.example.supplement.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.example.entity.system.SysPermissionEntity;
import com.cy.example.entity.system.SysRoleEntity;
import com.cy.example.entity.system.SysUserEntity;
import com.cy.example.service.IUserService;
import com.cy.example.util.StringUtil;

public class AuthRealm extends AuthorizingRealm {

	@Autowired
	private IUserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(AuthRealm.class);

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		logger.info("--------------权限配置——授权----------------");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
		authorizationInfo.addRole(user.getRole().getC_roleCode());
		for (SysPermissionEntity p : user.getRole().getPermisList()) {
			authorizationInfo.addStringPermission(p.getC_permisCode());
		}
		logger.info(user.toString());
		return authorizationInfo;
	}

	/*
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份 如果返回一个SimpleAccount
	 * 对象则认证通过，如果返回值为空或者异常，则认证不通过。 1、检查提交的进行认证的令牌信息 2、根据令牌信息从数据源(通常为数据库)中获取用户信息
	 * 3、对用户信息进行匹配验证 4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例
	 * 5、验证失败则抛出AuthenticationException异常信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// 获取用户的输入的账号.
		String username = (String) token.getPrincipal();
		if (StringUtil.IsNullOrEmptyT(username)) {
			return null;
		}
//        /*
//         * 在这里写错误登陆次数的限制代码
//         */
//		userService.incrLoginCount(username);
		
		
//		//使用緩存存儲用戶信息
//		SysUserEntity user = userService.getUserCache(username);
//		if(null == user){
		SysUserEntity user = userService.findOneByUsername(username);
//			userService.insertUserCache(user);
//		}
		
		// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		if(null == user){
			throw new UnknownAccountException();
		}
		if ("0".equals(user.getN_status())) {
            // 用户被管理员锁定抛出异常
            throw new LockedAccountException();
        }
//		if (Integer.valueOf(userService.getLoginCount(username)) > 5){
//	        throw new DisabledAccountException("由于密码输入错误次数大于5次，帐号已经禁止登录！");
//	    }
		logger.info("***登录user：" + user);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user, // 用户名
				user.getC_pwd(), // 密码
				ByteSource.Util.bytes(user.getCredentialsSalt()),// salt=username+salt
				getName() // realm name
		);
		return authenticationInfo;
	}

}
