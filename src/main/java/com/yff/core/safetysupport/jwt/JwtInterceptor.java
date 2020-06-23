package com.yff.core.safetysupport.jwt;


import com.yff.core.safetysupport.parameterconf.Parameterconf;
import com.yff.core.util.CustomException;
import com.yff.core.util.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class JwtInterceptor extends HandlerInterceptorAdapter {

    private String USER_NOT_LOGGED_IN="用户未登录，请先登录";
    private String TOKEN_EXPIRED="token过期";

    @Autowired
    private Parameterconf parameterconf;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws CustomException {
        String url= request.getRequestURI();

        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            JwtIgnore jwtIgnore = handlerMethod.getMethodAnnotation(JwtIgnore.class);
            if (jwtIgnore != null) {
                return true;
            }
        }

        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        final String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            System.out.println("### 用户未登录，请先登录 ###   url:"+url);
//            throw new CustomException(USER_NOT_LOGGED_IN);
        }
//        System.out.println("authHeader:"+authHeader);
        final String token = authHeader.substring(6);

        if (parameterconf == null) {
//            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
//            parameterconf = (Parameterconf) factory.getBean("parameterconf");
            parameterconf =  SpringContextHolder.getBean(Parameterconf.class);
            System.out.println("parameterconf:"+parameterconf.getBnumber());
        }
        // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
        try{
            JwtTokenUtil.parseJWT(token, parameterconf.getBase64Secret());
        }catch (CustomException e){
            response.setHeader("sessionstatus", "tokenexpired");
            System.out.println("token过期");
//            throw  new CustomException(TOKEN_EXPIRED);
        }

        return true;
    }

}
