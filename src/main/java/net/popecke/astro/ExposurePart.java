package net.popecke.astro;

import java.time.Duration;

public class ExposurePart {
  public int count;
  public Duration duration;
  public Spectrum spectrum;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public Spectrum getSpectrum() {
    return spectrum;
  }

  public void setSpectrum(Spectrum spectrum) {
    this.spectrum = spectrum;
  }
}
