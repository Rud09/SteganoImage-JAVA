package app;

import java.io.File;

public class HeaderManager {
  final static int HEADER_LENGTH = 25;
  final static char SEPARATOR = '~';

  static String formHeader(String fname, long fsize) {
    int forSize = 8;
    int forSeparator = 1;
    int forName = HEADER_LENGTH - forSize - forSeparator;

    String fs = String.valueOf(fsize);
    while (fs.length() < forSize)
      fs = "#" + fs;

    File f = new File(fname);
    fname = f.getName();

    if (fname.length() > forName) {
      int start = fname.length() - forName;
      fname = fname.substring(start);
    } else {
      while (fname.length() < forName)
        fname = "#" + fname;
    }

    return fname + SEPARATOR + fs;

  }

  static String getFileName(String hdr) {
    int forSize = 8;
    int forSeparator = 1;
    int forName = HEADER_LENGTH - forSize - forSeparator;

    String temp = hdr.substring(0, forName);

    temp = temp.replaceAll("#", " ");

    temp = temp.trim();

    if (temp.startsWith("."))
      temp = "untitled" + temp;

    return temp;
  }

  static int getFileSize(String hdr) throws Exception {
    int forSize = 8;

    String temp = hdr.substring(HEADER_LENGTH - forSize);

    temp = temp.replaceAll("#", " ");

    temp = temp.trim();

    return Integer.parseInt(temp);
  }
}
