/*
Andrew Darwin
CSC 420: Graphical User Interfaces
Fall 2012
*/

package csc420.hw1.bin;

import javax.swing.SwingWorker;
import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ExecutionException;
//import javax.swing.filechooser.FileFilter;

public class Worker extends SwingWorker<File[], Integer>
{
  File[] countryFiles;
  Object[] values;
  public Worker(File[] fileList)
  {
    super();
    countryFiles = fileList;
  }
  @Override
  protected File[] doInBackground() throws Exception
  {
    //Load images
    String path = "csc420/hw1/img";
    System.out.println("Begin loading images from " + path + " into worker thread.");
    File directory = new File(path);
    File[] fileList = null;
    if (directory != null)
    {
      fileList = directory.listFiles(new FileFilter()
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
        public String getDescription()
        {
          return "SomeString";
        }
      });
      if (fileList != null)
      {
        EuroCountry.setCountryFiles(fileList);

        //countryFiles = new File[fileList.length];
        values = new Object[fileList.length];
        for (int i = 0; i < fileList.length; i++)
        {
          Thread.sleep(50);
          //countryFiles[i] = fileList[i];
          String name = fileList[i].getName();
          int extensionIndex = name.lastIndexOf('.');
          name = name.substring(0, extensionIndex);
          values[i] = name;
          final int tempInt = i;
          javax.swing.SwingUtilities.invokeLater(new Runnable()
          {
            public void run()
            {
              EuroCountry.countries.setListData(values);
              EuroCountry.countries.setSelectedIndex(tempInt);
            }
          });
        }
        //list.setListData(values);
      }
    }
    else
    {
      throw new Exception("null directory");
    }
    return fileList;
  }

  @Override
  public void done()
  {
    System.out.println("All done. :-)");
    try
    {
      System.out.println("Attempting to get File[]");
      File[] temp = get();
      System.out.println("File[0] = " + temp[0]);
      EuroCountry.setCountryFiles(temp);
      javax.swing.SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          EuroCountry.updateCountryImage(0);
        }
      });
      javax.swing.SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          //EuroCountry.countries.setListData(values);
        }
      });
    }
    catch (InterruptedException ex)
    {
      System.out.println(ex.getMessage());
    }
    catch (ExecutionException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}
