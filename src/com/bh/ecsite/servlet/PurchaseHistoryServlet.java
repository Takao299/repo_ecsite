package com.bh.ecsite.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.common.PathConstant;
import com.bh.ecsite.service.PurchaseHistoryService;
import com.bh.ecsite.value.PurchaseDTO;
import com.bh.ecsite.value.UserDTO;

/**
 * Servlet implementation class PurchaseHistoryServlet
 */
@WebServlet("/PurchaseHistoryServlet")
public class PurchaseHistoryServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public PurchaseHistoryServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		try {
			HttpSession session = request.getSession();

			UserDTO userDTO = (UserDTO) session.getAttribute("loginUserInfo"); //←ObjectなのでUserDTOにダウンキャスト


			//ログインユーザーのオブジェクトが入っているかどうか
			if(userDTO == null) {
			//空ならログインしてない
				request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);

			}else {
			//ログインしてる
				String userId = userDTO.getUserId();

				PurchaseHistoryService purchaseHistoryService = new PurchaseHistoryService();
				ArrayList<PurchaseDTO> returnList = purchaseHistoryService.selectByUserId(userId);

				request.setAttribute("PurchaseList", returnList);
				request.getRequestDispatcher(PathConstant.PURCHASEHISTORY).forward(request, response);
			}

		} catch (AmazonException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();

			//致命的エラーが発生した場合、ユーザには『システム管理者に連絡してください』
			//画面を表示する事、それ以外できることはない！
			request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
		}

	}
}

