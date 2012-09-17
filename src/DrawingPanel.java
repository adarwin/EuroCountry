/*
Andrew Darwin
CSC 420: Graphical User Interfaces
Fall 2012
*/

package csc420.hw1.bin;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel
{
  public static final long serialVersionUID = 0;
  private BufferedImage img;
  private int originalHeight;
  private int originalWidth;

  public void setImage(BufferedImage image)
  {
    img = image;
    originalHeight = img.getHeight();
    originalWidth = img.getWidth();
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if (img != null)
    {
      Graphics2D g2D = (Graphics2D)g;
      int margin = 15;
      int titleOffset = 5;
      int x = margin;
      int y = margin + titleOffset;
      int width;
      int height;

      //Figure out how to place and size the image
      int imageWidth = originalWidth;
      int imageHeight = originalHeight;
      int panelWidth = this.getWidth();
      int panelHeight = this.getHeight();
      Integer tempWidth = new Integer(imageWidth);
      Integer tempHeight = new Integer(imageHeight);
      double imageAspectRatio = (tempWidth.doubleValue())/(tempHeight.doubleValue());
      tempWidth = new Integer(panelWidth);
      tempHeight = new Integer(panelHeight);
      double panelAspectRatio = (tempWidth.doubleValue())/(tempHeight.doubleValue());
      if (imageAspectRatio > panelAspectRatio)
      {
        //Image is more widescreen than panel is
        width = panelWidth;
        height = (int)(width/imageAspectRatio);
        //int centerY = (this.getHeight()-2*margin)/2;
        int centerY = panelHeight/2;
        y = y+(centerY-(height/2));
      }
      else
      {
        //Panel is more widescreen than image
        height = panelHeight;
        width = (int)(height*imageAspectRatio);
        int centerX = panelWidth/2;
        x = x+(centerX-(width/2));
      }
      g2D.drawImage(img, x, y, width-2*margin, height-2*margin, this.getBackground(), null);
    }
  }
}
