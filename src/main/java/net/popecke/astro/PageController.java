package net.popecke.astro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.FacesEvent;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class PageController implements Serializable {

  public static final String PHOTO_VIEW = "/photo-view.xhtml";
  public static final String PHOTO_LIST = "/photo-list.xhtml";
  public static final String PHOTO_EDIT = "/photo-edit.xhtml";

  private static final Logger LOG = LoggerFactory.getLogger(PageController.class);

  private String current = PHOTO_LIST;

  public String getCurrent() {
    LOG.info("current is " + current);
    return current;
  }

  public void setCurrent(String current) {
    this.current = current;
  }

  public void toList(final FacesEvent event) {
    current = PHOTO_LIST;
  }

  public void toPhotoEdit(final FacesEvent event) {
    current = PHOTO_EDIT;
  }
}
