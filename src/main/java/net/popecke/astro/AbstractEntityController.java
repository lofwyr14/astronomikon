package net.popecke.astro;

import org.apache.myfaces.tobago.util.ComponentUtils;
import org.ektorp.support.CouchDbRepositorySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public abstract class AbstractEntityController<T extends AbstractEntity> {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractEntityController.class);

  private Class<T> clazz;
  private CouchDbRepositorySupport<T> repository;

  private List<T> all;
  private T current;

  @Inject
  protected Navigation navigation;

  private String name;

  public void init(final Class<T> clazz, CouchDbRepositorySupport<T> repository) {
    this.clazz = clazz;
    this.repository = repository;
    this.name = clazz.getSimpleName().toLowerCase();
  }

  public void toList(final FacesEvent event) {
    findAll();
    navigation.setCurrent("/" + name + "-list.xhtml");
  }

  @RolesAllowed("editor")
  public void toEdit(final FacesEvent event) {
    navigation.setCurrent("/" + name + "-edit.xhtml");
  }

  public void toView(final FacesEvent event) {
    navigation.setCurrent("/" + name + "-view.xhtml");
  }

  public void select(final T object) {
    LOG.info("Select '{}'", object);
    this.current = object;
    toView(null);
  }

  public void select(final FacesEvent event) {
    LOG.info("Select any, event={}", event);
    final UIData data = ComponentUtils.findAncestor(event.getComponent(), UIData.class);
    if (data != null) {
      current = (T) data.getRowData();
      LOG.info("Selected: " + current.getName());
    } else {
      current = null;
      LOG.info("Deselect.");
    }
//    LOG.info("Select photo {}", photo);
//    this.photo = photo;
    toView(null);
  }

  public void setCurrent(final T current) {
    this.current = current;
  }

  public T getCurrent() {
    if (current == null) {
      current = createInstance();
    }
    return current;
  }

  private T createInstance() {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      final String summary = "Can't create new instance of " + name;
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
      LOG.error(summary, e);
      return null;
    }
  }

  public List<T> getAll() {
    return all;
  }

  public void findAll() {
    all = repository.getAll();
  }

  @RolesAllowed("editor")
  public void create(final FacesEvent event) {
    current = createInstance();
    toEdit(null);
  }

  @RolesAllowed("editor")
  public void save(final FacesEvent event) {
    LOG.info("save");
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
    toEdit(null);
  }

  @RolesAllowed("editor")
  public void delete(final FacesEvent event) {
    repository.remove(current);
    current = null;
    toEdit(null);
  }


}
