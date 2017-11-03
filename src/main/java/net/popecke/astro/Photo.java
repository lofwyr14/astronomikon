package net.popecke.astro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties({"id", "revision"})
public class Photo extends CouchDbDocument implements Serializable {

  private String name; // tbd ?

  private Target target;

  private String thumb;
  private String preview;
  private String full;
  private Map<String, String> pictures = new HashMap<>();

  private Optic optic;

  private Camera camera;

  private Date begin;
  private Duration exposureTime;
  private Duration totalDuration;
  private Integer count;
  private Integer countDark;
  private Integer countFlat;
  private Integer countBias;

  private String description;

  private Date releaseDate;
  private Date updateDate;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Target getTarget() {
    return target;
  }

  public void setTarget(Target target) {
    this.target = target;
  }

  public String getThumb() {
      return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public String getPreview() {
    return preview;
  }

  public void setPreview(String preview) {
    this.preview = preview;
  }

  public String getFull() {
    return full;
  }

  public void setFull(String full) {
    this.full = full;
  }

  public Map<String, String> getPictures() {
    return pictures;
  }

  public Optic getOptic() {
    return optic;
  }

  public void setOptic(Optic optic) {
    this.optic = optic;
  }

  public Camera getCamera() {
    return camera;
  }

  public void setCamera(Camera camera) {
    this.camera = camera;
  }

  public Date getBegin() {
    return begin;
  }

  public void setBegin(Date begin) {
    this.begin = begin;
  }

  public Duration getExposureTime() {
    return exposureTime;
  }

  public void setExposureTime(Duration exposureTime) {
    this.exposureTime = exposureTime;
  }

  public Duration getTotalDuration() {
    return totalDuration;
  }

  public void setTotalDuration(Duration totalDuration) {
    this.totalDuration = totalDuration;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Integer getCountDark() {
    return countDark;
  }

  public void setCountDark(Integer countDark) {
    this.countDark = countDark;
  }

  public Integer getCountFlat() {
    return countFlat;
  }

  public void setCountFlat(Integer countFlat) {
    this.countFlat = countFlat;
  }

  public Integer getCountBias() {
    return countBias;
  }

  public void setCountBias(Integer countBias) {
    this.countBias = countBias;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  @TypeDiscriminator
  public String getType() {
    return getClass().getSimpleName();
  }

  // XXX a bit ugly
  public void setType(String type) {
    assert type.equals(getClass().getSimpleName());
  }

  @Override
  public String toString() {
    return "Photo{" +
        "id='" + getId() + '\'' +
        ", revision='" + getRevision() + '\'' +
        ", name='" + name + '\'' +
        ", target=" + target +
        ", thumb='" + thumb + '\'' +
        ", preview='" + preview + '\'' +
        ", full='" + full + '\'' +
        ", pictures=" + pictures +
        ", optic=" + optic +
        ", sensor=" + camera +
        ", begin=" + begin +
        ", exposureTime=" + exposureTime +
        ", totalDuration=" + totalDuration +
        ", count=" + count +
        ", countDark=" + countDark +
        ", countFlat=" + countFlat +
        ", countBias=" + countBias +
        ", description='" + description + '\'' +
        ", releaseDate=" + releaseDate +
        ", updateDate=" + updateDate +
        '}';
  }
}
