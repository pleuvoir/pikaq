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
		heartBeatReqCommand.setAttachs("currentTimeMillis", System.currentTimeMillis());
		
		System.out.println(heartBeatReqCommand.toJSON());
		
		
		RemoteCommand convert = DefaultRemoteCommandFactory.INSTANCE.convertConvert2Response(heartBeatReqCommand.getCommandCode());
		System.out.println(convert.toJSON());
		
		
		RemoteCommand convertConvert2Response = DefaultRemoteCommandFactory.INSTANCE.convertConvert2Response(heartBeatReqCommand);
		System.out.println(convertConvert2Response.toJSON());
	}

}
