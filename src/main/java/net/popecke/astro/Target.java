//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.popecke.astro;

public class Target {
  private String name;
  private Target.Category category;

  public Target() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Target.Category getCategory() {
    return this.category;
  }

  public void setCategory(Target.Category category) {
    this.category = category;
  }

  public static enum Category {
    galaxy,
    nebula,
    planetaryNebula;

    private Category() {
    }
  }
}
