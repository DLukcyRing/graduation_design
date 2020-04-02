package com.graduation.demo.common.shiro;

import com.graduation.demo.common.entity.Role;
import com.graduation.demo.common.entity.User;
import com.graduation.demo.service.LoginService;
import com.graduation.demo.service.UserService;
import org.apache.el.parser.TokenMgrError;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CustomRealm extends AuthorizingRealm {

    @Resource
    private LoginService loginService;

    /**
     * 缓存用户的角色set<key>
     */
    public static String SHRIO_USER_ROLES = "shiro_user_roles";

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Map<String, Object> map = loginService.getUserByName(username);
        List<Role> role = (List<Role>) map.get("role");
        Set<String> stringSet = new HashSet<>();
        stringSet.add("user:show");
        stringSet.add("user:admin");
        info.setStringPermissions(stringSet);
        return info;
    }

    /**
     * <p>
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证方法--------");
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        //根据用户名从数据库获取密码
        Map<String, Object> map = loginService.getUserByName(userName);
        User user = (User) map.get("user");
        String password = user.getPassword();
        if (userName == null) {
            throw new AccountException("用户名不正确");
        } else if (!userPwd.equals(password)) {
            throw new AccountException("密码不正确");
        }
        return new SimpleAuthenticationInfo(userName, password, getName());
    }

    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
