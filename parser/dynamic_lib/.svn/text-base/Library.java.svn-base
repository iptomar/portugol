/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamic_lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arm
 */
public class Library {

    Class cl = null;
    Object obj;
    Method methods[];
    // An empty array; used for methods with no arguments at all.
    Object[] args = new Object[]{};
    private String classname = "";

    public Library(String className) {
        classname = className;
        loadClass(className);
    }

    public Vector<String> getMethodsNames() {
        Vector metodos = new Vector<String>();
        for (Method m : methods) {
            if (m.getDeclaringClass().getCanonicalName().equals(classname)) {
                metodos.add(m.getName());
            }
        }
        return metodos;
    }

    private Method getMethod(String name) {
        for (Method m : methods) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public Object Execute(String method) {
        Method m = getMethod(method);
        if (m != null) {
            try {
                return m.invoke(obj, args);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Object[] getParameters(String method, String param[]) {
        Class cparams[] = getMethod(method).getParameterTypes();
        if (param.length == cparams.length) {
            Object objParam[] = new Object[cparams.length];
            for (int i = 0; i < cparams.length; i++) {
                //System.out.println(cparams[i].getCanonicalName());
                if (cparams[i].getCanonicalName().equals("double")) {
                    objParam[i] = new Double(0.0);
                } else if (cparams[i].getCanonicalName().equals("int")) {
                    objParam[i] = new Integer(0);
                } else if (cparams[i].getCanonicalName().equals("boolean")) {
                    objParam[i] = new Boolean(false);
                } else if (cparams[i].getCanonicalName().equals("java.lang.String")) {
                    objParam[i] = new String("");
                } else if (cparams[i].getCanonicalName().equals("char")) {
                    objParam[i] = new Character(' ');
                }
            }
            return objParam;
        } else {
            return null;
        }
    }

    public Vector<String> getParametersTypeNames(String method) {
        Class cparams[] = getMethod(method).getParameterTypes();
        Vector<String> tipos = new Vector<String>();
        for (int i = 0; i < cparams.length; i++) {
            tipos.add(cparams[i].getCanonicalName());
        }
        return tipos;
    }

    public int getParametersSize(String method) {
        Class cparams[] = getMethod(method).getParameterTypes();
        return cparams.length;
    }

    public Vector<String> getConstsNames() {
        Field fields[] = cl.getFields();
        Vector<String> names = new Vector<String>();
        for (int i = 0; i < fields.length; i++) {
            names.add(fields[i].getName());
        }
        return names;
    }

    public Object[] getConstsValues() throws IllegalAccessException {
        Field fields[] = cl.getFields();
        Object consts[] = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getType().getCanonicalName().equals("double") || fields[i].getType().getCanonicalName().equals("int") || fields[i].getType().getCanonicalName().equals("boolean") || fields[i].getType().getCanonicalName().equals("java.lang.String") || fields[i].getType().getCanonicalName().equals("char")) {
                consts[i] = fields[i].get(null);
            }
        }
        return consts;
    }

    public Object Execute(String method, Object param[]) {
        Method m = getMethod(method);
        if (m != null) {
            try {
                return m.invoke(obj, param);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * make a object given a string name
     * @param className name of the class
     * @return object of the class
     */
    public void loadClass(String className) {
        try {
            Class[] classParam = null;
            Object[] objectParam = null;
            //-------------------------------------
            Thread t = Thread.currentThread();
            ClassLoader cloader = t.getContextClassLoader();
            cl = cloader.loadClass(className);
            //-----------------------------------
            Constructor co = cl.getConstructor(classParam);
            obj = co.newInstance(objectParam);
            methods = cl.getMethods();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String aux = "";
        Vector<String> methods_names = this.getMethodsNames();

        for (int i = 0; i < this.getConstsNames().size(); i++) {
            try {
                aux = aux + i + " " + this.getConstsValues()[i].getClass().getSimpleName() + " " + this.getConstsNames().get(i) + " = " + this.getConstsValues()[i].toString() + "\n";
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        aux = aux + "\n";

        int u = 0;
        for (String method : methods_names) {
            aux = aux + u + " " + this.getMethod(method).getReturnType().getSimpleName() + " ";
            aux = aux + method + " ( ";
            String[] ia = new String[this.getParametersSize(method)];
            Object params[] = this.getParameters(method, ia);
            for (int i = 0; i < this.getParametersSize(method); i++) {
                aux = aux + params[i].getClass().getSimpleName();
                if (i != this.getParametersSize(method) - 1) {
                    aux = aux + ", ";
                }
            }
            aux = aux + " )\n";
            u++;
        }
        return aux;
    }
    
    //pedro dias
    /*public Object getConstType(String name) {
        Field fields[] = cl.getFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(name)) {
                return fields[i].getType();
            }
        }
        return null;
    }*/
	//luís talento
	public String getConstType(String name) {
        Field fields[] = cl.getFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(name)) {
                return fields[i].getType().getSimpleName();
            }
        }
        return null;
    }
	
	 public String getMethodReturnType(String name) {
        for (Method m : methods) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m.getReturnType().getSimpleName();
            }
        }
        return null;
    }
    
}
