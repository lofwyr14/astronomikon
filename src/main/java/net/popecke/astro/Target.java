package net.popecke.astro;

public class Target {

  private String name;
  private Category category;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public static enum Category {
    galaxy,
    nebula,
    planetaryNebula, // PN
  }
}
