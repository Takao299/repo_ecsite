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
import com.bh.ecsite.service.ItemDetailService;
import com.bh.ecsite.service.RemoveFromCartConfirmService;
import com.bh.ecsite.value.ItemDTO;
import com.bh.ecsite.value.ItemInCartDTO;
import com.bh.ecsite.value.UserDTO;

/**
 * Servlet implementation class ItemDetailServlet
 */
@WebServlet("/ItemDetailServlet")
public class ItemDetailServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public ItemDetailServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	//response.getWriter().append("Served at: ").append(request.getContextPath());
    	request.setCharacterEncoding("UTF-8");
    	int itemId = Integer.parseInt(request.getParameter("itemId"));

    	try {
			HttpSession session = request.getSession();
			UserDTO userDTO = (UserDTO) session.getAttribute("loginUserInfo");
			//ログインしていたら、カート内の数量を考慮する
			if(userDTO != null) {
	    		RemoveFromCartConfirmService cartService = new RemoveFromCartConfirmService();
	    		ItemInCartDTO itemInCartDTO = cartService.executeRemoveConfirm(userDTO.getUserId(), itemId);
	    		request.setAttribute("itemInCartDTO", itemInCartDTO);
			}

    		ItemDetailService itemDetailService = new ItemDetailService();
    		ItemDTO itemDTO = itemDetailService.selectByItemId(itemId);
    		request.setAttribute("itemDTO", itemDTO);

    		request.getRequestDispatcher(PathConstant.ITEMDETAIL).forward(request, response);
    	} catch(AmazonException e){
    		e.printStackTrace();
    		request.getRequestDispatcher(PathConstant.ERROR_PAGE).forward(request, response);
    	}
    }
}
