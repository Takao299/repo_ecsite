package com.bh.ecsite.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bh.ecsite.common.PathConstant;
import com.bh.ecsite.value.UserDTO;

/**
 * Servlet implementation class UpdateUserConfirmServlet
 */
@WebServlet("/UpdateUserConfirmServlet")
public class UpdateUserConfirmServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected boolean checkParameters(HttpServletRequest request) {
    	//空ならfalseというデフォルト実装。チェックロジックが独自に
    	//あるなら（例：数字以外だめ、ある種の記号は入力NG）そのよう
    	//なコードをここに書く。
		if (!checkValue(request.getParameter("password1"))) {
			return false;
		}
		if (!checkValue(request.getParameter("password2"))) {
			return false;
		}
		return true;
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

		HttpSession session = request.getSession();
		UserDTO userDTO = (UserDTO) session.getAttribute("loginUserInfo");

		if(userDTO == null) {
			request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);
		}else {

			//入力画面に戻るにしても一応一度入力した名前と住所は引き継ぐ、
			String userId = userDTO.getUserId();
			String pass = null;
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			UserDTO userDomain = parse(userId, pass, name, address);

			//まずパスワード入力チェック　入力されていないか不一致であれば変更入力画面に戻る
			if (!checkParameters(request) ||
				!request.getParameter("password1").equals(request.getParameter("password2"))) {
				request.setAttribute("UserDTO", userDomain);
				request.setAttribute("errorMessage", 1);
				request.getRequestDispatcher(PathConstant.UPDATEUSER).forward(request, response);

			//pass1とpass2が一致していればpass1をセットして確認画面に進む
			}else {
				userDomain.setPassword(request.getParameter("password1"));
				//UpdateUserConfirmService service = new UpdateUserConfirmService();
				//UserDTO returnDTO = service.executeUpdateUserConfirm(userDomain);
				request.setAttribute("UserDTO", userDomain);
				request.getRequestDispatcher(PathConstant.UPDATEUSERCONFIRM).forward(request, response);
			}
		}
	}
}
