package com.crud.portal.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.crud.portal.model.User;
import com.crud.portal.service.LoginActivityService;
import com.jedlab.framework.util.StringUtil;

/**
 * @author omidp
 *
 */
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{

    public static final String CURRENT_USERNAME = "currentUsername";
    public static final String CURRENT_USERID = "currentUserId";

    @Autowired
    LoginActivityService loginActivityService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        if (authentication != null && authentication.isAuthenticated())
        {
            Object principal = authentication.getPrincipal();
            boolean anonymous = principal instanceof String || principal.equals("anonymousUser");
            if (!anonymous)
            {
                if (principal instanceof User)
                {
                    HttpSession session = request.getSession(false);
                    if (session != null)
                    {
                        session.setAttribute(CURRENT_USERNAME, ((User) principal).getUsername());
                        session.setAttribute(CURRENT_USERID, ((User) principal).getId());
                        loginActivityService.revokeOtherTokens(((User) principal).getUsername());                        
                        String browser = request.getHeader("User-Agent");
                        if (StringUtil.isEmpty(browser))
                            browser = "Unknown Device";
                        loginActivityService.addActivity(((User) principal).getUsername(), browser, request.getRemoteAddr());
                    }
                }
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
