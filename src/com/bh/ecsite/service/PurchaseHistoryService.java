package com.bh.ecsite.service;//購入履歴一覧画面でPurchaseDAOへの橋渡し用Serviceです

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.PurchaseDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.PurchaseDTO;

public class PurchaseHistoryService {

		public ArrayList<PurchaseDTO> selectByUserId(String userId)throws AmazonException{


		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		try {
			connection = man.getConnection();
			PurchaseDAO service = new PurchaseDAO(connection);
			return service.selectByUserId(userId, false);
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


