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
import com.bh.ecsite.service.WithdrawCommitService;
import com.bh.ecsite.value.UserDTO;

/**
 * Servlet implementation class WithdrawCommitServlet
 */
@WebServlet("/WithdrawCommitServlet")
public class WithdrawCommitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WithdrawCommitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("UTF-8");

		try {
			HttpSession session = request.getSession();
			UserDTO userDTO = (UserDTO) session.getAttribute("loginUserInfo");

			if(userDTO == null) {
				request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);
			}else {
				session.invalidate(); //セッションオブジェクトの破棄　一応削除処理の前に行う

				WithdrawCommitService service = new WithdrawCommitService();
				service.executeWithdraw(userDTO.getUserId());
				request.setAttribute("name", userDTO.getName());
				request.getRequestDispatcher(PathConstant.WITHDRAWCOMMIT).forward(request, response);
			}
		} catch (AmazonException e) {
			e.printStackTrace();
			request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
		}
	}

}
