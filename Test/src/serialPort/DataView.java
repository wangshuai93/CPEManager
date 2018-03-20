package serialPort;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.JOptionPane;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import serialException.*;

/**
 * 监测数据显示类
 * 
 * @author Zhong
 * 
 */
public class DataView extends Frame {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	Client client = null;

	private List<String> commList = null; // 保存可用端口号
	private SerialPort serialPort = null; // 保存串口对象
	private String oldName = null;
	private String oldIP = null;
	private String inputName = null;
	private String inputIpaddr = null;
	private boolean isSetName = false;
	private boolean isSetIP = false;

	private Font font = new Font("微软雅黑", Font.BOLD, 25);

	private Label cpu = new Label("暂无数据", Label.CENTER); // cpu
	private Label process = new Label("暂无数据", Label.CENTER); // 进程
	private Label ram = new Label("暂无数据", Label.CENTER); // ram
	private Label flash = new Label("暂无数据", Label.CENTER); // flash
	private Label stbname = new Label("暂无数据", Label.CENTER); // 设备名称
	private Label ipaddr = new Label("暂无数据", Label.CENTER); // ip地址
	private Label serialnumber = new Label("暂无数据", Label.CENTER);
	private Label softwareversion = new Label("暂无数据", Label.CENTER);
	private TextField nameInput = new TextField();
	private TextField ipInput = new TextField();

	private Choice commChoice = new Choice(); // 串口选择（下拉框）
	private Choice bpsChoice = new Choice(); // 波特率选择

	private Button openSerialButton = new Button("打开串口");
	private Button setIpButton = new Button("设置IP");
	private Button setNameButton = new Button("设置名称");
	private Button upgradeButton = new Button("设备软件升级");
	private Button getInfoButton = new Button("获取设备信息");

	Image offScreen = null; // 重画时的画布

	// 设置window的icon
	Toolkit toolKit = getToolkit();
	Image icon = toolKit.getImage(DataView.class.getResource("icon.png"));

	/**
	 * 类的构造方法
	 * 
	 * @param client
	 */
	public DataView(Client client) {
		this.client = client;
		commList = SerialTool.findPort(); // 程序初始化时就扫描一次有效串口
	}

	/**
	 * 主菜单窗口显示； 添加Label、按钮、下拉条及相关事件监听；
	 */
	public void dataFrame() {
		this.setBounds(client.LOC_X, client.LOC_Y, client.WIDTH, client.HEIGHT);
		this.setTitle("探针设备管理");
		this.setIconImage(icon);
		this.setBackground(Color.white);
		this.setLayout(null);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				if (serialPort != null) {
					// 程序退出时关闭串口释放资源
					SerialTool.closePort(serialPort);
				}
				System.exit(0);
			}

		});

		
		
		
		getInfoButton.setBounds(140, 50, 150, 30);
		getInfoButton.setBackground(Color.LIGHT_GRAY);
		getInfoButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
		getInfoButton.setForeground(Color.darkGray);
		add(getInfoButton);
		
		cpu.setBounds(140, 103, 225, 50);
		cpu.setBackground(Color.black);
		cpu.setFont(font);
		cpu.setForeground(Color.white);
		add(cpu);

		process.setBounds(520, 103, 225, 50);
		process.setBackground(Color.black);
		process.setFont(font);
		process.setForeground(Color.white);
		add(process);

		ram.setBounds(140, 193, 225, 50);
		ram.setBackground(Color.black);
		ram.setFont(font);
		ram.setForeground(Color.white);
		add(ram);

		flash.setBounds(520, 193, 225, 50);
		flash.setBackground(Color.black);
		flash.setFont(font);
		flash.setForeground(Color.white);
		add(flash);

		stbname.setBounds(140, 283, 225, 50);
		stbname.setBackground(Color.black);
		stbname.setFont(font);
		stbname.setForeground(Color.white);
		add(stbname);

		ipaddr.setBounds(520, 283, 225, 50);
		ipaddr.setBackground(Color.black);
		ipaddr.setFont(font);
		ipaddr.setForeground(Color.white);
		add(ipaddr);

		serialnumber.setBounds(140, 550, 180, 30);
		serialnumber.setBackground(Color.black);
		serialnumber.setFont(font);
		serialnumber.setForeground(Color.white);
		serialnumber.setFont(new Font("Dialog", 1, 16));
		add(serialnumber);

		softwareversion.setBounds(425, 550, 80, 30);
		softwareversion.setBackground(Color.black);
		softwareversion.setFont(font);
		softwareversion.setForeground(Color.white);
		softwareversion.setFont(new Font("Dialog", 1, 16));
		add(softwareversion);

		// 添加串口选择选项
		commChoice.setBounds(160, 397, 100, 200);
		// 检查是否有可用串口，有则加入选项中
		if (commList == null || commList.size() < 1) {
			JOptionPane.showMessageDialog(null, "没有搜索到有效串口！", "错误",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			for (String s : commList) {
				commChoice.add(s);
			}
		}
		add(commChoice);

		// 添加波特率选项
		bpsChoice.setBounds(425, 396, 100, 200);
		bpsChoice.add("1200");
		bpsChoice.add("2400");
		bpsChoice.add("4800");
		bpsChoice.add("9600");
		bpsChoice.add("14400");
		bpsChoice.add("19200");
		bpsChoice.add("115200");
		bpsChoice.select(6);

		add(bpsChoice);

		// 添加打开串口按钮
		openSerialButton.setBounds(626, 396, 100, 30);
		openSerialButton.setBackground(Color.LIGHT_GRAY);
		openSerialButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
		openSerialButton.setForeground(Color.darkGray);
		add(openSerialButton);
		// 添加打开串口按钮的事件监听
		nameInput.setBounds(140, 470, 200, 30);
		nameInput.setBackground(Color.white);
		nameInput.setFont(new Font("微软雅黑", Font.CENTER_BASELINE, 16));
		nameInput.setPreferredSize(new Dimension(140, 27));
		add(nameInput);

		ipInput.setBounds(520, 470, 200, 30);
		ipInput.setBackground(Color.white);
		ipInput.setFont(new Font("微软雅黑", Font.CENTER_BASELINE, 16));
		add(ipInput);

		setNameButton.setBounds(45, 470, 80, 30);
		setNameButton.setBackground(Color.lightGray);
		setNameButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
		setNameButton.setForeground(Color.darkGray);
		add(setNameButton);

		setIpButton.setBounds(425, 470, 80, 30);
		setIpButton.setBackground(Color.lightGray);
		setIpButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
		setIpButton.setForeground(Color.darkGray);
		add(setIpButton);

		upgradeButton.setBounds(576, 550, 150, 30);
		upgradeButton.setBackground(Color.lightGray);
		upgradeButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
		upgradeButton.setForeground(Color.darkGray);
		add(upgradeButton);

		setNameButton.addActionListener(new ActionListener() {
			// 更换名称前->oldname -->更换名称后-> newname,newname == inputname;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				System.out.println("setNameButton");
				inputName = nameInput.getText().toString();
				// Command.setName(nameInput.getText().toString(), serialPort);
				Command.setName(inputName, serialPort);

				stbname.setText("读取中...");
				isSetName = true;
				Command.getInfobyCommand(serialPort);

			}
		});

		setIpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("setIpButton");
				inputIpaddr = ipInput.getText().toString();
				Command.setIP("eth0",inputIpaddr, serialPort);

				isSetIP = true;
				ipaddr.setText("读取中...");
				Command.getInfobyCommand(serialPort);
			}
		});

		upgradeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				resetGraphic();
				JOptionPane.showMessageDialog(null, "终端系统正在升级中，请等待2~3分钟！",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				 Command.upGrade(serialPort);

			}
		});

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
							// 监听成功进行提示
							JOptionPane.showMessageDialog(null,
									"连接设备成功！", "提示",
									JOptionPane.INFORMATION_MESSAGE);

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
		getInfoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//Command.getInfo(serialPort);
				if (serialPort == null) {
					JOptionPane.showMessageDialog(null, "串口未连接！获取设备信息失败！",
							"错误", JOptionPane.INFORMATION_MESSAGE);
				}else{
				Command.getInfobyCommand(serialPort);
				}
				
			}
		});

		this.setResizable(false);

		new Thread(new RepaintThread()).start(); // 启动重画线程

	}

	/**
	 * 画出主界面组件元素
	 */
	public void paint(Graphics g) {
		Color c = g.getColor();

		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 18));
		g.drawString("CPU占用 ", 45, 130);

		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 18));
		g.drawString("进程状态", 425, 130);

		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 18));
		g.drawString("内存剩余 ", 45, 220);

		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 18));
		g.drawString("硬盘剩余", 425, 220);

		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 18));
		g.drawString("终端名称 ", 45, 310);

		g.setColor(Color.black);
		g.setFont(new Font("微软雅黑", Font.BOLD, 18));
		g.drawString("终端IP", 425, 310);

		g.setColor(Color.gray);
		g.setFont(new Font("微软雅黑", Font.BOLD, 18));
		g.drawString(" 串口选择： ", 45, 410);

		g.setColor(Color.gray);
		g.setFont(new Font("微软雅黑", Font.BOLD, 18));
		g.drawString(" 波特率： ", 326, 410);

		g.setColor(Color.gray);
		g.setFont(new Font("微软雅黑", Font.BOLD, 18));
		g.drawString(" 序列号： ", 45, 565);

		g.setColor(Color.gray);
		g.setFont(new Font("微软雅黑", Font.BOLD, 18));
		g.drawString(" 软件版本：", 326, 565);

	}

	public void resetGraphic() {
		cpu.setText("暂无数据");
		process.setText("暂无数据");
		ram.setText("暂无数据");
		flash.setText("暂无数据");
		stbname.setText("暂无数据");
		ipaddr.setText("暂无数据");
		serialnumber.setText("暂无数据");
		softwareversion.setText("暂无数据");

	}

	/**
	 * 双缓冲方式重画界面各元素组件
	 */
	public void update(Graphics g) {
		if (offScreen == null)
			offScreen = this.createImage(Client.WIDTH, Client.HEIGHT);
		Graphics gOffScreen = offScreen.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.white);
		gOffScreen.fillRect(0, 0, Client.WIDTH, Client.HEIGHT); // 重画背景画布
		this.paint(gOffScreen); // 重画界面元素
		gOffScreen.setColor(c);
		g.drawImage(offScreen, 0, 0, null); // 将新画好的画布“贴”在原画布上
	}

	public String ParseData(String data) {
		int start = 0;
		int end = 0;
		for (int i = 0; i < data.length(); i++) {
			// System.out.println("assiic:"+data.charAt(i));
			if (data.charAt(i) == '~') {
				start = i;

			}
			if (data.charAt(i) == '^') {
				end = i;

			}
		}
		String stbStatusInfo = data.substring(start, end);
		return stbStatusInfo;

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
							if (dataOriginal.indexOf("bootsuccess")!=-1) {
								JOptionPane.showMessageDialog(null,
										"设备系统启动成功！", "提示",
										JOptionPane.INFORMATION_MESSAGE);

								
								
							}
							System.out.println("[dataOriginal]" + dataOriginal);
							// System.out.println("ws*******" + data);
							String dataValid = ""; // 有效数据（用来保存原始数据字符串去除最开头*号以后的字符串）
							String[] elements = null; // 用来保存按空格拆分原始字符串后得到的字符串数组
														// 解析数据

							// if (dataOriginal.charAt(0) == '~') { //
							// 当数据的第一个字符是*号时表示数据接收完成，开始解析
							dataValid = DataParse.getValidData(dataOriginal);
							System.out.println(dataValid);
							if (dataValid != null) {
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
										if (elements.length > 9) {
											ipaddr.setText(elements[9]);
											// oldIP = elements[9];
											if (isSetIP) {

												if (elements[9]
														.equals(inputIpaddr)) {
													JOptionPane
															.showMessageDialog(
																	null,
																	"成功把终端IP设置为:"
																			+ inputIpaddr
																			+ "！",
																	"提示",
																	JOptionPane.INFORMATION_MESSAGE);
													isSetIP = false;

												} else {
													JOptionPane
															.showMessageDialog(
																	null,
																	"IP设置失败！请重新设置!"
																			+ inputIpaddr,
																	"提示",
																	JOptionPane.INFORMATION_MESSAGE);
													isSetIP = false;
												}

											}

										} else {
											if (isSetIP) {
												JOptionPane
														.showMessageDialog(
																null,
																"IP设置失败！请重新设置！"
																		+ inputIpaddr,
																"提示",
																JOptionPane.INFORMATION_MESSAGE);
												isSetIP = false;

											}

											ipaddr.setText("未获取到IP");

										}

										if (isSetName) {
											if (elements[8].equals(inputName)) {
												JOptionPane
														.showMessageDialog(
																null,
																"成功把终端名称设置为:"
																		+ inputName
																		+ "！",
																"提示",
																JOptionPane.INFORMATION_MESSAGE);

												isSetName = false;

											} else {

												JOptionPane
														.showMessageDialog(
																null,
																"终端名称设置失败！请重新设置！"
																		+ inputName,
																"提示",
																JOptionPane.INFORMATION_MESSAGE);
												isSetName = false;

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

						}

					}

				} catch (ReadDataFromSerialPortFailure
						| SerialPortInputStreamCloseFailure e) {
					JOptionPane.showMessageDialog(null, e, "错误",
							JOptionPane.INFORMATION_MESSAGE);
					System.exit(0); // 发生读取错误时显示错误信息后退出系统
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			}

		}

	}

}
