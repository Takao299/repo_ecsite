package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.CartDAO;
import com.bh.ecsite.dao.ItemsDAO;
import com.bh.ecsite.dao.PurchaseDAO;
import com.bh.ecsite.dao.PurchaseDetailDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.ItemInCartDTO;
import com.bh.ecsite.value.PurchaseDTO;
import com.bh.ecsite.value.UserDTO;

public class PurchaseCommitService {

	/**
	 * ログイン認証を実行する
	 * 例外はControllerに丸投げ
	 * @param loginDomain
	 * @param loginDomain
	 * @return
	 * @throws AmazonException
	 */
	public PurchaseDTO executePurchase(UserDTO loginDomain) throws AmazonException{

		//DB接続サポートクラス
		ConnectionManager man = new ConnectionManager();
		Connection connection = null;


		try {
			connection = man.getConnection();

			CartDAO cartDAO = new CartDAO(connection);
			ArrayList<ItemInCartDTO> cartDTOs = cartDAO.selectAll(loginDomain.getUserId());

			PurchaseDTO returnDTO = new PurchaseDTO();

			//カートが空なら門前払い
			if(cartDTOs.size() == 0) {
				man.commit();
				return returnDTO = null;
			}

			boolean no_stock = false;

			//コレクションの中の、商品IDごとに異なるItemInCartDTOの数量をそれぞれ比較して
			//一つでも在庫が足りてない商品IDがあったらフラグを立てる
			for(ItemInCartDTO val: cartDTOs) {

				int stock = val.getItem().getStock();
				int amount = val.getAmount();

				//在庫 < 数量
				if(stock < amount) {
					no_stock =true;
				}
			}

			PurchaseDAO pDAO = new PurchaseDAO(connection);
			ItemsDAO iDAO = new ItemsDAO(connection);
			PurchaseDetailDAO pdDAO = new PurchaseDetailDAO(connection);

			//品切れが無ければ購入処理を行う 外のreturnのために外でインスタンス化
			//PurchaseDTO returnDTO = new PurchaseDTO();
			if(no_stock == false) {
				//returnDTO = pDAO.Purchase(loginDomain);	//購入処理

				int purchaseId = pDAO.selectPurchaseId();
				pDAO.insert(loginDomain, purchaseId);
				for(ItemInCartDTO val: cartDTOs) {
					pdDAO.insert(purchaseId, val.getItem().getItemId(), val.getAmount());
					iDAO.updateStock(val.getItem().getItemId(), val.getItem().getStock()-val.getAmount());
				}
				//ArrayList<PurchaseDetailDTO> pDTOs = pDAO.selectAll(purchaseId);
				returnDTO = pDAO.selectById(purchaseId, false);

				cartDAO.delete(loginDomain.getUserId()); //カート内全削除

			}else {
			//品切れであれば
				returnDTO = null;//空で返す
			}

			//ポイント！Serviceの主目的の一つであるトランザクションの
			//処理。SELECTでもCOMMITする
			man.commit();

			return returnDTO;

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			try {
				man.rollback();
			} catch (SQLException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
				throw new AmazonException(e1);
			}
			throw new AmazonException(e);
		}

		finally {
			try {
				man.closeConnection();

			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				throw new AmazonException(e);
			}
		}

	}

}
