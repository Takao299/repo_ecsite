package com.bh.ecsite.common;


/**
 * JSPにfowradするときのパスを管理するクラス。
 * ソース上にパスをべた書きすると、別の人間が同じ
 * パスを記述する時に間違えやすい。
 * 定数にしておけばそのような危険性を減らすことが
 * できる。
 *
 * */
public class PathConstant {
	public static final String MAIN = "WEB-INF/jsp/shop/main.jsp";
	public static final String LOGIN = "WEB-INF/jsp/shop/login.jsp";

	public static final String SEARCHRESULT = "WEB-INF/jsp/shop/searchResult.jsp";
	public static final String ITEMDETAIL = "WEB-INF/jsp/shop/itemDetail.jsp";

	public static final String CART = "WEB-INF/jsp/shop/cart.jsp";
	public static final String PURCHASECONFIRM = "WEB-INF/jsp/shop/purchaseConfirm.jsp";
	public static final String PURCHASECOMMIT = "WEB-INF/jsp/shop/purchaseCommit.jsp";

	public static final String REMOVEFROMCARTCONFIRM = "WEB-INF/jsp/shop/removeFromCartConfirm.jsp";
	public static final String REMOVEFROMCARTCOMMIT = "WEB-INF/jsp/shop/removeFromCartCommit.jsp";

	public static final String PURCHASEHISTORY = "WEB-INF/jsp/shop/purchaseHistory.jsp";
	public static final String PURCHASECANCELCONFIRM = "WEB-INF/jsp/shop/purchaseCancelConfirm.jsp";
	public static final String PURCHASECANCELCOMMIT = "WEB-INF/jsp/shop/purchaseCancelCommit.jsp";

	public static final String UPDATEUSER = "WEB-INF/jsp/shop/updateUser.jsp";
	public static final String UPDATEUSERCONFIRM = "WEB-INF/jsp/shop/updateUserConfirm.jsp";
	public static final String UPDATEUSERCOMMIT = "WEB-INF/jsp/shop/updateUserCommit.jsp";

	public static final String WITHDRAWCONFIRM = "WEB-INF/jsp/shop/withdrawConfirm.jsp";
	public static final String WITHDRAWCOMMIT = "WEB-INF/jsp/shop/withdrawCommit.jsp";

	public static final String REGISTERUSER = "WEB-INF/jsp/shop/registerUser.jsp";
	public static final String REGISTERUSERCONFIRM = "WEB-INF/jsp/shop/registerUserConfirm.jsp";
	public static final String REGISTERUSERCOMMIT = "WEB-INF/jsp/shop/registerUserCommit.jsp";

	public static final String ERROR_PAGE = "WEB-INF/error.jsp";


}
