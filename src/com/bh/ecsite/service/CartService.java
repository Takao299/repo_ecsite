package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.CartDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.ItemDTO;
import com.bh.ecsite.value.ItemInCartDTO;

public class CartService {

	/**
	 * ログイン認証を実行する
	 * 例外はControllerに丸投げ
	 * @param loginDomain
	 * @return
	 * @throws AmazonException
	 */
	public ArrayList<ItemInCartDTO> executeCart(ItemInCartDTO cartDomain) throws AmazonException{

		//DB接続サポートクラス
		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		ArrayList<ItemInCartDTO> returnDTO = null;
		//boolean result = false;
		try {
			connection = man.getConnection();
			CartDAO dao = new CartDAO(connection);
			//引数に入力したidを入れる
			//ArrayList<ItemInCartDTO> returnDTO = dao.selectAll(cartDomain.getUserId());


			if(cartDomain.getItem().getItemId() != -1) {

				ItemDTO item = cartDomain.getItem();
				ItemInCartDTO selectDTO = dao.selectById(cartDomain.getUserId(),item.getItemId());
				//このユーザーのカート内にすでに同じ商品があるかどうか　無ければinsert、あればupdate
					if(selectDTO == null) {
						dao.insert(cartDomain);
					}
					else {
						//入力した数量+元々の数量
						cartDomain.setAmount(cartDomain.getAmount()+selectDTO.getAmount());
						//在庫数-足した後のカート内数量が0以上、つまり在庫が足りてる時のみ更新する
						if( selectDTO.getItem().getStock()-cartDomain.getAmount() >= 0) {
							dao.update(cartDomain);
						}
					}
			}
			returnDTO = dao.selectAll(cartDomain.getUserId());

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
