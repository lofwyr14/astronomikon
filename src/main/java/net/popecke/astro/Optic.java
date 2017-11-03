package net.popecke.astro;

import java.io.Serializable;

public class Optic implements Serializable {

  private String name;
  private int diameter;
  private int focalLength;
  private int focalLengthMin;
  private int focalLengthMax;
  private boolean zoom;

  public Optic(String name, int diameter, int focalLength) {
    this.diameter = diameter;
    this.name = name;
    this.focalLength = focalLength;
    this.focalLengthMin = focalLength;
    this.focalLengthMax = focalLength;
    this.zoom = false;
  }

  public Optic(String name, int diameter, int focalLength, int focalLengthMin, int focalLengthMax) {
    this.name = name;
    this.diameter = diameter;
    this.focalLengthMin = focalLengthMin;
    this.focalLengthMax = focalLengthMax;
    this.focalLength = focalLength;
    this.zoom = true;
  }

  public double getFocalRatio() {
    return (double) focalLength / diameter;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDiameter() {
    return diameter;
  }

  public void setDiameter(int diameter) {
    this.diameter = diameter;
  }

  public int getFocalLength() {
    return focalLength;
  }

  public void setFocalLength(int focalLength) {
    this.focalLength = focalLength;
  }

  public int getFocalLengthMin() {
    return focalLengthMin;
  }

  public void setFocalLengthMin(int focalLengthMin) {
    this.focalLengthMin = focalLengthMin;
  }

  public int getFocalLengthMax() {
    return focalLengthMax;
  }

  public void setFocalLengthMax(int focalLengthMax) {
    this.focalLengthMax = focalLengthMax;
  }

  public boolean isZoom() {
    return zoom;
  }

  public void setZoom(boolean zoom) {
    this.zoom = zoom;
  }

  @Override
  public String toString() {
    return name +
        " (Öffnung: " + diameter + " mm, " +
        (zoom ? "Brennweite (min): " + focalLengthMin + " mm Brennweite (max): " + focalLengthMax + " mm"
            : "Brennweite: " + focalLength + " mm") +
        ')';
  }
}
