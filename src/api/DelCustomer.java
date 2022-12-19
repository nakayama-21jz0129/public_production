package api;

import java.io.BufferedReader;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Customer;

/**
 * Servlet implementation class DelCustomer
 */
@WebServlet("/G10/api/del-customer")
public class DelCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
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
            try {
                BufferedReader buffer = new BufferedReader(request.getReader());
                String requestJson = buffer.readLine();
                Map<String, Object> requestMap = mapper.readValue(requestJson, new TypeReference<Map<String, Object>>() {
                });
                
                if (requestMap != null) {
                    if (requestMap.containsKey("id")) {
                        Customer customer = new Customer();
                        Map<String, Object> regResult = customer.delCustomer(Integer.parseInt((String)requestMap.get("id")));
                        if ((boolean)regResult.get("result")) {
                            responseMap.replace("result", true);
                            responseMap.put("customer_list", customer.getMapCustomerList());
                        }
                        responseMap.replace("msg", (String)regResult.get("msg"));
                    }
                    else {
                        responseMap.replace("msg", "データが不足しています");
                    }
                }
                else {
                    responseMap.replace("msg", "データが存在しません");
                }
            }
            catch (IllegalArgumentException e) {
                responseMap.replace("msg", "データの形式が不適切です");
            }
            catch (ClassCastException e) {
                responseMap.replace("msg", "データの型が不適切です");
            }
        }
        else {
            responseMap.replace("msg", "セッションが存在しません");
        }
        
        String responseJson = mapper.writeValueAsString(responseMap);
        PrintWriter out = response.getWriter();
        out.print(responseJson);
	}

}
