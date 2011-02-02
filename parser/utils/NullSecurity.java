/*****************************************************************************/
/****     Copyright (C) 2007                                              ****/
/****     António Manuel Rodrigues Manso                                  ****/
/****     e-mail: manso@ipt.pt                                            ****/
/****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
/****     Instituto Politécnico de Tomar                                  ****/
/****     Escola Superior de Tecnologia de Tomar                          ****/
/****                                                                     ****/
/*****************************************************************************/
/****     This software was build with the purpose of learning.           ****/
/****     Its use is free and is not given any guarantee                  ****/
/****     or support for the software.                                     ****/
/****                                                                     ****/
/****                                                                     ****/
/*****************************************************************************/

package utils;

import java.security.Permission;


public class NullSecurity extends  SecurityManager{
    /**
     * Construtor por defeito da classe NullSecurity
     */
    public NullSecurity() {
        super();
    }
    /**
     * 
     * @param perm 
     * @param context 
     */
    public void checkPermission(Permission perm, Object context) {}
    /**
     * 
     * @param host 
     * @param port 
     */
    public void checkConnect(String host, int port) {}
    /**
     * 
     * @param host 
     * @param port 
     * @param context 
     */
    public void checkConnect(String host, int port, Object context) {}
    /**
     * 
     * @param perm 
     */
    public void checkPermission(Permission perm){}
    public void checkPropertiesAccess(){}
    /**
     * 
     * @param key 
     */
    public void checkPropertyAccess(String key) {}
    /**
     * 
     * @param host 
     * @param port 
     */
    public void checkAccept(String host, int port) {}
}
