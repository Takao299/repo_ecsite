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
import com.bh.ecsite.service.PurchaseCancelConfirmService;
import com.bh.ecsite.value.PurchaseDTO;
import com.bh.ecsite.value.UserDTO;

/**
 * Servlet implementation class PurchaseCancelConfirmServlet
 */
@WebServlet("/PurchaseCancelConfirmServlet")
public class PurchaseCancelConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseCancelConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession session = request.getSession();

			UserDTO userDTO = (UserDTO) session.getAttribute("loginUserInfo");

			if(userDTO == null) {
				request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);
			}else {

				int purchaseId = Integer.parseInt(request.getParameter("purchaseId"));

				PurchaseCancelConfirmService purchaseCancelConfirmService = new PurchaseCancelConfirmService();
				PurchaseDTO returnDTO = purchaseCancelConfirmService.executeCancelConfirm(purchaseId);
				//purchaseHistory.jspでキャンセル対象にした一つの注文IDに紐付いたPurchaseDTOを取得し、次のjspで表示させる

				request.setAttribute("PurchaseDTO", returnDTO);

				request.getRequestDispatcher(PathConstant.PURCHASECANCELCONFIRM).forward(request, response);
			}

		} catch (AmazonException e) {
			e.printStackTrace();
			request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

//RemoveFromCartConfirmServletから引用
