//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.popecke.astro;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Photo implements Serializable {
  private String name;
  private Target target;
  private String thumb;
  private String preview;
  private String full;
  private Map<String, String> pictures = new HashMap();
  private Optic optic;
  private Sensor sensor;
  private Date begin;
  private Duration exposureTime;
  private Duration totalDuration;
  private Integer count;
  private Integer countDark;
  private Integer countFlat;
  private Integer countBias;
  private String description;

  public Photo() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Target getTarget() {
    return this.target;
  }

  public void setTarget(Target target) {
    this.target = target;
  }

  public String getThumb() {
    return this.thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public String getPreview() {
    return this.preview;
  }

  public void setPreview(String preview) {
    this.preview = preview;
  }

  public String getFull() {
    return this.full;
  }

  public void setFull(String full) {
    this.full = full;
  }

  public Map<String, String> getPictures() {
    return this.pictures;
  }

  public Optic getOptic() {
    return this.optic;
  }

  public void setOptic(Optic optic) {
    this.optic = optic;
  }

  public Sensor getSensor() {
    return this.sensor;
  }

  public void setSensor(Sensor sensor) {
    this.sensor = sensor;
  }

  public Date getBegin() {
    return this.begin;
  }

  public void setBegin(Date begin) {
    this.begin = begin;
  }

  public Duration getExposureTime() {
    return this.exposureTime;
  }

  public void setExposureTime(Duration exposureTime) {
    this.exposureTime = exposureTime;
  }

  public Duration getTotalDuration() {
    return this.totalDuration;
  }

  public void setTotalDuration(Duration totalDuration) {
    this.totalDuration = totalDuration;
  }

  public Integer getCount() {
    return this.count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Integer getCountDark() {
    return this.countDark;
  }

  public void setCountDark(Integer countDark) {
    this.countDark = countDark;
  }

  public Integer getCountFlat() {
    return this.countFlat;
  }

  public void setCountFlat(Integer countFlat) {
    this.countFlat = countFlat;
  }

  public Integer getCountBias() {
    return this.countBias;
  }

  public void setCountBias(Integer countBias) {
    this.countBias = countBias;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String toString() {
    return "Photo{target=" + this.target.getName() + ", begin=" + this.begin + '}';
  }
}
