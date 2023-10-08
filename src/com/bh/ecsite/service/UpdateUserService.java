package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.UserDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.UserDTO;

public class UpdateUserService {

	public UserDTO executeUpdateUser(String userId) throws AmazonException{

		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		//boolean result = false;
		try {
			connection = man.getConnection();
			UserDAO dao = new UserDAO(connection);
			UserDTO returnDTO = dao.selectById(userId);
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
