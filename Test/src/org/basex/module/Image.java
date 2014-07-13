package org.basex.module;

import java.awt.*;
import java.awt.color.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;

import org.basex.query.*;
import org.basex.query.iter.*;
import org.basex.query.value.item.*;
/**
 * Image class which has all the image manipulation methods.
 * @author Nikhilesh
 */
public class Image extends QueryModule{

  /** Kernel variable.*/
  protected Kernel kernel;
  /** boolean variable. */
  public boolean alpha = true;
  /** integer variable. */
  public static int clampedges = 1;
 /** float variable. */
   private float distance;
    /** float variable.  */
   private float angle;
    /** float variable. */
   private float zoom;
    /** float variable. */
    private final float radius = 10;
  /** float variable. */
 private final float bloomThreshold = 255;
/** float variable.*/
 private final int sides = 5;
 /** float variable. */
 private final int threshold = 1;
 /** boolean variable.*/
 private final boolean wrapEdges = false;
 /** integer variable.*/
 private final int iterations = 1;
 /** boolean variable.*/
 private final boolean premultiplyAlpha = true;
 /**
  * Converts the specified image to another format.
   * @param input input image
   * @param format image format
   * @return resulting image
   * @throws Exception exception
  */
 public B64 convert(final B64 input, final String format)throws Exception {
   return toBinary(toImage(input), format);
  }
 /**
	 * crops a given image.
	 * @param input input
	 * @param w Integer
	 * @param h Integer
	 * @return image
	 * @throws Exception exception
	 */
  public B64 cropImage(final B64 input, final Int w, final Int h)throws Exception {
		 int width = (int) w.itr();
		 int height = (int) h.itr();
       BufferedImage p = toImage(input);
	      BufferedImage dest = p.getSubimage(0, 0, width, height);
	      B64 b = toBinary(dest, format(input));
	      return b;
	   }
 /**
	 * Gives emboss effect on the image.
	 * @param input input
	 * @return image
	 * @throws Exception exception
	 */
	public B64 emboss(final B64 input)throws Exception {
		 B64 b = null;
		 final BufferedImage image = toImage(input);
		 int width = image.getWidth();
		    int height = image.getHeight();
    BufferedImage  dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++)
		      for (int j = 0; j < width; j++) {
		        int upperLeft = 0;
		        int lowerRight = 0;

		        if (i > 0 && j > 0)
		          upperLeft = image.getRGB(j - 1, i - 1);

		        if (i < height - 1 && j < width - 1)
		          lowerRight = image.getRGB(j + 1, i + 1);

		        int redDiff = ((lowerRight >> 16) & 255) - ((upperLeft >> 16) & 255);

		        int greenDiff = ((lowerRight >> 8) & 255) - ((upperLeft >> 8) & 255);

		        int blueDiff = (lowerRight & 255) - (upperLeft & 255);

		        int diff = redDiff;
		        if (Math.abs(greenDiff) > Math.abs(diff))
		          diff = greenDiff;
		        if (Math.abs(blueDiff) > Math.abs(diff))
		          diff = blueDiff;

		        int grayColor = 128 + diff;

		        if (grayColor > 255)
		          grayColor = 255;
		        else if (grayColor < 0)
		          grayColor = 0;

		        int newColor = (grayColor << 16) + (grayColor << 8) + grayColor;

		        dst.setRGB(j, i, newColor);
		      }
				b = toBinary(dst, format(input));
           return b;
	}

	/**
	 * Resizes the specified image.
	 * @param input input image
	 * @param h Item
	 * @param w Item
	 * @return resulting image
	 * @throws Exception exception
	 */
	public B64 resize(final B64 input, final Int w, final Int h) throws Exception {
		int width = (int) w.itr();
		int height = (int) h.itr();
       BufferedImage inputImage = toImage(input);
        BufferedImage outputImage = new BufferedImage(width,
        		height, inputImage.getType());
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, width, height, null);
        g2d.dispose();
        B64 b = toBinary(outputImage, format(input));
        return b;
  }
	/**
	 * Zooms an image.
	 * @param input B64
	 * @param w item
	 * @param h item
	 * @return image
	 * @throws Exception exception
	 */
	public B64 zoom(final B64 input, final Int w, final Int h) throws Exception {
		  BufferedImage inputImage = toImage(input);
      int width = (int) w.itr();
      int height = (int) h.itr();

       BufferedImage outputImage = new BufferedImage(width,
       		height, inputImage.getType());

       Graphics2D g2d = outputImage.createGraphics();
       g2d.drawImage(inputImage, 0, 0, width, height, null);
       g2d.dispose();
       B64 b = toBinary(outputImage, format(input));
       return b;
 }
	/**
	 * Zooms an image by its Height.
	 * @param input B64
	 * @param h Item
	 * @return image
	 * @throws Exception exception
	 */
	public B64 zoomheight(final B64 input, final Int h) throws Exception {
		  BufferedImage inputImage = toImage(input);
    int height = (int) h.itr();
		    BufferedImage outputImage = new BufferedImage(inputImage.getWidth(),
       		height, inputImage.getType());
        Graphics2D g2d = outputImage.createGraphics();
       g2d.drawImage(inputImage, 0, 0, inputImage.getWidth(), height, null);
       g2d.dispose();
       B64 b = toBinary(outputImage, format(input));
		return b;
 }
	/**
	 * Zooms an image by its width.
	 * @param input B64
	 * @param w Item
	 * @return image
	 * @throws Exception exception
	 */
	public B64 zoomwidth(final B64 input, final Int w) throws Exception {
		 // reads input image
       BufferedImage inputImage = toImage(input);
       int width = (int) w.itr();
       // creates output image
       BufferedImage outputImage = new BufferedImage(width,
    		   inputImage.getHeight(), inputImage.getType());
             // scales the input image to the output image
       Graphics2D g2d = outputImage.createGraphics();
       g2d.drawImage(inputImage, 0, 0, width, inputImage.getHeight(), null);
       g2d.dispose();
       B64 b = toBinary(outputImage, format(input));
		return b;
 }
	/**
	 * Flops an image.
	 * @param input B64
	 * @return image
	 * @throws Exception exception
	 */
	public B64 flop(final B64 input) throws Exception {
		B64 b = null;
		BufferedImage image = toImage(input);
	    int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, image.getType());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(image, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
			b = toBinary(dimg, format(input));
        return b;
    }
	/**
	 * Flips an image.
	 * @param input B64
	 * @return image
	 * @throws Exception exception
	 */
	public B64 flip(final B64 input) throws Exception {
		B64 b = null;
		BufferedImage image = toImage(input);
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, image.getType());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(image, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
	    b = toBinary(dimg, format(input));
        return b;
    }
	/**
	 * Gives the rotated image.
	 * @param input B64
	 * @param a item
	 * @return Rotated image
	 * @throws Exception exception
	 */
	public B64 rotate(final B64 input, final  Int a) throws Exception {
		B64 b = null;
		BufferedImage image = toImage(input);
		int ang = (int) a.itr();;
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, image.getType());
        Graphics2D g = dimg.createGraphics();
        g.rotate(Math.toRadians(ang), w / 2, h / 2);
        g.drawImage(image, null, 0, 0);
        g.dispose();
		b = toBinary(dimg, format(input));
        return b;
    }
	/**
	 * Gets the water mark on an image.
	 * @param input input data
	 * @param text String
	 * @param s Item
	 * @return image
	 * @throws Exception exception
	 */
	public B64 watermark(final B64 input, final String text, final Int s) throws Exception {
		B64 b = null;
			int size = (int) s.itr();
			BufferedImage sourceImage = toImage(input);
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
            // initializes necessary graphic properties
            AlphaComposite alphaChannel = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.3f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, size));
            FontMetrics fontMetrics = g2d.getFontMetrics();
            Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);
            // calculates the coordinate where the String is painted
            int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
            int centerY = sourceImage.getHeight() / 2;
            // paints the textual watermark
            g2d.drawString(text, centerX, centerY);
            g2d.dispose();
            System.out.println("The tex watermark is added to the image.");
			b = toBinary(sourceImage, format(input));
		return b;
	}


	/**
	 * Returns the format of an image.
	 * @param input input image
	 * @return image format
	 * @throws Exception exception
	 */
	public String format(final B64 input) throws Exception {
	   final byte[] array = input.binary(null);

	   final ByteArrayInputStream bais = new ByteArrayInputStream(array);
	   final ImageInputStream iis = ImageIO.createImageInputStream(bais);
	   final Iterator<ImageReader> it = ImageIO.getImageReaders(iis);
	   if(it.hasNext()) {
		   ImageReader ir =  it.next();
		   return ir.getFormatName();
	   }
	   throw new QueryException("Input format cannot be detected");
  }
	/**
	 * calculates the height of the image.
	 * @param input input data
	 * @return Height
	 * @throws Exception exception
	 */
	public int imageHeight(final B64 input) throws Exception {
		BufferedImage image = toImage(input);
		int height = image.getHeight();
		return height;

	}
	/**
	 * calculates the width of the image.
	 * @param input input data
	 * @return width
	 * @throws Exception exception
	 */
	public int imageWidth(final B64 input) throws Exception {
		BufferedImage image = toImage(input);
	    int h = image.getWidth();
		return h;
	}
	/**
	 * Creates an image instance from the given binary data.
	 * @param input input data
	 * @return image
	 * @throws Exception exception
	 */
	private BufferedImage toImage(final B64 input) throws Exception {
		   byte[] array = input.binary(null);
		   ByteArrayInputStream bais = new ByteArrayInputStream(array);
		   return ImageIO.read(bais);
	}
	/**
	 * Creates an image instance from the given binary data.
	 * @param image buffered image
	 * @param format image format
	 * @return binary data
	 * @throws Exception  Exception
	 */
	private static B64 toBinary(final BufferedImage image, final String format) throws Exception {
		   ByteArrayOutputStream baos = new ByteArrayOutputStream();
		   ImageIO.write(image, format, baos);
		   return new B64(baos.toByteArray());
	}
	/**
	 * Compresses an image.
	 * @param input input
	 * @param path String
	 * @param p Item
	 * @return Image
	 * @throws Exception Exception
	 */
	public B64 compressImage(final B64 input, final String path, final Int p) throws Exception {
	    BufferedImage img = null;
	    B64 b = null;
	     float quality = (int) p.itr();
	        img = toImage(input);
	        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(format(input));
	        @SuppressWarnings("cast")
          ImageWriter writer = (ImageWriter) iter.next();
	        //instantiate an ImageWriteParam object with default compression options
	        ImageWriteParam iwp = writer.getDefaultWriteParam();
	        //Set the compression quality
	        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	        iwp.setCompressionQuality(quality);
	        //delete the file. If I dont the file size will stay the same
	        //file.delete();
	        ImageOutputStream output = ImageIO.createImageOutputStream(new File(path));
	        writer.setOutput(output);
	        IIOImage image = new IIOImage(img, null, null);
	        writer.write(null, image, iwp);
	        writer.dispose();
				b = toBinary(img, format(input));
	    return b;
	}
	/**
	 * Creates a new image.
	 * @param w item
	 * @param h item
	 * @param format String
	 * @return Image
	 * @throws Exception exception
	 */
	public B64 createImage(final Int w, final Int h, final String format) throws Exception {
		B64 b = null;
		int width = (int) w.itr();
		int height = (int) h.itr();
			  BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		      Graphics g = image.getGraphics();
		      g.setColor(Color.white);
		      g.fillRect(0, 0, width, height);
		    g.dispose();
		    b = toBinary(image, format);
	    return b;
	}
	/**
	 * Checks the equality of the two images.
	 * @param image1 B64
	 * @param image2 B64
	 * @return boolean
	 * @throws Exception exception
	 */
	public boolean equals(final B64 image1, final B64 image2) throws Exception {
		BufferedImage rockImage = toImage(image1);
		BufferedImage mapImage = toImage(image2);
		int w = rockImage.getWidth();
		int h = rockImage.getHeight();
		if(w != mapImage.getWidth() || h != mapImage.getHeight())
			return false;

		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				   if(rockImage.getRGB(x, y) != mapImage.getRGB(x, y))
					   return false;
			}
		}
		return true;
	}
/**.
	 * Gives the exif information of the images.
	 * @param fileName  String
	 * @throws IOException   Ioexception
	 * @throws FileNotFoundException File not found
	 * @return String Array
	 */
	@SuppressWarnings("javadoc")
  public String[] exif(final String fileName) throws Exception {
		ImageInputStream iis = null;
		String[] names = null;
			iis = ImageIO.createImageInputStream(
					 new BufferedInputStream(
					  new FileInputStream(fileName)));
			Iterator<ImageReader> readers =
				 ImageIO.getImageReadersByMIMEType("image/jpeg");
				IIOImage image = null;
				if (readers.hasNext()) {
				 ImageReader reader = /*(ImageReader)*/ readers.next();
				 reader.setInput(iis, true);
					image = reader.readAll(0, null);
				  IIOMetadata metadata = image.getMetadata();
				   names = metadata.getMetadataFormatNames();
				 for (int i = 0; i < names.length; i++) {
					 System.out.println("Format name: " + names[i]);
					 System.out.println(metadata.getAsTree(names[i]));
				 }
				}
				return names;
	}
 /**.
	 * Gets an edge effect on the image
	 * @param input B64
	 * @return image
	 * @throws Exception Exception
	 */
	public B64 edge(final B64 input) throws Exception {
		B64 b = null;
		BufferedImage binput = null;
		BufferedImage outputImage = null;
	     binput = toImage(input);
	     int edgeVal = 4;
	   float[] edgeArr = {0, -1, 0, -1, edgeVal, -1, 0, -1, 0};
       ConvolveOp edgeOp = new ConvolveOp(new Kernel(3, 3, edgeArr),
           ConvolveOp.EDGE_NO_OP, null);
        outputImage = edgeOp.filter(binput, null);
       		b = toBinary(outputImage, format(input));
       		return b;
	}
	/**
	 * Overlays by taking an image.
	 * @param input1 B64
	 * @param input2 B64
	 * @return image
	 * @throws Exception exception
	 */
	public B64 overlayImage(final B64 input1, final B64 input2) throws Exception {
		 B64 b = null;
			 BufferedImage image = toImage(input1);
			 BufferedImage imageOverlay = toImage(input2);
			 int w = Math.max(image.getWidth(), imageOverlay.getWidth());
			 int h = Math.max(image.getHeight(), imageOverlay.getHeight());
			 BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			 Graphics g = combined.getGraphics();
			 g.drawImage(image, 0, 0, null);
			 g.drawImage(imageOverlay, 0, 0, null);
  		 b = toBinary(combined, format(input1));
		return b;
	 }
	/**
	 * Adds noise to Image.
	 * @param input B64
	 * @param value Item
	 * @return image
	 * @throws Exception exception
	 */
public B64 noise(final B64 input, final Int value) throws Exception {

        B64 b = null;
  		BufferedImage inputImage = null;
  		double stdDev = (int) value.itr();
  			inputImage = toImage(input);
  			BufferedImage output = new BufferedImage(inputImage.getWidth(),
  			    inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
  		    output.setData(inputImage.getData());
  			Raster source = inputImage.getRaster();
  		    WritableRaster out = output.getRaster();

  		    int currVal;
  		    double newVal;
  		    double gaussian;
  		    int bands  = out.getNumBands();
  		    int width  = inputImage.getWidth();
  		    int height = inputImage.getHeight();
  		    java.util.Random randGen = new java.util.Random();

  		    for (int j = 0; j < height; j++) {
  		        for (int i = 0; i < width; i++) {
  		            gaussian = randGen.nextGaussian();

  		            for (int k = 0; k < bands; k++) {
  		                newVal = stdDev * gaussian;
  		                currVal = source.getSample(i, j, k);
    	  newVal = newVal + currVal;
  		                if (newVal < 0)   newVal = 0.0;
  		                if (newVal > 255) newVal = 255.0;

  		                out.setSample(i, j, k, (int) newVal);
  		                b = toBinary(output, format(input));
  		            }
  		        }
  		    }
        return b;
}
      	/**
      	 * creates contrast effect on an image.
	 * @param input B64
	 * @param value Item
 	 * @return image
	 * @throws Exception Exception
	 */
	public B64 contrast(final B64 input, final Int value) throws Exception {
		B64 b = null;
		 float v = (int) value.itr();
		BufferedImage img = toImage(input);
			RescaleOp rescaleOp = new RescaleOp(v, 15, null);
			rescaleOp.filter(img, img);
	       	b = toBinary(img, format(input));
			return b;
	}
	/**
	 * chops an image.
	 * @param input B64
	 * @param r Item
	 * @param c Item
	 * @return image
	 * @throws Exception exception
	 */
	public ValueBuilder chop(final B64 input, final Int r, final Int c) throws Exception {
	   	final ValueBuilder vb = new ValueBuilder();
	        BufferedImage image = toImage(input);

	        int rows = (int) r.itr();
	        int cols = (int) c.itr();
	        int chunks = rows * cols;

	        int chunkWidth = image.getWidth() / cols;
	        int chunkHeight = image.getHeight() / rows;
	        int count = 0;
	        BufferedImage[] imgs = new BufferedImage[chunks];
	        for (int x = 0; x < rows; x++) {
	            for (int y = 0; y < cols; y++) {

	                imgs[count] = new BufferedImage(chunkWidth,
	                    chunkHeight, BufferedImage.TYPE_INT_RGB);

	                Graphics2D gr = imgs[count++].createGraphics();
	                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight,
	                    chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth,
	                    chunkHeight * x + chunkHeight, null);
	                gr.dispose();
	            }
	        }
	        System.out.println("Splitting done");
	        for (int i = 0; i < imgs.length; i++) {
	        	vb.add(toBinary(imgs[i], format(input)));
	          }
	        System.out.println("Mini images created");

		return vb;
	}
 /**
  * creates transparency of an image.
  * @param input B64
  * @return Image
  * @throws Exception exception
  */
	public B64 transparent(final B64 input) throws Exception {
		 B64 b = null;
    	final Color backColor = Color.BLACK;
    	final int thresh = 70;
        final int transparent = 1;
        BufferedImage initImage = toImage(input);
        int width = initImage.getWidth(null),
            height = initImage.getHeight(null);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(initImage, 0, 0, null);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel);

                int dr = Math.abs(color.getRed()   - backColor.getRed()),
                    dg = Math.abs(color.getGreen() - backColor.getGreen()),
                    db = Math.abs(color.getBlue()  - backColor.getBlue());

                if (dr <  thresh && dg < thresh && db < thresh) {
                    image.setRGB(x, y, transparent);
                }
            }
        }
       b = toBinary(image, format(input));
        return b;
    }
	/**
	 * Sharpens an image.
	 * @param input B64
	 * @return image
	 * @throws Exception exception
	 */
	 public B64 sharpen(final B64 input) throws Exception {
		  B64 b = null;
		  float opacity = 5.9f;
		  float opi = opacity - 1.0f;
		 float[] identityKernel = {
			       opacity, opi, opacity,
			        opi, opacity + 5.0f, opi,
			        opacity, opi, opacity
		  };
		  BufferedImage p = toImage(input);
		  BufferedImageOp identity =
		     new ConvolveOp(new Kernel(3, 3, identityKernel), ConvolveOp.EDGE_NO_OP, null);
		  BufferedImage op = identity.filter(p, null);
	     b = toBinary(op, format(input));
		 return b;
	    }
	 /**
	  * scales an image.
	  * @param input B64
	  * @param w integer
	  * @param h integer
	  * @return Image
	  * @throws Exception exception
	  */
	 public B64 scale(final B64 input, final Int w, final Int h) throws Exception {
         B64 b = null;
    	BufferedImage src = toImage(input);
    	int width = (int) w.itr();
    	int height = (int) h.itr();
        BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dest.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(
                (double) width / src.getWidth(),
                (double) height / src.getHeight());
        g.drawRenderedImage(src, at);
        b = toBinary(dest, format(input));
        return b;
}
	 /**.
	  * creates a black and white image
	  * @param input b64
	  * @return Image
	  * @throws Exception Exception
	  */
	  public B64 blacknWhite(final B64 input) throws Exception {
		   B64 b = null;
	    		       BufferedImage bi = toImage(input);
	    		       BufferedImage im =
	    		         new BufferedImage(bi.getWidth(),
	    		             bi.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
	    		       Graphics2D g2d = im.createGraphics();
	    		       g2d.drawImage(bi, 0, 0, null);
	    		     b = toBinary(im, format(input));
	       return b;
	     }
	  /**
	   * creates a grayscale of an image.
	   * @param input B64
	   * @return image
	   * @throws Exception exception
	   */
	  public B64 grayscale(final B64 input) throws Exception {
		  B64 b = null;
		  BufferedImage bi = toImage(input);
		  ColorConvertOp colorConvert =
		      new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),
		      null);
			    colorConvert.filter(bi, bi);
	    b = toBinary(bi, format(input));
	    return b;
	  }
	  /**
	   * Increase or decreases an image brightness.
	   * @param input B64
	   * @param q item
	   * @return Image
	   * @throws Exception exception
	   */
	  public B64 brightness(final B64 input, final Int q) throws Exception {
		  float quality = q.itr();
		  B64 c = null;
		  BufferedImage biSrc, biDest, bi;
		  Graphics2D big;
		  BufferedImage b = toImage(input);
		  biSrc = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_INT_RGB);
		  big = biSrc.createGraphics();
         big.drawImage(b, 0, 0, null);
         biDest = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_INT_RGB);
              bi = biSrc;
		   RescaleOp rescale;
		    float offset = 10;
		  rescale = new RescaleOp(quality, offset, null);
	        rescale.filter(biSrc, biDest);
	        bi = biDest;
	      c = toBinary(bi, format(input));
	      return c;
	  }
	  /**
	   * Creates gradientImage.
	   * @param w Item
	   * @param h Item
	   * @param gradient1 String
	   * @param gradient2 String
	   * @param extension String
	   * @return Image
	   * @throws Exception Exception
	   */
	  public B64 createGradientImage(final Int w, final Int h, final String gradient1,
		     final String gradient2, final String extension) throws Exception {
		    B64 b = null;
		    int width = (int) w.itr();
		    int height = (int) h.itr();
		    BufferedImage gradientImage = null; //createCompatibleImage(width, height);
		    Color aColor   = (Color) Color.class.getField(gradient1).get(null);
		    Color bColor   = (Color) Color.class.getField(gradient2).get(null);
		    gradientImage =
		        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
		        getDefaultConfiguration().createCompatibleImage(width, height);
		    GradientPaint gradient = new GradientPaint(0, 0, aColor, 0, height, bColor, false);
		    Graphics2D g2 = (Graphics2D) gradientImage.getGraphics();
		    g2.setPaint(gradient);
		    g2.fillRect(0, 0, width, height);
		    g2.dispose();
            b = toBinary(gradientImage, extension);
		    return b;
		  }
	  /**
	   * Checks whether a format is supported.
	   * @param extName String
	   * @return boolean
	   */
	  public boolean isSupportedImage(final String extName) {
	        boolean isSupported = false;
	        for (String format : ImageIO.getReaderFormatNames()) {
	            if (format.equalsIgnoreCase(extName)) {
	                isSupported = true;
	                break;
	            }
	        }
	        System.out.println(isSupported);
	        return isSupported;
	    }
	  /**
	   * Gives complete information of the image.
	   * @param file String
	   * @return String
	   * @throws Exception exception
	   */
	  public String imageInfo(final String file) throws Exception {
	       File p = new File(file);
    	  BufferedImage pq = ImageIO.read(p);
    	  String s = pq.toString();
     return s;
      }
	  /**
	   * creates a compatible image.
	   * @param src Buffered Image
	   * @param dstCM colorModel
	   * @return CompatibleImage
	   */
  	  private BufferedImage createCompatibleDestImage(final BufferedImage src,
  	      final ColorModel dstCM) {
        ColorModel dstCM2 = null;
  	    if (dstCM == null)
  	    dstCM2 = src.getColorModel();
      return new BufferedImage(dstCM2,
          dstCM2.createCompatibleWritableRaster(src.getWidth(),
          src.getHeight()), dstCM2.isAlphaPremultiplied(), null);
  }
	  /**
	   * Increase or decrease blur of an image.
	   * @param input B64
	   * @param value Item
	   * @return Image
	   * @throws Exception Exception
	   */

    @SuppressWarnings({"null"})
    public  B64 blur(final B64 input, final Int value) throws Exception {
           B64 b = null;
		  BufferedImage src = toImage(input);
	        float rad = value.itr();
	        BufferedImage dst = null;
		   int width = src.getWidth();
	        int height = src.getHeight();

	        if (dst == null)
	            dst = this.createCompatibleDestImage(src, null);

	        int[] inPixels = new int[width * height];
	        int[] outPixels = new int[width * height];
	        src.getRGB(0, 0, width, height, inPixels, 0, width);

			if (rad > 0) {
    	Gausian.convolveAndTranspose(makeKernel(rad), inPixels, outPixels, width, height,
			    alpha, alpha && premultiplyAlpha, false, clampedges);
			Gausian.convolveAndTranspose(makeKernel(rad), outPixels, inPixels, height, width,
			    alpha, false, alpha && premultiplyAlpha, clampedges);
			}

	        dst.setRGB(0, 0, width, height, inPixels, 0, width);
	        b = toBinary(dst, format(input));
          return b;

	  }
	  /**
	   *Blurs an image with motion.
	   * @param input B64
	   * @param value Item
	   * @return Image
	   * @throws Exception exception
	   */
    @SuppressWarnings({"null"})
	  public B64 motionblur(final B64 input, final Int value) throws Exception {
		  B64 b1 = null;
		  BufferedImage src = toImage(input);
	       float  rotate = value.itr();
	     float ang = 1.0f;

      BufferedImage dst = null;
		      int width = src.getWidth();
	        int height = src.getHeight();

	        if (dst == null)
	            dst = createCompatibleDestImage(src, null);

	        int[] inPixels = new int[width * height];
	        int[] outPixels = new int[width * height ];
	        getRGB(src, 0, 0, width, height, inPixels);

			int cx = width / 2;
			int cy = height / 2;
			int index = 0;

	        float imageRadius = (float) Math.sqrt(cx * cx + cy * cy);
	        float translateX = (float) (distance * Math.cos(ang));
	        float translateY = (float) (distance * -Math.sin(ang));
			float maxDistance = distance + Math.abs(rotate * imageRadius) + zoom * imageRadius;
			int repetitions = (int) maxDistance;
			AffineTransform t = new AffineTransform();
			Point2D.Float p = new Point2D.Float();

	        if (premultiplyAlpha)
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int a = 0, r = 0, g = 0, b = 0;
					int count = 0;
					for (int i = 0; i < repetitions; i++) {
						int newX = x, newY = y;
						float f = (float) i / repetitions;

						p.x = x;
						p.y = y;
						t.setToIdentity();
						t.translate(cx + f * translateX, cy + f * translateY);
						float s = 1 - zoom * f;
						t.scale(s, s);
						if (rotate != 0)
							t.rotate(-rotate * f);
						t.translate(-cx, -cy);
						t.transform(p, p);
						newX = (int) p.x;
						newY = (int) p.y;

						if (newX < 0 || newX >= width) {
							if (wrapEdges)
								newX = ImageMath.mod(newX, width);
							else
								break;
						}
						if (newY < 0 || newY >= height) {
							if (wrapEdges)
								newY = ImageMath.mod(newY, height);
							else
								break;
						}

						count++;
						int rgb = inPixels[newY * width + newX];
						a += (rgb >> 24) & 0xff;
						r += (rgb >> 16) & 0xff;
						g += (rgb >> 8) & 0xff;
						b += rgb & 0xff;
					}
					if (count == 0) {
						outPixels[index] = inPixels[index];
					} else {
						a = PixelUtils.clamp(/*(int)*/a / count);
						r = PixelUtils.clamp(/*(int)*/r / count);
						g = PixelUtils.clamp(/*(int)*/g / count);
						b = PixelUtils.clamp(/*(int)*/b / count);
						outPixels[index] = (a << 24) | (r << 16) | (g << 8) | b;
					}
					index++;
				}
			}
	        if (premultiplyAlpha)

	        setRGB(dst, 0, 0, width, height, outPixels);
	        b1 = toBinary(dst, format(input));
	        return b1;
	    }
	  /**
	   * Blurs an image.
	   * @param input B64
	   * @param value1 Integer
	   * @param value2 Integer
	   * @return Image
	   * @throws Exception exception
	   */
    @SuppressWarnings({"null"})
	  public B64 boxblurfilter(final B64 input,
	      final Int value1, final Int value2)throws Exception {
		   B64 b = null;
		   float hRadius2 = value1.itr();
		   float vRadius2 = value2.itr();
		   BufferedImage src = toImage(input);
      BufferedImage dst = null;

	        int width = src.getWidth();
	        int height = src.getHeight();
       if (dst == null)
	            dst = createCompatibleDestImage(src, null);

	        int[] inPixels = new int[width * height];
	        int[] outPixels = new int[width * height];
	        getRGB(src, 0, 0, width, height, inPixels);

	        if (premultiplyAlpha)
			for (int i = 0; i < iterations; i++) {
	         BoxBlurFilter.blur(inPixels, outPixels, width, height, hRadius2);
	         BoxBlurFilter.blur(outPixels, inPixels, height, width, vRadius2);
	        }
	        BoxBlurFilter.blurFractional(inPixels, outPixels, width, height, hRadius2);
	        BoxBlurFilter.blurFractional(outPixels, inPixels, height, width, vRadius2);
	        if (premultiplyAlpha)
           setRGB(dst, 0, 0, width, height, inPixels);
	        b = toBinary(dst, format(input));
	        return b;
	    }
	  /**
	   * Blurs an image by its lens.
	   * @param input B64
	   * @param value Item
	   * @return image
	 * @throws Exception Exception
	   */
    @SuppressWarnings({"hiding", "null", "unused"})
    public B64 lensBlurfilter(final B64 input, final Int value)throws Exception {
	        B64 b1 = null;
	        float radius = (int) value.itr();
	        BufferedImage src = toImage(input);
	        BufferedImage dst = null;
		  int width = src.getWidth();
	        int height = src.getHeight();
	        int rows = 1, cols = 1;
	        int log2rows = 0, log2cols = 0;
	        int bloom = 1;
	        int iradius = (int) Math.ceil(radius);
	        int tileWidth = 128;
	        int tileHeight = tileWidth;


			tileWidth = iradius < 32 ? Math.min(128, width + 2 * iradius)
			                         : Math.min(256, width + 2 * iradius);
			tileHeight = iradius < 32 ? Math.min(128, height + 2 * iradius)
			                          : Math.min(256, height + 2 * iradius);

	        if (dst == null)
	            dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

	        while (rows < tileHeight) {
	            rows *= 2;
	            log2rows++;
	        }
	        while (cols < tileWidth) {
	            cols *= 2;
	            log2cols++;
	        }
	        int w = cols;
	        int h = rows;

			tileWidth = w;
			tileHeight = h;
	        FFT fft = new FFT(Math.max(log2rows, log2cols));

	        int[] rgb = new int[w * h];
	        float[][] mask = new float[2][w * h];
	        float[][] gb = new float[2][w * h];
	        float[][] ar = new float[2][w * h];

			double polyAngle = Math.PI / sides;
			double polyScale = 1.0f / Math.cos(polyAngle);
			double r2 = radius * radius;
			double rangle = Math.toRadians(angle);
			float total = 0;
	        int i = 0;
	        for (int y = 0; y < h; y++) {
	            for (int x = 0; x < w; x++) {
	                double dx = x - w / 2f;
	                double dy = y - h / 2f;
					double r = dx * dx + dy * dy;
					double f = r < r2 ? 1 : 0;
					if (f != 0) {
						r = Math.sqrt(r);
						if (sides != 0) {
							double a = Math.atan2(dy, dx) + rangle;
							a = ImageMath.mod(a, polyAngle * 2) - polyAngle;
							f = Math.cos(a) * polyScale;
						} else
							f = 1;
						f = f * r < radius ? 1 : 0;
					}
					total += (float) f;

					mask[0][i] = (float) f;
	                mask[1][i] = 0;
	                i++;
	            }
	        }

	        i = 0;
	        for (int y = 0; y < h; y++) {
	            for (int x = 0; x < w; x++) {
	                mask[0][i] /= total;
	                i++;
	            }
	        }

	        fft.transform2D(mask[0], mask[1], w, h, true);

	        for (int tileY = -iradius; tileY < height; tileY += tileHeight - 2 * iradius) {
	            for (int tileX = -iradius; tileX < width; tileX += tileWidth - 2 * iradius) {
	                int tx = tileX, ty = tileY, tw = tileWidth, th = tileHeight;
	                int fx = 0, fy = 0;
	                if (tx < 0) {
	                    tw += tx;
	                    fx -= tx;
	                    tx = 0;
	                }
	                if (ty < 0) {
	                    th += ty;
	                    fy -= ty;
	                    ty = 0;
	                }
	                if (tx + tw > width)
	                    tw = width - tx;
	                if (ty + th > height)
	                    th = height - ty;
	                src.getRGB(tx, ty, tw, th, rgb, fy * w + fx, w);

	                i = 0;
	                for (int y = 0; y < h; y++) {
	                    int imageY = y + tileY;
	                    int j;
	                    if (imageY < 0)
	                        j = fy;
	                    else if (imageY > height)
	                        j = fy + th - 1;
	                    else
	                        j = y;
	                    j *= w;
	                    for (int x = 0; x < w; x++) {
	                        int imageX = x + tileX;
	                        int k;
	                        if (imageX < 0)
	                            k = fx;
	                        else if (imageX > width)
	                            k = fx + tw - 1;
	                        else
	                            k = x;
	                        k += j;

	                        ar[0][i] = (rgb[k] >> 24) & 0xff;
	                        float r = (rgb[k] >> 16) & 0xff;
	                        float g = (rgb[k] >> 8) & 0xff;
	                        float b = rgb[k] & 0xff;


	                        if (r > bloomThreshold)
								r *= bloom;
//								r = bloomThreshold + (r-bloomThreshold) * bloom;
	                        if (g > bloomThreshold)
								g *= bloom;
//								g = bloomThreshold + (g-bloomThreshold) * bloom;
	                        if (b > bloomThreshold)
								b *= bloom;
//								b = bloomThreshold + (b-bloomThreshold) * bloom;

							ar[1][i] = r;
							gb[0][i] = g;
							gb[1][i] = b;

	                        i++;
	                        k++;
	                    }
	                }

	                // Transform into frequency space
	                fft.transform2D(ar[0], ar[1], cols, rows, true);
	                fft.transform2D(gb[0], gb[1], cols, rows, true);

	                // Multiply the transformed pixels by the transformed kernel
	                i = 0;
	                for (int y = 0; y < h; y++) {
	                    for (int x = 0; x < w; x++) {
	                        float re = ar[0][i];
	                        float im = ar[1][i];
	                        float rem = mask[0][i];
	                        float imm = mask[1][i];
	                        ar[0][i] = re * rem - im * imm;
	                        ar[1][i] = re * imm + im * rem;

	                        re = gb[0][i];
	                        im = gb[1][i];
	                        gb[0][i] = re * rem - im * imm;
	                        gb[1][i] = re * imm + im * rem;
	                        i++;
	                    }
	                }

	                // Transform back
	                fft.transform2D(ar[0], ar[1], cols, rows, false);
	                fft.transform2D(gb[0], gb[1], cols, rows, false);

	                // Convert back to RGB pixels, with quadrant remapping
	                int rowflip = w >> 1;
	                int colflip = h >> 1;
	                int index = 0;

	                //FIXME-don't bother converting pixels off image edges
	                for (int y = 0; y < w; y++) {
	                    int ym = y ^ rowflip;
	                    int yi = ym * cols;
	                    for (int x = 0; x < w; x++) {
	                        int xm = yi + (x ^ colflip);
	                        int a = (int) ar[0][xm];
	                        int r = (int) ar[1][xm];
	                        int g = (int) gb[0][xm];
	                        int b = (int) gb[1][xm];

							// Clamp high pixels due to blooming
							if (r > 255)
								r = 255;
							if (g > 255)
								g = 255;
							if (b > 255)
								b = 255;
	                        int argb = (a << 24) | (r << 16) | (g << 8) | b;
	                        rgb[index++] = argb;
	                    }
	                }

	                // Clip to the output image
	                tx = tileX + iradius;
	                ty = tileY + iradius;
	                tw = tileWidth - 2 * iradius;
	                th = tileHeight - 2 * iradius;
	                if (tx + tw > width)
	                    tw = width - tx;
	                if (ty + th > height)
	                    th = height - ty;
	                dst.setRGB(tx, ty, tw, th, rgb, iradius * w + iradius, w);
	            }
	        }
	        b1 = toBinary(dst, format(input));
	        return b1;
	    }
	  /**
	   * Blurs an image by cleaning the image.
	   * @param input B64
	   * @param value Item
	   * @return image
	 * @throws Exception exception
	   */
    @SuppressWarnings({"null"})
	  public B64 smartBlurfilter(final B64 input, final Int value) throws Exception {
	        B64 b = null;
	        int hRad = (int) value.itr();
		  BufferedImage src = toImage(input);
	       BufferedImage dst = null;
		  int width = src.getWidth();
	        int height = src.getHeight();

	        if (dst == null)
	            dst = createCompatibleDestImage(src, null);

	        int[] inPixels = new int[width * height];
	        int[] outPixels = new int[width * height];
	        getRGB(src, 0, 0, width, height, inPixels);

			Kernel ker = Gausian.makeKernel(hRad);
			thresholdBlur(ker, inPixels, outPixels, width, height, true);
			thresholdBlur(ker, outPixels, inPixels, height, width, true);

	        setRGB(dst, 0, 0, width, height, inPixels);
	        b = toBinary(dst, format(input));
	        return b;
	    }
	  /**
	   *  get the RGB pixels of an image.
	   * @param image BufferedImage
	   * @param x integer
	   * @param y integer
	   * @param width integer
	   * @param height integer
	   * @param pixels integer
	   * @return Image
	   */
	  private int[] getRGB(final BufferedImage image, final int x,
	      final int y, final int width, final int height, final int[] pixels) {
			int type = image.getType();
			if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
				return (int []) image.getRaster().getDataElements(x, y, width, height, pixels);
			return image.getRGB(x, y, width, height, pixels, 0, width);
	    }
	  /**
	   * Sets the RGB of an image.
	   * @param image BufferedImage
	   * @param x integer
	   * @param y integer
	   * @param width integer
	   * @param height integer
	   * @param pixels integer
	   */
		private void setRGB(final BufferedImage image, final int x, final int y,
		    final int width, final int height, final int[] pixels) {
			int type = image.getType();
			if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
				image.getRaster().setDataElements(x, y, width, height, pixels);
			else
				image.setRGB(x, y, width, height, pixels, 0, width);
	    }
		/**
		 * Returns an Image with blurring features.
		 * @param ker Kernel
		 * @param inPixels Integer
		 * @param outPixels Integer
		 * @param width Integer
		 * @param height Integer
		 * @param alp boolean
		 */
	  private void thresholdBlur(final Kernel ker, final int[] inPixels, final int[] outPixels,
	      final int width, final int height, final boolean alp) {
//			int index = 0;
//			 int hRadius = 5;
//			 int vRadius = 5;
//			 int threshold = 10;

			float[] matrix = ker.getKernelData(null);
			int cols = ker.getWidth();
			int cols2 = cols / 2;

			for (int y = 0; y < height; y++) {
				int ioffset = y * width;
	            int outIndex = y;
				for (int x = 0; x < width; x++) {
					float r = 0, g = 0, b = 0, a = 0;
					int moffset = cols2;

	                int rgb1 = inPixels[ioffset + x];
	                int a1 = (rgb1 >> 24) & 0xff;
	                int r1 = (rgb1 >> 16) & 0xff;
	                int g1 = (rgb1 >> 8) & 0xff;
	                int b1 = rgb1 & 0xff;
					float af = 0, rf = 0, gf = 0, bf = 0;
	                for (int col = -cols2; col <= cols2; col++) {
						float f = matrix[moffset + col];

						if (f != 0) {
							int ix = x + col;
							if (!(0 <= ix && ix < width))
								ix = x;
							int rgb2 = inPixels[ioffset + ix];
	                        int a2 = (rgb2 >> 24) & 0xff;
	                        int r2 = (rgb2 >> 16) & 0xff;
	                        int g2 = (rgb2 >> 8) & 0xff;
	                        int b2 = rgb2 & 0xff;

							int d;
	                        d = a1 - a2;
	                        if (d >= -threshold && d <= threshold) {
	                            a += f * a2;
	                            af += f;
	                        }
	                        d = r1 - r2;
	                        if (d >= -threshold && d <= threshold) {
	                            r += f * r2;
	                            rf += f;
	                        }
	                        d = g1 - g2;
	                        if (d >= -threshold && d <= threshold) {
	                            g += f * g2;
	                            gf += f;
	                        }
	                        d = b1 - b2;
	                        if (d >= -threshold && d <= threshold) {
	                            b += f * b2;
	                            bf += f;
	                        }
						}
					}
	                a = af == 0 ? a1 : a / af;
	                r = rf == 0 ? r1 : r / rf;
	                g = gf == 0 ? g1 : g / gf;
	                b = bf == 0 ? b1 : b / bf;
					int ia = alp ? PixelUtils.clamp((int) (a + 0.5)) : 0xff;
					int ir = PixelUtils.clamp((int) (r + 0.5));
					int ig = PixelUtils.clamp((int) (g + 0.5));
					int ib = PixelUtils.clamp((int) (b + 0.5));
					outPixels[outIndex] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
	                outIndex += height;
				}
			}
		}
	  /**
	   * Gives  unsharp blur of an image.
	   * @param input B64
	   * @param am Item
	   * @return B64 Image
	   * @throws Exception exception
	   */
	  @SuppressWarnings({"null"})
	  public B64  unsharpfilter(final B64 input, final Int am) throws Exception {
	    B64 b = null;
      int amount = (int) am.itr();
     BufferedImage src = toImage(input);
     BufferedImage dst = null;
	    int width = src.getWidth();
	        int height = src.getHeight();

	        if (dst == null)
	            dst = createCompatibleDestImage(src, null);

	        int[] inPixels = new int[width * height];
	        int[] outPixels = new int[width * height];
	        src.getRGB(0, 0, width, height, inPixels, 0, width);

			if (radius > 0) {
				//boolean premultiplyAlpha=true;
			Gausian.convolveAndTranspose(makeKernel(radius), inPixels, outPixels, width, height,
			    alpha, alpha && premultiplyAlpha, false, clampedges);
			Gausian.convolveAndTranspose(makeKernel(radius), outPixels, inPixels, height,
			    width, alpha, false, alpha && premultiplyAlpha, clampedges);
			}

	        src.getRGB(0, 0, width, height, outPixels, 0, width);

			float a = 4 * amount;

			int index = 0;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int rgb1 = outPixels[index];
					int r1 = (rgb1 >> 16) & 0xff;
					int g1 = (rgb1 >> 8) & 0xff;
					int b1 = rgb1 & 0xff;

					int rgb2 = inPixels[index];
					int r2 = (rgb2 >> 16) & 0xff;
					int g2 = (rgb2 >> 8) & 0xff;
					int b2 = rgb2 & 0xff;

					if (Math.abs(r1 - r2) >= threshold)
						r1 = PixelUtils.clamp((int) ((a + 1) * (r1 - r2) + r2));
					if (Math.abs(g1 -  g2) >= threshold)
						g1 = PixelUtils.clamp((int) ((a + 1) * (g1 - g2) + g2));
					if (Math.abs(b1 -  b2) >= threshold)
						b1 = PixelUtils.clamp((int) ((a + 1) * (b1 - b2) + b2));

					inPixels[index] = (rgb1 & 0xff000000) | (r1 << 16) | (g1 << 8) | b1;
					index++;
				}
			}

	        dst.setRGB(0, 0, width, height, inPixels, 0, width);
	        b = toBinary(dst, format(input));
	        return b;
	    }
		  /**
	   * sets the radius according to the kernel.
	   * @param radius float
	   * @return kernel
	   */
		private static Kernel makeKernel(final float radius) {
			int r = (int) Math.ceil(radius);
			int rows = r * 2 + 1;
			float[] matrix = new float[rows];
			float sigma = radius / 3;
			float sigma22 = 2 * sigma * sigma;
			float sigmaPi2 = 2 * ImageMath.PI * sigma;
			float sqrtSigmaPi2 = (float) Math.sqrt(sigmaPi2);
			float radius2 = radius * radius;
			float total = 0;
			int index = 0;
			for (int row = -r; row <= r; row++) {
				float distance = row * row;
				if (distance > radius2)
					matrix[index] = 0;
				else
					matrix[index] = (float) Math.exp(-distance / sigma22) / sqrtSigmaPi2;
				total += matrix[index];
				index++;
			}
			for (int i = 0; i < rows; i++)
				matrix[i] /= total;

			return new Kernel(rows, 1, matrix);
		}
}

