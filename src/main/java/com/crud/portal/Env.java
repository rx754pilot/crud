package com.crud.portal;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;

public final class Env
{

    public static final String NL = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = File.separator;
    public static final String JBOSS7_DATA_HOME = System.getProperty("jboss.server.data.dir");
    public static final String JBOSS7_LOG_HOME = System.getProperty("jboss.server.log.dir");
    public static final String OS = System.getProperty("os.name");
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    public static final String USER_HOME = System.getProperty("user.home");
    
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sdf_without_tz = new SimpleDateFormat("yyyy-MM-dd");
    
    public static final DecimalFormat df = new DecimalFormat("###,###.##");
    
    public static final Axon axon = new AxonBuilder().create();

    public static File getFile(String fileName) throws URISyntaxException
    {
        String path = fileName;
        URL rsrc = Thread.currentThread().getContextClassLoader().getResource(path);
        if (rsrc == null)
            rsrc = Env.class.getClassLoader().getResource(path);
        if (rsrc == null)
            rsrc = Env.class.getResource(path);
        if (rsrc == null)
            return null;
        return Paths.get(rsrc.toURI()).toFile();
    }

}
