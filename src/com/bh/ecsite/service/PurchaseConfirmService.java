package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.CartDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.ItemInCartDTO;

public class PurchaseConfirmService {

	/**
	 * ログイン認証を実行する
	 * 例外はControllerに丸投げ
	 * @param loginDomain
	 * @return
	 * @throws AmazonException
	 */
	public ArrayList<ItemInCartDTO> executePurchaseConfirm(String userId) throws AmazonException{

		//DB接続サポートクラス
		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		ArrayList<ItemInCartDTO> returnDTO = null;
		//boolean result = false;
		try {
			connection = man.getConnection();
			CartDAO dao = new CartDAO(connection);
			returnDTO = dao.selectAll(userId);
			man.commit();
			return returnDTO;

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			try {
				man.rollback();
			} catch (SQLException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
				throw new AmazonException(e1);
			}
			throw new AmazonException(e);
		}

		finally {
			try {
				man.closeConnection();

			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				throw new AmazonException(e);
			}
		}

	}

}
