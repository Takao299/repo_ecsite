package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.CartDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.ItemInCartDTO;

public class RemoveFromCartCommitService {

	public ItemInCartDTO executeRemoveCommit(String userId, int itemId) throws AmazonException{

		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		//boolean result = false;
		try {
			connection = man.getConnection();
			CartDAO dao = new CartDAO(connection);
			ItemInCartDTO returnDTO = dao.selectById(userId,itemId);	//一つのDTOを取得　表示用として一応削除前に
			if(returnDTO != null) {
				dao.deleteOne(userId, itemId);	//１件削除
			}
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

//RemoveFromCartConfirmServiceから引用