package net.popecke.astro;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@SessionScoped
@Named
public class AngleCalculator implements Serializable {

  @Inject
  private CameraController cameraController;

  private List<Optic> optics = Arrays.asList(
      // XXX diameter
      new Optic("Sigma Fisheye *", 4, 10),
      // XXX diameter bei zoom?
      new Optic("Nikon Weitwinkel 10-24 mm *", 5, 24, 10, 24),
      new Optic("Nikon 50mm", 36, 50),
      // XXX diameter bei zoom?
      new Optic("Nikon Reisezoom 18-200 mm *", 35, 200, 18, 200),
      new Optic("Takahashi + Reducer", 60, 264),
      new Optic("Takahashi", 60, 355),
      new Optic("Takahashi + Flattner", 60, 374),
      new Optic("Takahashi + Extender", 60, 710),

      new Optic("Custom", 100, 1000, 4, 10000),

      new Optic("Newton/4", 200, 800),
      new Optic("Newton/4", 250, 1000),
      new Optic("Newton/4", 300, 1200),

      new Optic("Ritchey-Chrétien/8", 200, 1600),
      new Optic("Ritchey-Chrétien/8", 250, 2000),
      new Optic("Ritchey-Chrétien/8", 300, 2400),

      new Optic("Vixen R200SS", 200, 800),
      new Optic("Celestron C8", 203, 2000),
      new Optic("Hubble Space Telescope", 2400, 57600)
  );

  private Camera selectedCamera;

  public String getMillis() {
    return "" + System.currentTimeMillis();
  }

  public List<Optic> getOptics() {
    return optics;
  }

  public CameraController getCameraController() {
    return cameraController;
  }

  public String formatSensorAngle(Camera camera, Optic optic) {
    if (camera != null && optic != null) {
      double widthAngle = angle(camera.getWidth(), optic);
      double heightAngle = angle(camera.getHeight(), optic);
      return format(widthAngle) + " x " + format(heightAngle);
    } else {
      return "undefiniert";
    }
  }

  public String formatPixelAngle(Camera camera, Optic optic) {
    if (camera != null && optic != null) {
      double widthAngle = angle(camera.getWidth(), optic) / camera.getPixelCountX();
      double heightAngle = angle(camera.getHeight(), optic) / camera.getPixelCountY();
      if (almostEqual(widthAngle, heightAngle)) {
        return formatSmall(widthAngle);
      } else {
        return formatSmall(widthAngle) + " x " + formatSmall(heightAngle);
      }
    } else {
      return "undefiniert";
    }
  }

  private boolean almostEqual(double widthAngle, double heightAngle) {
    return (Math.abs(widthAngle - heightAngle) / widthAngle) < 0.01;
  }

  private static double angle(final double size, final Optic optic) {
    return Math.atan(size / 2.0 / optic.getFocalLength()) / Math.PI * 180.0 * 2.0;
  }

  private static String format(final double angle) {

    int d = (int) Math.floor(angle);
    double minfloat = (angle - d) * 60;
    int m = (int) Math.floor(minfloat);
    double secfloat = (minfloat - m) * 60;
    int s = (int) Math.round(secfloat);
    // After rounding, the seconds might become 60.
    if (s == 60) {
      m++;
      s = 0;
    }
    if (m == 60) {
      d++;
      m = 0;
    }

    if (Math.log10(angle) > 1) {
      return ("" + d + "°");
    } else if (Math.log10(angle) > -0.8) {
      return ("" + d + "° " + m + "'");
    } else {
      return ("" + d + "° " + m + "' " + s + '"');
    }
  }

  private static String formatSmall(final double angle) {
//    LOG.debug("-----------------------------------------------=");
//    LOG.debug("angle=" + angle);
    double second = angle * 60 * 60;
//    LOG.debug("second=" + second);
    final double a = Math.log10(second);
//    LOG.debug("a=" + a);
    final long b = Math.round(a);
//    LOG.debug("b=" + b);
    final long c = 2 - b;
//    LOG.debug("c=" + c);
    final double factor = Math.pow(10, c);
//    LOG.debug("factor=" + factor);
    second = Math.round((second * factor)) / factor;
//    LOG.debug("second=" + second);
    return ("" + second + '"');
  }

  public Converter getSensorConverter() {
    return new SensorConverter();
  }

  public class SensorConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
      for (Camera camera : cameraController.getAll()) {
        if (camera.getName().equals(value)) {
          return camera;
        }
      }
      return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
      return ((Camera) value).getName();
    }
  }
}
