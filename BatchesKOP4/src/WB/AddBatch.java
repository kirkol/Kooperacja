package WB;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import PDF.PDF_ListaDlaKOP;
import PDF.PDF_Zamowienie;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class AddBatch extends JFrame {

	private JPanel contentPane;
	private JTextField txtNrBonu;
	private JTextField textField_2;
	private JTextField textField;
	private JTextField txtNrSerii;
	private JTextField txtNrArtykuu;
	private JTextField txtOpis;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField txtIloSztuk;
	private JTextField txtMateria;
	private JTextField txtObrbkaCieplnachemiczna;
	private JTextField txtKomentarz;
	private JTextField textField_12;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JButton btnStwrzPdf;
	private JTable table;
	private JTextField txtAdresDostawcy;
	private JTextField txtAdresDostawcy_1;
	private JTextField textField_4;
	private JTextField textField_5;
	private static String nazwaOdbiorcy;
	private static String adresOdbiorcy;
	private static double nrWZ;
	private JButton btnCofnij;
	private JFrame frame;
	
	String stanowisko ="";
	int nrOperacji = 0;
	String IloscSztuk="";
	String NrArtykulu = "";
	private JButton btnNewButton_1;
	

	public static double getNrWZ() {
		return nrWZ;
	}


	public static void setNrWZ(double d) {
		AddBatch.nrWZ = d;
	}


	public static String getNazwaOdbiorcy() {
		return nazwaOdbiorcy;
	}


	public static void setNazwaOdbiorcy(String nazwaOdbiorcy) {
		AddBatch.nazwaOdbiorcy = nazwaOdbiorcy;
	}


	public static String getAdresOdbiorcy() {
		return adresOdbiorcy;
	}


	public static void setAdresOdbiorcy(String adresOdbiorcy) {
		AddBatch.adresOdbiorcy = adresOdbiorcy;
	}


	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection connection = MariaDBConnection2HacoSoftDB.dbConnector("tosia", "1234");
				try {
					AddBatch frame = new AddBatch(connection, ChooseTypeOfFilling.a);
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
	public AddBatch(final Connection connection, int a) {
		
		setBackground(Color.WHITE);
		setTitle("Dodaj seriê do wysy³ki na kooperacjê");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 721);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblMojeMenu = new JLabel("Serie do obr\u00F3bki cieplnej");
		lblMojeMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMojeMenu.setFont(new Font("Century", Font.BOLD, 24));
		
		final SimpleDateFormat doNazwy = new SimpleDateFormat("yyyy.MM.dd");
		final Calendar date = Calendar.getInstance();
		Image img = new ImageIcon(this.getClass().getResource("/slonie.png")).getImage();
		
		
		// co jeœli u¿ytkownik jest adminem
		
		if (Login.getAdmin()==true)
		{
		
		}
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setAutoCreateRowSorter(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				table.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me){
						if (me.getClickCount()==1){
						try {
							
						//po kliknieciu cos tu zrobi z tymi danymi
						int row = table.getSelectedRow();
							textField_2.setText((table.getModel().getValueAt(row, 0)).toString());
						//setPaleta1(Integer.parseInt((table.getModel().getValueAt(row, 0)).toString()));
						//setPaleta2(Integer.parseInt((table.getModel().getValueAt(row, 0)).toString()));
						
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						}
					}
				}
						);
			}});
		scrollPane.setViewportView(table);
		ShowTable(connection, table);
		
		txtNrBonu = new JTextField();
		txtNrBonu.setText("NR BONU PRACY");
		txtNrBonu.setHorizontalAlignment(SwingConstants.CENTER);
		txtNrBonu.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtNrBonu.setEditable(false);
		txtNrBonu.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setColumns(10);
		textField_2.grabFocus();
		textField_2.requestFocus();
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);
		
		txtNrSerii = new JTextField();
		txtNrSerii.setText("NR SERII");
		txtNrSerii.setHorizontalAlignment(SwingConstants.CENTER);
		txtNrSerii.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtNrSerii.setEditable(false);
		txtNrSerii.setColumns(10);
		
		txtNrArtykuu = new JTextField();
		txtNrArtykuu.setText("NR ARTYKU\u0141U");
		txtNrArtykuu.setHorizontalAlignment(SwingConstants.CENTER);
		txtNrArtykuu.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtNrArtykuu.setEditable(false);
		txtNrArtykuu.setColumns(10);
		
		txtOpis = new JTextField();
		txtOpis.setText("OPIS");
		txtOpis.setHorizontalAlignment(SwingConstants.CENTER);
		txtOpis.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtOpis.setEditable(false);
		txtOpis.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setEditable(false);
		textField_6.setHorizontalAlignment(SwingConstants.CENTER);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setEditable(false);
		textField_7.setHorizontalAlignment(SwingConstants.CENTER);
		textField_7.setColumns(10);
		
		txtIloSztuk = new JTextField();
		txtIloSztuk.setText("ILO\u015A\u0106 SZTUK");
		txtIloSztuk.setHorizontalAlignment(SwingConstants.CENTER);
		txtIloSztuk.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtIloSztuk.setEditable(false);
		txtIloSztuk.setColumns(10);
		
		txtMateria = new JTextField();
		txtMateria.setText("MATERIA\u0141");
		txtMateria.setHorizontalAlignment(SwingConstants.CENTER);
		txtMateria.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtMateria.setEditable(false);
		txtMateria.setColumns(10);
		
		txtObrbkaCieplnachemiczna = new JTextField();
		txtObrbkaCieplnachemiczna.setText("OBR. CIEPLNA/CHEMICZNA");
		txtObrbkaCieplnachemiczna.setHorizontalAlignment(SwingConstants.CENTER);
		txtObrbkaCieplnachemiczna.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtObrbkaCieplnachemiczna.setEditable(false);
		txtObrbkaCieplnachemiczna.setColumns(10);
		
		txtKomentarz = new JTextField();
		txtKomentarz.setText("KOMENTARZ");
		txtKomentarz.setHorizontalAlignment(SwingConstants.CENTER);
		txtKomentarz.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtKomentarz.setEditable(false);
		txtKomentarz.setColumns(10);
		
		textField_12 = new JTextField();
		textField_12.setHorizontalAlignment(SwingConstants.CENTER);
		textField_12.setColumns(10);
		
		textField_14 = new JTextField();
		textField_14.setHorizontalAlignment(SwingConstants.CENTER);
		textField_14.setColumns(10);
		
		textField_15 = new JTextField();
		textField_15.setHorizontalAlignment(SwingConstants.CENTER);
		textField_15.setColumns(10);
		
		textField_16 = new JTextField();
		textField_16.setHorizontalAlignment(SwingConstants.CENTER);
		textField_16.setColumns(10);
		
		System.out.println(a);
		
		JLabel lblNewLabel_1 = new JLabel("");
		
		if(a==1){
			
			lblNewLabel_1 = new JLabel("kontynuacja poprzedniej listy");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setFont(new Font("Century", Font.PLAIN, 20));
			lblNewLabel_1.setBounds(108, 63, 337, 30);
			contentPane.add(lblNewLabel_1);
			
		} else{
			
			lblNewLabel_1 = new JLabel("nowa lista");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setFont(new Font("Century", Font.PLAIN, 20));
			lblNewLabel_1.setBounds(200, 63, 337, 30);
			contentPane.add(lblNewLabel_1);
		}
		
		JButton btnNewButton = new JButton("Dodaj seri\u0119 do listy");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (textField.getText().isEmpty()){
					
					JOptionPane.showMessageDialog(null, "Nie potwierdzono wyboru");
					
				} else{
				
					PreparedStatement pst =null;
					
					String artykul = textField_6.getText();
					artykul = artykul.replace("'", "-");
					textField_6.setText(artykul);
					
					String komentarz = textField_12.getText();
					komentarz = komentarz.replace("'", "-");
					textField_12.setText(komentarz);
					
					String obrobka = textField_16.getText();
					obrobka = obrobka.replace("'", "-");
					textField_16.setText(obrobka);
					
					try {
						String query="";
						
						if(Login.getAdmin()){
							query = "INSERT INTO BatchesKOP (NrFiszki, NrSerii, NrArtykulu, Nazwa, IloscSzt, Material, RodzajObrobki, Komentarz) "
									+ "VALUES ('"+textField_2.getText()+"', '"+textField.getText()+"', '"+textField_7.getText()+"', '"+textField_6.getText()+"', '"+textField_15.getText()+"',"
									+ " '"+textField_14.getText()+"', '"+textField_16.getText()+"', '"+textField_12.getText()+"')";
						}else{
							query = "INSERT INTO BatchesKOP2 (NrFiszki, NrSerii, NrArtykulu, Nazwa, IloscSzt, Material, RodzajObrobki, Komentarz) "
									+ "VALUES ('"+textField_2.getText()+"', '"+textField.getText()+"', '"+textField_7.getText()+"', '"+textField_6.getText()+"', '"+textField_15.getText()+"',"
									+ " '"+textField_14.getText()+"', '"+textField_16.getText()+"', '"+textField_12.getText()+"')";
						}
						
						pst = connection.prepareStatement(query);
						pst.execute();
						pst.close();
					
					
					}catch (SQLException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
	
					}
					
					int Delay = 300;
					JOptionPane pane = new JOptionPane("Data added", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = pane.createDialog(null, "Title");
					dialog.setModal(false);
					dialog.setVisible(true);
					
					new Timer(Delay, new ActionListener(){
						public void actionPerformed(ActionEvent e){
							dialog.setVisible(false);
						}
					}).start();
					
					textField.setText("");
					textField_2.setText("");
					textField_7.setText("");
					textField_6.setText("");
					textField_15.setText("");
					textField_14.setText("");
					textField_12.setText("");
					textField_16.setText("");
					
					textField_2.grabFocus();
					textField_2.requestFocus();
					
					ShowTable(connection, table);
				}	 

			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 14));
				
				JButton btnUsuSeriZ = new JButton("Usu\u0144 seri\u0119 z listy");
				btnUsuSeriZ.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						PreparedStatement pst2 =null;
						
						try {
							String query2 = "";
							if(Login.getAdmin()){
								query2 = "DELETE FROM BatchesKOP WHERE NrFiszki='"+textField_2.getText()+"'";
							}else{
								query2 = "DELETE FROM BatchesKOP2 WHERE NrFiszki='"+textField_2.getText()+"'";
							}
							pst2 = connection.prepareStatement(query2);
							pst2.execute();
							pst2.close();
							
							textField.setText("");
							textField_2.setText("");
							textField_7.setText("");
							textField_6.setText("");
							textField_15.setText("");
							textField_14.setText("");
							textField_12.setText("");
							textField_16.setText("");
							
							textField_2.grabFocus();
							textField_2.requestFocus();
							
							ShowTable(connection, table);
							
						
						}catch (SQLException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();

						}
					}
					
				});
				btnUsuSeriZ.setFont(new Font("Arial", Font.BOLD, 14));
						
						btnStwrzPdf = new JButton("Utw\u00F3rz WZ");
						btnStwrzPdf.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								
								if(textField_4.getText().equals("")||textField_5.getText().equals("")){
									JOptionPane.showMessageDialog(null, "Nie wprowadzono nazwy odbiorcy lub jego adresu");
								}else{
									
									try {
										String query="SELECT DISTINCT NumeryKolejnejWZ FROM NrWZ ORDER BY NumeryKolejnejWZ DESC";
										PreparedStatement pst=connection.prepareStatement(query);
										ResultSet rs=pst.executeQuery();
										if(rs.next()){
										setNrWZ(rs.getDouble("NumeryKolejnejWZ")+1);
										}
										pst.close();
										rs.close();
										
										System.out.println(getNrWZ());
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									try {
										String query="INSERT INTO NrWZ (NumeryKolejnejWZ) VALUES ("+getNrWZ()+")";
										PreparedStatement pst=connection.prepareStatement(query);
										pst.execute();
										pst.close();
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									setNazwaOdbiorcy(textField_5.getText());
									setAdresOdbiorcy(textField_4.getText());
									
									try {
										String query = "";
										if(Login.getAdmin()){
											query="UPDATE BatchesKOP SET NrWZ="+getNrWZ()+"";
										}else{
											query="UPDATE BatchesKOP2 SET NrWZ="+getNrWZ()+"";
										}
										PreparedStatement pst=connection.prepareStatement(query);
										pst.execute();
										pst.close();
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									try {
										String query = "";
										if(Login.getAdmin()){
											query="INSERT INTO BatchesKOPdetail (NrFiszki, NrSerii, NrArtykulu, Nazwa, IloscSzt, Material, RodzajObrobki, Komentarz, NrWZ) SELECT NrFiszki, NrSerii, NrArtykulu, Nazwa, IloscSzt, Material, RodzajObrobki, Komentarz, NrWZ FROM BatchesKOP";
										}else{
											query="INSERT INTO BatchesKOPdetail (NrFiszki, NrSerii, NrArtykulu, Nazwa, IloscSzt, Material, RodzajObrobki, Komentarz, NrWZ) SELECT NrFiszki, NrSerii, NrArtykulu, Nazwa, IloscSzt, Material, RodzajObrobki, Komentarz, NrWZ FROM BatchesKOP2";
										}
										
										PreparedStatement pst=connection.prepareStatement(query);
										pst.execute();
										pst.close();
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									try {
										String query="UPDATE BatchesKOPdetail SET DoFirmy='"+getNazwaOdbiorcy()+"' WHERE NrWZ="+getNrWZ()+"";
										PreparedStatement pst=connection.prepareStatement(query);
										pst.execute();
										pst.close();
										
									} catch (Exception e) {
										e.printStackTrace();
									}
								
									try {
										PDF_ListaDlaKOP.createPDF(connection);
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						});
						btnStwrzPdf.setFont(new Font("Tahoma", Font.BOLD, 12));
						Image img2 = new ImageIcon(this.getClass().getResource("/PdfIcon_mini.png")).getImage();
						btnStwrzPdf.setIcon(new ImageIcon(img2));
						
						txtAdresDostawcy = new JTextField();
						txtAdresDostawcy.setText("ODBIORCA");
						txtAdresDostawcy.setHorizontalAlignment(SwingConstants.CENTER);
						txtAdresDostawcy.setFont(new Font("Tahoma", Font.BOLD, 14));
						txtAdresDostawcy.setEditable(false);
						txtAdresDostawcy.setColumns(10);
						
						txtAdresDostawcy_1 = new JTextField();
						txtAdresDostawcy_1.setText("ADRES");
						txtAdresDostawcy_1.setHorizontalAlignment(SwingConstants.CENTER);
						txtAdresDostawcy_1.setFont(new Font("Tahoma", Font.BOLD, 14));
						txtAdresDostawcy_1.setEditable(false);
						txtAdresDostawcy_1.setColumns(10);
						
						textField_4 = new JTextField();
						textField_4.setHorizontalAlignment(SwingConstants.CENTER);
						textField_4.setColumns(10);
						
						textField_5 = new JTextField();
						textField_5.setHorizontalAlignment(SwingConstants.CENTER);
						textField_5.setColumns(10);
						
						btnCofnij = new JButton("Cofnij");
						btnCofnij.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								dispose();
								Login menu = new Login();
								menu.frame.setVisible(true);
								
							}
						});
						
						JButton btnUtwrzZamwienie = new JButton("Utw\u00F3rz Zam\u00F3wienie");
						btnUtwrzZamwienie.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								
								if(textField_4.getText().equals("")||textField_5.getText().equals("")){
									JOptionPane.showMessageDialog(null, "Nie wprowadzono nazwy odbiorcy lub jego adresu");
								}else{
									
									try {
										String query="SELECT DISTINCT NumeryKolejnejWZ FROM NrWZ ORDER BY NumeryKolejnejWZ DESC";
										PreparedStatement pst=connection.prepareStatement(query);
										ResultSet rs=pst.executeQuery();
										if(rs.next()){
										setNrWZ(rs.getDouble("NumeryKolejnejWZ")+1);
										}
										System.out.println(getNrWZ());
										pst.close();
										rs.close();
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									try {
										String query="INSERT INTO NrWZ (NumeryKolejnejWZ) VALUES ("+getNrWZ()+")";
										PreparedStatement pst=connection.prepareStatement(query);
										pst.execute();
										pst.close();
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									setNazwaOdbiorcy(textField_5.getText());
									setAdresOdbiorcy(textField_4.getText());
									
									try {
										String query="";
										if(Login.getAdmin()){
											query="UPDATE BatchesKOP SET NrWZ="+getNrWZ()+"";
										}else{
											query="UPDATE BatchesKOP2 SET NrWZ="+getNrWZ()+"";
										}
										
										PreparedStatement pst=connection.prepareStatement(query);
										pst.execute();
										pst.close();
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									try {
										String query="";
										if(Login.getAdmin()){
											query="INSERT INTO BatchesKOPdetail (NrFiszki, NrSerii, NrArtykulu, Nazwa, IloscSzt, Material, RodzajObrobki, Komentarz, NrWZ) SELECT NrFiszki, NrSerii, NrArtykulu, Nazwa, IloscSzt, Material, RodzajObrobki, Komentarz, NrWZ FROM BatchesKOP";
										}else{
											query="INSERT INTO BatchesKOPdetail (NrFiszki, NrSerii, NrArtykulu, Nazwa, IloscSzt, Material, RodzajObrobki, Komentarz, NrWZ) SELECT NrFiszki, NrSerii, NrArtykulu, Nazwa, IloscSzt, Material, RodzajObrobki, Komentarz, NrWZ FROM BatchesKOP2";
										}
										PreparedStatement pst=connection.prepareStatement(query);
										pst.execute();
										pst.close();
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									try {
										String query="UPDATE BatchesKOPdetail SET DoFirmy='"+getNazwaOdbiorcy()+"' WHERE NrWZ="+getNrWZ()+"";
										PreparedStatement pst=connection.prepareStatement(query);
										pst.execute();
										pst.close();
										
									} catch (Exception e) {
										e.printStackTrace();
									}
								
									try {
										PDF_Zamowienie.createPDF(connection);
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						});
						btnUtwrzZamwienie.setFont(new Font("Tahoma", Font.BOLD, 12));
						Image img3 = new ImageIcon(this.getClass().getResource("/PdfIcon_mini.png")).getImage();
						btnUtwrzZamwienie.setIcon(new ImageIcon(img3));
						
						btnNewButton_1 = new JButton("...");
						btnNewButton_1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								SuppliersTable okno = new SuppliersTable(connection);
								okno.setVisible(true);
							}
						});
						btnNewButton_1.setToolTipText("Otwiera list\u0119 dostawc\u00F3w do wyboru");
						
						GroupLayout gl_contentPane = new GroupLayout(contentPane);
						gl_contentPane.setHorizontalGroup(
							gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(19)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(txtNrArtykuu, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
											.addComponent(textField_7, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(txtOpis, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
											.addComponent(textField_6, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(txtIloSztuk, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
												.addComponent(txtObrbkaCieplnachemiczna, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
												.addComponent(txtMateria, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(textField_15, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
												.addComponent(textField_14, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
												.addComponent(textField_16, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(txtKomentarz, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
											.addComponent(textField_12, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(txtNrBonu, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
											.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(txtAdresDostawcy, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
												.addComponent(txtAdresDostawcy_1, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(textField_4, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
												.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
													.addComponent(textField_5, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(txtNrSerii, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
											.addComponent(textField, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)))
									.addContainerGap())
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(19)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(btnCofnij)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblMojeMenu, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
											.addGap(16))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
											.addContainerGap())))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(19)
									.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
									.addContainerGap())
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(97)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btnUtwrzZamwienie, GroupLayout.PREFERRED_SIZE, 257, Short.MAX_VALUE)
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
										.addComponent(btnUsuSeriZ, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
										.addComponent(btnStwrzPdf, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
									.addGap(121))
						);
						gl_contentPane.setVerticalGroup(
							gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addContainerGap()
											.addComponent(btnCofnij))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(6)
											.addComponent(lblMojeMenu, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(txtNrBonu, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
									.addGap(1)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(txtNrSerii, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
									.addGap(1)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(txtNrArtykuu, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField_7, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(txtOpis, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField_6, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(txtIloSztuk, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(52)
											.addComponent(txtObrbkaCieplnachemiczna, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(26)
											.addComponent(txtMateria, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
										.addComponent(textField_15, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(26)
											.addComponent(textField_14, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(52)
											.addComponent(textField_16, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(txtKomentarz, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField_12, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
									.addGap(1)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(txtAdresDostawcy, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(26)
											.addComponent(txtAdresDostawcy_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnNewButton_1))
											.addGap(1)
											.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
									.addGap(8)
									.addComponent(btnUsuSeriZ, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
									.addGap(8)
									.addComponent(btnStwrzPdf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnUtwrzZamwienie, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE))
						);
						contentPane.setLayout(gl_contentPane);
						
								
//		JLabel lblNewLabel = new JLabel("");
//		lblNewLabel.setBackground(Color.WHITE);
//		lblNewLabel.setIcon(new ImageIcon(img));
//		lblNewLabel.setBounds(-257, -34, 1858, 1438);
//		contentPane.add(lblNewLabel);
//		
	
		textField_2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		
		Statement st = null;
		ResultSet rs = null;
		
		try {

		st = connection.createStatement();
		rs = st.executeQuery("SELECT * FROM Werkbon WHERE Werkbonnummer='"+textField_2.getText()+"'");	
		
			if(rs.next())
			{
				String Afdeling = rs.getString("Afdeling");
				String AfdelingSeq = rs.getString("Afdelingseq");
				String NrSerii = Afdeling+"/"+AfdelingSeq;
				NrArtykulu = rs.getString("Artikelcode");
				String Opis = rs.getString("Omschrijving");
				stanowisko = rs.getString("Werkpost");
				IloscSztuk = rs.getString("Hoeveelheid");
				//nrOperacji = Integer.parseInt(rs.getString("Opseq"));
				//String ObrobkaOpis = rs.getString("CFTEKST");
				
				textField.setText(NrSerii);
				textField_7.setText(NrArtykulu);
				textField_6.setText(Opis);
				textField_15.setText(IloscSztuk);
				
				System.out.println(stanowisko);
				System.out.println(IloscSztuk);
				System.out.println(nrOperacji);
				//textField_16.setText(ObrobkaOpis);
			}
			
			rs.close();
			st.close();
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		
		Statement st2 = null;
		ResultSet rs2 = null;
		
		try {
			
		st2 = connection.createStatement();
		rs2 = st2.executeQuery("SELECT * FROM ProjectMaterials WHERE Order500='"+textField.getText()+"'");
		
		System.out.println(textField.getText());
		
			if(rs2.next())
			{
				String MaterialFAT = rs2.getString("MaterialAssigned");
				String MaterialNorm = rs2.getString("MaterialNorm");
				textField_14.setText(MaterialNorm+" ("+MaterialFAT+")");
				System.out.println("a");
			}
			
		st2.close();
		rs2.close();
			
		}
		catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();

		}
		
		Statement st3 = null;
		ResultSet rs3 = null;
		
		try {
			
		st3 = connection.createStatement();
		rs3 = st3.executeQuery("SELECT projectnummer, leverancier, ordernummer, besteld, besteleenheid FROM storenotesdetail WHERE projectnummer='"+textField.getText()+"'");
		
			if(rs3.next())
			{
			 String nrDostawcy = rs3.getString("leverancier");
			 String jednostka = rs3.getString("besteleenheid");
			 String ileZamowiono = rs3.getString("besteld");
			 
			 System.out.println(nrDostawcy);
			 System.out.println(jednostka);
			 System.out.println(ileZamowiono);
			 	if(nrDostawcy.equals("105")&&jednostka.equals("KG")){
			 		textField_15.setText("Sztuk: "+IloscSztuk+" / Masa: "+ileZamowiono+" "+jednostka);
			 	}
			}
			
		st3.close();
		rs3.close();
			
		}
		catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();

		}
		
		Statement st4 = null;
		ResultSet rs4 = null;
		
		try {
			
		st4 = connection.createStatement();
		rs4 = st4.executeQuery("SELECT tekst FROM nom_operatie WHERE artikelcode='"+NrArtykulu+"' AND werkpost='"+stanowisko+"'");
		
			if(rs4.next())
			{
			 String ObrobkaOpis = rs4.getString("tekst");
			 System.out.println(ObrobkaOpis);
			 textField_16.setText(ObrobkaOpis);
			}
			
		st4.close();
		rs4.close();
			
		}
		catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();

		}
		
			}
			});
		
		
		}
	public void ShowTable(Connection connection, JTable table)
	{
		try {
			String query="";
			if(Login.getAdmin()){
				query="SELECT * FROM BatchesKOP";
			}else{
				query="SELECT * FROM BatchesKOP2";
			}
			PreparedStatement pst=connection.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
