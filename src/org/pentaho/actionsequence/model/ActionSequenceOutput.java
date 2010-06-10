/*
 * Copyright 2006 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
*/
package org.pentaho.actionsequence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Convenience class used to distinguish action sequence inputs from action sequence outputs.
 * @author Angelo Rodriguez
 *
 */
public class ActionSequenceOutput extends AbstractIOElement implements IActionSequenceOutput, Serializable {
  
  IActionSequenceDocument parent;
  ArrayList<IActionSequenceOutputDestination> destinations = new ArrayList<IActionSequenceOutputDestination>();
  
  protected ActionSequenceOutput(IActionSequenceDocument parent) {
    this.parent = parent;
  }
  
  public List<IActionSequenceOutputDestination> getDestinations() {
    return destinations;
  }
  
  public IActionSequenceOutputDestination addDestination(String destination, String name) {
    IActionSequenceOutputDestination dest = new ActionSequenceOutputDestination(this);
    dest.setDestination(destination);
    dest.setName(name);
    destinations.add(dest);
    return dest;
  }
}