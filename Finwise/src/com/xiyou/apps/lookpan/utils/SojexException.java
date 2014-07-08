package com.xiyou.apps.lookpan.utils;

public class SojexException {
	public static class NetworkException extends Exception {
		/**
         *
         */
		private static final long serialVersionUID = 1L;

		public NetworkException(String s, Throwable e) {
			super(s, e);
		}
	}

	public static class SdcardException extends Exception {
		/**
         *
         */
		private static final long serialVersionUID = 1L;

		public SdcardException(String s, Throwable e) {
			super(s, e);
		}
	}

	public static class UnknownException extends Exception {
		/**
         *
         */
		private static final long serialVersionUID = 1L;

		public UnknownException(String s, Throwable e) {
			super(s, e);
		}
	}

}
