/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson;

import java.util.EventObject;

/**
 *
 * @author guillermot
 */
public class EndExecEventArgs extends EventObject {

    /**
     *
     */
    private static final long serialVersionUID = -2584326151374004631L;

    /**
     * Constructor
     * @param source
     */
    public EndExecEventArgs(Object source) {
        super(source);
    }

}
