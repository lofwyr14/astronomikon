package net.popecke.astro;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class CameraController extends AbstractEntityController<Camera> implements Serializable {

//  private static final Logger LOG = LoggerFactory.getLogger(CameraController.class);

  @Inject
//  @Dependent
  private CameraRepository repository;

  @PostConstruct
  public void init() {
    init(Camera.class, repository);
  }

/*
  private List<Camera> all;
  private Camera current;

  public String select(final Camera camera) {
    LOG.info("Select camera {}", camera);
    this.current = camera;
    return "/camera-editor";
  }

  public Camera getCurrent() {
    if (current == null) {
      current = new Camera();
    }
    return current;
  }

  public void setCurrent(Camera current) {
    this.current = current;
  }

  public List<Camera> getAll() {
    return all;
  }

  @PostConstruct
  public void init() {
    findAll();
  }

  public String findAll() {
    all = repository.getAll();
    return "/camera-editor";
  }

  public String create() {
    current = new Camera();
    return "/camera-editor";
  }

  public String delete() {
    repository.remove(current);
    current = null;
    return "/camera-editor";
  }

  public String save() {
    if (current.getRevision() == null) {
      repository.add(current);
    } else {
      repository.update(current);
    }
    return "/camera-editor";
  }
*/
}
