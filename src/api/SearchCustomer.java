package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Customer;

/**
 * Servlet implementation class SearchCustomer
 */
@WebServlet("/G10/api/search-customer")
public class SearchCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	    request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "nocache");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", false);
        responseMap.put("msg", "");

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("employee") != null) {
            String tel = request.getParameter("tel");

            if (tel != null) {
                Customer customer = new Customer();
                customer.setTel(tel);
                if (customer.searchCustomer()) {
                    responseMap.replace("result", true);
                    responseMap.replace("msg", "顧客を確認しました");
                    responseMap.put("customer", customer.getMapCustomer());
                }
                else {
                    responseMap.replace("msg", "顧客を確認できませんでした");
                }
            }
            else {
                responseMap.replace("msg", "データが存在しません");
            }
        }
        else {
            responseMap.replace("msg", "セッションが存在しません");
        }
        
        String responseJson = mapper.writeValueAsString(responseMap);
        PrintWriter out = response.getWriter();
        out.print(responseJson);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
	}

}
