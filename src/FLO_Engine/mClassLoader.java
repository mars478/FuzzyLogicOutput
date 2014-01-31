package FLO_Engine;

import java.util.Hashtable;
import java.io.FileInputStream;

public class mClassLoader extends ClassLoader {
    private final boolean DEBUG = false;
    private Hashtable classes = new Hashtable();
    private String path = null;

    public mClassLoader(String path) {
        this.path = path;
    }

    private byte getClassImplFromDataBase(String className)[] {
    	if (DEBUG) System.out.println("        >>>>>> Fetching the implementation of "+className);
    	byte result[];
    	try {
            System.out.println(path+"\\"+className+".class");
    	    FileInputStream fi = new FileInputStream(path+"\\"+className+".class");
    	    result = new byte[fi.available()];
    	    fi.read(result);
    	    return result;
    	} catch (Exception e) {
    	    return null;
    	}
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        return (loadClass(className, true));
    }

    public synchronized Class loadClass(String className, boolean resolveIt)
    	throws ClassNotFoundException {
        Class result;
        byte  classData[];

        if (DEBUG) System.out.println("        >>>>>> Load class : "+className);

        /* Check our local cache of classes */
        result = (Class)classes.get(className);
        if (result != null) {
            if (DEBUG) System.out.println("        >>>>>> returning cached result.");
            return result;
        }

        /* Check with the primordial class loader */
        try {
            result = super.findSystemClass(className);
            if (DEBUG) System.out.println("        >>>>>> returning system class (in CLASSPATH).");
            return result;
        } catch (ClassNotFoundException e) {
            if (DEBUG) System.out.println("        >>>>>> Not a system class.");
        }

        /* Try to load it from our repository */
        classData = getClassImplFromDataBase(className);
        if (classData == null) {
            throw new ClassNotFoundException();
        }

        /* Define it (parse the class file) */
        result = defineClass(classData, 0, classData.length);
        if (result == null) {
            throw new ClassFormatError();
        }

        if (resolveIt) {
            resolveClass(result);
        }

        classes.put(className, result);
        if (DEBUG) System.out.println("        >>>>>> Returning newly loaded class.");
        return result;
    }
}