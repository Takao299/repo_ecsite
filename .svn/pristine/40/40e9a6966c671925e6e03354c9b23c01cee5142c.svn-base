package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.CartDAO;
import com.bh.ecsite.database.ConnectionManager;

public class RemoveFromCartAllCommitService {

	public void executeRemoveAllCommit(String userId) throws AmazonException{

		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		//boolean result = false;
		try {
			connection = man.getConnection();
			CartDAO dao = new CartDAO(connection);
			dao.delete(userId);	//全削除
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

//RemoveFromCartCommitServiceから引用