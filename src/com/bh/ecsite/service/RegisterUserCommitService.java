package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.UserDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.UserDTO;

public class RegisterUserCommitService {

	public void execute(UserDTO domain) throws AmazonException{

		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		//boolean result = false;
		try {
			connection = man.getConnection();
			UserDAO dao = new UserDAO(connection);
			dao.insert(domain);	//新規会員登録
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
