package com.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.entity.Message;
import com.model.entity.User;

/**
 * Servlet implementation class HandleLogout
 */
@WebServlet("/HandleLogout")
public class HandleLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleLogout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(true);
		User userBean=(User)session.getAttribute("userBean");
		Message messageBean=new Message();
		request.setAttribute("MessageBean", messageBean);
		if(userBean!=null){
			session.removeAttribute("userBean");
			session.invalidate();
			messageBean.setBackNews("退出成功");
		}
		else{
			messageBean.setBackNews("未登录，不用退出哦！");
		}
		RequestDispatcher dispatcher=request.getRequestDispatcher("hint.jsp");
		dispatcher.forward(request, response);//转发
	}

}
