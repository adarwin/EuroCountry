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
import java.util.List;
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
        final File[] temp = fileList;
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            EuroCountry.progressBar.setMaximum(temp.length);
          }
        });

        //countryFiles = new File[fileList.length];
        values = new Object[fileList.length];
        for (int i = 0; i < fileList.length; i++)
        {
          if (EuroCountry.slowEffect) Thread.sleep(50);
          //countryFiles[i] = fileList[i];
          String name = fileList[i].getName();
          int extensionIndex = name.lastIndexOf('.');
          name = name.substring(0, extensionIndex);
          values[i] = name;
          publish(i+1);
          final int tempInt = i;
          javax.swing.SwingUtilities.invokeLater(new Runnable()
          {
            public void run()
            {
              EuroCountry.countries.setListData(values);
              //EuroCountry.countries.setSelectedIndex(tempInt);
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
          EuroCountry.countries.setSelectedIndex(0);
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

  @Override
  protected void process(List<Integer> chunks)
  {
    for(Integer value : chunks)
    {
      final int temp = value;
      javax.swing.SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          EuroCountry.setProgress(temp);
        }
      });
    }
  }
}
