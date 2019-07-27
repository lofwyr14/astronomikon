package net.popecke.astro;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class PhotoController extends AbstractEntityController<Photo> implements Serializable {

//  private static final Logger LOG = LoggerFactory.getLogger(PhotoController.class);

  @Inject
//  @Dependent
  private PhotoRepository repository;

  @PostConstruct
  public void init() {
    init(Photo.class, repository);
  }

/*
  @Inject
  private Navigation navigation;

  private List<Photo> all;
  private Photo current;

  public void select(final FacesEvent event) {
    LOG.info("Select photo {}", event);
    final UIData data = ComponentUtils.findAncestor(event.getComponent(), UIData.class);
    if (data != null) {
      current = (Photo) data.getRowData();
      LOG.info("Selected: " + current.getName());
    } else {
      current = null;
      LOG.info("Deselect.");
    }
//    LOG.info("Select photo {}", current);
//    this.current = current;
    navigation.setCurrent(Navigation.PHOTO_VIEW);
  }

  public Photo getCurrent() {

    if (current == null) {
      current = new Photo();
    }
    return current;
  }

  public List<Photo> getAll() {
    return all;
  }

  public void findAll(final ComponentSystemEvent event) {
    all = repository.getAll();
  }

  public void create(final FacesEvent event) {
    current = new Photo();
    navigation.setCurrent(Navigation.PHOTO_EDIT);
  }

  public void saveX(final FacesEvent event) {
    LOG.info("X");

    save(event);
  }

  public void save(final FacesEvent event) {
    LOG.info("save");
    try {
      Thread.sleep(10_000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Date now = new Date();
    current.setUpdateDate(now);
    if (current.getReleaseDate() == null) {
      current.setReleaseDate(now);
    }
    if (current.getRevision() == null) {
      repository.add(current);
    } else {
      repository.update(current);
    }
    navigation.setCurrent(Navigation.PHOTO_VIEW);
  }

  public void delete(final FacesEvent event) {
    repository.remove(current);
    current = null;
    navigation.setCurrent(Navigation.PHOTO_VIEW);
  }*/
}
