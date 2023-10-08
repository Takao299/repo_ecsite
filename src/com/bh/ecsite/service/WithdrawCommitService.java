package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.CartDAO;
import com.bh.ecsite.dao.PurchaseDAO;
import com.bh.ecsite.dao.PurchaseDetailDAO;
import com.bh.ecsite.dao.UserDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.PurchaseDTO;

public class WithdrawCommitService {

	public void executeWithdraw(String userId) throws AmazonException{

		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		//boolean result = false;
		try {
			connection = man.getConnection();

			PurchaseDAO pdao = new PurchaseDAO(connection);
			ArrayList<PurchaseDTO> pDTOs = pdao.selectByUserId(userId, true); //1ユーザーの全購入履歴
			PurchaseDetailDAO pddao = new PurchaseDetailDAO(connection);
			for(PurchaseDTO pDTO: pDTOs) {
				pddao.delete(pDTO.getPurchaseId()); //購入履歴の1注文の詳細を削除→繰り返して全注文の詳細を削除
			}
			pdao.delete(userId); //1ユーザーの全購入履歴を削除
			CartDAO cdao = new CartDAO(connection);
			cdao.delete(userId);
			UserDAO udao = new UserDAO(connection);
			udao.delete(userId);
			man.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				man.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new AmazonException(e1);
			}
			throw new AmazonException(e);
		}

		finally {
			try {
				man.closeConnection();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new AmazonException(e);
			}
		}

	}

}
