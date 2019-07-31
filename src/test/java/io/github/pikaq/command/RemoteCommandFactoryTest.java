package io.github.pikaq.command;

import com.alibaba.fastjson.JSON;

import io.github.pikaq.remoting.CommandCode;
import io.github.pikaq.remoting.CommandCodeType;
import io.github.pikaq.remoting.RemoteCommand;
import io.github.pikaq.remoting.RemoteCommandFactory;
import io.github.pikaq.remoting.protocol.Packet;

public class RemoteCommandFactoryTest {
	
	public static void main(String[] args) {
		RemoteCommand command = RemoteCommandFactory.select(CommandCode.HEART_BEAT_REQ);
		System.out.println(JSON.toJSONString(command));
		Packet packet = command.getPacket();
		System.out.println(JSON.toJSONString(packet));
		CommandCodeType commandCodeType = command.commandCodeType();
		System.out.println(commandCodeType);
	}

}
