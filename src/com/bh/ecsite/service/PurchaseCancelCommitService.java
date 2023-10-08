package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.ItemsDAO;
import com.bh.ecsite.dao.PurchaseDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.PurchaseDTO;
import com.bh.ecsite.value.PurchaseDetailDTO;

public class PurchaseCancelCommitService {

	public PurchaseDTO executeCancelCommit(int purchaseId) throws AmazonException{

		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		//boolean result = false;
		try {
			connection = man.getConnection();
			PurchaseDAO dao = new PurchaseDAO(connection);
			PurchaseDTO returnDTO = dao.selectById(purchaseId, false); //この時点でキャンセルしてあるものは拾ってこれない
			dao.update(purchaseId); //キャンセルをtrueにする
			//在庫を戻す
			ItemsDAO idao = new ItemsDAO(connection);
			for(PurchaseDetailDTO val: returnDTO.getPurchasedItems()) {
				//現在の在庫 + 注文した数量
				int stock = val.getItem().getStock() + val.getAmount();
				idao.updateStock(val.getItem().getItemId(), stock); //在庫の更新
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

//PurchaseCancelConfirmServiceから引用
