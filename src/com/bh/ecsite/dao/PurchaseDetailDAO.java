package com.bh.ecsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bh.ecsite.value.ItemDTO;
import com.bh.ecsite.value.PurchaseDetailDTO;

public class PurchaseDetailDAO extends CommonDAO {
	//Connection connection = null;

	public PurchaseDetailDAO(Connection connection) {
		super(connection);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public void insert(int purchaseId,int itemId, int amount) throws SQLException{
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO public.purchase_details (purchase_detail_id, purchase_id, item_id, amount) VALUES ((select nextval('seq_pur_detail_id')), ?, ?, ?);";
			//			ps = connection.prepareStatement(sql);
			ps = connection.prepareStatement(sql);


			ps.setInt(1, purchaseId);	//全部同じ
			ps.setInt(2, itemId);
			ps.setInt(3, amount);
			ps.executeUpdate();	// 1件ずつ挿入

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
	public ArrayList<PurchaseDetailDTO> selectAll(int purchaseId) throws Exception{
		PreparedStatement ps = null;
		ArrayList<PurchaseDetailDTO> list = new ArrayList<PurchaseDetailDTO>();

		try {
			String sql = "SELECT * FROM purchase_details WHERE purchase_id=?;";
			//			ps = connection.prepareStatement(sql);

			ps = connection.prepareStatement(sql);
			ps.setInt(1, purchaseId);
			ResultSet rs = ps.executeQuery();


			while(rs.next()) {
				PurchaseDetailDTO pdDTO = new PurchaseDetailDTO();
				pdDTO.setPurchaseDetailId(rs.getInt("purchase_detail_id"));
				pdDTO.setPurchaseId(purchaseId);
				ItemsDAO itemsDao = new ItemsDAO(connection);
				ItemDTO itemDTO = itemsDao.selectByItemId(rs.getInt("item_id"));
				pdDTO.setItem(itemDTO);
				pdDTO.setAmount(rs.getInt("amount"));

				list.add(pdDTO);
			}

			return list;

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

	public void delete(int purchaseId) throws SQLException{

		PreparedStatement ps = null;
			try {
				String sql = "DELETE FROM purchase_details WHERE purchase_id=?;";
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
}
