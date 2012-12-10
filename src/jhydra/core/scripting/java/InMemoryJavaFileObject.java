/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import java.io.IOException;
import java.net.URI;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

/**
 *
 * @author jantic
 */
public class InMemoryJavaFileObject  extends SimpleJavaFileObject{
    private String contents = null;

    public InMemoryJavaFileObject(String className, String contents) throws Exception {
        super(URI.create("string:///" + className.replace('.', '/')
                + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
        this.contents = contents;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors)
            throws IOException {
        return contents;
    }
}
