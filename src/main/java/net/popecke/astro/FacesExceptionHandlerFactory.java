package net.popecke.astro;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class FacesExceptionHandlerFactory extends ExceptionHandlerFactory {

  private ExceptionHandlerFactory parent;

  public FacesExceptionHandlerFactory(final ExceptionHandlerFactory parent) {
    this.parent = parent;
  }

  @Override
  public ExceptionHandler getExceptionHandler() {
    ExceptionHandler result = parent.getExceptionHandler();
    result = new FacesExceptionHandler(result);
    return result;
  }

}


