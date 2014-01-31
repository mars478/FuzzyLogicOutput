/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import FLO_Engine.Adapter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author mars
 */

public class FindClasses {
    private static JFrame mainFrame;
    private JButton next;
    private JButton exit;
    private JButton chooseInp;
    private JButton chooseFunc;
    private JLabel iClass;
    private JLabel fClasses;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JSlider sParam;

    private String fc_text;
    private File inpClass;
    private File[] functionClasses;
    private LinkedList<File> fCl_list = new LinkedList();

    public FindClasses(){
        LookAndFeelInfo[] lArr=UIManager.getInstalledLookAndFeels();
        try{
            for (LookAndFeelInfo laf:lArr)
                UIManager.setLookAndFeel(laf.getClassName());
            }

        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Cannot to set LookAndFeel UIManager now","GUI error: UnsupportedLookAndFeelException",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        mainFrame = new JFrame("FL");
            mainFrame.setPreferredSize(new Dimension(950,480));
            mainFrame.setMinimumSize(new Dimension(950,480));
            mainFrame.setLayout(new BorderLayout());
            //mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        next = new JButton("Next");
            next.setSize(100,25);
            next.setEnabled(true);
            next.addActionListener(new Button_next_Listener());

        exit = new JButton("Exit");
            exit.setSize(100,25);
            exit.setEnabled(true);
            exit.addActionListener(new Button_exit_Listener());

        chooseInp = new JButton("Choose an input class");
            chooseInp.setSize(100,25);
            chooseInp.setEnabled(true);
            chooseInp.addActionListener(new Button_inpCl_Listener());

        chooseFunc = new JButton("Add a function class");
            chooseFunc.setSize(100,25);
            chooseFunc.setEnabled(true);
            chooseFunc.addActionListener(new Button_fnCls_Listener());

        iClass = new JLabel("<html><body>The input class:</html></body>");
            iClass.setHorizontalAlignment(JLabel.CENTER);
            iClass.setPreferredSize(new Dimension(100, 25));

        fClasses = new JLabel("<html><body>The function classes:</html></body>");
            fClasses.setHorizontalAlignment(JLabel.CENTER);
            fClasses.setPreferredSize(new Dimension(100, 100));

        sParam = new JSlider();
            sParam.setMinimum(2);
            sParam.setMaximum(20);
            sParam.setBorder(BorderFactory.createTitledBorder("Count of objects"));
            sParam.setMajorTickSpacing(1);
            sParam.setMinorTickSpacing(1);
            sParam.setPaintTicks(true);
            sParam.setPaintLabels(true);

        topPanel = new JPanel();
            topPanel.setLayout(new FlowLayout());
            topPanel.add(next);
            topPanel.add(exit);
            
        centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.add(chooseInp);
            centerPanel.add(iClass);
            centerPanel.add(chooseFunc);
            centerPanel.add(fClasses);

        mainFrame.add(topPanel,BorderLayout.NORTH);
        mainFrame.add(centerPanel,BorderLayout.CENTER);
        mainFrame.add(sParam, BorderLayout.SOUTH);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

  private File button_openlog(){
        JFileChooser FC = new JFileChooser();
        FC.setMultiSelectionEnabled(false);
        int returnVal = FC.showOpenDialog(mainFrame);

        if (returnVal == JFileChooser.APPROVE_OPTION) 
              return FC.getSelectedFile();

        return null;
    }

  class Button_next_Listener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            if ((inpClass == null) || (functionClasses == null))
                JOptionPane.showMessageDialog(mainFrame, "Incorrect classes have been chosen.", "Error", JOptionPane.ERROR_MESSAGE);
            else{
                 new Adapter(inpClass, functionClasses);
                 if (functionClasses.length != Adapter.getInstance().getFCnt())
                    JOptionPane.showMessageDialog(mainFrame, "Incorrect count of function classes.", "Error", JOptionPane.ERROR_MESSAGE);
                 else{
                    if (Adapter.getInstance().STATUS<1){
                        String error = "";
                        switch (Adapter.getInstance().STATUS){
                            case -3: error = "The input class cast error has occurred. Try again with another class."; break;
                            case -2: error = "The functions classes cast error has occurred. Try again with another classes."; break;
                            case -1: error = "Unexpected error has occurred. Try again."; break;
                        }
                        JOptionPane.showMessageDialog(mainFrame, error, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        new FillArr(5,Math.round(sParam.getValue()));
                        mainFrame.dispose();
                    }
                }
            }
        }
      }

  class Button_exit_Listener implements ActionListener {
        Button_exit_Listener() {}

        public void actionPerformed(ActionEvent e) {
            mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            mainFrame.dispose();
            System.exit(0);
            }
        }

  class Button_inpCl_Listener implements ActionListener {
        Button_inpCl_Listener() {}

        public void actionPerformed(ActionEvent e) {
            inpClass = button_openlog();
            iClass.setText("<html><body>" + "The input class is: " + inpClass.getAbsolutePath() + "</body></html>");
            }
        }

  class Button_fnCls_Listener implements ActionListener {
        Button_fnCls_Listener() {
            fc_text = "";
        }

        public void actionPerformed(ActionEvent e) {
            File temp = button_openlog();
            fCl_list.add(temp);
            fc_text += "<br>" + temp.getAbsolutePath();
            fClasses.setText("<html><body>" + fc_text + "</body></html>");
            centerPanel.repaint();
            functionClasses = new File[fCl_list.size()];
            fCl_list.toArray(functionClasses);
            }
        }
}