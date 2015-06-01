/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson;

/**
 *
 * @author guillermot
 */
public class IFException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3258378685457355761L;

        /**
         * constructor
         * @param string 
         */
	public IFException(String string) {
        super(string);
    }
        
}
