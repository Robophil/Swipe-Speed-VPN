package com.Webtunnel.Gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Cursor;

import javax.swing.JButton;

import com.Webtunnel.Constants.ProxyConstants;
import com.Webtunnel.Start.StartServer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Toolkit;

import javax.swing.JRadioButton;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private JLabel uploadLabel;
	private JLabel DownloadLabel;
	private int startid=1;
	private JRadioButton mth1;
	private JRadioButton mth2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//keystore properties, must be set in a static class 
		System.setProperty("javax.net.ssl.keyStore",ProxyConstants.trustPath);
		System.setProperty("javax.net.ssl.keyStorePassword", ProxyConstants.password);
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/com/Webtunnel/res/icon.jpg")));
		setResizable(false);
		setBackground(new Color(255, 0, 0));
		setTitle("Swipe Speed");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 637, 419);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(135, 206, 235));
		panel.setBounds(0, 0, 155, 380);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MainFrame.class.getResource("/com/Webtunnel/res/images (4).jpg")));
		label.setBounds(0, 37, 145, 145);
		panel.add(label);
		
		final JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(1==startid){
					
					if (mth1.isSelected()){
						ProxyConstants.tweak_method=0;
					}else{
						ProxyConstants.tweak_method=1;
					}
					
					new StartServer().Startmeup();
					btnStart.setText("Exit");
					startid=0;
				}else if(0==startid){
					System.exit(1);
				}
			}
		});
		btnStart.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnStart.setBounds(27, 334, 89, 35);
		panel.add(btnStart);
		
		mth1 = new JRadioButton("Method 1");
		mth1.setBounds(7, 206, 109, 23);
		panel.add(mth1);
		mth1.setSelected(true);
		
		mth2 = new JRadioButton("Method 2");
		mth2.setBounds(7, 248, 109, 23);
		panel.add(mth2);
		
		ButtonGroup group_rb=new ButtonGroup();
		group_rb.add(mth1);
		group_rb.add(mth2);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.setBounds(194, 0, 417, 316);
		contentPane.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Home", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(MainFrame.class.getResource("/com/Webtunnel/res/images.jpg")));
		lblNewLabel.setBounds(0, 0, 412, 288);
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tabbedPane.addTab("About", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 412, 288);
		panel_2.add(scrollPane);
		
		JTextArea txtrConactUsEmail = new JTextArea();
		txtrConactUsEmail.setEditable(false);
		txtrConactUsEmail.setText("CONACT US\r\n\r\nEmail : SwipeTeam123@gmail.com\r\n\r\nDISCLAIMER\r\n\r\nYou are responsible for all your actions. \r\n\r\n\u00A9 2014 SwipeTeam");
		txtrConactUsEmail.setForeground(Color.BLACK);
		txtrConactUsEmail.setBackground(new Color(135, 206, 235));
		txtrConactUsEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(txtrConactUsEmail);
		
		uploadLabel = new JLabel("upload");
		uploadLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		uploadLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		uploadLabel.setIcon(new ImageIcon(MainFrame.class.getResource("/com/Webtunnel/res/images (9).jpg")));
		uploadLabel.setBounds(204, 320, 107, 60);
		contentPane.add(uploadLabel);
		
		DownloadLabel = new JLabel("download");
		DownloadLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		DownloadLabel.setIcon(new ImageIcon(MainFrame.class.getResource("/com/Webtunnel/res/images (9) - Copy.jpg")));
		DownloadLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		DownloadLabel.setBounds(437, 320, 107, 60);
		contentPane.add(DownloadLabel);
		
		myworkers my=new myworkers();
		my.execute();
	}
	
	
	private class myworkers  extends SwingWorker<Void, Void>{

		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			while(true){
				
				publish((Void) null);
				
				Thread.sleep(3000);
			}
		}
		
		@Override
		protected void process(List<Void> chunks) {
			// TODO Auto-generated method stub
			for(int i=0; i<chunks.size();i++){
				
				//for upload link
				final double up=ProxyConstants.upload;
				if (up<1000) {
					uploadLabel.setText(up+" B");
				} else if(up < 1000000){
					final String v=String.format( "%.2f KB" ,(double)((double)up/(double)1000) );
					uploadLabel.setText(v);
				} else if(up < 1000000000){
					final String v=String.format( "%.2f MB" ,(double)((double)up/(double)1000000) );
					uploadLabel.setText(v);
				}else if(up >=1000000000 ){
					final String v=String.format( "%.2f GB" ,(double)((double)up/(double)1000000000) );
					uploadLabel.setText(v);
				}
				
				//for download link
				//for upload link
				final double dn=ProxyConstants.download;
				if (dn<1000) {
					DownloadLabel.setText(dn+" B");
				} else if(dn < 1000000){
					final String v=String.format( "%.2f KB" ,(double)((double)dn/(double)1000) );
					DownloadLabel.setText(v);
				} else if(dn < 1000000000){
					final String v=String.format( "%.2f MB" ,(double)((double)dn/(double)1000000) );
					DownloadLabel.setText(v);
				}else if(dn >=1000000000 ){
					final String v=String.format( "%.2f GB" ,(double)((double)dn/(double)1000000000) );
					DownloadLabel.setText(v);
				}
				
			}
		}
		
	}
}
