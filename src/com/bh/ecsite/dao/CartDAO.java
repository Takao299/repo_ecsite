package com.bh.ecsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bh.ecsite.value.ItemDTO;
import com.bh.ecsite.value.ItemInCartDTO;
import com.bh.ecsite.value.UserDTO;


public class CartDAO extends CommonDAO{


	/**
	 * DAOインスタンス化時にConnectionを引数でもらう
	 * @param connection
	 */
	public CartDAO(Connection connection){
		super(connection);
	}

	//↓とりあえずできるまでのテスト用
	ItemDTO selectById() {

		ItemDTO ItemDTO =new ItemDTO();
		ItemDTO.setItemId(3);
		ItemDTO.setName("ドラえもん");
		ItemDTO.setManufacturer("未来");
		ItemDTO.setColor("青");
		ItemDTO.setPrice(19800);
		return ItemDTO;
	}





//useridをもとに対応する複数件のレコードを取ってくる
	public ArrayList<ItemInCartDTO> selectAll(String userId) throws Exception{

		//初期化
		PreparedStatement ps = null;


		//1件以上DBからの検索結果を返却するので、DTOを要素に持つコレクション
		//を戻り値で返却用にインスタンス化
		ArrayList<ItemInCartDTO> list = new ArrayList<ItemInCartDTO>();
		try {

			String sql= "SELECT * FROM items_in_cart WHERE user_id = ? ORDER BY item_id";

			//"SELECT user_id, item_id, amount, booked_date FROM public.items_in_cart WHERE user_id = ?";

			//SQL実行オブジェクトの取得(insert用)
			ps = connection.prepareStatement(sql);

			//引数cartDomainを使える変数に分解
			//INSERTにVALUE値をセット
			ps.setString(1, userId);

			ResultSet rs = ps.executeQuery();
			//ResultSet rs = ps.executeQuery();





			UserDAO dao=new UserDAO(connection);
			ItemsDAO dao1=new ItemsDAO(connection);
			while(rs.next()) {
				//結果セットのレコード件数分、DTOに値を詰め込んでコレクション
				//に格納するので、while内でDTOをnewする。
				//whileの外でDTOをnewしても意味がないので注意！

				//				ItemDTO dto =new ItemDTO();
				//				dto.setName(rs.getString("name"));
				//				dto.setManufacturer(rs.getString("manufacturer"));
				//				dto.setColor(rs.getString("color"));
				//				dto.setPrice(rs.getInt("price"));

				//↓商品詳細で作るはずのメソッド
				//	ItemDTO itemDTO =dao.selectById(rs.getString("item_id"));


				ItemInCartDTO itemInCartDTO = new ItemInCartDTO();

				//useridセット
				UserDTO userDTO =dao.selectById(userId);
				itemInCartDTO.setUserId(userDTO.getUserId());


				//↓仮のitem_idの取得方法(今は連携済み)
				//本来ならItemDAOのselectById(item_id)でItemDTOを取得し
				//ItemDTO.getItemId()で取得する
				ItemDTO itemDTO=new ItemDTO();
				itemDTO=dao1.selectByItemId(rs.getInt("item_id"));

				//itemDTOをセット
				itemInCartDTO.setItem(itemDTO);

				//amountをセット
				itemInCartDTO.setAmount(rs.getInt("amount"));

				//dateをセット
				itemInCartDTO.setBookedDate(rs.getDate("booked_Date"));


				//コレクションにDTOをセット
				list.add(itemInCartDTO);

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


//userIdとitemIdをもとに対応する1件のレコードを取ってくる
	public ItemInCartDTO selectById(String userId, int itemId) throws Exception{

		PreparedStatement ps = null;
		ItemInCartDTO itemInCartDTO=null;


		try {

			String sql="SELECT * FROM items_in_cart WHERE user_id=? AND item_id=?";

			ps = connection.prepareStatement(sql);


			ps.setString(1,userId);
			ps.setInt(2, itemId);
			ResultSet rs = ps.executeQuery();


			UserDAO dao=new UserDAO(connection);
			ItemsDAO dao1=new ItemsDAO(connection);

			if (rs.next()){
				itemInCartDTO = new ItemInCartDTO();

				//↓商品詳細で作るはずのメソッド
				//	ItemDTO itemDTO =dao.selectById(rs.getString("item_id"));

				//useridセット
				UserDTO userDTO =dao.selectById(userId);
				itemInCartDTO.setUserId(userDTO.getUserId());


				//↓仮のitem_idの取得方法
				//本来ならItemDAOのselectById(item_id)でItemDTOを取得し
				//ItemDTO.getItemId()で取得する
				ItemDTO itemDTO=new ItemDTO();;
				itemDTO=dao1.selectByItemId(rs.getInt("item_id"));


				//itemDTOをセット
				itemInCartDTO.setItem(itemDTO);

				//amountをセット
				itemInCartDTO.setAmount(rs.getInt("amount"));

				//dateをセット
				itemInCartDTO.setBookedDate(rs.getDate("booked_Date"));
			}

			return itemInCartDTO;

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
			catch(Exception e) {
				throw e;
			}
		}
	}



//cartDomainの中の値をItem_in_cartテーブルに追加する
	public void insert(ItemInCartDTO cartDomain ) throws SQLException{
		PreparedStatement ps = null;

		String sql = "INSERT INTO items_in_cart (user_id,item_id,amount,booked_date) VALUES(?,?,?,Now())";

		try {
			//SQL実行オブジェクトの取得(insert用)
			ps = connection.prepareStatement(sql);
			ps.setString(1, cartDomain.getUserId());
			ItemDTO itemDTO= cartDomain.getItem();
			ps.setInt(2, itemDTO.getItemId());
			ps.setInt(3, cartDomain.getAmount());
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



	//cartDomainの中の値でItem_in_cartテーブルを更新する
	public void update(ItemInCartDTO cartDomain) throws SQLException{

		PreparedStatement ps = null;

		try {
			//item
			String sql="UPDATE items_in_cart SET amount=? WHERE user_id=? AND item_id=?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,cartDomain.getAmount());
			ps.setString(2, cartDomain.getUserId());

			ItemDTO itemDTO= cartDomain.getItem();
			ps.setInt(3, itemDTO.getItemId());

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

	//userIdを引数として対応するIdのレコードを全て削除する
	public void delete(String userId) throws SQLException{

		PreparedStatement ps = null;

		try {

			String sql="DELETE FROM items_in_cart WHERE user_id=?";


			ps = connection.prepareStatement(sql);
			ps.setString(1,userId);


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



	public void deleteOne(String userId, int itemId) throws SQLException{

		PreparedStatement ps = null;

		try {

			String sql="DELETE FROM items_in_cart WHERE user_id=? AND item_id=?";


			ps = connection.prepareStatement(sql);
			ps.setString(1,userId);
			ps.setInt(2,itemId);

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
/*
 *
 *
 *


}



class ItemInCartDAO{

 ArrayList<ItemInCart> selectByUserId(String userId){

//商品テーブルと結合しない
 String sql = "SELECT * FORM items_in_cart WHERE user_id = ?";

　//前田さんチームが作ってくれる！
 ItemDAO dao = new ItemDAO();
  while(rs.next()){

     ItemInCartDTO dto = new ItemInCartDTO();

　　//ユーザIDをセット
     dto.setId(rs.getString("user_id"));


　　　//SELECTした結果から、item_idを取り出し、前田さんDAOのメソッドを
　　　//呼び出せば、ItemDTOが得られる！（自分でつくらなくていい）
     ItemDTO item = dao.selectByIdI(rs.getString("item_id"))

　　　//ItemInCartとItemDTOの関連づけ
     dto.setItem(item);

    //以下のように、ItemInCartDT商品名を取り出せる！
　　//dto.item.getName();
　　　dto.amount



  }
 *
 */
