package com.bh.ecsite.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.common.PathConstant;
import com.bh.ecsite.service.RemoveFromCartConfirmService;
import com.bh.ecsite.value.ItemInCartDTO;
import com.bh.ecsite.value.UserDTO;

/**
 * Servlet implementation class RemoveFromCartConfirmServlet
 */
@WebServlet("/RemoveFromCartConfirmServlet")
public class RemoveFromCartConfirmServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFromCartConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		try {
			HttpSession session = request.getSession();

			UserDTO userDTO = (UserDTO) session.getAttribute("loginUserInfo");

			if(userDTO == null) {
				request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);
			}else {

				String userId = userDTO.getUserId();
				int itemId = Integer.parseInt(request.getParameter("itemId"));	//	/RemoveFromCartConfirm?itemId=${val.item.itemId}　から取得

				RemoveFromCartConfirmService removeConfirmService = new RemoveFromCartConfirmService();
				ItemInCartDTO returnDTO = removeConfirmService.executeRemoveConfirm(userId, itemId);
				//cart.jspで削除対象にした一つの商品IDに紐付いたItemInCartDTOを取得し、次のjspで表示させる

				request.setAttribute("itemInCartDTO", returnDTO);

				request.getRequestDispatcher(PathConstant.REMOVEFROMCARTCONFIRM).forward(request, response);
			}

		} catch (AmazonException e) {
			e.printStackTrace();
			request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
		}

	}

}

//PurchaseConfirmServletから引用