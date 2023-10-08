package com.bh.ecsite.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.common.PathConstant;
import com.bh.ecsite.service.PurchaseCommitService;
import com.bh.ecsite.service.PurchaseConfirmService;
import com.bh.ecsite.value.ItemInCartDTO;
import com.bh.ecsite.value.PurchaseDTO;
import com.bh.ecsite.value.PurchaseDetailDTO;
import com.bh.ecsite.value.UserDTO;


@WebServlet("/PurchaseCommit")
public class PurchaseCommitServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;

    public PurchaseCommitServlet() {
        super();
    }

    @Override
    protected boolean checkParameters(HttpServletRequest request) {
    	//空ならfalseというデフォルト実装。チェックロジックが独自に
    	//あるなら（例：数字以外だめ、ある種の記号は入力NG）そのよう
    	//なコードをここに書く。
		if (!checkValue(request.getParameter("address"))) {
			return false;
		}

		return true;
	}

    //購入者と配送先を入れるドメイン
    UserDTO parse(String userId, String address) {

    	UserDTO loginDomain = new UserDTO();
    	loginDomain.setUserId(userId);
    	loginDomain.setAddress(address);

    	return loginDomain;
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("UTF-8");	//入力された日本語に対応

		try {
			HttpSession session = request.getSession();

			UserDTO userDTO = (UserDTO) session.getAttribute("loginUserInfo");
			//ログインユーザーのオブジェクトが入っているかどうか
			if(userDTO == null) {
			//空ならログインしてない
				request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);

			}else {
			//ログインしてる

				String userId = userDTO.getUserId();
				String address = null;		//userDTO.getAddress(); まず自宅住所を入れてその後自由指定なら上書き…これは良くない
				//配送先がご自宅指定なら
				if( request.getParameter("destination").equals("registered") ){
					//address = userDTO.getAddress();
				//配送先が自由入力での指定なら
				//if( request.getParameter("destination").equals("another") ){
				}else {
					address = request.getParameter("address");
					//自由指定にしたにも関わらず未入力だった場合
					//入力チェック
					if (!checkParameters(request)) {

						//たかだかエラーメッセージ一つ表示するためだけに
						//わざわざサービスを作ることに　以下の内容だけでサーブレットの動作一つまるごと分はあります
						PurchaseConfirmService pcService = new PurchaseConfirmService();
						ArrayList<ItemInCartDTO> returnDTO = pcService.executePurchaseConfirm(userId);
						//カート内のすべての商品の合計金額
						int sum = 0;
						for(ItemInCartDTO val:returnDTO) {
							int itemPrice = val.getItem().getPrice() * val.getAmount();
							sum += itemPrice;
						}
						request.setAttribute("sum", sum);
						request.setAttribute("itemInCartDTO", returnDTO);
						request.setAttribute("errorMessage", 1);
						request.getRequestDispatcher(PathConstant.PURCHASECONFIRM).forward(request, response);

						return;
					}
				}

				//サービスに投げ渡すDTOを作成
				UserDTO loginDomain = parse(userId, address);


				//注文処理（成功or失敗）
				PurchaseCommitService purchaseCommitService = new PurchaseCommitService();
				//purchaseCommitService.executePurchase(loginDomain);
				PurchaseDTO returnDTO = purchaseCommitService.executePurchase(loginDomain);
				//一応注文DTOが戻り値で受け止める

				request.setAttribute("purchaseDTO", returnDTO);

				//何か一つで在庫切れならカート（サーブレット）に戻る //更にカートが空でも
				if(returnDTO == null) {
					request.getRequestDispatcher("/Cart").forward(request, response);
				}



				//カート内のすべての商品の合計金額
				int sum = 0;
				if(returnDTO.getPurchasedUser() != null) {
					for(PurchaseDetailDTO val:returnDTO.getPurchasedItems()) {
						int itemPrice = val.getItem().getPrice() * val.getAmount();
						sum += itemPrice;
					}
				}
				request.setAttribute("sum", sum);



				request.getRequestDispatcher(PathConstant.PURCHASECOMMIT).forward(request, response);

			}

		} catch (AmazonException e) {
			e.printStackTrace();

			request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
		}

	}


}