/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gt.ifepson;

import java.util.EventListener;

/**
 *
 * @author guillermot
 */
public interface EndExecEventListener extends EventListener {
    
    /**
     * disparo del evento
     * @param evt
     * @return 
     */
    public Boolean onEvent(EndExecEventArgs evt);
}
