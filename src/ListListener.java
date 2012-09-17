/*
Andrew Darwin
CSC 420: Graphical User Interfaces
Fall 2012
*/

package csc420.hw1.bin;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;

public class ListListener implements ListSelectionListener
{
  public void valueChanged(ListSelectionEvent e)
  {
    JList list = (JList)e.getSource();
    final int index = list.getSelectedIndex();
    final String value = (String)(list.getSelectedValue());
    if (index >= 0)
    {
      javax.swing.SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          EuroCountry.countrySelected = true;
          EuroCountry.updateCountryImage(value);
        }
      });
    }
  }
}
