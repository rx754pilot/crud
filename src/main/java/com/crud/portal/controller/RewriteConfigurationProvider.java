package com.crud.portal.controller;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.springframework.stereotype.Component;

@Component
public class RewriteConfigurationProvider extends HttpConfigurationProvider
{

    @Override
    public Configuration getConfiguration(ServletContext context)
    {
        return ConfigurationBuilder.begin()
                
                .addRule(Join.path("/app/home").to("/home.xhtml"))
                .addRule(Join.path("/app/test").to("/test.xhtml"))
                .addRule(Join.path("/app/login").to("/login.xhtml"))
                .addRule(Join.path("/app/register").to("/register.xhtml"))
                ;
    }

    @Override
    public int priority()
    {
        return 10;
    }
}
