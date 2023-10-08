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
import com.bh.ecsite.service.CartService;
import com.bh.ecsite.value.ItemDTO;
import com.bh.ecsite.value.ItemInCartDTO;
import com.bh.ecsite.value.UserDTO;


@WebServlet("/Cart")
public class CartServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    //ドメイン	データベース用ではなく表示用DTO
    ItemInCartDTO parse(String userId, int itemId, int amount ) {

    	ItemInCartDTO cartDomain = new ItemInCartDTO();
    	cartDomain.setUserId(userId);
    	ItemDTO dto = new ItemDTO();
    	dto.setItemId(itemId);
    	cartDomain.setItem(dto);
    	cartDomain.setAmount(amount);

    	return cartDomain;
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		try {
			HttpSession session = request.getSession();

			//ログインユーザー情報
			UserDTO userDTO = (UserDTO) session.getAttribute("loginUserInfo"); //←ObjectなのでUserDTOにダウンキャスト

			//メイン画面から来たという判断に -1という値を利用していく（サービスで）
			int itemId = -1;
			int amount = -1;

			String requestId = request.getParameter("itemId");
			Object sessionId = session.getAttribute("itemId"); // null判定したいのでまだint型にしない
			if(requestId != null) {
				//前のjspで"itemId"が設定されていたら上書き
					//cart.jspで数量変更した場合はamountはマイナス値になっている
				itemId = Integer.parseInt(requestId);
				amount = Integer.parseInt(request.getParameter("amount"));
			}else if(sessionId != null) {
				//ログイン画面から戻って来たならセッションオブジェクトに格納されている
				itemId = (Integer)sessionId;
				amount = (Integer)session.getAttribute("amount");	//←Integerにダウンキャスト
			}

			//セッションオブジェクトを取り出し終えたらすぐに消す
			session.removeAttribute("itemId");
			session.removeAttribute("amount");



			//ログイン判断
			if(userDTO == null) {
			//空ならログインしてない
				//セッションに、買おうとしてた商品IDと数量を格納し、ログイン画面に飛ばす
				//メイン画面から来ていた場合、それぞれ -1を格納する（意味無し）
				session.setAttribute("itemId", itemId);
				session.setAttribute("amount", amount);
				request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);

			}else{
			//ログインしてる

				String userId = userDTO.getUserId();
				ItemInCartDTO cartDomain = parse(userId, itemId, amount);
				//サービスにDTOを渡す
				CartService cartService = new CartService();
				ArrayList<ItemInCartDTO> returnDTO = cartService.executeCart(cartDomain);

				//カート内のすべての商品の合計金額
				int sum = 0;
				for(ItemInCartDTO val:returnDTO) {
					int itemPrice = val.getItem().getPrice() * val.getAmount();
					sum += itemPrice;
				}
				request.setAttribute("sum", sum);

				request.setAttribute("itemInCartDTO", returnDTO);

				request.getRequestDispatcher(PathConstant.CART).forward(request, response);

			}

		} catch (AmazonException e) {

			e.printStackTrace();
			request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
		}

	}

}