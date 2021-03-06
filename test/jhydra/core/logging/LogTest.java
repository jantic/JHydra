/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.logging;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import jhydra.core.config.IRuntimeConfig;
import jhydra.core.exceptions.FatalException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;


/**
 *
 * @author jantic
 */
public class LogTest {
    private final String proj1Path = "./test projects/project 1";
    private final String logFolderPath = proj1Path + "/logs/";
    private final String logFilePath = logFolderPath + "rolling-log.log";
    
    public LogTest() {
    }
    
    /***Tests creation of fresh log file/folder, reads from it to make sure logs are being recorded as expected************/ 
    @Test
    public void proj1_loginfo_message_success() throws FatalException, IOException{
        clearLogDirectory();
        final ILog log = getLog();
        log.info("log line 1");
        log.info("log line 2");
        final String logContents = getLogContents();
        final Boolean expected = true;
        final Boolean actual = logContents.contains("INFO  - log line 1") 
                && logContents.contains("INFO  - log line 2");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void proj1_logdebug_message_success() throws FatalException, IOException{
        clearLogDirectory();
        final ILog log = getLog();
        log.debug("log line 1");
        log.debug("log line 2");
        final String logContents = getLogContents();
        final Boolean expected = true;
        final Boolean actual = logContents.contains("DEBUG - log line 1") 
                && logContents.contains("DEBUG - log line 2");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void proj1_logwarn_message_success() throws FatalException, IOException{
        clearLogDirectory();
        final ILog log = getLog();
        log.warn("log line 1");
        log.warn("log line 2");
        final String logContents = getLogContents();
        final Boolean expected = true;
        final Boolean actual = logContents.contains("WARN  - log line 1") 
                && logContents.contains("WARN  - log line 2");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void proj1_logerror_message_success() throws FatalException, IOException{
        clearLogDirectory();
        final ILog log = getLog();
        log.error("log line 1");
        log.error("log line 2");
        final String logContents = getLogContents();
        final Boolean expected = true;
        final Boolean actual = logContents.contains("ERROR - log line 1") 
                && logContents.contains("ERROR - log line 2");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void proj1_loginfo_messageException_success() throws FatalException, IOException{
        clearLogDirectory();
        final ILog log = getLog();
        log.info("log line 1");
        
        try{
            @SuppressWarnings({"NumericOverflow", "UnusedDeclaration"}) int result = 1/0;
        }
        catch(Exception e){
            log.info("log line 2", e);
        }
                
        final String logContents = getLogContents();
        final Boolean expected = true;
        final Boolean actual = logContents.contains("INFO  - log line 1") 
                && logContents.contains("INFO  - log line 2")
                && logContents.contains("/ by zero");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void proj1_logdebug_messageException_success() throws FatalException, IOException{
        clearLogDirectory();
        final ILog log = getLog();
        log.debug("log line 1");
        
        try{
            @SuppressWarnings({"NumericOverflow", "UnusedDeclaration"}) int result = 1/0;
        }
        catch(Exception e){
            log.debug("log line 2", e);
        }
                
        final String logContents = getLogContents();
        final Boolean expected = true;
        final Boolean actual = logContents.contains("DEBUG - log line 1") 
                && logContents.contains("DEBUG - log line 2")
                && logContents.contains("/ by zero");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void proj1_logwarn_messageException_success() throws FatalException, IOException{
        clearLogDirectory();
        final ILog log = getLog();
        log.warn("log line 1");
        
        try{
            @SuppressWarnings({"NumericOverflow", "UnusedDeclaration"}) int result = 1/0;
        }
        catch(Exception e){
            log.warn("log line 2", e);
        }
                
        final String logContents = getLogContents();
        final Boolean expected = true;
        final Boolean actual = logContents.contains("WARN  - log line 1") 
                && logContents.contains("WARN  - log line 2")
                && logContents.contains("/ by zero");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void proj1_logerror_messageException_success() throws FatalException, IOException{
        clearLogDirectory();
        final ILog log = getLog();
        log.error("log line 1");
        
        try{
            @SuppressWarnings({"NumericOverflow", "UnusedDeclaration"}) int result = 1/0;
        }
        catch(Exception e){
            log.error("log line 2", e);
        }
                
        final String logContents = getLogContents();
        final Boolean expected = true;
        final Boolean actual = logContents.contains("ERROR - log line 1") 
                && logContents.contains("ERROR - log line 2")
                && logContents.contains("/ by zero");
        Assert.assertEquals(expected, actual);
    }
    
    private String getLogContents() throws IOException{
        final File logFile = new File(logFilePath);
        return FileUtils.readFileToString(logFile);
    }
    
    private ILog getLog() throws FatalException{
        final IRuntimeConfig config = mock(IRuntimeConfig.class);
        when(config.getLogsDirectory()).thenReturn(getURI(logFolderPath)); 
        return new Log(config);
    }
    
    private URI getURI(String relativePath){
        final File file = new File(relativePath);
        return file.toURI();
    }
    
    private void clearLogDirectory() throws IOException{
        final File logDirectory = new File(logFolderPath);
        if(logDirectory.isDirectory()){
            FileUtils.deleteDirectory(logDirectory);
        }
    }
}
