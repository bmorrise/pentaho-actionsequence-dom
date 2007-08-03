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

import java.util.ArrayList;

import org.dom4j.Element;
import org.pentaho.actionsequence.dom.ActionDefinition;
import org.pentaho.actionsequence.dom.ActionInput;
import org.pentaho.actionsequence.dom.ActionOutput;
import org.pentaho.actionsequence.dom.ActionSequenceDocument;
import org.pentaho.actionsequence.dom.ActionSequenceValidationError;
import org.pentaho.actionsequence.dom.IActionVariable;

public class XQueryAction extends ActionDefinition {

  public static final String COMPONENT_NAME = "org.pentaho.component.XQueryLookupRule"; //$NON-NLS-1$
  public static final String DOCUMENT_ELEMENT = "document" ; //$NON-NLS-1$
  public static final String QUERY_ELEMENT = "query" ; //$NON-NLS-1$
  public static final String QUERY_RESULT_ELEMENT = "query-result" ; //$NON-NLS-1$
  public static final String SOURCE_XML = "source-xml"; //$NON-NLS-1$
  public static final String PREPARED_COMPONENT_ELEMENT = "prepared_component"; //$NON-NLS-1$
  public static final String OUTPUT_RESULT_SET = "output-result-set"; //$NON-NLS-1$
  public static final String OUTPUT_PREPARED_STATEMENT = "output-prepared_statement"; //$NON-NLS-1$
  public static final String DEFAULT_QUERY_RESULTS_NAME = "query_result"; //$NON-NLS-1$

  protected static final String[] EXPECTED_RESOURCES = new String[] {
    DOCUMENT_ELEMENT
  };
  
  protected static final String[] EXPECTED_INPUTS = new String[] {
    DOCUMENT_ELEMENT,
    QUERY_ELEMENT
  };

  public XQueryAction(Element actionDefElement) {
    super(actionDefElement);
  }

  public XQueryAction() {
    super(COMPONENT_NAME);
  }
  
  public String[] getExpectedInputs() {
    return EXPECTED_INPUTS;
  }
  
  public String[] getExpectedOutputs() {
    String expectedOutput = QUERY_RESULT_ELEMENT;
    if (getOutputParam(expectedOutput) ==  null) { 
      ActionOutput[] actionOutputs = getOutputParams(ActionSequenceDocument.RESULTSET_TYPE);
      if (actionOutputs.length > 0) {
        expectedOutput = actionOutputs[0].getName();
      }
    }
    return new String[]{expectedOutput};
  }
  
  public String[] getExpectedResources() {
    return EXPECTED_RESOURCES;
  }
  
  public void setSourceXml(String value) {
    setInputValue(DOCUMENT_ELEMENT, value);
  }
  
  public String getSourceXml() {
    return getComponentDefinitionValue(DOCUMENT_ELEMENT);
  }
  
  public void setSourceXmlParam(IActionVariable variable) {
    setReferencedVariable(DOCUMENT_ELEMENT, variable);
  }
  
  public ActionInput getSourceXmlParam() {
    return getInputParam(DOCUMENT_ELEMENT);
  }

  public void setQuery(String value) {
    setInputValue(QUERY_ELEMENT, value);
  }
  
  public String getQuery() {
    return getComponentDefinitionValue(QUERY_ELEMENT);
  }
  
  public void setQueryParam(IActionVariable variable) {
    setReferencedVariable(QUERY_ELEMENT, variable);
  }
  
  public ActionInput getQueryParam() {
    return getInputParam(QUERY_ELEMENT);
  }
  
  public void setOutputResultSetName(String name) {
    setOutputName(QUERY_RESULT_ELEMENT, name, ActionSequenceDocument.RESULTSET_TYPE);
    if ((name != null) && (name.trim().length() > 0)) {
      setOutputPreparedStatementName(null);
    }
  }
  
  public String getOutputResultSetName() {
    return getOutputPublicName(QUERY_RESULT_ELEMENT);
  }
  
  public ActionOutput getOutputResultSetParam() {
    return getOutputParam(QUERY_RESULT_ELEMENT);
  }
  
  public void setOutputPreparedStatementName(String name) {
    setOutputName(PREPARED_COMPONENT_ELEMENT, name, ActionSequenceDocument.XQUERY_TYPE);
    if ((name != null) && (name.trim().length() > 0)) {
      setOutputResultSetName(null);
      ActionOutput[] actionOutputs = getOutputParams();
      for (int i = 0; i < actionOutputs.length; i++) {
        if (!actionOutputs[i].getType().equals(ActionSequenceDocument.XQUERY_TYPE)) {
          actionOutputs[i].delete();
        }
      }
    }
  }
  
  public String getOutputPreparedStatementName() {
    return getOutputPublicName(PREPARED_COMPONENT_ELEMENT);
  }  
  
  public ActionOutput getOutputPreparedStatementParam() {
    return getOutputParam(PREPARED_COMPONENT_ELEMENT);
  }
  
  public ActionSequenceValidationError[] validate() {
    
    ArrayList errors = new ArrayList();
    ActionSequenceValidationError validationError = validateInputParam(DOCUMENT_ELEMENT);
    if (validationError != null) {
      if (validationError.errorCode == ActionSequenceValidationError.INPUT_MISSING) {
        validationError = validateResourceParam(DOCUMENT_ELEMENT);
        if (validationError != null) {
          switch (validationError.errorCode) {
            case ActionSequenceValidationError.INPUT_MISSING:
              validationError.errorMsg = "Missing source XML input parameter.";
              break;
            case ActionSequenceValidationError.INPUT_REFERENCES_UNKNOWN_VAR:
              validationError.errorMsg = "Source XML input parameter references unknown variable.";
              break;
            case ActionSequenceValidationError.INPUT_UNINITIALIZED:
              validationError.errorMsg = "Source XML input parameter is uninitialized.";
              break;
          }
          errors.add(validationError);
        }
      } else if (validationError.errorCode == ActionSequenceValidationError.INPUT_REFERENCES_UNKNOWN_VAR) {
          validationError.errorMsg = "Source XML input parameter references unknown variable.";
          errors.add(validationError);
      } else if (validationError.errorCode == ActionSequenceValidationError.INPUT_UNINITIALIZED) {
        validationError.errorMsg = "Source XML input parameter is uninitialized.";
        errors.add(validationError);
      }
    }
    
    validationError = validateInputParam(QUERY_ELEMENT);
    if (validationError != null) {
      switch (validationError.errorCode) {
        case ActionSequenceValidationError.INPUT_MISSING:
          validationError.errorMsg = "Missing query input parameter.";
          break;
        case ActionSequenceValidationError.INPUT_REFERENCES_UNKNOWN_VAR:
          validationError.errorMsg = "Query input parameter references unknown variable.";
          break;
        case ActionSequenceValidationError.INPUT_UNINITIALIZED:
          validationError.errorMsg = "Query input parameter is uninitialized.";
          break;
      }
      errors.add(validationError);
    }
    
    validationError = validateOutputParam(PREPARED_COMPONENT_ELEMENT);
    if (validationError != null) {
      validationError = validateOutputParam(QUERY_RESULT_ELEMENT);
      if (validationError != null) {
        validationError.errorMsg = "Missing query results output parameter.";
        errors.add(validationError);
      }
    }
    
    return (ActionSequenceValidationError[])errors.toArray(new ActionSequenceValidationError[0]);
  }
  
  protected void initNewActionDefinition() {
    super.initNewActionDefinition();
    setOutputResultSetName(DEFAULT_QUERY_RESULTS_NAME);
  }
}