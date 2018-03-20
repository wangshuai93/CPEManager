package serialPort;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.acl.Owner;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import serialException.ExceptionWriter;
import serialException.NoSuchPort;
import serialException.NotASerialPort;
import serialException.PortInUse;
import serialException.ReadDataFromSerialPortFailure;
import serialException.SerialPortInputStreamCloseFailure;
import serialException.SerialPortParameterFailure;
import serialException.TooManyListeners;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;

public class MainView extends Frame {

	private List<String> commList = null; // 保存可用端口号
	private SerialPort serialPort = null; // 保存串口对象
	private String oldName = null;
	private String oldIP = null;

	private String inputIpaddr = null;
	private String inputIpaddr1 = null;
	private boolean isSetIP = false;
	private boolean isStartPPPOE = false;
	private boolean isSetIP1 = false;
	private String inputName = null;
	private boolean isSetName = false;
	static Client client = null;

	private JFrame frmv;
	private JTextField cpu;
	private JTextField ram;
	private JTextField process;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JPanel panel;
	private Choice commChoice;
	private Choice bpsChoice;
	private JButton openSerialButton;
	private JPanel panel_1;
	private JTextField flash;
	private JPanel panel_2;
	private JLabel label;
	private JTextField serialnumber;
	private JLabel label_1;
	private JTextField stbname;
	private JButton btnNewButton;
	private JPanel panel_4;
	private JLabel lblIp;
	private JTextField ipaddr;
	private JLabel lblMac;
	private JTextField mask;
	private JTextField gateway;
	private JLabel label_9;
	private JTextField mac;
	private JLabel label_6;
	private JButton eth0Button;
	private JPanel panel_3;
	private JLabel label_2;
	private JTextField ipaddr1;
	private JLabel label_3;
	private JTextField mask1;
	private JLabel label_4;
	private JTextField gateway1;
	private JLabel label_5;
	private JTextField mac1;
	private JButton eth1Button;
	private JLabel label_7;
	private JTextField hardversion;
	private JLabel label_8;
	private JTextField softwareversion;
	private JPanel panel_5;
	private JButton setIpButton;
	private JLabel lblIp_1;
	private JButton stopSerialButton;
	private JLabel lblIp_2;
	private JButton setIp1Button;
	private JRadioButton serialConnect;
	private JLabel label_14;
	private JButton setGateway0Button;
	private JLabel label_15;
	private JButton setGateway1Button;
  private JTextField textfield_pppoeDNS;
  private JTextField textField_username;
  private JButton button_StartPPPoe;
  private JButton button_StopPPPOE;
  private JPasswordField passwordField;
  private JTextField textField_stbname;
  private JTextField textField_ipaddr0;
  private JTextField textField_eth0;
  private JTextField textField_ipaddr1;
  private JTextField textField_eth1;
  private JButton button_CopyRecFile;
  private JLayeredPane layeredPane;
  private JLabel lbldns;
  private JTextField textField_dns1;
  private JLabel lbldns_1;
  private JTextField textField_dns2;
  private JPanel panel_9;
  private JTextField textField_sn;
  JRadioButton option60 ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView(client);
					window.frmv.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// public MainView(Client client) {
	// this.client = client;
	// commList = SerialTool.findPort(); // 程序初始化时就扫描一次有效串口
	// }
	/**
	 * Create the application.
	 */
	public MainView(Client client) {
		this.client = client;
		commList = SerialTool.findPort(); // 程序初始化时就扫描一次有效串口
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmv = new JFrame();
		frmv.setBackground(Color.WHITE);
		frmv.setTitle("探针设备管理V1.2");
		frmv.setIconImage(Toolkit.getDefaultToolkit().getImage(
				MainView.class.getResource("/serialPort/icon.png")));
		frmv.setBounds(100, 100, 788, 720);
		frmv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmv.getContentPane().setLayout(null);
		 frmv.setLocationRelativeTo(null);
		panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u4E32\u53E3\u914D\u7F6E",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel.setBounds(10, 10, 564, 64);
		frmv.getContentPane().add(panel);

		commChoice = new Choice();

		if (commList == null || commList.size() < 1) {
			JOptionPane.showMessageDialog(null, "没有搜索到有效串口！", "错误",
					JOptionPane.INFORMATION_MESSAGE);
			commChoice.add("    ");
		} else {
			for (String s : commList) {
				commChoice.add(s);
			}
		}

		JLabel label_12 = new JLabel("端口号");
		panel.add(label_12);
		panel.add(commChoice);

		bpsChoice = new Choice();
		bpsChoice.add("1200");
		bpsChoice.add("2400");
		bpsChoice.add("4800");
		bpsChoice.add("9600");
		bpsChoice.add("14400");
		bpsChoice.add("19200");
		bpsChoice.add("115200");
		bpsChoice.select(6);

		JLabel label_13 = new JLabel("  波特率");
		panel.add(label_13);

		panel.add(bpsChoice);

		openSerialButton = new JButton(" 打开串口 ");

		panel.add(openSerialButton);

		stopSerialButton = new JButton(" 断开连接 ");
		panel.add(stopSerialButton);

		JButton serialStatFlash = new JButton("刷新");
		serialStatFlash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(serialStatFlash);

		JLabel label_10 = new JLabel("");
		panel.add(label_10);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u8BBE\u5907\u72B6\u6001",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel_1.setBounds(10, 308, 453, 90);
		frmv.getContentPane().add(panel_1);
		frmv.setResizable(false);

		JLabel lblNewLabel = new JLabel("CPU占用 ");
		panel_1.add(lblNewLabel);

		cpu = new JTextField();
		cpu.setHorizontalAlignment(SwingConstants.CENTER);
		cpu.setEditable(false);

		panel_1.add(cpu);
		cpu.setColumns(12);

		lblNewLabel_2 = new JLabel("内存剩余");
		panel_1.add(lblNewLabel_2);

		ram = new JTextField();
		ram.setHorizontalAlignment(SwingConstants.CENTER);
		ram.setEditable(false);
		panel_1.add(ram);
		ram.setColumns(12);

		JLabel lblNewLabel_1 = new JLabel("进程状态");
		panel_1.add(lblNewLabel_1);

		process = new JTextField();
		process.setEditable(false);
		process.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(process);
		process.setColumns(12);

		lblNewLabel_3 = new JLabel("磁盘剩余");
		panel_1.add(lblNewLabel_3);

		flash = new JTextField();
		flash.setEditable(false);
		flash.setHorizontalAlignment(SwingConstants.CENTER);
		flash.setColumns(12);
		panel_1.add(flash);

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null,
				"\u8BBE\u5907\u57FA\u7840\u4FE1\u606F", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 460, 453, 121);
		frmv.getContentPane().add(panel_2);

		label = new JLabel("   序列号");
		panel_2.add(label);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBorder(UIManager.getBorder("TitledBorder.border"));
		panel_2.add(layeredPane);

		serialnumber = new JTextField();
		serialnumber.setHorizontalAlignment(SwingConstants.CENTER);
		serialnumber.setEditable(false);
		serialnumber.setColumns(12);
		panel_2.add(serialnumber);

		label_1 = new JLabel("  设备名称");
		panel_2.add(label_1);

		stbname = new JTextField();
		stbname.setHorizontalAlignment(SwingConstants.CENTER);
		stbname.setEditable(false);
		stbname.setColumns(12);
		panel_2.add(stbname);

		label_7 = new JLabel("硬件版本");
		panel_2.add(label_7);

		hardversion = new JTextField();
		hardversion.setHorizontalAlignment(SwingConstants.CENTER);
		hardversion.setEditable(false);
		hardversion.setColumns(12);
		panel_2.add(hardversion);

		label_8 = new JLabel("  软件版本");
		panel_2.add(label_8);

		softwareversion = new JTextField();
		softwareversion.setHorizontalAlignment(SwingConstants.CENTER);
		softwareversion.setEditable(false);
		softwareversion.setColumns(12);
		panel_2.add(softwareversion);

		btnNewButton = new JButton("刷新");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel_2.add(btnNewButton);

		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u7F51\u53E30",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel_4.setBounds(10, 91, 764, 73);
		frmv.getContentPane().add(panel_4);

		lblIp = new JLabel("IP地址");
		panel_4.add(lblIp);

		ipaddr = new JTextField();
		ipaddr.setHorizontalAlignment(SwingConstants.CENTER);
		ipaddr.setColumns(10);
		panel_4.add(ipaddr);

		lblMac = new JLabel("  子网掩码");
		panel_4.add(lblMac);

		mask = new JTextField();
		mask.setHorizontalAlignment(SwingConstants.CENTER);
		mask.setColumns(10);
		panel_4.add(mask);

		label_6 = new JLabel("  网关");
		panel_4.add(label_6);

		gateway = new JTextField();
		gateway.setHorizontalAlignment(SwingConstants.CENTER);
		gateway.setColumns(10);
		panel_4.add(gateway);

		label_9 = new JLabel("  物理地址");
		panel_4.add(label_9);

		mac = new JTextField();
		mac.setHorizontalAlignment(SwingConstants.CENTER);
		mac.setColumns(10);
		panel_4.add(mac);

		eth0Button = new JButton("刷新");
		panel_4.add(eth0Button);

		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u7F51\u53E31",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel_3.setBounds(10, 163, 764, 73);
		frmv.getContentPane().add(panel_3);

		label_2 = new JLabel("IP地址");
		panel_3.add(label_2);

		ipaddr1 = new JTextField();
		ipaddr1.setHorizontalAlignment(SwingConstants.CENTER);
		ipaddr1.setColumns(10);
		panel_3.add(ipaddr1);

		label_3 = new JLabel("  子网掩码");
		panel_3.add(label_3);

		mask1 = new JTextField();
		mask1.setHorizontalAlignment(SwingConstants.CENTER);
		mask1.setColumns(10);
		panel_3.add(mask1);

		label_4 = new JLabel("  网关");
		panel_3.add(label_4);

		gateway1 = new JTextField();
		gateway1.setHorizontalAlignment(SwingConstants.CENTER);
		gateway1.setColumns(10);
		panel_3.add(gateway1);

		label_5 = new JLabel("  物理地址");
		panel_3.add(label_5);

		mac1 = new JTextField();
		mac1.setHorizontalAlignment(SwingConstants.CENTER);
		mac1.setColumns(10);
		panel_3.add(mac1);

		eth1Button = new JButton("刷新");
		panel_3.add(eth1Button);

		panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u7F51\u7EDC\u8BBE\u7F6E",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel_5.setBounds(493, 308, 281, 153);
		frmv.getContentPane().add(panel_5);

		lblIp_1 = new JLabel("IP地址 0");
		panel_5.add(lblIp_1);
		
		textField_ipaddr0 = new JTextField();
		panel_5.add(textField_ipaddr0);
		textField_ipaddr0.setColumns(10);

		setIpButton = new JButton("设置");
		panel_5.add(setIpButton);

		label_14 = new JLabel("    网关 0");
		panel_5.add(label_14);
		
		textField_eth0 = new JTextField();
		panel_5.add(textField_eth0);
		textField_eth0.setColumns(10);

		setGateway0Button = new JButton("设置");
		panel_5.add(setGateway0Button);

		lblIp_2 = new JLabel("IP地址 1");
		panel_5.add(lblIp_2);
		
		textField_ipaddr1 = new JTextField();
		panel_5.add(textField_ipaddr1);
		textField_ipaddr1.setColumns(10);

		setIp1Button = new JButton("设置");
		panel_5.add(setIp1Button);

		label_15 = new JLabel("    网关 1");
		panel_5.add(label_15);
		
		textField_eth1 = new JTextField();
		panel_5.add(textField_eth1);
		textField_eth1.setColumns(10);

		setGateway1Button = new JButton("设置");

		panel_5.add(setGateway1Button);

		JList<?> list = new JList<Object>();
		list.setBounds(26, 9, 1, 1);
		frmv.getContentPane().add(list);

		serialConnect = new JRadioButton("未连接");
		serialConnect.setEnabled(false);
		serialConnect.setBounds(614, 31, 121, 23);
		frmv.getContentPane().add(serialConnect);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u5176\u4ED6\u8BBE\u7F6E",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel_6.setBounds(493, 460, 281, 121);
		frmv.getContentPane().add(panel_6);

		JLabel label_11 = new JLabel("设备名称");
		panel_6.add(label_11);
		
		textField_stbname = new JTextField();
		panel_6.add(textField_stbname);
		textField_stbname.setColumns(10);

		JButton setNameButton = new JButton("设置");
		panel_6.add(setNameButton);
		

		JButton upgradeButton = new JButton("设备软件升级");
		upgradeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Command.upGrade(serialPort);
				JOptionPane.showMessageDialog(null, "正在启动系统升级，请耐心等待1~2分钟！",
						"提示", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		JLabel lblSn = new JLabel("SN序列号");
		panel_6.add(lblSn);
		
		textField_sn = new JTextField();
		panel_6.add(textField_sn);
		textField_sn.setColumns(10);
		
		JButton button_SNset = new JButton("设置");
		button_SNset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			String serialNumber =	textField_sn.getText().toString();
			Command.SetSerialNumber(serialPort, serialNumber);
			JOptionPane.showMessageDialog(null,
					"序列号设置成功，需重启设备才能生效", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			}
		});
		panel_6.add(button_SNset);
		
		panel_6.add(upgradeButton);
		
		button_CopyRecFile = new JButton("复制录制文件");
		panel_6.add(button_CopyRecFile);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(null, "PPPOE\u62E8\u53F7", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setToolTipText("");
		panel_7.setBounds(10, 581, 764, 80);
		frmv.getContentPane().add(panel_7);
		
		JLabel lblNewLabel_4 = new JLabel("DNS服务器");
		panel_7.add(lblNewLabel_4);
		
		textfield_pppoeDNS = new JTextField();
		panel_7.add(textfield_pppoeDNS);
		textfield_pppoeDNS.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("用户名");
		panel_7.add(lblNewLabel_5);
		
		textField_username = new JTextField();
		panel_7.add(textField_username);
		textField_username.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("密码");
		panel_7.add(lblNewLabel_6);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		panel_7.add(passwordField);
		//passwordField.setColumns(10);
		
		
		button_StartPPPoe = new JButton("开始连接");
		panel_7.add(button_StartPPPoe);
		
		button_StopPPPOE = new JButton("断开连接");
		panel_7.add(button_StopPPPOE);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "DNS\u670D\u52A1\u5668\u5730\u5740", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_8.setBounds(10, 235, 453, 73);
		frmv.getContentPane().add(panel_8);
		
		lbldns = new JLabel("首选DNS服务器");
		panel_8.add(lbldns);
		
		textField_dns1 = new JTextField();
		textField_dns1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_dns1.setColumns(10);
		panel_8.add(textField_dns1);
		
		lbldns_1 = new JLabel("  备选DNS服务器");
		panel_8.add(lbldns_1);
		
		textField_dns2 = new JTextField();
		textField_dns2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_dns2.setColumns(10);
		panel_8.add(textField_dns2);
		
		panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(null, "DHCP\u914D\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_9.setBounds(493, 235, 281, 73);
		frmv.getContentPane().add(panel_9);
		
		option60 = new JRadioButton("是否带有OPTION60字段");
		panel_9.add(option60);
		
		JButton btn_dhcpSet = new JButton("设置");
		btn_dhcpSet.addActionListener(new ActionListener() {
			String tip ;
			public void actionPerformed(ActionEvent arg0) {
				if(option60.isSelected()){
					System.out.println("option true");
					Command.SetOption60(serialPort, 1);
					
				}else{
					System.out.println("option false");
					Command.SetOption60(serialPort, 0);
				}
				JOptionPane.showMessageDialog(null,
						"设置成功，需重启设备才能生效", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		panel_9.add(btn_dhcpSet);

		openSerialButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// 获取串口名称
				String commName = commChoice.getSelectedItem();
				// 获取波特率
				String bpsStr = bpsChoice.getSelectedItem();

				// 检查串口名称是否获取正确
				if (commName == null || commName.equals("")) {
					JOptionPane.showMessageDialog(null, "没有搜索到有效串口！", "错误",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 检查波特率是否获取正确
					if (bpsStr == null || bpsStr.equals("")) {
						JOptionPane.showMessageDialog(null, "波特率获取错误！", "错误",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						// 串口名、波特率均获取正确时
						int bps = Integer.parseInt(bpsStr);
						try {

							// 获取指定端口名及波特率的串口对象
							serialPort = SerialTool.openPort(commName, bps);
							// 在该串口对象上添加监听器
							SerialTool.addListener(serialPort,
									new SerialListener());
							serialConnect.setText("已连接");
							serialConnect.setSelected(true);
							// 监听成功进行提示
							JOptionPane.showMessageDialog(null, "连接设备成功！",
									"提示", JOptionPane.INFORMATION_MESSAGE);

							Command.getInfo(serialPort);
							Command.getEthInfo(serialPort);
						} catch (SerialPortParameterFailure | NotASerialPort
								| NoSuchPort | PortInUse | TooManyListeners e1) {
							// 发生错误时使用一个Dialog提示具体的错误信息
							JOptionPane.showMessageDialog(null, e1, "错误",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}

			}
		});

		setIpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// System.out.println("setIpButton");
				inputIpaddr = textField_ipaddr0.getText().toString();
				Command.setIP("eth0", inputIpaddr, serialPort);

				isSetIP = true;
				// ipaddr.setText("读取中...");
				// Command.getInfobyCommand(serialPort);
				Command.getEthInfo(serialPort);
			}
		});

		setIp1Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// System.out.println("setIpButton");
				inputIpaddr1 = textField_ipaddr1.getText().toString();
				Command.setIP("eth1", inputIpaddr1, serialPort);
				Command.getEthInfo(serialPort);

				isSetIP1 = true;

			}
		});

		eth0Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
                
				// resetGraphic();
				Command.getEthInfo(serialPort);
			}
		});

		eth1Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				// resetGraphic();
				Command.getEthInfo(serialPort);

			}
		});
		
		

		stopSerialButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// resetGraphic();
				SerialTool.closePort(serialPort);
				resetGraphic();
				serialConnect.setSelected(false);
				serialConnect.setText("未连接");

			}
		});

		setGateway0Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String gateway = textField_eth0.getText().toString();
				Command.setGateWay("eth0", gateway, serialPort);
				Command.getEthInfo(serialPort);
			}
		});

		setGateway1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String gateway1 = textField_eth1.getText().toString();
				Command.setGateWay("eth1", gateway1, serialPort);
				Command.getEthInfo(serialPort);
			}
		});

		
		setNameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = textField_stbname.getText().toString();
				
				try {
					//System.out.println("old"+(int)name.charAt(0));
					String strUTF8 = URLDecoder.decode(name, "UTF-8");
					//System.out.println("utf8"+(int)name.charAt(0));
					
					 String utf8 = new String(strUTF8.getBytes("gbk"), "utf-8"); 
//					 byte [] order = utf8.getBytes();
					 //ipInput.setText(printChart(order));
					Command.setName(strUTF8, serialPort);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
					//String name1 = DataParse.GBK2Unicode(name);
					
				
	
				
			}
		});
		button_StartPPPoe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String DNS = textfield_pppoeDNS.getText().toString();
				String usr = textField_username.getText().toString();
				String pwd = passwordField.getText().toString();
				Command.startPPPOE(serialPort, usr, pwd, DNS);
				isStartPPPOE = true;
				
			}
		});
		button_StopPPPOE.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Command.stopPPPOE(serialPort);
				
			}
		});
		new Thread(new RepaintThread()).start();
		
		button_CopyRecFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Command.CopyRecFile(serialPort);
			}
		});

	}

	public void resetGraphic() {

		ipaddr.setText("");
		mask.setText("");
		gateway.setText("");
		mac.setText("");

		ipaddr1.setText("");
		mask1.setText("");
		gateway1.setText("");
		mac1.setText("");

		cpu.setText("");
		process.setText("");
		ram.setText("");
		flash.setText("");
		stbname.setText("");

		serialnumber.setText("");
		softwareversion.setText("");
		hardversion.setText("");
		option60.setSelected(false);
		textField_dns1.setText("");
		textField_dns2.setText("");
	}
	
	/*
	 * 重画线程（每隔30毫秒重画一次）
	 */

	private class RepaintThread implements Runnable {
		public void run() {
			while (true) {
				// 调用重画方法
				repaint();

				// 扫描可用串口
				commList = SerialTool.findPort();
				if (commList != null && commList.size() > 0) {

					// 添加新扫描到的可用串口
					for (String s : commList) {

						// 该串口名是否已存在，初始默认为不存在（在commList里存在但在commChoice里不存在，则新添加）
						boolean commExist = false;

						for (int i = 0; i < commChoice.getItemCount(); i++) {
							if (s.equals(commChoice.getItem(i))) {
								// 当前扫描到的串口名已经在初始扫描时存在
								commExist = true;
								break;
							}
						}

						if (commExist) {
							// 当前扫描到的串口名已经在初始扫描时存在，直接进入下一次循环
							continue;
						} else {
							// 若不存在则添加新串口名至可用串口下拉列表
							commChoice.add(s);
						}
					}

					// 移除已经不可用的串口
					for (int i = 0; i < commChoice.getItemCount(); i++) {

						// 该串口是否已失效，初始默认为已经失效（在commChoice里存在但在commList里不存在，则已经失效）
						boolean commNotExist = true;

						for (String s : commList) {
							if (s.equals(commChoice.getItem(i))) {
								commNotExist = false;
								break;
							}
						}

						if (commNotExist) {
							// System.out.println("remove" +
							// commChoice.getItem(i));
							commChoice.remove(i);
						} else {
							continue;
						}
					}

				} else {
					// 如果扫描到的commList为空，则移除所有已有串口
					commChoice.removeAll();
				}

				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					String err = ExceptionWriter.getErrorInfoFromException(e);
					JOptionPane.showMessageDialog(null, err, "错误",
							JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			}
		}

	}

	/**
	 * 以内部类形式创建一个串口监听类
	 * 
	 * @author zhong
	 * 
	 */
	private class SerialListener implements SerialPortEventListener {

		/**
		 * 处理监控到的串口事件
		 */
		public void serialEvent(SerialPortEvent serialPortEvent) {

			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			switch (serialPortEvent.getEventType()) {

			case SerialPortEvent.BI: // 10 通讯中断
				JOptionPane.showMessageDialog(null, "与串口设备通讯中断", "错误",
						JOptionPane.INFORMATION_MESSAGE);

				resetGraphic();

				break;

			case SerialPortEvent.OE: // 7 溢位（溢出）错误

			case SerialPortEvent.FE: // 9 帧错误

			case SerialPortEvent.PE: // 8 奇偶校验错误

			case SerialPortEvent.CD: // 6 载波检测

			case SerialPortEvent.CTS: // 3 清除待发送数据

			case SerialPortEvent.DSR: // 4 待发送数据准备好了

			case SerialPortEvent.RI: // 5 振铃指示

			case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
				break;

			case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据

				// System.out.println("found data");
				byte[] data = null;

				try {
					if (serialPort == null) {
						JOptionPane.showMessageDialog(null, "串口对象为空！监听失败！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
					} else {
						data = SerialTool.readFromPort(serialPort); // 读取数据，存入字节数组
						// System.out.println(new String(data, "GBK"));

						// 自定义解析过程
						if (data == null || data.length < 1) { // 检查数据是否读取正确
							JOptionPane.showMessageDialog(null,
									"读取数据过程中未获取到有效数据！请检查设备或程序！", "错误",
									JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
						} else {

							String dataOriginal = new String(data, "UTF-8"); // 将字节数组数据转换位为保存了原始数据的字符串
							// String dataNew = ParseData(dataOriginal);
							if (dataOriginal.indexOf("bootsuccess") != -1) {
								JOptionPane.showMessageDialog(null,
										"设备系统启动成功！", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								Command.getInfo(serialPort);
								Command.getEthInfo(serialPort);

							}
							//System.out.println("[dataOriginal]" + dataOriginal);
							// System.out.println("ws*******" + data);
							String dataValid = ""; // 有效数据（用来保存原始数据字符串去除最开头*号以后的字符串）
							String eth0dataValid = "";// 有效的网口数据
							String dnsdata = "";
							String eth1dataValid = "";// 有效的网口数据
							String[] elements = null; // 用来保存按空格拆分原始字符串后得到的字符串数组
														// 解析数据
							String[] eth0Info = null; // 用来保存按空格拆分原始字符串后得到的字符串数组
							String[] eth1Info = null;
							String[] dnsInfo = null;

							// if (dataOriginal.charAt(0) == '~') { //
							// 当数据的第一个字符是*号时表示数据接收完成，开始解析
							dataValid = DataParse.getValidData(dataOriginal);
							//System.out.println("dataValid: "+dataValid);
							//System.out.println("dataValid1***********: "+dataValid);
							if(dataValid != null) {
								elements = dataValid.split(" ");
								System.out.println("elementlengthh="
										+ elements.length);
								if (elements == null || elements.length < 1) { // 检查数据是否解析正确
									JOptionPane.showMessageDialog(null,
											"数据解析过程出错，请检查设备或程序！", "错误",
											JOptionPane.INFORMATION_MESSAGE);
									System.exit(0);
								} else {
									try {
										// 更新界面Label值
										/*
										 * for (int i=0; i<elements.length; i++)
										 * { System.out.println(elements[i]); }
										 */
										// System.out.println("ipaddr: " +
										// elements[5]);
										// ~71510516011863409 5 5 95 435 44108
										// 72 1 WS ^
										// 数据格式序列号
										// ，软件版本，cpu占用率，CPU闲置，ram剩余，rom剩余，总进程，运行进程
										// 设备名称，设备ip
										serialnumber.setText(elements[0]);
										softwareversion.setText(elements[1]);
										cpu.setText(elements[2] + "%");
										process.setText(elements[7] + "/"
												+ elements[6]);
										ram.setText(elements[4] + "M / 1024M");
										flash.setText(elements[5] + "M / 4096M");
										stbname.setText(elements[8]);
										hardversion.setText("8");
										
										if (elements.length > 9) {
											// ipaddr.setText(elements[9]);
											// oldIP = elements[9];
											// if (isSetIP) {
											//
											// if (elements[9]
											// .equals(inputIpaddr)) {
											// JOptionPane
											// .showMessageDialog(
											// null,
											// "成功把终端IP设置为:"
											// + inputIpaddr
											// + "！",
											// "提示",
											// JOptionPane.INFORMATION_MESSAGE);
											// isSetIP = false;
											//
											// } else {
											// JOptionPane
											// .showMessageDialog(
											// null,
											// "IP设置失败！请重新设置!"
											// + inputIpaddr,
											// "提示",
											// JOptionPane.INFORMATION_MESSAGE);
											// isSetIP = false;
											// }
											//
											// }

										} else {
											// if (isSetIP) {
											// JOptionPane
											// .showMessageDialog(
											// null,
											// "IP设置失败！请重新设置！"
											// + inputIpaddr,
											// "提示",
											// JOptionPane.INFORMATION_MESSAGE);
											// isSetIP = false;
											//
											// }

											// ipaddr.setText("未获取到IP");

										}
                                       
										if (isSetName) {
											if (elements[8].equals(inputName)) {
												// JOptionPane
												// .showMessageDialog(
												// null,
												// "成功把终端名称设置为:"
												// + inputName
												// + "！",
												// "提示",
												// JOptionPane.INFORMATION_MESSAGE);
												//
												// isSetName = false;

											} else {

												// JOptionPane
												// .showMessageDialog(
												// null,
												// "终端名称设置失败！请重新设置！"
												// + inputName,
												// "提示",
												// JOptionPane.INFORMATION_MESSAGE);
												// isSetName = false;

											}

										}

									} catch (ArrayIndexOutOfBoundsException e) {
										JOptionPane
												.showMessageDialog(
														null,
														"数据解析过程出错，更新界面数据失败！请检查设备或程序！",
														"错误",
														JOptionPane.INFORMATION_MESSAGE);
										System.exit(0);
									}
								}
								// }

							}
							eth0dataValid = DataParse.getEthValidData("eth0",
									dataOriginal);
							// System.out
							// .println("eth0dataValid:" + eth0dataValid);
							eth1dataValid = DataParse.getEthValidData("eth1",
									dataOriginal);
							//dnsdata = DataParse.getEthValidData("dns", dataOriginal);
							
							
							 System.out
							.println("eth1dataValid:" + eth1dataValid);
							 if (isStartPPPOE) {
                             	
                             	if (dataOriginal.indexOf("TIMED OUT")!=-1) {
                             		
                             		JOptionPane.showMessageDialog(null,
											"PPPOE拨号失败", "错误",
											JOptionPane.INFORMATION_MESSAGE);
                             		isStartPPPOE = false;
								}
                             	if(dataOriginal.indexOf("Connected!")!=-1){
									JOptionPane.showMessageDialog(null,
											"PPPOE拨号成功", "提示",
											JOptionPane.INFORMATION_MESSAGE);
									
									isStartPPPOE = false;
									
								}
                             	if(dataOriginal.indexOf("connection up")!=-1){
									
									JOptionPane.showMessageDialog(null,
											"PPPOE已经处于连接状态，请断开重新连接", "提示",
											JOptionPane.INFORMATION_MESSAGE);
									isStartPPPOE = false;
									
								}
                            	
						}
					    if (eth0dataValid != null) {
								eth0Info = eth0dataValid.split(" ");
//								//System.out.println("eth0Info" + eth0Info[0]
//										+ "eth0info[1]" + eth0Info[1]);
//								//System.out.println("eth0Info length"
//										+ eth0Info.length);
								if (eth0Info != null && eth0Info.length > 1) {
								/*网络信息串口传输格式
								 * 
								 * eth0 0.0.0.0 B6:2E:F4:AB:5F:8A 0.0.0.0 0.0.0.0 0 172.16.175.130 172.16.175.131^
								eth1 0.0.0.0 1A:00:BB:28:D8:76 0.0.0.0 0.0.0.0^*/
									ipaddr.setText(eth0Info[0]);
									mask.setText(eth0Info[3]);
									mac.setText(eth0Info[1]);
									gateway.setText(eth0Info[2]);
									textField_dns1.setText(eth0Info[5]);
									textField_dns2.setText(eth0Info[6]);
									int option60_flag = Integer.parseInt(eth0Info[4]);	
									if(option60_flag == 0){
										option60.setSelected(false);
									}else if(option60_flag == 1){
										option60.setSelected(true);
										
									}
								} else if (eth0Info != null
										&& eth0Info.length == 2) {
									mac.setText(eth0Info[1]);
								}

							}
//							if (dnsdata!=null) {
//								dnsInfo = dnsdata.split(" ");
//								if (dnsInfo!=null) {
//									textField_dns1.setText(dnsInfo[0]);
//									textField_dns2.setText(dnsInfo[1]);
//									
//								}
//								
//								
//							}

							if (eth1dataValid != null) {
								eth1Info = eth1dataValid.split(" ");
//								System.out.println("eth1Info" + eth1Info);
								if (eth1Info != null && eth1Info.length > 1) {
									ipaddr1.setText(eth1Info[0]);
									mask1.setText(eth1Info[3]);
									mac1.setText(eth1Info[1]);
									gateway1.setText(eth1Info[2]);

								} else if (eth1Info != null
										&& eth1Info.length == 2) {
									mac1.setText(eth1Info[1]);
								}

							}
							

  					
						

						}

					}

				} catch (ReadDataFromSerialPortFailure
						| SerialPortInputStreamCloseFailure e) {
					JOptionPane.showMessageDialog(null, e, "错误",
							JOptionPane.INFORMATION_MESSAGE);
					serialConnect.setText("未连接");
					serialConnect.setSelected(false);
					SerialTool.closePort(serialPort);
					resetGraphic();
					commChoice.add("    ");
					// System.exit(0); // 发生读取错误时显示错误信息后退出系统
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
				break;

			}

		}

	}
	
	  public static String printChart(byte[] bytes){  
		  String str = "";
	        for(int i = 0 ; i < bytes.length ; i++){  
	            String hex = Integer.toHexString(bytes[i] & 0xFF);   
	             if (hex.length() == 1) {   
	               hex = '0' + hex;   
	             }   
	             str = str+hex.toUpperCase() + " ";   
	        }  
	         return str;
	    }  
}
