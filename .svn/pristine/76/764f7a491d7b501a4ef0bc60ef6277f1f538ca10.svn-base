package com.bh.ecsite.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.dao.CategoryDAO;
import com.bh.ecsite.dao.ItemsDAO;
import com.bh.ecsite.database.ConnectionManager;
import com.bh.ecsite.value.CategoryDTO;
import com.bh.ecsite.value.ItemDTO;

public class SearchResultService {

	public ArrayList<ItemDTO> selectByNameCateId(String itemName,String cateId) throws AmazonException{

		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		try {
			connection = man.getConnection();
			ItemsDAO service = new ItemsDAO(connection);
			return service.selectByNameCateId(itemName, cateId);
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

	public ArrayList<ItemDTO> selectByNameCateIdForPage(String itemName,String cateId, int offset) throws AmazonException{

		ConnectionManager man = new ConnectionManager();
		Connection connection = null;
		try {
			connection = man.getConnection();
			ItemsDAO service = new ItemsDAO(connection);
			return service.selectByNameCateIdForPage(itemName, cateId, offset);
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


	public CategoryDTO selectByCateId(int cateId) throws AmazonException{


		ConnectionManager man = new ConnectionManager();
		Connection connection = null;

		try {
			connection = man.getConnection();
			CategoryDAO service = new CategoryDAO(connection);
			return service.selectByCateId(cateId);
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
