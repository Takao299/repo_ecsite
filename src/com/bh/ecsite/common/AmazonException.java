package com.bh.ecsite.common;

/**
 * 全ての例外を表すクラス。
 * @author Owner
 *
 */
public class AmazonException extends Exception {

	public AmazonException(Exception e) {
		super(e);
	}

}
