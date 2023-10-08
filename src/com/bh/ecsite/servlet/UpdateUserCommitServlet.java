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
import com.bh.ecsite.service.UpdateUserCommitService;
import com.bh.ecsite.value.UserDTO;

/**
 * Servlet implementation class UpdateUserCommitServlet
 */
@WebServlet("/UpdateUserCommitServlet")
public class UpdateUserCommitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserCommitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    UserDTO parse(String userId, String pass, String name, String address) {
    	UserDTO Domain = new UserDTO();
    	Domain.setUserId(userId);
    	Domain.setPassword(pass);
    	Domain.setName(name);
    	Domain.setAddress(address);
    	return Domain;
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

				String userId = userDTO.getUserId();
				String pass = request.getParameter("password");
				String name = request.getParameter("name");
				String address = request.getParameter("address");
				UserDTO userDomain = parse(userId, pass, name, address);

				UpdateUserCommitService service = new UpdateUserCommitService();
				UserDTO returnDTO = service.executeUpdateUserCommit(userDomain);

				request.setAttribute("UserDTO", returnDTO);

				request.getRequestDispatcher(PathConstant.UPDATEUSERCOMMIT).forward(request, response);
			}
		} catch (AmazonException e) {
			e.printStackTrace();
			request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
		}
	}

}
