package GUI;

import FLO_Engine.Adapter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author mars
 */

public class FillArr {

    private int param;
    private int obj;

    private static JFrame mainFrame;
    private JButton next;
    private JButton exit;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JTextField[][] arr;
    private JTextField[] params;
    private JTextField[] objects;

    public FillArr(int param, int obj){
        this.param = param;
        this.obj = obj;
        Font f = new Font("Times New Roman", Font.BOLD, 18);

        arr = new JTextField[param][obj];
        params = new JTextField[param+1];
        objects = new JTextField[obj];

        //filling the center panel
        for (int i=0;i<param+1;i++)
            if (i!=0){
                params[i] = new JTextField("param#"+i);
                params[i].setAlignmentX(params[i].CENTER_ALIGNMENT);
                params[i].setFont(f);
            }


        for (int i=0;i<obj;i++){
            objects[i] = new JTextField("obj#"+(i+1));
            objects[i].setAlignmentX(objects[i].CENTER_ALIGNMENT);
            objects[i].setFont(f);
        }

        for (int i=0;i<param;i++)
            for (int j=0;j<obj;j++){
                arr[i][j] = new JTextField();
                arr[i][j].setAlignmentX(arr[i][j].CENTER_ALIGNMENT);
                arr[i][j].setFont(f);
                /*
                arr[i][j].setSize(70,25);
                arr[i][j].setMinimumSize(new Dimension(70, 25));
                arr[i][j].setPreferredSize(new Dimension(70, 25));
                 */
            }
              
        Object[][] elements = new Object[param+1][obj+1];

        elements[0][0] = new JLabel("");
        for (int i=1;i<param+1;i++)
            elements[i][0] = params[i];
        for (int i=1;i<obj+1;i++)
            elements[0][i] = objects[i-1];

        for (int i=1;i<param+1;i++)
            for (int j=1;j<obj+1;j++)
                elements[i][j] = arr[i-1][j-1];
        //eoftcp

        mainFrame = new JFrame("FL");
            mainFrame.setPreferredSize(new Dimension(950,480));
            mainFrame.setMinimumSize(new Dimension(950,480));
            mainFrame.setLayout(new BorderLayout());
            mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        next = new JButton("Calculate");
            next.setSize(100,25);
            next.addActionListener(new Button_next_Listener());

        exit = new JButton("Exit");
            exit.setSize(100,25);
            exit.addActionListener(new Button_exit_Listener());

        topPanel = new JPanel();
            topPanel.setLayout(new FlowLayout());
            topPanel.add(next);
            topPanel.add(exit);

        centerPanel = new JPanel();
            centerPanel.setLayout(new GridLayout(param+1,obj+1));
            for (int i=0;i<param+1;i++)
                for (int j=0;j<obj+1;j++){
                    if ((i==0) & (j==0))
                        centerPanel.add((JLabel)elements[i][j]);
                    else
                        centerPanel.add((JTextField)elements[i][j]);
                }

        mainFrame.add(topPanel,BorderLayout.NORTH);
        mainFrame.add(centerPanel,BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    class Button_next_Listener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            double temp;
            double[][] u = new double[param][obj];
            try{
            for (int i=0;i<param;i++)
                for (int j=0;j<obj;j++){
                    temp = Double.parseDouble(arr[i][j].getText());
                    if ((temp > 0) && (temp <1))
                        u[i][j] = temp;
                    else
                        if (temp < 0)
                            u[i][j] = 0;
                        else
                            u[i][j] = 1;
                }

            JOptionPane.showMessageDialog(mainFrame, "The best is object #" + Adapter.getInstance().Calc(u), "Result", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (NumberFormatException exc){
                JOptionPane.showMessageDialog(mainFrame, "Filling of the array is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
      }

  class Button_exit_Listener implements ActionListener {
        Button_exit_Listener() {}

        public void actionPerformed(ActionEvent e) {
            mainFrame.dispose();
            System.exit(0);
            }
        }
}