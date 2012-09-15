/*
Andrew Darwin
CSC 420: Graphical User Interfaces
Fall 2012
*/

package csc420.hw1.bin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener implements ActionListener
{
  public void actionPerformed(ActionEvent e)
  {
    System.out.println("I'm in the actionPerformed method of action listener");
  }
}
