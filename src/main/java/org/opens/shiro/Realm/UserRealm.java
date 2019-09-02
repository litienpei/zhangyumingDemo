package org.opens.shiro.Realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.opens.shiro.dao.CompetenceMapper;
import org.opens.shiro.dao.EwayUserDao;
import org.opens.shiro.pojo.EwayUser;
import org.opens.shiro.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private EwayUserDao ewayUserDao;

    @Autowired
    private CompetenceMapper competenceMapper;

    /**
     * 简介:
     *      获取授权信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = ((EwayUser) principals.getPrimaryPrincipal()).getAccountNumber();
        Set<String> rolesName = ewayUserDao.getRolesNameByAccount(username);
        List<Role> roles = ewayUserDao.getUsersByAccount(username).getList();

        Set<String> competences = new HashSet<>();
        roles.forEach(x -> {
            competences.addAll(competenceMapper.getPermissionBasedOnRoleId(x.getId()));
        });

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(competences);
        simpleAuthorizationInfo.setRoles(rolesName);
        return simpleAuthorizationInfo;
    }

    /**
     * 简介:
     *      获取身份验证信息(登录)
     * @param token 令牌, 包含用户名和密码
     * @return
     * @throws AuthenticationException 登录信息错误将会导致的顶级异常, 其下可能会是没有这个用户异常或者是密码错误异常.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String userName = userToken.getUsername();
        EwayUser user = ewayUserDao.getUsersByAccount(userName);
        if(user == null) {
            throw new AccountException("此用户不存在!");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes("ewaytek"),  getName());
        return simpleAuthenticationInfo;
    }
}
