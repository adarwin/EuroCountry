/*
Andrew Darwin
CSC 420: Graphical User Interfaces
Fall 2012
*/

package csc420.hw1;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;

public class ListListener implements ListSelectionListener
{
  public void valueChanged(ListSelectionEvent e)
  {
    JList list = (JList)e.getSource();
    final int index = list.getSelectedIndex();
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        EuroCountry.updateCountryImage(index);
      }
    });
    System.out.println(list.getSelectedIndex());
  }
}
