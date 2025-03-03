package cn.puc.uas.common.shiro.realm;

import cn.puc.uas.domain.system.response.ProfileResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import java.util.Set;

//公共的realm
public class IhrmRealm extends AuthorizingRealm {
    public void setName(String name){
        super.setName("ihrmRealm");
    }

    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取安全数据
        ProfileResult result = (ProfileResult)principalCollection.getPrimaryPrincipal();
        //2.获取权限信息
        Set<String> apisPerms = (Set<String>)result.getRoles().get("apis");
        //3.构造权限数据，返回值
        SimpleAuthorizationInfo info = new  SimpleAuthorizationInfo();
        info.setStringPermissions(apisPerms);
        return info;
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
