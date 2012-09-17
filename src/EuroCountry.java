/*
Andrew Darwin
CSC 420: Graphical User Interfaces
Fall 2012
*/

package csc420.hw1.bin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Container;
import java.awt.BorderLayout;
import java.io.*;
import java.util.ArrayList;
import javax.swing.plaf.basic.*;
import javax.swing.SwingWorker;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ColorModel;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.imageio.ImageIO;

public class EuroCountry
{

  private static File[] countryFiles = null;
  private static Listener actionListener = null;
  //private static JLabel countryImageLabel;
  public static JList countries;
  public static JProgressBar progressBar;
  private static DrawingPanel rightPanel;
  private static JFrame frame;
  public static boolean slowEffect = true;
  public static boolean countrySelected = false;


  public static boolean setCountryFiles(File[] files)
  {
    if (files != null)
    {
      countryFiles = files;
      return true;
    }
    else
    {
      return false;
    }
  }
  private static void populate(JList list) throws Exception
  {
    Worker worker = new Worker(countryFiles);
    worker.execute();
  }

  public static void createAndShowGUI()
  {
    /*
    Do the necessary things to show the JFrame and it's components.
    Then modify those components and begin added images and countries.
    */

    //Declare objects specific to this method
      ListSelectionModel listSelectionModel;
      int panelOutsideMargin;
      int panelInsideMargin;
      JPanel leftPanel;
      JScrollPane scrollPane;
      SpringLayout leftPanelLayout;
      Container contentPane;
      BorderLayout rightPanelLayout;
      SpringLayout contentLayout;

    //Initialize all components
      actionListener = new Listener();
      panelOutsideMargin = 10;
      panelInsideMargin = 10;
      frame = new JFrame("Euro Country");
      leftPanel = new JPanel();
      rightPanel = new DrawingPanel();
      countries = new JList();
      //countryImageLabel = new JLabel();
      scrollPane = new JScrollPane();
      progressBar = new JProgressBar(0, 100);
      leftPanelLayout = new SpringLayout();
      rightPanelLayout = new BorderLayout();
      contentLayout = new SpringLayout();

    //Prepare leftPanel for placing in JFrame
      leftPanel.setBorder(new TitledBorder("European Countries"));
      leftPanel.setLayout(leftPanelLayout);
      //Add the combo box to the left panel
        leftPanel.add(scrollPane);
    //Prepare rightPanel for placing in JFrame
      rightPanel.setLayout(rightPanelLayout);
      rightPanel.setBorder(new TitledBorder("European Country Flags"));
    
    //Configure Layouts
      contentPane = frame.getContentPane();
      contentPane.setLayout(contentLayout);
      //Content Layout
        //Left JPanel
          contentLayout.putConstraint(SpringLayout.NORTH, leftPanel, panelOutsideMargin, SpringLayout.NORTH, contentPane);
          contentLayout.putConstraint(SpringLayout.SOUTH, leftPanel, -1*panelOutsideMargin, SpringLayout.NORTH, progressBar);
          contentLayout.putConstraint(SpringLayout.EAST, leftPanel, 250, SpringLayout.WEST, contentPane);
          contentLayout.putConstraint(SpringLayout.WEST, leftPanel, panelOutsideMargin, SpringLayout.WEST, contentPane);
        //Right JPanel
          contentLayout.putConstraint(SpringLayout.NORTH, rightPanel, panelOutsideMargin, SpringLayout.NORTH, contentPane);
          contentLayout.putConstraint(SpringLayout.SOUTH, rightPanel, -1*panelOutsideMargin, SpringLayout.NORTH, progressBar);
          contentLayout.putConstraint(SpringLayout.EAST, rightPanel, -1*panelOutsideMargin, SpringLayout.EAST, contentPane);
          contentLayout.putConstraint(SpringLayout.WEST, rightPanel, panelOutsideMargin, SpringLayout.EAST, leftPanel);
        //JProgressBar
          contentLayout.putConstraint(SpringLayout.SOUTH, progressBar, -1*panelOutsideMargin, SpringLayout.SOUTH, contentPane);
          contentLayout.putConstraint(SpringLayout.EAST, progressBar, -1*panelOutsideMargin, SpringLayout.EAST, contentPane);
          contentLayout.putConstraint(SpringLayout.WEST, progressBar, panelOutsideMargin, SpringLayout.WEST, contentPane);

      //Panel Layouts
        //Left
          leftPanelLayout.putConstraint(SpringLayout.NORTH, scrollPane, panelInsideMargin, SpringLayout.NORTH, leftPanel);
          leftPanelLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -1*panelInsideMargin, SpringLayout.SOUTH, leftPanel);
          leftPanelLayout.putConstraint(SpringLayout.EAST, scrollPane, -1*panelInsideMargin, SpringLayout.EAST, leftPanel);
          leftPanelLayout.putConstraint(SpringLayout.WEST, scrollPane, panelInsideMargin, SpringLayout.WEST, leftPanel);
        //Right
          //None just yet

    //Add components to JFrame
      contentPane.add(leftPanel);
      contentPane.add(rightPanel);
      contentPane.add(progressBar);

    //Display slow loading effect bypass option dialog
      if (slowEffect)
      {
        String message = "This application is set to pause for 50 milliseconds each time\n";
        message += "it loads an image. Would you like to keep this feature enabled?";
        int result = JOptionPane.showConfirmDialog(frame, message, "Advisory", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        System.out.println(result);
        if (result == 1) //Indicates user selected 'No'
        {
          slowEffect = false;
        }
      }

    //Make JFrame ready for visibility
      frame.setSize(800, 400);
      frame.setLocationRelativeTo(null); //Center the frame on the screen
      frame.setMinimumSize(new Dimension(440, 142));
      frame.setVisible(true);

    


    //Start loading images
    try
    {
      System.out.println("Starting to load images");
    }
    catch (Exception ex)
    {
    }
      try
      {
        populate(countries);
      }
      catch (Exception ex)
      {
        System.out.println(ex.getMessage());
        System.out.println(ex.getStackTrace());
      }

    //Modify components
      //JFrame Modifications
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //Left JPanel Modifications
      //Right JPanel Modifications
      //JLabel Modifications
      //JProgressBar Modifications
        progressBar.setStringPainted(true);

      //JList Modifications
        countries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        countries.addListSelectionListener(new ListListener());
        scrollPane.getViewport().setView(countries);




    //rightPanel.add(countryImageLabel, BorderLayout.CENTER);



    frame.setVisible(true);
  }

  public static void updateCountryImage(String name)
  {
    File imageFile = null;
    for (File f : countryFiles)
    {
      if (f.getName().contains(name))
      {
        imageFile = f;
        break;
      }
    }
    try
    {
      rightPanel.setImage(ImageIO.read(imageFile));
      frame.repaint();
    }
    catch (IOException ex)
    {
      System.out.println(ex.getMessage());
    }
  }

  public static void setProgress(int value)
  {
    System.out.println("Set progressBar to " + value);
    progressBar.setValue(value);
    double percentComplete = (progressBar.getPercentComplete()) * 100;
    if (percentComplete >= 100)
    {
      progressBar.setString("Complete");
    }
    else
    {
      progressBar.setString("Loading images... " + (int)percentComplete + "%");
    }
  }

  public static void main (String[] args)
  {
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        createAndShowGUI();
      }
    });
  }
}
