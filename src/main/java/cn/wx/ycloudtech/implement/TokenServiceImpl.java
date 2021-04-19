package cn.wx.ycloudtech.implement;

import cn.wx.ycloudtech.domain.Role;
import cn.wx.ycloudtech.service.TokenService;
import cn.wx.ycloudtech.util.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    RedisServiceImpl redisService;

    @Override
    public UserDetails authenticateToken(@NonNull String token, String id) {
        Object obj = redisService.getUserOrAdminBySessionId(token);

        if(obj!=null) {
            // 更新过期时间
            redisService.updateExpireTime(token);

            if (obj instanceof cn.wx.ycloudtech.domain.User) {

                cn.wx.ycloudtech.domain.User user = (cn.wx.ycloudtech.domain.User) obj;

                if (id.equals(user.getUserId())) {

                    if(user.getUserIfSuper().equals(MyConstants.USER_SUPER_YES)){
                        return User.builder()
                                .username(id)
                                .password("")
                                .authorities(Role.ADMIN)
                                .build();
                    }else{
                        return User.builder()
                                .username(id)
                                .password("")
                                .authorities(Role.USER)
                                .build();
                    }
                }
            }
        }

        return User.builder()
                .username(id)
                .password("")
                .authorities(Role.VISITOR).build();
    }
}
