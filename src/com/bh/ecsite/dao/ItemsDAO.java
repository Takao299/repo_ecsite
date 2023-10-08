package com.bh.ecsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.value.ItemDTO;

public class ItemsDAO extends CommonDAO {

	public ItemsDAO(Connection connection) {
		super(connection);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public ArrayList<ItemDTO> selectByNameCateId(String itemName,String cateId) throws Exception{

		PreparedStatement ps = null;

		//1件以上DBからの検索結果を返却するので、DTOを要素に持つコレクション
		//を戻り値で返却用にインスタンス化
		ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
		try {

			String sql1 = "SELECT item_id, name, manufacturer, color, price, recommended FROM public.items WHERE name LIKE ? ORDER BY item_id;";
			String sql2 = "SELECT item_id, name, manufacturer, color, price, recommended FROM public.items WHERE name LIKE ? AND category_id = ? ORDER BY item_id;";

			if(cateId.equals("0")){
				//SQL実行オブジェクトの取得
				ps = connection.prepareStatement(sql1);
				ps.setString(1, "%" + itemName + "%");

			} else {
				//SQL実行オブジェクトの取得
				ps = connection.prepareStatement(sql2);
				ps.setString(1, "%" + itemName + "%");
				ps.setInt(2, Integer.parseInt(cateId));
			}
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				//結果セットのレコード件数分、DTOに値を詰め込んでコレクション
				//に格納するので、while内でDTOをnewする。
				//whileの外でDTOをnewしても意味がないので注意！
				ItemDTO itemDTO = new ItemDTO();

				itemDTO.setItemId(rs.getInt("item_id"));
				itemDTO.setName(rs.getString("name"));
				itemDTO.setManufacturer(rs.getString("manufacturer"));
				itemDTO.setColor(rs.getString("color"));
				itemDTO.setPrice(rs.getInt("price"));
				itemDTO.setRecommended(rs.getBoolean("recommended"));
				//コレクションにDTOをセット
				list.add(itemDTO);
			}
			return list;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw new AmazonException(e);
		}
	}

	public ArrayList<ItemDTO> selectByNameCateIdForPage(String itemName,String cateId, int offset) throws AmazonException{

		PreparedStatement ps = null;

		//1件以上DBからの検索結果を返却するので、DTOを要素に持つコレクション
		//を戻り値で返却用にインスタンス化
		ArrayList<ItemDTO> list = new ArrayList<ItemDTO>();
		try {

			String sql1 = "SELECT item_id, name, manufacturer, color, price, recommended FROM public.items WHERE name LIKE ? ORDER BY item_id limit 10 offset ?;";
			String sql2 = "SELECT item_id, name, manufacturer, color, price, recommended FROM public.items WHERE name LIKE ? AND category_id = ? ORDER BY item_id limit 10 offset ?;";

			if(cateId.equals("0")){
				//SQL実行オブジェクトの取得
				ps = connection.prepareStatement(sql1);
				ps.setString(1, "%" + itemName + "%");
				ps.setInt(2, offset);

			} else {
				//SQL実行オブジェクトの取得
				ps = connection.prepareStatement(sql2);
				ps.setString(1, "%" + itemName + "%");
				ps.setInt(2, Integer.parseInt(cateId));
				ps.setInt(3, offset);
			}
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				//結果セットのレコード件数分、DTOに値を詰め込んでコレクション
				//に格納するので、while内でDTOをnewする。
				//whileの外でDTOをnewしても意味がないので注意！
				ItemDTO itemDTO = new ItemDTO();

				itemDTO.setItemId(rs.getInt("item_id"));
				itemDTO.setName(rs.getString("name"));
				itemDTO.setManufacturer(rs.getString("manufacturer"));
				itemDTO.setColor(rs.getString("color"));
				itemDTO.setPrice(rs.getInt("price"));
				itemDTO.setRecommended(rs.getBoolean("recommended"));
				//コレクションにDTOをセット
				list.add(itemDTO);
			}
			return list;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw new AmazonException(e);
		}
	}

	public ItemDTO selectByItemId(int itemId) throws Exception{

		PreparedStatement ps = null;
		ItemDTO itemDTO = new ItemDTO();

		try {

			String sql = "SELECT item_id, name, manufacturer, color, price, stock FROM public.items WHERE item_id = ? FOR UPDATE;";

			//SQL実行オブジェクトの取得
			ps = connection.prepareStatement(sql);
			ps.setInt(1, itemId);

			ResultSet rs = ps.executeQuery();


			if(rs.next()) {
				//ifがtrueの時newすることで、呼びだし側はItemDTOがnullかどうか
				//で取得の成功・失敗を判断できる。
				//					itemDTO = new ItemDTO();

				itemDTO.setItemId(rs.getInt("item_id"));
				itemDTO.setName(rs.getString("name"));
				itemDTO.setManufacturer(rs.getString("manufacturer"));
				itemDTO.setColor(rs.getString("color"));
				itemDTO.setPrice(rs.getInt("price"));
				itemDTO.setStock(rs.getInt("stock"));


			}
			return itemDTO;

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw new Exception(e);
		}

	}

	public void updateStock(int itemId,int stock) throws SQLException{
		PreparedStatement ps = null;

		try {
			//item
			String sql="UPDATE items SET stock=? WHERE item_id=?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,stock);
			ps.setInt(2, itemId);

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
