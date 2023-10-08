package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.UserDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.UserDTO;



public class LoginService {

	/**
	 * ログイン認証を実行する
	 * 例外はControllerに丸投げ
	 * @param loginDomain
	 * @return
	 * @throws AmazonException
	 */
	public UserDTO execute(UserDTO loginDomain) throws AmazonException{

		//DB接続サポートクラス
		ConnectionManager man = new ConnectionManager();
		Connection connection = null;

		boolean result = false;
		try {
			connection = man.getConnection();
			UserDAO dao = new UserDAO(connection);
			UserDTO returnDTO = dao.selectById(loginDomain.getUserId());

			//以下がログイン認証における業務処理。ユーザIDをつかってDBから
			//レコードを取得し、パスワードが画面から入力されたものと等しいか
			//チェックする。
			if(returnDTO != null
					&& returnDTO.getPassword().equals(loginDomain.getPassword()) == true) {
				//ログイン成功。何もしない

			}
			else {
				//ログイン失敗
				returnDTO =  null;
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
