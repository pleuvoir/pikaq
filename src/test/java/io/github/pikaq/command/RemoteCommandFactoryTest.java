package io.github.pikaq.command;

import io.github.pikaq.PikaqConst;
import io.github.pikaq.remoting.protocol.command.CommandCode;
import io.github.pikaq.remoting.protocol.command.DefaultRemoteCommandFactory;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;

public class RemoteCommandFactoryTest {
	
	public static void main(String[] args) {
		
		DefaultRemoteCommandFactory.INSTANCE.load(PikaqConst.COMMAND_SCANNER_PATH);
		DefaultRemoteCommandFactory.INSTANCE.load(PikaqConst.COMMAND_SCANNER_PATH);
		
		RemoteCommand heartBeatReqCommand = DefaultRemoteCommandFactory.INSTANCE.newRemoteCommand(CommandCode.HEART_BEAT_REQ);
		heartBeatReqCommand.set("currentTimeMillis", System.currentTimeMillis());
		
		System.out.println(heartBeatReqCommand.toJSON());
		
		
		//	DefaultRemoteCommandFactory.INSTANCE.load("io.github.pikaq.command");
		UserReqCommand userReqCommand = new UserReqCommand();
		userReqCommand.setName("pleuvoir");
		
		System.out.println(userReqCommand.toJSON());
		
	}

}
