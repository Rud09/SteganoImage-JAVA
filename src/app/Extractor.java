package app;

import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Extractor {
  private String vessel;
  private String extractedFile;
  private SecurityManager smgr;
  private String trgtFolder;

  Extractor(String password, String vessel, String trgtFolder) throws Exception {

    File f1 = new File(vessel);
    if (!f1.exists())
      throw new Exception(vessel + " doesnt exists");

    smgr = new SecurityManager(password);

    this.vessel = vessel;
    this.trgtFolder = trgtFolder;
  }

  void extract() throws Exception {

    File fileVessel = new File(vessel);
    BufferedImage buffVessel = ImageIO.read(fileVessel);

    Raster rstr = buffVessel.getData();

    int w, h;
    w = buffVessel.getWidth();
    h = buffVessel.getHeight();

    int x, y;
    int r, g, b;
    int arr[];
    int cnt = 0;
    int data;
    int flag = smgr.getPermutation();
    int fileSize = 0;
    boolean keepExtracting = true;
    String hdr = "";

    FileOutputStream fout = null;

    for (y = 0; y < h && keepExtracting; y++) {
      for (x = 0; x < w; x++) {

        r = rstr.getSample(x, y, 0);
        g = rstr.getSample(x, y, 1);
        b = rstr.getSample(x, y, 2);

        arr = ByteProcessor.extract(r, g, b, flag);

        data = ByteProcessor.combine(arr, flag);

        if (cnt < HeaderManager.HEADER_LENGTH) {
          hdr = hdr + (char) data;

          if (cnt == HeaderManager.HEADER_LENGTH - 1) {

            extractedFile = HeaderManager.getFileName(hdr);
            fileSize = HeaderManager.getFileSize(hdr);
            System.out.println("FILESIZE : " + fileSize);

            fout = new FileOutputStream(trgtFolder + "/" + extractedFile);
          }
        } else {
          data = smgr.primaryCrypto(data);
          fout.write(data);

          if (cnt == fileSize + HeaderManager.HEADER_LENGTH) {
            System.out.println("***" + cnt);
            keepExtracting = false;
            fout.close();
            break;
          }

        }

        cnt++;
        flag = (flag + 1) % 3 + 1;
      }

    }

  }
}
