//package io.github.pikaq.protocol.command;
//
//import lombok.*;
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@Builder
//@ToString(callSuper = true)
//@NoArgsConstructor
//@AllArgsConstructor
//public class CarrierCommand<T> extends RemoteBaseCommand {
//
//	private boolean success;
//
//	private String message;
//
//	private T payload;
//
//	@Override
//	public int requestCode() {
//		return RequestCode.CARRIER.getCode();
//	}
//
//
//	public static CarrierCommand<String> buildString(boolean success, String message, String payload) {
//		return CarrierCommand.<String>builder()
//				.success(success)
//				.message(message)
//				.payload(payload)
//				.build();
//	}
//
//	@Override
//	public boolean responsible() {
//		return false;
//	}
//
//}
