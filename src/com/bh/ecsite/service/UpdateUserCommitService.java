package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.UserDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.UserDTO;

public class UpdateUserCommitService {

	public UserDTO executeUpdateUserCommit(UserDTO userDomain) throws AmazonException{

		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		//boolean result = false;
		try {
			connection = man.getConnection();
			UserDAO dao = new UserDAO(connection);
			dao.update(userDomain); //更新
			UserDTO returnDTO = dao.selectById(userDomain.getUserId()); //更新後の会員情報を取得
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
