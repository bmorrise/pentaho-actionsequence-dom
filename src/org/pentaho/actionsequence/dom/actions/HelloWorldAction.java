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
package org.pentaho.actionsequence.dom.actions;

import org.dom4j.Element;
import org.pentaho.actionsequence.dom.ActionDefinition;
import org.pentaho.actionsequence.dom.ActionInput;
import org.pentaho.actionsequence.dom.IActionVariable;

public class HelloWorldAction extends ActionDefinition {

  public static final String COMPONENT_NAME = "org.pentaho.component.HelloWorldComponent"; //$NON-NLS-1$
  public static final String QUOTE_ELEMENT = "quote"; //$NON-NLS-1$
  
  protected static final String[] EXPECTED_INPUTS = new String[] {
    QUOTE_ELEMENT
  };
  
  public HelloWorldAction(Element actionDefElement) {
    super(actionDefElement);
  }

  public HelloWorldAction() {
    super(COMPONENT_NAME);
  }
  
  public String[] getExpectedInputs() {
    return EXPECTED_INPUTS;
  }
  
  public void setQuote(String quote) {
    setInputValue(QUOTE_ELEMENT, quote);
  }
  
  public String getQuote() {
    return getComponentDefinitionValue(QUOTE_ELEMENT);
  }
  
  public void setQuoteParam(IActionVariable variable) {
    setReferencedVariable(QUOTE_ELEMENT, variable);
  }
  
  public ActionInput getQuoteParam() {
    return getInputParam(QUOTE_ELEMENT);
  }
}