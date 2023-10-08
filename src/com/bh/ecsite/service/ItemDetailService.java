package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.ItemsDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.ItemDTO;

public class ItemDetailService {
	public ItemDTO selectByItemId(Integer itemId) throws AmazonException{

		ConnectionManager man = new ConnectionManager();
		Connection connection = null;

		try {
			connection = man.getConnection();
			ItemsDAO service = new ItemsDAO(connection);
			return service.selectByItemId(itemId);
		}
		catch(Exception e) {
			try {
				man.rollback();
			} catch (SQLException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
			throw new AmazonException(e);
		}

		finally {
			try {
				man.commit();
				man.closeConnection();
			}
			catch(Exception e) {
				throw new AmazonException(e);
			}
		}

	}
}
