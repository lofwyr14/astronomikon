package net.popecke.astro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

@Named
@RequestScoped
public class Login {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private String username;
  private String password;

  public String login() throws ServletException {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final ExternalContext externalContext = facesContext.getExternalContext();
    final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

    LOG.info("Try to login user: '{}'", username);
    try {
      request.login(username, password);
      LOG.info("Successful login user: '{}'", username);
    } catch (ServletException e) {
      LOG.error("Login failed for user '{}'", username);
      facesContext.addMessage(null, new FacesMessage("Login fehlgeschlagen!"));
    }

    return "/astro.xhtml";
  }

  public String logout() throws ServletException, IOException {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final ExternalContext externalContext = facesContext.getExternalContext();
    final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

    request.logout();

    return facesContext.getViewRoot().getViewId();
  }

  public String resetSession() throws IOException {
    LOG.info("Resetting the session.");
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final ExternalContext externalContext = facesContext.getExternalContext();
    final HttpSession session = (HttpSession) externalContext.getSession(false);
    if (session != null) {
      session.invalidate();
    }
/* XXX reset theme doesn't work
    CookieUtils.removeThemeNameCookie(
        (HttpServletRequest)externalContext.getRequest(),
        (HttpServletResponse) externalContext.getResponse());
*/
    externalContext.redirect(externalContext.getRequestContextPath() + "/");
    facesContext.responseComplete();
    return null;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }
}
