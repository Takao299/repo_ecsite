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
import com.bh.ecsite.service.RemoveFromCartCommitService;
import com.bh.ecsite.value.ItemInCartDTO;
import com.bh.ecsite.value.UserDTO;

/**
 * Servlet implementation class RemoveFromCartCommitServlet
 */
@WebServlet("/RemoveFromCartCommitServlet")
public class RemoveFromCartCommitServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFromCartCommitServlet() {
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

				RemoveFromCartCommitService removeCommitService = new RemoveFromCartCommitService();
				ItemInCartDTO returnDTO = removeCommitService.executeRemoveCommit(userId, itemId);

				request.setAttribute("itemInCartDTO", returnDTO);

				request.getRequestDispatcher(PathConstant.REMOVEFROMCARTCOMMIT).forward(request, response);
			}

		} catch (AmazonException e) {
			e.printStackTrace();
			request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
		}

	}

}

//RemoveFromCartCommitServletから引用