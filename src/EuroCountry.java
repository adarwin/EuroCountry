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

public class EuroCountry
{

  private static File[] countryFiles = null;
  private static Listener actionListener = null;
  private static JLabel countryImageLabel;
  public static JList countries;
  public static JProgressBar progressBar;


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
      JFrame frame;
      JPanel leftPanel;
      JScrollPane scrollPane;
      SpringLayout leftPanelLayout;
      Container contentPane;
      JPanel rightPanel;
      BorderLayout rightPanelLayout;
      SpringLayout contentLayout;

    //Initialize all components
      actionListener = new Listener();
      panelOutsideMargin = 10;
      panelInsideMargin = 10;
      frame = new JFrame("Euro Country");
      leftPanel = new JPanel();
      rightPanel = new JPanel();
      countries = new JList();
      countryImageLabel = new JLabel();
      scrollPane = new JScrollPane();
      progressBar = new JProgressBar(0, 100);
      leftPanelLayout = new SpringLayout();
      rightPanelLayout = new BorderLayout();
      contentLayout = new SpringLayout();

    //Prepare leftPanel for placing in JFrame
      leftPanel.setBorder(new TitledBorder("Left Panel"));
      leftPanel.setLayout(leftPanelLayout);
      //Add the combo box to the left panel
        leftPanel.add(scrollPane);
    //Prepare rightPanel for placing in JFrame
      rightPanel.setLayout(rightPanelLayout);
      rightPanel.setBorder(new TitledBorder("Right Panel"));
    
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


    //Make JFrame ready for visibility
      frame.setSize(800, 600);
      frame.setLocationRelativeTo(null); //Center the frame on the screen
      frame.setVisible(true);
      try
      {
        //Thread.sleep(1000);
      }
      catch (Exception ex)
      {
      }

    


    //Start loading images
    try
    {
      System.out.println("Starting to load images");
      Thread.sleep(1000);
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

      //JList Modifications
        countries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        countries.addListSelectionListener(new ListListener());
        scrollPane.getViewport().setView(countries);





    /*
    rightPanelLayout.putConstraint(SpringLayout.NORTH, countryImageLabel, panelInsideMargin, SpringLayout.NORTH, rightPanel);
    rightPanelLayout.putConstraint(SpringLayout.SOUTH, countryImageLabel, -1*panelInsideMargin, SpringLayout.SOUTH, rightPanel);
    rightPanelLayout.putConstraint(SpringLayout.EAST, countryImageLabel, -1*panelInsideMargin, SpringLayout.EAST, rightPanel);
    rightPanelLayout.putConstraint(SpringLayout.WEST, countryImageLabel, panelInsideMargin, SpringLayout.WEST, rightPanel);
    */
    rightPanel.add(countryImageLabel, BorderLayout.CENTER);



    frame.setVisible(true);
  }

  public static void updateCountryImage(int index)
  {
    Icon countryImageIcon = new ImageIcon(countryFiles[index].getAbsolutePath());
    countryImageLabel.setIcon(countryImageIcon);
  }

  public static void setProgress(int value)
  {
    System.out.println("Set progressBar to " + value);
    progressBar.setValue(value);
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
