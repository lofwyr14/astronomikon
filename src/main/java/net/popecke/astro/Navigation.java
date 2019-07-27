package net.popecke.astro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.FacesEvent;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class Navigation implements Serializable {

  public static final String PHOTO_VIEW = "/photo-view.xhtml";
  public static final String PHOTO_LIST = "/photo-list.xhtml";
  public static final String PHOTO_EDIT = "/photo-edit.xhtml";
  public static final String CAMERA_EDITOR = "/camera-editor.xhtml";
  public static final String CELESTIAL_VIEW = "/celestial-view.xhtml";
  public static final String CELESTIAL_LIST = "/celestial-list.xhtml";
  public static final String CELESTIAL_EDIT = "/celestial-edit.xhtml";

  private static final Logger LOG = LoggerFactory.getLogger(Navigation.class);

  private String current = PHOTO_LIST;

  public String getCurrent() {
    if (current == null) {
      LOG.error("No current site found, using default");
      current = PHOTO_LIST;
    }
    LOG.info("current='{}'", current);
    return current;
  }

  public void setCurrent(String current) {
    this.current = current;
  }

  public void checkRequestParam(final ComponentSystemEvent event) {
    final String blog = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("blog");
    if (blog != null) {
      current = "/blog/" + blog + ".xhtml";
      LOG.debug("Next page: '{}'", current);
    } else {
      LOG.debug("Nothing do to.");
    }
  }

  public void toList(final FacesEvent event) {
    current = PHOTO_LIST;
  }

  public void toCameraEditor(final FacesEvent event) {
    current = CAMERA_EDITOR;
  }

  public void toCelestialEdit(final FacesEvent event) {
    current = CELESTIAL_EDIT;
  }

  public void toPhotoEdit(final FacesEvent event) {
    current = PHOTO_EDIT;
  }

  public void toBlog(final FacesEvent event) {
    String value = null;
    for (UIComponent uiComponent : event.getComponent().getChildren()) {
      if (uiComponent instanceof UIParameter) {
       UIParameter parameter = (UIParameter) uiComponent;
        if ("blog" .equals( parameter.getName())) {
          value = (String) parameter.getValue();
        }
      }
    }
    if (value != null) {
      current = "/blog/" + value + ".xhtml";
      LOG.debug("Next page: '{}'", current);
    } else {
      LOG.error("No parameter with name blog found.");
    }
  }
}
