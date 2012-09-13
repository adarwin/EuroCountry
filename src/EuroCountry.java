/*
Andrew Darwin
CSC 420: Graphical User Interfaces
Fall 2012
*/

package csc420.hw1;

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

  public static File[] countryFiles = null;
  private static Listener actionListener = null;
  private static JLabel countryImageLabel;


  private static void populate(JList list) throws Exception
  {
    Worker worker = new Worker(countryFiles);
    worker.execute();
    String path = "csc420/hw1/img";
    File directory = new File(path);
    if (directory != null)
    {
      File[] fileList = directory.listFiles(new FileFilter()
      {
        public boolean accept(File pathname)
        {
          if (pathname.getName().endsWith(".gif"))
          {
            return true;
          }
          else
          {
            return false;
          }
        }
      });
      if (fileList != null)
      {
        countryFiles = new File[fileList.length];
        Object[] values = new Object[fileList.length];
        for (int i = 0; i < fileList.length; i++)
        {
          countryFiles[i] = fileList[i];
          String name = fileList[i].getName();
          int extensionIndex = name.lastIndexOf('.');
          name = name.substring(0, extensionIndex);
          values[i] = name;
          //list.addItem(name);
        }
        list.setListData(values);
      }
    }
    else
    {
      throw new Exception("null directory");
    }
  }

  public static void createAndShowGUI()
  {
    actionListener = new Listener();
    ListSelectionModel listSelectionModel;
    int panelOutsideMargin = 10;
    int panelInsideMargin = 10;
    JFrame frame = new JFrame("Euro Country");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null); //Center the frame on the screen

    Container contentPane = frame.getContentPane();
    SpringLayout contentLayout = new SpringLayout();
    contentPane.setLayout(contentLayout);

    //Set up the left panel
    JPanel leftPanel = new JPanel();
    SpringLayout leftPanelLayout = new SpringLayout();
    leftPanel.setBorder(new TitledBorder("Left Panel"));
    leftPanel.setLayout(leftPanelLayout);
    contentLayout.putConstraint(SpringLayout.NORTH, leftPanel, panelOutsideMargin, SpringLayout.NORTH, contentPane);
    contentLayout.putConstraint(SpringLayout.SOUTH, leftPanel, -1*panelOutsideMargin, SpringLayout.SOUTH, contentPane);
    contentLayout.putConstraint(SpringLayout.EAST, leftPanel, 250, SpringLayout.WEST, contentPane);
    contentLayout.putConstraint(SpringLayout.WEST, leftPanel, panelOutsideMargin, SpringLayout.WEST, contentPane);
    //Set up the countries combo box
    JList countries = new JList();
    countries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scrollPane = new JScrollPane();
    try
    {
      populate(countries);
    }
    catch (Exception ex)
    {
      System.out.println(ex.getMessage());
      System.out.println(ex.getStackTrace());
    }
    countries.addListSelectionListener(new ListListener());
    scrollPane.getViewport().setView(countries);
    leftPanelLayout.putConstraint(SpringLayout.NORTH, scrollPane, panelInsideMargin, SpringLayout.NORTH, leftPanel);
    leftPanelLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -1*panelInsideMargin, SpringLayout.SOUTH, leftPanel);
    leftPanelLayout.putConstraint(SpringLayout.EAST, scrollPane, -1*panelInsideMargin, SpringLayout.EAST, leftPanel);
    leftPanelLayout.putConstraint(SpringLayout.WEST, scrollPane, panelInsideMargin, SpringLayout.WEST, leftPanel);
    //Add the combo box to the left panel
    leftPanel.add(scrollPane);




    //Set up the right panel
    JPanel rightPanel = new JPanel();
    BorderLayout rightPanelLayout = new BorderLayout();
    rightPanel.setLayout(rightPanelLayout);
    rightPanel.setBorder(new TitledBorder("Right Panel"));
    contentLayout.putConstraint(SpringLayout.NORTH, rightPanel, panelOutsideMargin, SpringLayout.NORTH, contentPane);
    contentLayout.putConstraint(SpringLayout.SOUTH, rightPanel, -1*panelOutsideMargin, SpringLayout.SOUTH, contentPane);
    contentLayout.putConstraint(SpringLayout.EAST, rightPanel, -1*panelOutsideMargin, SpringLayout.EAST, contentPane);
    contentLayout.putConstraint(SpringLayout.WEST, rightPanel, panelOutsideMargin, SpringLayout.EAST, leftPanel);
    //Set up the image label
    countryImageLabel = new JLabel();
    Icon countryImageIcon = new ImageIcon(countryFiles[0].getAbsolutePath());
    countryImageLabel.setIcon(countryImageIcon);
    /*
    rightPanelLayout.putConstraint(SpringLayout.NORTH, countryImageLabel, panelInsideMargin, SpringLayout.NORTH, rightPanel);
    rightPanelLayout.putConstraint(SpringLayout.SOUTH, countryImageLabel, -1*panelInsideMargin, SpringLayout.SOUTH, rightPanel);
    rightPanelLayout.putConstraint(SpringLayout.EAST, countryImageLabel, -1*panelInsideMargin, SpringLayout.EAST, rightPanel);
    rightPanelLayout.putConstraint(SpringLayout.WEST, countryImageLabel, panelInsideMargin, SpringLayout.WEST, rightPanel);
    */
    rightPanel.add(countryImageLabel, BorderLayout.CENTER);


    //Add the left and right panels to the contentPane
    contentPane.add(leftPanel);
    contentPane.add(rightPanel);

    frame.setVisible(true);
  }

  public static void updateCountryImage(int index)
  {
    Icon countryImageIcon = new ImageIcon(countryFiles[index].getAbsolutePath());
    countryImageLabel.setIcon(countryImageIcon);
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
