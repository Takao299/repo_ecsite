package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.CartDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.ItemInCartDTO;

public class RemoveFromCartConfirmService {

	public ItemInCartDTO executeRemoveConfirm(String userId, int itemId) throws AmazonException{

		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		//boolean result = false;
		try {
			connection = man.getConnection();
			CartDAO dao = new CartDAO(connection);
			ItemInCartDTO returnDTO = dao.selectById(userId,itemId);	//CartDAOのメソッド使いまわし	一つのDTOを取得
			man.commit();
			return returnDTO;

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

//CartServiceから引用