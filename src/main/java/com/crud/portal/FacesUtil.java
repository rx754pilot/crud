package com.crud.portal;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class FacesUtil
{

    public static void redirect(String pageView)
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();

        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, pageView));

        try
        {
            extContext.redirect(url);
        }
        catch (IOException ioe)
        {
            throw new FacesException(ioe);
        }
    }

}
