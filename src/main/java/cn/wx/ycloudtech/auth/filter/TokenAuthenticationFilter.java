package cn.wx.ycloudtech.auth.filter;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import cn.wx.ycloudtech.auth.TokenAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc)
            throws ServletException, IOException {

        SecurityContext context = SecurityContextHolder.getContext();

        if (context.getAuthentication() != null && context.getAuthentication().isAuthenticated() || req.getMethod().equals("GET")) {
            // do nothing
        } else {

//            从请求体中获取相关认证参数，弃用改为从请求首部获取
//            BufferedReader reader = req.getReader();
//            StringBuilder builder = new StringBuilder();
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                builder.append(line);
//            }
//            String json = builder.toString();
//            if (json.equals("")) {
//                fc.doFilter(req, res);
//                return;
//            }
//
////            int len = req.getContentLength();
////            ServletInputStream iii = req.getInputStream();
////            byte[] buffer = new byte[len];
////            iii.read(buffer, 0, len);
////            String json = new String(buffer);
//            JSONObject data = JSON.parseObject(json);


            String sessionKey = req.getHeader("sessionKey");
            String userId = req.getHeader("userId");


            if (sessionKey != null && userId != null) {
                Authentication auth = new TokenAuthentication(sessionKey, userId);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            req.setAttribute("com.whu.checky.auth.filter.TokenAuthenticationFilter.FILTERED", true);
        }

        fc.doFilter(req, res);

    }


}
