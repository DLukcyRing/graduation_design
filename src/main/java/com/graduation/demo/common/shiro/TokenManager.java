package com.graduation.demo.common.shiro;

import com.graduation.demo.common.entity.User;
import com.graduation.demo.common.utils.Md5Utils;
import com.graduation.demo.common.utils.SpringContextUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.List;

public class TokenManager {

    //用户登录管理
    public static final CustomRealm realm = (CustomRealm) SpringContextUtil.getBean("CustomRealm");
    //用户session管理
    public static final CustomSessionManager customSessionManager = (CustomSessionManager) SpringContextUtil.getBean("customSessionManager");

    /**
     * 获取当前登录的用户User对象
     *
     * @return
     */
    public static User getToken() {
        User token = (User) SecurityUtils.getSubject().getPrincipal();
        return token;
    }


    /**
     * 获取当前用户的Session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取当前用户NAME
     *
     * @return
     */
    public static String getName() {
        return getToken().getName();
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public static String getUserId() {
        return getToken() == null ? null : getToken().getId();
    }

    /**
     * 登录
     *
     * @param user
     * @param rememberMe
     * @return
     */
    public static User login(User user, Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), Md5Utils.encode(user.getPassword()));
        token.setRememberMe(rememberMe);
        SecurityUtils.getSubject().login(token);
        return getToken();
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return null != SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 退出登录
     */
    public static void logout() {
        //清理自定义的session缓存
        cleanSession();
        SecurityUtils.getSubject().logout();
    }

    /**
     * 清理自定义的session缓存
     */
    public static void cleanSession() {
        Session session = getSession();
        session.removeAttribute(CustomRealm.SHRIO_USER_ROLES);
    }

    /**
     * 根据UserIds 	清空权限信息。
     *
     * @param id 用户ID
     */
    public static void clearUserAuthByUserId(String... userIds) {

        if (null == userIds || userIds.length == 0) return;
        List<SimplePrincipalCollection> result = customSessionManager.getSimplePrincipalCollectionByUserId(userIds);

        for (SimplePrincipalCollection simplePrincipalCollection : result) {
            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }


    /**
     * 方法重载
     *
     * @param userIds
     */
    public static void clearUserAuthByUserId(List<String> userIds) {
        if (null == userIds || userIds.size() == 0) {
            return;
        }
        clearUserAuthByUserId(userIds.toArray(new String[0]));
    }

    /**
     * 判断角色是否存在
     *
     * @param role
     * @return
     */
    public static boolean hasRole(String role) {
        return SecurityUtils.getSubject().hasRole(role);
    }

    /**
     * 判断权限是否存在
     *
     * @param perm
     * @return
     */
    public static boolean hasPermission(String perm) {
        return SecurityUtils.getSubject().isPermitted(perm);
    }

}
