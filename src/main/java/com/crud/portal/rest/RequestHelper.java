package com.crud.portal.rest;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jedlab.framework.util.CollectionUtil;
import com.jedlab.framework.util.StringUtil;

public class RequestHelper
{

    private static final Logger logger = LoggerFactory.getLogger(RequestHelper.class);
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    public static Response get(String uri)
    {
        try
        {
            HttpGet get = new HttpGet(uri);
            get.setHeader("Accept", "application/json");
            get.setHeader("User-Agent", "Mozilla/5.0");
            CloseableHttpResponse response = httpclient.execute(get);
            InputStream content = null;
            try
            {
                int statusCode = response.getStatusLine().getStatusCode();
                content = response.getEntity().getContent();
                byte[] byteArray = IOUtils.toByteArray(content);
                return new Response(byteArray, statusCode);
            }
            catch (Exception e)
            {
                logger.info("exception occured : " + e.getMessage());
            }
            finally
            {
                IOUtils.closeQuietly(response);
                IOUtils.closeQuietly(content);
            }
        }
        catch (Exception e)
        {
            logger.info("exception occured : " + e.getMessage());
        }
        return new Response("".getBytes());
    }

    public static Response post(String uri, PostContent postContent)
    {
        try
        {
            HttpPost post = new HttpPost(uri);
            post.setHeader("Accept", "application/json");
            post.setHeader("User-Agent", "Mozilla/5.0");
            if (StringUtil.isNotEmpty(postContent.getContent()))
                post.setEntity(new StringEntity(postContent.getContent(), "UTF-8"));
            else if (CollectionUtil.isNotEmpty(postContent.getParams()))
            {
                post.setEntity(new UrlEncodedFormEntity(postContent.getParams(), "UTF-8"));
            }
            CloseableHttpResponse response = httpclient.execute(post);
            InputStream content = null;
            try
            {
                int statusCode = response.getStatusLine().getStatusCode();
                content = response.getEntity().getContent();
                byte[] byteArray = IOUtils.toByteArray(content);
                return new Response(byteArray, statusCode);
            }
            catch (Exception e)
            {
                logger.info("exception occured : " + e.getMessage());
            }
            finally
            {
                IOUtils.closeQuietly(response);
                IOUtils.closeQuietly(content);
            }
        }
        catch (Exception e)
        {
            logger.info("exception occured : " + e.getMessage());
        }
        return new Response("".getBytes());
    }

    public static class PostContent
    {
        private String content;
        ArrayList<NameValuePair> params;

        public ArrayList<NameValuePair> getParams()
        {
            if (params == null)
                params = new ArrayList<>();
            return params;
        }

        public PostContent()
        {
        }

        public PostContent(String content)
        {
            this.content = content;
        }

        public String getContent()
        {
            return content;
        }

    }

    public static class Response
    {
        private byte[] content;
        private int statusCode;

        public Response(byte[] content)
        {
            this.content = content;
        }

        public Response(byte[] content, int statusCode)
        {
            this.content = content;
            this.statusCode = statusCode;
        }

        public int getStatusCode()
        {
            return statusCode;
        }

        public byte[] getContent()
        {
            return content;
        }

        public String getContentAsString()
        {
            try
            {
                return new String(content, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                return "";
            }
        }

    }

}
