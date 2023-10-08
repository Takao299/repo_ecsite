package com.bh.ecsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.value.CategoryDTO;

public class CategoryDAO extends CommonDAO {

	public CategoryDAO(Connection connection) {
		super(connection);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public CategoryDTO selectByCateId(int cateId) throws AmazonException{

		PreparedStatement ps = null;
		CategoryDTO catDTO = new CategoryDTO();
		catDTO.setCategoryId(cateId);

		try {

			String sql = "SELECT name FROM public.categories WHERE category_id = ?;";

				//SQL実行オブジェクトの取得
				ps = connection.prepareStatement(sql);
				ps.setInt(1, cateId);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					//ifがtrueの時newすることで、呼びだし側はItemDTOがnullかどうか
					//で取得の成功・失敗を判断できる。
//					itemDTO = new ItemDTO();
					catDTO.setName(rs.getString("name"));

				}
				return catDTO;

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw new AmazonException(e);
		}

	}
}

