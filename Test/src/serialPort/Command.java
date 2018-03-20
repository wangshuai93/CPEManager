package serialPort;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import gnu.io.SerialPort;
import serialException.SendDataToSerialPortFailure;
import serialException.SerialPortOutputStreamCloseFailure;

public class Command {

	public static void setIP(String eth,String ipaddr, SerialPort serialPort) {
		
		// System.out.println("setIpButton");
		String command = "1A";

		// byte[] order = command.getBytes();
		byte[] order = SerialTool.hexStringToByte(command);
		try {
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);

			command = "ifconfig" + " "+eth+" " + ipaddr;
			System.out.println("setIP****" + command);
			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "fg";
			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);

			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);

		} catch (SendDataToSerialPortFailure
				| SerialPortOutputStreamCloseFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void setGateWay(String eth,String gateway, SerialPort serialPort) {
		
		// System.out.println("setIpButton");
		String command = "1A";

		// byte[] order = command.getBytes();
		byte[] order = SerialTool.hexStringToByte(command);
		try {
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "route add default gw "+gateway +" "+eth; 
			System.out.println("setgateway****" + command);
			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "fg";
			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);

			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);

		} catch (SendDataToSerialPortFailure
				| SerialPortOutputStreamCloseFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void setName(String name, SerialPort serialPort) {
		// System.out.println("setIpButton");
		System.out.println("setName****" + name);
		String command = "1A";
		byte[] order = SerialTool.hexStringToByte(command);

		try {
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "0D";

			order = SerialTool.hexStringToByte(command);

     		command = "sed -i 's/^stbname.*/stbname=" + name
					+ "/' /ffs/stbinfo.dat";
     		
	 
			order = command.getBytes();
			
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "fg";
			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);

			command = "0D";
			order = SerialTool.hexStringToByte(command);
              
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
		} catch (SendDataToSerialPortFailure
				| SerialPortOutputStreamCloseFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void getInfo(SerialPort serialPort) {
		// System.out.println("setIpButton");
		// System.out.println("setName****" +name);
		String command = "1A";
		byte[] order = SerialTool.hexStringToByte(command);

		try {
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "0D";

			order = SerialTool.hexStringToByte(command);

			command = "sed -i 's/^connection.*/connection=" + "1"
					+ "/' ./serialstat.dat";

			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "fg";
			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);

			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
		} catch (SendDataToSerialPortFailure
				| SerialPortOutputStreamCloseFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
//萨斯
	public static void stbSystemRun(SerialPort serialPort) {
		String command = "fg";
		byte[] order = SerialTool.hexStringToByte(command);
		command = "fg";
		order = command.getBytes();
		try {
			SerialTool.sendToPort(serialPort, order);
			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);

		} catch (SendDataToSerialPortFailure
				| SerialPortOutputStreamCloseFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
//ͨ�������豸�ϵ�getinfo�����ȡ�豸��Ϣ
	public static void getInfobyCommand(SerialPort serialPort) {
		// System.out.println("setIpButton");
		// System.out.println("setName****" +name);
		String command = "1A";
		byte[] order = SerialTool.hexStringToByte(command);

		try {
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "0D";

			order = SerialTool.hexStringToByte(command);

			command = "./getinfo";

			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			// command = "fg";
			// order = command.getBytes();
			// SerialTool.sendToPort(serialPort, order);
			// Thread.sleep(100);
			//
			// command = "0D";
			// order = SerialTool.hexStringToByte(command);
			//
			// SerialTool.sendToPort(serialPort, order);
			// Thread.sleep(100);
		} catch (SendDataToSerialPortFailure
				| SerialPortOutputStreamCloseFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sendCommand(SerialPort serialPort,String commandStr){
		
		
		
		String command = commandStr;
		
		byte[] order = SerialTool.hexStringToByte(command);
		

		order = command.getBytes();
		try {
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
		} catch (SendDataToSerialPortFailure
				| SerialPortOutputStreamCloseFailure e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		command = "0D";
		order = SerialTool.hexStringToByte(command);

		try {
			SerialTool.sendToPort(serialPort, order);
		} catch (SendDataToSerialPortFailure
				| SerialPortOutputStreamCloseFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void startPPPOE(SerialPort serialPort ,String usr,String pwd,String DNS) {
		// System.out.println("setIpButton");
		// System.out.println("setName****" +name);
		String command = "1A";
		byte[] order = SerialTool.hexStringToByte(command);

			try {
				SerialTool.sendToPort(serialPort, order);
			} catch (SendDataToSerialPortFailure
					| SerialPortOutputStreamCloseFailure e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		sendCommand(serialPort, "pppoe-setup");
		sendCommand(serialPort, usr);
		sendCommand(serialPort, "eth0");
		sendCommand(serialPort, "no");
		sendCommand(serialPort, DNS);
		sendCommand(serialPort, DNS);
		sendCommand(serialPort, pwd);
		sendCommand(serialPort, pwd);
		sendCommand(serialPort, "0");
		sendCommand(serialPort, "y");
		sendCommand(serialPort, "pppoe-start");
	}
	public static void stopPPPOE(SerialPort serialPort){
		sendCommand(serialPort, "pppoe-stop");
	}
	
	public static void getEthInfo(SerialPort serialPort) {
		// System.out.println("setIpButton");
		// System.out.println("setName****" +name);
		String command = "1A";
		byte[] order = SerialTool.hexStringToByte(command);

		try {
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "0D";

			order = SerialTool.hexStringToByte(command);

			command = "./getEthInfo";

			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			// command = "fg";
			// order = command.getBytes();
			// SerialTool.sendToPort(serialPort, order);
			// Thread.sleep(100);
			//
			// command = "0D";
			// order = SerialTool.hexStringToByte(command);
			//
			// SerialTool.sendToPort(serialPort, order);
			// Thread.sleep(100);
			command = "fg";
			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);

			command = "0D";
			order = SerialTool.hexStringToByte(command);
			Thread.sleep(100);
            
			SerialTool.sendToPort(serialPort, order);
		} catch (SendDataToSerialPortFailure
				| SerialPortOutputStreamCloseFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void upGrade(SerialPort serialPort) {
		// System.out.println("setName****" +name);
		String command = "1A";
		byte[] order = SerialTool.hexStringToByte(command);

		try {
			// HiLOADER upgrade success finished
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(200);

			command = "cp libhiloader.so /lib/";
			order = command.getBytes();
			// order = SerialTool.hexStringToByte(command);
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);

			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);

			command = "sample_loader -t";
			order = command.getBytes();
			// order = SerialTool.hexStringToByte(command);
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);

			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(200);

			command = "2";

			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(200);

			command = "usb_update.bin";
			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);

			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(500);

			command = "reboot";
			order = command.getBytes();
			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);

			command = "0D";
			order = SerialTool.hexStringToByte(command);

			SerialTool.sendToPort(serialPort, order);
			Thread.sleep(100);
		} catch (SendDataToSerialPortFailure
				| SerialPortOutputStreamCloseFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void CopyRecFile(SerialPort serialPort){
		String command = "1A";
		byte[] order = SerialTool.hexStringToByte(command);

			try {
				SerialTool.sendToPort(serialPort, order);
			} catch (SendDataToSerialPortFailure
					| SerialPortOutputStreamCloseFailure e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		sendCommand(serialPort, "cp -rf /pvr /tmp/udisk_a4/");
		sendCommand(serialPort, "sync");
		sendCommand(serialPort, "fg");
	}
	
	public static void SetSerialNumber(SerialPort serialPort,String number){
		String command = "1A";
		byte[] order = SerialTool.hexStringToByte(command);

			try {
				SerialTool.sendToPort(serialPort, order);
			} catch (SendDataToSerialPortFailure
					| SerialPortOutputStreamCloseFailure e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			command = "./mwriter_hdCV200_v1.1 -s "+number; 
		sendCommand(serialPort, command);
		sendCommand(serialPort, "fg");
		
	}

	public static void SetOption60(SerialPort serialPort,int option60_flag){
		String command = "1A";
		byte[] order = SerialTool.hexStringToByte(command);

			try {
				SerialTool.sendToPort(serialPort, order);
			} catch (SendDataToSerialPortFailure
					| SerialPortOutputStreamCloseFailure e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			command = "sed -i 's/^option60.*/option60=" + option60_flag
					+ "/' /ffs/dhcp_config.dat";
			sendCommand(serialPort, command);
			sendCommand(serialPort, "fg");
		
	}
}
