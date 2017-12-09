package com.crud.portal.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.crud.portal.service.LoginActivityService;

/**
 * @author omidp
 *
 */
public class AuthLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler
{

    @Autowired
    LoginActivityService loginActivityService;
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        HttpSession session = request.getSession(false);
        if(session != null)
        {
            String userName =(String) session.getAttribute(AuthSuccessHandler.CURRENT_USERNAME);
            loginActivityService.revokeOtherTokens(userName);
            session.removeAttribute(AuthSuccessHandler.CURRENT_USERNAME);
            session.removeAttribute(AuthSuccessHandler.CURRENT_USERID);
        }
        super.handle(request, response, authentication);
    }

}
