package WB;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class ChooseTypeOfFilling extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	public static int a;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection connection = MariaDBConnection2HacoSoftDB.dbConnector("tosia", "1234");
				try {
					ChooseTypeOfFilling frame = new ChooseTypeOfFilling(connection);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChooseTypeOfFilling(Connection connection) {
		
		Image img = new ImageIcon(this.getClass().getResource("/slonie.png")).getImage();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Menu");
		setBounds(100, 100, 458, 459);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMenu = new JLabel("Menu");
		lblMenu.setFont(new Font("Century", Font.BOLD, 24));
		lblMenu.setBounds(181, 11, 314, 53);
		contentPane.add(lblMenu);
		
		JButton btnNewButton = new JButton("Nowa lista");
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String query = "";
					if(Login.getAdmin()){
						query = "delete from BatchesKOP";
					}else{
						query = "delete from BatchesKOP2";
					}
					Statement st = null;
					st=connection.createStatement();
					st.executeUpdate(query);
				
					
					st.close();
					PreparedStatement pst=connection.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Table has been cleared");
					pst.close();
				
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				a = 0;
				dispose();
				AddBatch mojeMenu = new AddBatch(connection, a);
				mojeMenu.setVisible(true);
				
			}
		});
		btnNewButton.setBounds(36, 84, 371, 53);
		contentPane.add(btnNewButton);
		
		JButton btnKontunuuj = new JButton("Kontunuuj ostatni\u0105 list\u0119");
		btnKontunuuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				a = 1;
				
				dispose();
				AddBatch mojeMenu = new AddBatch(connection, a);
				mojeMenu.setVisible(true);
				
			}
		});
		btnKontunuuj.setFont(new Font("Arial", Font.BOLD, 14));
		btnKontunuuj.setBounds(36, 148, 371, 53);
		contentPane.add(btnKontunuuj);
		
		JButton btnTransferDoHacosofta = new JButton("Transfer do HacoSofta");
		btnTransferDoHacosofta.setEnabled(false);
		btnTransferDoHacosofta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnTransferDoHacosofta.setFont(new Font("Arial", Font.BOLD, 14));
		btnTransferDoHacosofta.setBounds(36, 212, 371, 53);
		contentPane.add(btnTransferDoHacosofta);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(-220, -112, 878, 533);
		lblNewLabel.setIcon(new ImageIcon(img));
		contentPane.add(lblNewLabel);
	}
}
