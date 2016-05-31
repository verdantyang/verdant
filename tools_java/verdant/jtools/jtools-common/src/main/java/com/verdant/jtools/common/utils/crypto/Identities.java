package com.verdant.jtools.common.utils.crypto;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
public class Identities {

	public static String valueBeforeMD5 = "";
	public static String valueAfterMD5 = "";
	private static Random myRand;
	private static SecureRandom mySecureRand;

	private static String s_id;

	static {
		mySecureRand = new SecureRandom();
		long secureInitializer = mySecureRand.nextLong();
		myRand = new Random(secureInitializer);
		try {
			s_id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to generate the random GUID
	 */
	public static String getRandomGUID(boolean secure) {
		MessageDigest md5 = null;
		StringBuffer sbValueBeforeMD5 = new StringBuffer(128);

		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}

		try {
			long time = System.currentTimeMillis();
			long rand = 0;

			if (secure) {
				rand = mySecureRand.nextLong();
			} else {
				rand = myRand.nextLong();
			}
			sbValueBeforeMD5.append(s_id);
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(time));
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(rand));

			valueBeforeMD5 = sbValueBeforeMD5.toString();
			md5.update(valueBeforeMD5.getBytes());

			byte[] array = md5.digest();
			StringBuffer sb = new StringBuffer(32);
			for (int j = 0; j < array.length; ++j) {
				int b = array[j] & 0xFF;
				if (b < 0x10)
					sb.append('0');
				sb.append(Integer.toHexString(b));
			}

			valueAfterMD5 = sb.toString();

		} catch (Exception e) {
			System.out.println(e);
		}
		String raw = valueAfterMD5.toUpperCase();
		StringBuffer sb2 = new StringBuffer(64);
		sb2.append(raw.substring(0, 8));
		sb2.append("-");
		sb2.append(raw.substring(8, 12));
		sb2.append("-");
		sb2.append(raw.substring(12, 16));
		sb2.append("-");
		sb2.append(raw.substring(16, 20));
		sb2.append("-");
		sb2.append(raw.substring(20));

		return sb2.toString();
	}
}
