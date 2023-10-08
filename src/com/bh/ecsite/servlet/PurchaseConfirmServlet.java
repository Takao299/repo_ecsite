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
import com.bh.ecsite.service.PurchaseConfirmService;
import com.bh.ecsite.value.ItemDTO;
import com.bh.ecsite.value.ItemInCartDTO;
import com.bh.ecsite.value.UserDTO;

/**
 * Servlet implementation class PurchaseConfirmServlet
 */
@WebServlet("/PurchaseConfirm")
public class PurchaseConfirmServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    //ドメイン　　　データベース用ではなく表示用DTO
    ItemInCartDTO parse(String userId, int itemId, int amount ) {

    	ItemInCartDTO cartDomain = new ItemInCartDTO();
    	cartDomain.setUserId(userId);
    	ItemDTO dto = new ItemDTO();
    	dto.setItemId(itemId);
    	cartDomain.setItem(dto);
    	cartDomain.setAmount(amount);

    	return cartDomain;

    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//doPost(request, response);
		//もし無理やり外部から飛んできた場合、カート画面に行く
		request.getRequestDispatcher(PathConstant.CART).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		try {
			HttpSession session = request.getSession();

			UserDTO userDTO = (UserDTO) session.getAttribute("loginUserInfo"); //←ObjectなのでUserDTOにダウンキャスト

			//ログインユーザーのオブジェクトが入っているかどうか
			if(userDTO == null) {
			//空ならログインしてない
				request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);

			}else {
			//ログインしてる

//				String userId = userDTO.getUserId();
//
//				int itemId = -1;
//				int amount = -1;
//
//				String strid = request.getParameter("itemId");
//				if(strid != null) {
//					itemId = Integer.parseInt(strid);
//					amount = Integer.parseInt(request.getParameter("amount"));
//				}
//
//				ItemInCartDTO cartDomain = parse(userId, itemId, amount);
//
//				CartService cartService = new CartService();
//
//				ArrayList<ItemInCartDTO> returnDTO = cartService.executeCart(cartDomain);

				PurchaseConfirmService pcService = new PurchaseConfirmService();
				ArrayList<ItemInCartDTO> returnDTO = pcService.executePurchaseConfirm(userDTO.getUserId());


				//ありえない動作だが念の為、カートが空だった場合、カート画面に戻す
				if(returnDTO.size() == 0) {
					request.getRequestDispatcher(PathConstant.CART).forward(request, response);
				}


				//カート内のすべての商品の合計金額
				int sum = 0;
				for(ItemInCartDTO val:returnDTO) {
					int itemPrice = val.getItem().getPrice() * val.getAmount();
					sum += itemPrice;
				}
				request.setAttribute("sum", sum);

				request.setAttribute("itemInCartDTO", returnDTO);

				//何か一つで在庫切れならカート（jsp）に遷移
				for(ItemInCartDTO val: returnDTO) {
					//在庫 < 数量
					if(val.getItem().getStock() < val.getAmount()) {
						request.getRequestDispatcher(PathConstant.CART).forward(request, response);
					}
				}

				request.getRequestDispatcher(PathConstant.PURCHASECONFIRM).forward(request, response);

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
