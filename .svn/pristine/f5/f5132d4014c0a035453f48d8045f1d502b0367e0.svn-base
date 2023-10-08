package com.bh.ecsite.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bh.ecsite.common.AmazonException;
import com.bh.ecsite.common.PathConstant;
import com.bh.ecsite.service.LoginService;
import com.bh.ecsite.value.UserDTO;


/**
 * Servlet implementation class LoginController
 *
 * Loginを表すコントローラ
 */
@WebServlet("/Login")
public class LoginServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;


    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 入力パラメータのチェックは様々な画面でチェックするので、スーパ-クラスで
     * 空だったらNGというcheckValueメソッドがデフォルト実装が用意されている。
     * パラメータチェックはcheckParametersをオーバーライドする事で、パラメータ
     * チェックのやり方にプログラム全体で一貫性を設けることができる。
     * ※抽象メソッドにするのも一つの方法！
     */
    @Override
    protected boolean checkParameters(HttpServletRequest request) {
    	//空ならfalseというデフォルト実装。チェックロジックが独自に
    	//あるなら（例：数字以外だめ、ある種の記号は入力NG）そのよう
    	//なコードをここに書く。
		if (!checkValue(request.getParameter("id"))) {
			return false;
		}

		if (!checkValue(request.getParameter("password"))) {
			return false;
		}

		return true;
	}

    /**
     * ブラウザからの入力パラメータを取り出し、DTOに
     * 変換して返す
     * @param userId
     * @param pass
     * @return
     */
    UserDTO parse(String userId, String pass) {

    	UserDTO loginDomain = new UserDTO();
    	loginDomain.setPassword(pass);
    	loginDomain.setUserId(userId);

    	return loginDomain;

    }


    /**
     * リンククリック、別サイトから移動してきたときに呼ばれ、
     * ログイン画面用のJSPを表示する。
     *
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		//別画面からログインへ遷移する時は、セッションをクリアする
		request.getSession(true).invalidate();
		request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);
	}

	/**
	 * ブラウザのformタグでPOST送信された時に呼ばれる
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		//入力チェック
		if (!checkParameters(request)) {
			request.setAttribute("errorMessage", 1);
			//ログインに失敗したので、ログイン画面に再度戻る
			request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);
			return;
		}

		//ユーザが入力したパラメータを元に、DTOを生成
		//目的：入力パラメータの数が多いと引数の数が多くて大変なので
		//バグの温床になりうるのでオブジェクトとして扱うようにする
		String userId = request.getParameter("id");
		String pass = request.getParameter("password");
		UserDTO loginDomain = parse(userId,pass);



		try {
			LoginService loginService = new LoginService();

			//ログイン処理をサービスに丸投げ。
			//成功すれば非nullのUserDTOが返却される
			UserDTO returnDTO = loginService.execute(loginDomain);
			if(returnDTO == null) {

				request.setAttribute("errorMessage", 1);
				//ログインに失敗したので、ログイン画面に再度戻る
				request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);
			}
			else
			{
				//各リクエストごとのクッキーID（セッションID）でセッション管理
				//が存在し、そのセッションIDに対応するハッシュテーブルを戻り値
				//で返している。
				HttpSession session = request.getSession();

				//セッションにログインユーザ情報を格納する。
				session.setAttribute("loginUserInfo", returnDTO);


				/*
				 * //別Servletでは以下のようにして、セッションが有効かどうかを確認できる
					UserDTO user = (UserDTO)session.getAttribute("loginUserInfo");
					if(user == null) {
						//セッションが切れているか不正なアクセスなので、ログイン画面に戻る
						//ログインに失敗したので、ログイン画面に再度戻る
						//セッション切れの時のメッセージを本当は入れるべきだがここでは手抜き。
						request.getRequestDispatcher(PathConstant.LOGIN).forward(request, response);
					}
				 * */


				//ログイン成功。次の画面に遷移する。次の画面は商品一覧の表示
				//JSPで商品一覧を描画するための元ネタをServiceにとってきてくれ
				//と依頼する。

//				ItemService itemService = new ItemService();
//				ArrayList<ItemDTO> list = itemService.selectAll();

				/*
				selectAllでResultSetを返すとControllerにJDBCコードは入り込んで
				スキルのない者のハードルが上がる。要はデータが取れればいいので
				オブジェクトで扱うようにする。

				以下の２つを比較し、JSPで画面出力用データを得るのにどちらが
				開発者に優しいかを考える

				JDBC使用：rs.getString("item_id");
				JDBC非使用：obj.getItemId();
				*/

				//商品の一覧情報を配列でJSPに引き渡し、JSP側で描画する元ネタにしてもらう。
//				request.setAttribute("itemlist", list);
//				request.getRequestDispatcher(PathConstant.ITEMLIST).forward(request, response);





				//メイン画面か商品詳細画面のどちらから来たのかの判断
				//商品詳細画面の場合はitemIdのセッションオブジェクトが存在し、CartServletに飛ぶ
				if(session.getAttribute("itemId") == null) {
					request.getRequestDispatcher(PathConstant.MAIN).forward(request, response);
				}else{
					request.getRequestDispatcher("/Cart").forward(request, response);
				}


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




/*				//遷移元のURLを取得しようとするもうまく行かず
 * 				//ここのサーブレット名を次のjspで表示するメソッド	//↓取得する文字列
				String url = request.getServletPath();				//	/Login
				String urlA = request.getRequestURI(); 				//  /ecsite/Login
				String urlB = new String(request.getRequestURL());	//	http://localhost:8080/ectest/Login

				request.setAttribute("url", url);	//		${url}<br />${urlA}<br />${urlB}とhtmlに書くと↑で表示される
				request.setAttribute("urlA", urlA);
				request.setAttribute("urlB", urlB);

				if( url.equals("/Cart") ) {
					request.getRequestDispatcher(PathConstant.CART).forward(request, response);
				}else{
					request.getRequestDispatcher(PathConstant.MAIN).forward(request, response);
				}
 *
 */


/*
				//■メイン画面に飛ぶかカート画面に飛ぶかの分岐■
				//流れは
				//itemDetail.jspに埋め込まれたhiddenの値（itemId, amount)を
				//→CartServletで取得しそれをそのままlogin.jspに埋め込まれたhiddenに設定する
				//login.jspに埋め込まれたhiddenの値（itemId, amount)を
				//→LoginServlet(ココ)で取得しcart.jsp…ではなくCartServletに渡す

				int itemId = -1;
				int amount = -1;

				String strid = request.getParameter("itemId");
				if(strid != null) {
					itemId = Integer.parseInt(strid);
					amount = Integer.parseInt(request.getParameter("amount"));
				}

				if( itemId == -1 ) {
					request.getRequestDispatcher(PathConstant.MAIN).forward(request, response);
				}else{
					request.getRequestDispatcher("/Cart").forward(request, response);
				}

				//	セッションに格納しても良いのでは

*/