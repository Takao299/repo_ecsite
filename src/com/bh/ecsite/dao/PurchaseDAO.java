package com.bh.ecsite.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bh.ecsite.value.PurchaseDTO;
import com.bh.ecsite.value.PurchaseDetailDTO;
import com.bh.ecsite.value.UserDTO;


public class PurchaseDAO extends CommonDAO {



	/**
	 * DAOインスタンス化時にConnectionを引数でもらう
	 * @param connection
	 */
	public PurchaseDAO(Connection connection){
		super(connection);
	}
	//	public PurchaseDAO(Connection connection){
	//		super(connection);
	//	}

	public int selectPurchaseId() throws SQLException{
		PreparedStatement ps = null;

		try {
			String sql = "select nextval('seq_purchase_id') as purchase_id";
			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			int purchaseId = -1;
			if(rs.next()) {
				purchaseId = rs.getInt("purchase_id");
			}
			return purchaseId;

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw e;
		}
		finally {
			try{
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}


	public void insert(UserDTO loginDomain,int purchaseId) throws SQLException{
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO public.purchases (purchase_id, purchased_user, purchased_date, destination, cancel) VALUES (?, ?, ?, ?, false);";
			ps = connection.prepareStatement(sql);


			//SQLのnow()関数 … 文字列として取得する場合は 'YYYY-MM-DD hh:mm:ss' 形式、数値として取得する場合は YYYYMMDDhhmmss 形式で取得します。
			//javaの方でDate型取ってSQLに渡します
			long utildate = new java.util.Date().getTime();
			Date date = new Date(utildate);	//longをDateクラスの引数に入れるとDate型に変換される？

			//1つ目の?はpurchase_id、2つ目の?user_id、3つ目の?はpurchased_date、4つ目の?address
			ps.setInt(1, purchaseId);
			ps.setString(2, loginDomain.getUserId());
			ps.setDate(3, date);
			ps.setString(4, loginDomain.getAddress());
			ps.executeUpdate();	//1件のみ挿入

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw e;
		}
		finally {
			try{
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}






	public PurchaseDTO selectById(int purchaseId, boolean all) throws Exception{
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM purchases WHERE cancel = false and purchase_id=?;";
			if(all == true) {	//Purchase～ではfalse、withdrawではtrue
				sql = "SELECT * FROM purchases WHERE purchase_id=?;"; //上書き
			}
			ps = connection.prepareStatement(sql);
			ps.setInt(1, purchaseId);
			ResultSet rs = ps.executeQuery();
			PurchaseDTO pdDTOs=new PurchaseDTO();
			PurchaseDetailDAO dao = new PurchaseDetailDAO(connection);

			if (rs.next()){
				pdDTOs.setPurchaseId(rs.getInt("purchase_id"));
				UserDAO userDao = new UserDAO(connection);
				UserDTO userDTO = userDao.selectById(rs.getString("purchased_user"));
				pdDTOs.setPurchasedUser(userDTO);
				ArrayList<PurchaseDetailDTO> selectAll = dao.selectAll(purchaseId);
				pdDTOs.setPurchasedItems(selectAll);
				pdDTOs.setPurchasedDate(rs.getDate("purchased_date"));
				pdDTOs.setDestination(rs.getString("destination"));
				pdDTOs.setCancel(rs.getBoolean("cancel"));

			}
			return pdDTOs;


		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw e;
		}
		finally {
			try{
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}

	public ArrayList<PurchaseDTO> selectByUserId(String userId, boolean all)throws Exception{
		PreparedStatement ps = null;
		try {

			String sql = "SELECT * FROM purchases WHERE purchased_user=?  AND cancel = FALSE ORDER BY purchase_id DESC;";
			if(all == true) {	//historyではfalse、withdrawではtrue
				sql = "SELECT * FROM purchases WHERE purchased_user=?;"; //上書き
			}
			ps = connection.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			ArrayList<PurchaseDTO> pdsLists=new ArrayList<PurchaseDTO>();


			while(rs.next()) {

				PurchaseDTO pdDTOs = new PurchaseDTO();
				pdDTOs=this.selectById(rs.getInt("purchase_id"), all);
				pdsLists.add(pdDTOs);



			}
			return pdsLists;


		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw e;
		}
		finally {
			try{
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}


	public void update(int purchaseId) throws SQLException{
		PreparedStatement ps = null;

		try {
			String sql = "UPDATE public.purchases SET cancel = true where purchase_id = ?;";
			ps = connection.prepareStatement(sql);

			ps.setInt(1, purchaseId);
			ps.executeUpdate();

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw e;
		}
		finally {
			try{
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
			}
			catch(SQLException e) {
				throw e;
			}
		}
	}

	public void delete(String userId) throws Exception{

		PreparedStatement ps = null;
			try {
				String sql = "DELETE FROM public.purchases WHERE purchased_user=?;";
				ps = connection.prepareStatement(sql);
				ps.setString(1, userId);
				ps.executeUpdate();

			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				throw e;
			}
			finally {
				try{
					if(ps != null && !ps.isClosed()) {
						ps.close();
					}
				}
				catch(SQLException e) {
					throw e;
				}
			}
	}

}