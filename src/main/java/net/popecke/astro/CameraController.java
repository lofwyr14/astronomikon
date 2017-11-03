package net.popecke.astro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CameraController implements Serializable {

  private static final Logger LOG = LoggerFactory.getLogger(CameraController.class);

  @Inject
//  @Dependent
  private CameraRepository cameraRepository;

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
    all = cameraRepository.getAll();
    return "/camera-editor";
  }

  public String create() {
    current = new Camera();
    return "/camera-editor";
  }

  public String delete() {
    cameraRepository.remove(current);
    current = null;
    return "/camera-editor";
  }

  public String save() {
    if (current.getRevision() == null) {
      cameraRepository.add(current);
    } else {
      cameraRepository.update(current);
    }
    return "/camera-editor";
  }
}
