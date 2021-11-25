//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.popecke.astro;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SessionScoped
@ManagedBean
public class AngleCalculator implements Serializable {
  private static final Logger LOG = LoggerFactory.getLogger(AngleCalculator.class);
  private List<Optic> optics = Arrays.asList(new Optic("Sigma Fisheye *", 4, 10), new Optic("Nikon Weitwinkel 10-24 mm *", 5, 24, 10, 24), new Optic("Nikon 50mm", 36, 50), new Optic("Nikon Reisezoom 18-200 mm *", 35, 200, 18, 200), new Optic("Takahashi + Reducer", 60, 264), new Optic("Takahashi", 60, 355), new Optic("Takahashi + Flattner", 60, 374), new Optic("Takahashi + Extender", 60, 710), new Optic("Custom", 100, 1000, 4, 10000), new Optic("Newton/4", 200, 800), new Optic("Newton/4", 250, 1000), new Optic("Newton/4", 300, 1200), new Optic("Ritchey-Chrétien/8", 200, 1600), new Optic("Ritchey-Chrétien/8", 250, 2000), new Optic("Ritchey-Chrétien/8", 300, 2400), new Optic("Vixen R200SS", 200, 800), new Optic("Celestron C8", 203, 2000), new Optic("Hubble Space Telescope", 2400, 57600));
  private List<Sensor> sensors = Arrays.asList(new Sensor("ASP-C", "Nikon D5200", 23.5D, 15.6D, 6000, 4000), new Sensor("Micro 3/4", "ZWO ASI 1600", 17.2D, 13.2D, 4656, 3520), new Sensor("Vollformat", "Nikon D810", 36.0D, 24.0D, 7360, 4912), new Sensor("Vollformat", "Sony A6S", 36.0D, 24.0D, 4240, 2832), new Sensor("Custom", (String)null, 15.0D, 10.0D, 800, 600));
  private Sensor selectedSensor;

  public AngleCalculator() {
    this.selectedSensor = (Sensor)this.sensors.get(0);
  }

  public String getMillis() {
    return "" + System.currentTimeMillis();
  }

  public List<Optic> getOptics() {
    return this.optics;
  }

  public List<Sensor> getSensors() {
    return this.sensors;
  }

  public Sensor getSelectedSensor() {
    return this.selectedSensor;
  }

  public void setSelectedSensor(Sensor selectedSensor) {
    this.selectedSensor = selectedSensor;
  }

  public String formatSensorAngle(Sensor sensor, Optic optic) {
    if (sensor != null && optic != null) {
      double widthAngle = angle(sensor.getWidth(), optic);
      double heightAngle = angle(sensor.getHeight(), optic);
      return format(widthAngle) + " x " + format(heightAngle);
    } else {
      return "undefiniert";
    }
  }

  public String formatPixelAngle(Sensor sensor, Optic optic) {
    if (sensor != null && optic != null) {
      double widthAngle = angle(sensor.getWidth(), optic) / (double)sensor.getResolutionX();
      double heightAngle = angle(sensor.getHeight(), optic) / (double)sensor.getResolutionY();
      return this.almostEqual(widthAngle, heightAngle) ? formatSmall(widthAngle) : formatSmall(widthAngle) + " x " + formatSmall(heightAngle);
    } else {
      return "undefiniert";
    }
  }

  private boolean almostEqual(double widthAngle, double heightAngle) {
    return Math.abs(widthAngle - heightAngle) / widthAngle < 0.01D;
  }

  private static double angle(double size, Optic optic) {
    return Math.atan(size / 2.0D / (double)optic.getFocalLength()) / 3.141592653589793D * 180.0D * 2.0D;
  }

  private static String format(double angle) {
    int d = (int)Math.floor(angle);
    double minfloat = (angle - (double)d) * 60.0D;
    int m = (int)Math.floor(minfloat);
    double secfloat = (minfloat - (double)m) * 60.0D;
    int s = (int)Math.round(secfloat);
    if (s == 60) {
      ++m;
      s = 0;
    }

    if (m == 60) {
      ++d;
      m = 0;
    }

    if (Math.log10(angle) > 1.0D) {
      return "" + d + "°";
    } else {
      return Math.log10(angle) > -0.8D ? "" + d + "° " + m + "'" : "" + d + "° " + m + "' " + s + '"';
    }
  }

  private static String formatSmall(double angle) {
    LOG.debug("-----------------------------------------------=");
    LOG.debug("angle=" + angle);
    double second = angle * 60.0D * 60.0D;
    LOG.debug("second=" + second);
    double a = Math.log10(second);
    LOG.debug("a=" + a);
    long b = Math.round(a);
    LOG.debug("b=" + b);
    long c = 2L - b;
    LOG.debug("c=" + c);
    double factor = Math.pow(10.0D, (double)c);
    LOG.debug("factor=" + factor);
    second = (double)Math.round(second * factor) / factor;
    LOG.debug("second=" + second);
    return "" + second + '"';
  }

  public Converter getSensorConverter() {
    return new AngleCalculator.SensorConverter();
  }

  public class SensorConverter implements Converter {
    public SensorConverter() {
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
      Iterator var4 = AngleCalculator.this.sensors.iterator();

      Sensor sensor;
      do {
        if (!var4.hasNext()) {
          return null;
        }

        sensor = (Sensor)var4.next();
      } while(!sensor.getName().equals(value));

      return sensor;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
      return ((Sensor)value).getName();
    }
  }
}
