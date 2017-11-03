package net.popecke.astro;

import org.apache.myfaces.tobago.util.ComponentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIData;
import javax.faces.event.FacesEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SessionScoped
@Named
public class PhotoController implements Serializable {

  private static final Logger LOG = LoggerFactory.getLogger(PhotoController.class);

  @Inject
//  @Dependent
  private PhotoService photoService;

  @Inject
  private PageController pageController;

  private List<Photo> all;
  private Photo photo;

  public String select(final Photo photo) {
    LOG.info("Select photo {}", photo);
    this.photo = photo;
    return "/photo";
  }

  public void select2(final FacesEvent event) {
    LOG.info("Select photo {}", event);
    final UIData data = ComponentUtils.findAncestor(event.getComponent(), UIData.class);
    if (data != null) {
      photo = (Photo) data.getRowData();
      LOG.info("Selected: " + photo.getName());
    } else {
      photo = null;
      LOG.info("Deselect.");
    }
//    LOG.info("Select photo {}", photo);
//    this.photo = photo;
    pageController.setCurrent(PageController.PHOTO);
  }

  public Photo getPhoto() {

    if (photo == null) {
      photo = new Photo();
    }
    return photo;
  }

  public List<Photo> getAll() {
    return all;
  }

  public String findAll() {
    all = photoService.getAll();
    return "/photos";
  }

  public String create() {
    photo = new Photo();
    return "/photo-editor";
  }

  public void create2(final FacesEvent event) {
    photo = new Photo();
    pageController.setCurrent(PageController.PHOTO_EDITOR);
  }

  public String save() {
    Date now = new Date();
    photo.setUpdateDate(now);
    if (photo.getReleaseDate() == null) {
      photo.setReleaseDate(now);
    }
    if (photo.getRevision() == null) {
      photoService.add(photo);
    } else {
      photoService.update(photo);
    }
    return "/photo-editor";
  }

  public void save2(final FacesEvent event) {
    Date now = new Date();
    photo.setUpdateDate(now);
    if (photo.getReleaseDate() == null) {
      photo.setReleaseDate(now);
    }
    if (photo.getRevision() == null) {
      photoService.add(photo);
    } else {
      photoService.update(photo);
    }
    pageController.setCurrent(PageController.PHOTO);
  }
}
