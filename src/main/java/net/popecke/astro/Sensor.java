//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.popecke.astro;

public class Sensor {
  private String name;
  private String example;
  private double width;
  private double height;
  private int resolutionX;
  private int resolutionY;

  public Sensor(String name, String example, double width, double height, int resolutionX, int resolutionY) {
    this.name = name;
    this.example = example;
    this.width = width;
    this.height = height;
    this.resolutionX = resolutionX;
    this.resolutionY = resolutionY;
  }

  public String getName() {
    return this.name;
  }

  public String getExample() {
    return this.example;
  }

  public double getWidth() {
    return this.width;
  }

  public double getHeight() {
    return this.height;
  }

  public int getResolutionX() {
    return this.resolutionX;
  }

  public int getResolutionY() {
    return this.resolutionY;
  }
}
