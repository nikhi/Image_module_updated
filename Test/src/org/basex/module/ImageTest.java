package org.basex.module;

import static org.junit.Assert.*;

import java.io.*;

import org.basex.io.*;
import org.basex.query.iter.*;
import org.basex.query.value.item.*;
import org.junit.*;
/**
 * JunitTest class for image.
 * @author nikhilesh
 */
public class ImageTest {
  /**
   * Test for Height.
   * @throws IOException exception
   * @throws Exception   exception
   */
@Test
public void testHeight() throws Exception {
assertEquals(new Image().imageHeight(new B64(new IOFile("bird.jpg").read())), 160);
}
/**
 * Test for width.
 * @throws IOException exception
 * @throws Exception   exception
 */
@Test
public void testWidth() throws Exception {
assertEquals(new Image().imageWidth(new B64(new IOFile("bird.jpg").read())), 134);
}
/**
 * Test for conversion. */
@Test
public void testConvert2() {

  B64 b = null;
  try {
    b = new Image().convert(new B64(new IOFile("bird.jpg").read()), "PNG");
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }

  assertNotNull(b);
}
/**
 *Test for blacknwhiteImage.
 */
 @Test
 public void testblacknWhite() {
   B64 b = null;
   try {
     b = new Image().blacknWhite(new B64(new IOFile("bird.jpg").read()));
   } catch (Exception e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
   }

   assertNotNull(b);
 }
 /**
  *Test to sharpen an image.
  */
  @Test
  public void testSharpen() {
    B64 b = null;
    try {
      b = new Image().sharpen(new B64(new IOFile("bird.jpg").read()));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertNotNull(b);
  }
  /**
   *Test to flip an image.
    */
   @Test
   public void testFlip() {
     B64 b = null;
     try {
       b = new Image().flip(new B64(new IOFile("bird.jpg").read()));
     } catch (Exception e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
     }

     assertNotNull(b);
   }
   /**
    *Test to flop an image.
    */
    @Test
    public void testFlop() {
      B64 b = null;
      try {
        b = new Image().flop(new B64(new IOFile("bird.jpg").read()));
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      assertNotNull(b);
    }
    /**
     *Test transparency of an image.
     */
     @Test
     public void testTransparent() {
       B64 b = null;
       try {
         b = new Image().transparent(new B64(new IOFile("bird.jpg").read()));
       } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
       }

       assertNotNull(b);
     }
     /**
      *Test for grayscale an image.
      */
      @Test
      public void testGrayscale() {
        B64 b = null;
        try {
          b = new Image().grayscale(new B64(new IOFile("bird.jpg").read()));
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        assertNotNull(b);
      }
      /**
       * Test for emboss of  an image.
       */
       @Test
       public void testemboss() {
         B64 b = null;
         try {
           b = new Image().emboss(new B64(new IOFile("bird.jpg").read()));
         } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
         }

         assertNotNull(b);
       }
       /**
        * Test support of an image.
        */
       public void Testissupported() {
         assertEquals(new Image().isSupportedImage("jpg"), true);
       }
        /**
         * Test to crop an image.
         */
 @Test
 public void testCropImage() {
   B64 b = null;
   try {
     b = new Image().cropImage(new B64(new IOFile("bird.jpg").read()),
         Int.get(20), Int.get(20));
   } catch (Exception e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
   }

   assertNotNull(b);
 }
 /**
  * Test to resize an image.
  */
@Test
public void testresize() {
  B64 b = null;
  try {
    b = new Image().resize(new B64(new IOFile("bird.jpg").read()), Int.get(20), Int.get(20));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
 assertNotNull(b);
}
/**
 * Test to zoom an image.
  */
@Test
public void testzoom() {
  B64 b = null;
  try {
    b = new Image().zoom(new B64(new IOFile("bird.jpg").read()), Int.get(20), Int.get(20));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }

  assertNotNull(b);
}
/**
 * Test to zoom an image by height.
  */
@Test
public void testzoombyheight() {
  B64 b = null;
  try {
    b = new Image().zoomheight(new B64(new IOFile("bird.jpg").read()), Int.get(20));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }

  assertNotNull(b);
}
/**
 * Test to zoom an image by width.
 */
@Test
public void testzoombywidth() {
  B64 b = null;
  try {
    b = new Image().zoomwidth(new B64(new IOFile("bird.jpg").read()), Int.get(20));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }

  assertNotNull(b);
}
/**
 * Test to rotate an image.
 */
@Test
public void testrotate() {
  B64 b = null;
  try {
    b = new Image().rotate(new B64(new IOFile("bird.jpg").read()), Int.get(20));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }

  assertNotNull(b);
}
/**
 * Test to watermark an image.
 */
@Test
public void testwatermark() {
  B64 b = null;
  try {
    b = new Image().watermark(new B64(new IOFile("bird.jpg").read()), "BaseX", Int.get(20));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }

  assertNotNull(b);
}
//@Test
//public void testcompressimage() throws IOException, Exception {
//  B64 b = null;
//  try {
//    b = new Image().compressImage(new B64(new IOFile("bird.jpg").read()),"C:\\Users\\nikhilesh\\Desktop\\Images\\Compress.jpg",Int.get(20));
//  } catch (Exception e) {
//    // TODO Auto-generated catch block
//    e.printStackTrace();
//  }
//
//  assertNotNull(b);
//}
/**
 * Test to create an  image by height.
 */
@Test
public void testcreateImage() {
  B64 b = null;
  try {
    b = new Image().createImage(Int.get(200), Int.get(200), "jpeg");
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }

  assertNotNull(b);
}
/**
 * Test equality of an image.
 * @throws Exception   exception
 */
@Test
public void testequals() throws Exception {
  assertEquals(new Image().equals(new B64(new IOFile("bird.jpg").read()),
      new B64(new IOFile("bird.jpg").read())), true);
}
/**
 * Test exif info of an image.
 */
@Test
public void testexif() {
  String[] b = null;
  try {
    b = new Image().exif("bird.jpg");
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }

  assertNotNull(b);
}
/**
 * Test edge of an image.
 */
@Test
public void testedge() {
  B64 b = null;
  try {
    b = new Image().edge(new B64(new IOFile("bird.jpg").read()));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }

  assertNotNull(b);
}
/**
 * Test overlay of an image.
 */
@Test
public void testOverlayImage()  {
  B64 b = null;
  try {
    b = new Image().overlayImage(new B64(new IOFile("bird.jpg").read()),
        new B64(new IOFile("bird.jpg").read()));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }

  assertNotNull(b);
}
//@Test
//public void testaddnoise() throws IOException, Exception {
//  B64 b = null;
//  try {
//    b = new Image().noise(new B64(new IOFile("bird.jpg").read()), Int.get(2));
//  } catch (Exception e) {
//    // TODO Auto-generated catch block
//    e.printStackTrace();
//  }
//
//  assertNotNull(b);
//}
/**
 * Test contrast of an image.
 */
@Test
public void testcontrast() {
  B64 b = null;
  try {
    b = new Image().contrast(new B64(new IOFile("bird.jpg").read()),
        Int.get(7));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  assertNotNull(b);
}
/**
 * Test chop of an image.
 */
@Test
public void testchop() {
  ValueBuilder b = null;
  try {
    b = new Image().chop(new B64(new IOFile("bird.jpg").read()),
        Int.get(3), Int.get(4));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  assertNotNull(b);
}
/**
 * Test scale of an image.
*/
@Test
public void testscale()  {
  B64 b = null;
  try {
    b = new Image().scale(new B64(new IOFile("bird.jpg").read()),
        Int.get(70), Int.get(80));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  assertNotNull(b);
}
/**
 * Test Brightness of an image.
 */
@Test
public void testBrightness()  {
  B64 b = null;
  try {
    b = new Image().brightness(new B64(new IOFile("bird.jpg").read()),
        Int.get(80));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  assertNotNull(b);
}
/**
 * Test blur of an image.*/
@Test
public void testBlur() {
  B64 b = null;
  try {
    b = new Image().blur(new B64(new IOFile("bird.jpg").read()),
        Int.get(10));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  assertNotNull(b);
}
/**
 * Test motion blur of an image.
 */
@Test
public void testMotionBlur() {
  B64 b = null;
  try {
    b = new Image().motionblur(new B64(new IOFile("bird.jpg").read()),
        Int.get(10));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  assertNotNull(b);
}
/**
 * Test box blur of an image.
 */
@Test
public void testBoxblurfilter() {
  B64 b = null;
  try {
    b = new Image().boxblurfilter(new B64(new IOFile("bird.jpg").read()),
        Int.get(5), Int.get(4));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  assertNotNull(b);
}
/**
 * Test lens Blur  of an image.
 */
@Test
public void testLensBlurfilter() {
  B64 b = null;
  try {
    b = new Image().lensBlurfilter(new B64(new IOFile("bird.jpg").read()), Int.get(10));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  assertNotNull(b);
}
/**
 * Test unsharp of an image.
 */
@Test
public void testUnsharpfilter() {
  B64 b = null;
  try {
    b = new Image().unsharpfilter(new B64(new IOFile("bird.jpg").read()), Int.get(10));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  assertNotNull(b);
}
///**
// * Test Glowfilter of an image.
// */
//@Test
//public void testGlowfilter() {
//  B64 b = null;
//  try {
//    b = new Image().glowfilter(new B64(new IOFile("bird.jpg").read()));
//  } catch (Exception e) {
//    // TODO Auto-generated catch block
//    e.printStackTrace();
//  }
//  assertNotNull(b);
//}
/**
 * Test gradient of an image.
 */
@Test
public void testGradientImage() {
  B64 b = null;
  try {
    b = new Image().createGradientImage(Int.get(200), Int.get(200), "blue", "red", "jpeg");
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  assertNotNull(b);
}

}