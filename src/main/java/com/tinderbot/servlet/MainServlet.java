package com.tinderbot.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.tinderbot.service.TinderService;
import com.tinderbot.service.impl.TinderServiceImpl;
import com.tinderbot.util.Constants;
import com.tinderbot.util.HttpUtil;
import com.tinderbot.util.HttpUtil.ResponseObject;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(Constants.View.INDEX).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fb_username = request.getParameter("fb_username");
		String fb_password = request.getParameter("fb_password");

		TinderService tinderService = new TinderServiceImpl();
		try {
			String[] fbResult 	= tinderService.getFbTokenAndFbId(fb_username, fb_password);
			String fb_token 	= fbResult[0];
			String fb_id 		= fbResult[1];

			String auth_token 	= tinderService.getToken(fb_token, fb_id);
			System.out.println("auth_token : "+auth_token);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

}
