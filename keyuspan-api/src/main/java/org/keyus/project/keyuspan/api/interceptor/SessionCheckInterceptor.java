package org.keyus.project.keyuspan.api.interceptor;

import org.keyus.project.keyuspan.api.pojo.Member;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author keyus
 * @create 2019-07-26  上午8:27
 */
public class SessionCheckInterceptor implements HandlerInterceptor {

    private final Member member;

    public SessionCheckInterceptor() {
        member = new Member();
        member.setId(230L);
        member.setUsername("keyus");
        member.setPassword("keyus");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.getSession().setAttribute("member", member);
        return true;
    }
}
