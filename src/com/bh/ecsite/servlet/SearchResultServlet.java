package com.bh.ecsite.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.common.PathConstant;
import com.bh.ecsite.service.SearchResultService;
import com.bh.ecsite.value.CategoryDTO;
import com.bh.ecsite.value.ItemDTO;

/**
 * Servlet implementation class SearchResultServlet
 */
@WebServlet("/SearchResultServlet")
public class SearchResultServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public SearchResultServlet() {
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String strPage = request.getParameter("page");
		int page = Integer.parseInt(strPage);
		int offset = (page-1)*10;
		String itemName = request.getParameter("keyword");
		String cateId = request.getParameter("category");

		try {
			SearchResultService searchResultService = new SearchResultService();
			ArrayList<ItemDTO> listAll = searchResultService.selectByNameCateId(itemName,cateId);   //全件取得
			int maxRaw = listAll.size();
			int pageNumber = (maxRaw +9) /10;

			ArrayList<ItemDTO> list = searchResultService.selectByNameCateIdForPage(itemName,cateId, offset);

			CategoryDTO cateDTO =searchResultService.selectByCateId(Integer.parseInt(cateId));

			//DTOの中身が空の場合の処理を書く可能性あり
			request.setAttribute("itemName", itemName);
			request.setAttribute("cateId", cateDTO);
			request.setAttribute("itemlist", list);
			request.setAttribute("pageNumber", pageNumber);
			request.getRequestDispatcher(PathConstant.SEARCHRESULT).forward(request, response);
		} catch(AmazonException e){
			e.printStackTrace();
			request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String itemName = request.getParameter("keyword");
		String cateId = request.getParameter("category");

		try {
			SearchResultService searchResultService = new SearchResultService();
			ArrayList<ItemDTO> listAll = searchResultService.selectByNameCateId(itemName,cateId);   //全件取得
			int maxRaw = listAll.size();
			int pageNumber = (maxRaw +9) /10;

			int offset = 0;
			ArrayList<ItemDTO> list = searchResultService.selectByNameCateIdForPage(itemName,cateId, offset);

			CategoryDTO cateDTO =searchResultService.selectByCateId(Integer.parseInt(cateId));

			//DTOの中身が空の場合の処理を書く可能性あり
			request.setAttribute("itemName", itemName);
			request.setAttribute("cateId", cateDTO);
			request.setAttribute("itemlist", list);
			request.setAttribute("pageNumber", pageNumber);
			request.getRequestDispatcher(PathConstant.SEARCHRESULT).forward(request, response);
		} catch(AmazonException e){
			e.printStackTrace();
			request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
		}
	}
}
