/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.lexicon;

import java.util.List;
import jhydra.core.FatalException;
import jhydra.core.config.IConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;


/**
 *
 * @author jantic
 */
public class LexiconTests {
    public LexiconTests() {
    }
    
    @Test
    public void initialization_success() throws FatalException{
        final IConfig config = mock(IConfig.class);
        when(config.getLexiconPath()).thenReturn("./test/test data/basic_lexicon.properties");
        final ILexicon lexicon = new Lexicon(config);
        final List<INameValue> pairs = lexicon.getAllNameDefaultValuePairs();
        final Integer expected = 6;
        final Integer actual = pairs.size();
        Assert.assertEquals(expected, actual);
    }
    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
