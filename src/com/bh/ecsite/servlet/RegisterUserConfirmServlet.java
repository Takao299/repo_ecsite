package com.bh.ecsite.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.common.PathConstant;
import com.bh.ecsite.service.RegisterUserConfirmService;
import com.bh.ecsite.value.UserDTO;

/**
 * Servlet implementation class RegisterUserConfirmServlet
 */
@WebServlet("/RegisterUserConfirmServlet")
public class RegisterUserConfirmServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUserConfirmServlet() {
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

		//入力画面に戻るにしても一応一度入力した名前と住所は引き継ぐ、
		String userId = request.getParameter("id");
		String pass = null;
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		UserDTO userDomain = parse(userId, pass, name, address);

		//まずパスワード入力チェック
		//入力されていないかpassword1&2が不一致であれば入力画面に戻る
		if (!checkParameters(request) ||
				!request.getParameter("password1").equals(request.getParameter("password2"))) {
			request.setAttribute("UserDTO", userDomain);
			request.setAttribute("errorMessage", 1);
			request.getRequestDispatcher(PathConstant.REGISTERUSER).forward(request, response);

		}else {

			try {
				RegisterUserConfirmService service = new RegisterUserConfirmService();
				UserDTO returnDTO = service.execute(userId);

				//何も帰ってこなかったら新規登録　入力したパスワードをセットして確認画面に進む
				if(returnDTO == null) {
					userDomain.setPassword(request.getParameter("password1"));
					request.setAttribute("UserDTO", userDomain);
					request.getRequestDispatcher(PathConstant.REGISTERUSERCONFIRM).forward(request, response);
				//何か帰ってきたら登録済みID 入力画面に戻ってエラー画面２を表示
				}else {
					//その前にIDをnullにしてしまおう　passwordもnull
					userDomain.setUserId(null);
					request.setAttribute("UserDTO", userDomain);
					request.setAttribute("errorMessage", 2);
					request.getRequestDispatcher(PathConstant.REGISTERUSER).forward(request, response);
				}
			} catch (AmazonException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
			}

		}
	}
}
