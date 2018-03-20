import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JSpinner;
import java.awt.GridBagLayout;
import java.awt.Checkbox;


public class PPPOE_setting extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PPPOE_setting frame = new PPPOE_setting();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    /*
     * Ethernet Interface: eth1
User name:          wangshuai
Activate-on-demand: No
DNS:                Do not adjust
Firewalling:        NONE*/
	/**
	 * Create the frame.
	 */
	public PPPOE_setting() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
	}

}
