package net.popecke.astro;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class FacesExceptionHandler extends ExceptionHandlerWrapper {

  private ExceptionHandler wrapped;

  public FacesExceptionHandler(final ExceptionHandler wrapped) {
    this.wrapped = wrapped;
  }

  @Override
  public ExceptionHandler getWrapped() {
    return wrapped;
  }

  @Override
  public void handle() throws FacesException {

    final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
    while (iterator.hasNext()) {
      final ExceptionQueuedEvent event = iterator.next();
      final ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
      final Throwable cause = context.getException();

      if (cause instanceof ViewExpiredException) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final NavigationHandler nav = facesContext.getApplication().getNavigationHandler();
        try {
          facesContext.addMessage(null, new FacesMessage("The view has been expired!"));

          /*
           * TODO analyse the '/faces'-prefix
           * actually the viewID should be enough, but if you do so, the FacesMessage won't be shown.
           */
          nav.handleNavigation(facesContext, null, ((ViewExpiredException) cause).getViewId());
          facesContext.renderResponse();
        } finally {
          iterator.remove();
        }
      } else if (cause instanceof FileNotFoundException
          || cause != null && cause.getCause() instanceof FileNotFoundException) {
        try {
          final FacesContext facesContext = FacesContext.getCurrentInstance();
          final NavigationHandler nav = facesContext.getApplication().getNavigationHandler();
          nav.handleNavigation(facesContext, null, "/error/404.xhtml");
          facesContext.getExternalContext().setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
          facesContext.renderResponse();
        } finally {
          iterator.remove();
        }
      }
    }
    getWrapped().handle();
  }
}
