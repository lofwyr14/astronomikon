//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.popecke.astro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class Controller implements Serializable {
  private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
  private Photo currentPhoto;

  public Controller() {
  }

  public String select(Photo photo) {
    LOG.info("Select photo {}", photo);
    this.currentPhoto = photo;
    return "photo.xhtml";
  }

  public Photo getCurrentPhoto() {
    return this.currentPhoto;
  }

  public void setCurrentPhoto(Photo currentPhoto) {
    this.currentPhoto = currentPhoto;
  }
}
