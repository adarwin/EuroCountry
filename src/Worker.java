/*
Andrew Darwin
CSC 420: Graphical User Interfaces
Fall 2012
*/

//package csc420.hw1.bin;

import javax.swing.SwingWorker;
import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.TreeSet;

public class Worker extends SwingWorker<File[], Integer>
{
  TreeSet<String> nameSet;;


  @Override
  protected File[] doInBackground() throws Exception
  {
    //Load images
    //String path = "csc420/hw1/img";
    String path = "img";
    File directory = new File(path);
    File[] fileList = null;

    if (directory != null)
    {
      fileList = directory.listFiles(new FileFilter()
      {
        public boolean accept(File pathname)
        {
          String name = pathname.getName();
          return name.endsWith(".gif") || name.endsWith(".jpg");
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

        nameSet = new TreeSet<String>();
        for (int i = 0; i < fileList.length; i++)
        {
          if (EuroCountry.slowEffect) Thread.sleep(50);
          String name = fileList[i].getName();
          int extensionIndex = name.lastIndexOf('.');
          name = name.substring(0, extensionIndex);
          nameSet.add(name);
          publish(i+1);

          final int tempInt = i;
          final Object[] output = nameSet.toArray();
          javax.swing.SwingUtilities.invokeLater(new Runnable()
          {
            public void run()
            {
              EuroCountry.countries.setListData(output);
              //EuroCountry.countries.setSelectedIndex(tempInt);
            }
          });
        }
      }
    }
    else
    {
      throw new Exception("Worker thread received NULL directory");
    }
    return fileList;
  }




  @Override
  public void done()
  {
    try
    {
      File[] temp = get();
      EuroCountry.setCountryFiles(temp);
      javax.swing.SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          if (!EuroCountry.countrySelected)
          {
            EuroCountry.countries.setSelectedIndex(0);
          }
        }
      });
    }
    catch (InterruptedException ex)
    {
      System.out.println(ex.getMessage());
      System.out.println(ex.getStackTrace());
    }
    catch (ExecutionException ex)
    {
      System.out.println(ex.getMessage());
      System.out.println(ex.getStackTrace());
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
