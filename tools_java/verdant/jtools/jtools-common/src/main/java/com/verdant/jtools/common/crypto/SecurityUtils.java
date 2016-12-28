package com.verdant.jtools.common.crypto;

import com.verdant.jtools.common.utils.base.StringUtils2;
import org.apache.commons.codec.digest.DigestUtils;
import org.jasypt.salt.RandomSaltGenerator;
import org.jasypt.util.text.BasicTextEncryptor;


public class SecurityUtils {
	private static final String COMMON_SECURITY_SALT="QWER!@#$()";
	/**
	 * 生成盐值
     */
	public static String generateSalt(int len){
		return StringUtils2.byte2hex(new RandomSaltGenerator().generateSalt(len));
	}

	/**
	 * 加密
	 */
	public static String encrypt(String password) {
		return encrypt(password,COMMON_SECURITY_SALT);
	}

	/**
	 * 解密
	 */
	public static String decrypt(String password) {
		return decrypt(password,COMMON_SECURITY_SALT);
	}

	/**
	 * 加密
	 */
	public static String encrypt(String password,String salt) {
		if (password==null) {
			return null;
		}
		BasicTextEncryptor encrypt = new BasicTextEncryptor();
		encrypt.setPassword(salt);
		String encrypted = encrypt.encrypt(password);
		return encrypted;
	}

	/**
	 * 解密
	 */
	public static String decrypt(String password,String salt) {
		BasicTextEncryptor encrypt = new BasicTextEncryptor();
		encrypt.setPassword(salt);
		String encrypted = encrypt.decrypt(password);
		return encrypted;
	}

	public static String md5(String plan){
		return DigestUtils.md5Hex(plan);
	}
	public static String sha512(String plan){
		return DigestUtils.sha512Hex(plan);
	}

	public static void main(String[] args) {
//		BasicTextEncryptor ste=new BasicTextEncryptor();
//		ste.setPassword("8CBB6062");
//		System.out.println(ste.encrypt("888888"));
//		System.out.println(ste.encrypt("888888"));
//		System.out.println(ste.decrypt("7SHekh4iR3tIXCTz+/4WzQ=="));
//		System.out.println(StringUtils2.byte2hex(new RandomSaltGenerator().generateSalt(2)));
//		System.out.println(StringUtils2.byte2hex(new RandomSaltGenerator().generateSalt(3)));
//		System.out.println(StringUtils2.byte2hex(new RandomSaltGenerator().generateSalt(4)));
//		System.out.println(StringUtils2.byte2hex(new RandomSaltGenerator().generateSalt(5)));
//		System.out.println(StringUtils2.byte2hex(new RandomSaltGenerator().generateSalt(6)));

//		System.out.println( DigestUtils.md5Hex("{js2}"));

//		BasicTextEncryptor encrypt = new BasicTextEncryptor();
//		String encrypted = encrypt.encrypt("123");

	}

}
