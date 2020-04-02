package com.graduation.demo.common.shiro;

import com.graduation.demo.common.entity.User;
import com.graduation.demo.common.utils.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import java.util.*;

public class CustomSessionManager {

    /**
     * session status
     */
    public static final String SESSION_STATUS = "online-status";
    ShiroSessionRepository shiroSessionRepository;

    CustomShiroSessionDAO customShiroSessionDAO;


    /**
     * 根据ID查询 SimplePrincipalCollection
     *
     * @param userIds 用户ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(String... userIds) {
        //把userIds 转成Set，好判断
        Set<String> idset = (Set<String>) StringUtils.array2Set(userIds);
        //获取所有session
        Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
        //定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session : sessions) {
            //获取SimplePrincipalCollection
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (null != obj && obj instanceof SimplePrincipalCollection) {
                //强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                //判断用户，匹配用户ID。
                obj = spc.getPrimaryPrincipal();
                if (null != obj && obj instanceof User) {
                    User user = (User) obj;
                    //比较用户ID，符合即加入集合
                    if (null != user && idset.contains(user.getId())) {
                        list.add(spc);
                    }
                }
            }
        }
        return list;
    }


    /**
     * 改变Session状态
     *
     * @param status    {true:踢出,false:激活}
     * @param sessionId
     * @return
     */
    public Map<String, Object> changeSessionStatus(Boolean status,
                                                   String sessionIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String[] sessionIdArray = null;
            if (sessionIds.indexOf(",") == -1) {
                sessionIdArray = new String[]{sessionIds};
            } else {
                sessionIdArray = sessionIds.split(",");
            }
            for (String id : sessionIdArray) {
                Session session = shiroSessionRepository.getSession(id);
                SessionStatus sessionStatus = new SessionStatus();
                sessionStatus.setOnlineStatus(status);
                session.setAttribute(SESSION_STATUS, sessionStatus);
                customShiroSessionDAO.update(session);
            }
            map.put("status", 200);
            map.put("sessionStatus", status ? 1 : 0);
            map.put("sessionStatusText", status ? "踢出" : "激活");
            map.put("sessionStatusTextTd", status ? "有效" : "已踢出");
        } catch (Exception e) {
            map.put("status", 500);
            map.put("message", "改变失败，有可能Session不存在，请刷新再试！");
        }
        return map;
    }

    public void setShiroSessionRepository(
            ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

    public void setCustomShiroSessionDAO(CustomShiroSessionDAO customShiroSessionDAO) {
        this.customShiroSessionDAO = customShiroSessionDAO;
    }
}
