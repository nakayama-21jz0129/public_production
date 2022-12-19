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

import model.Employee;
import model.Product;

/**
 * Servlet implementation class RegProduct
 */
@WebServlet("/G10/api/reg-product")
public class RegProduct extends HttpServlet {
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
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee.isManagerFlag()) {
                try {
                    BufferedReader buffer = new BufferedReader(request.getReader());
                    String requestJson = buffer.readLine();
                    Map<String, Object> requestMap = mapper.readValue(requestJson, new TypeReference<Map<String, Object>>() {
                    });
                    
                    if (requestMap != null) {
                        if (requestMap.containsKey("product_class_name") &&
                            requestMap.containsKey("name") &&
                            requestMap.containsKey("price") &&
                            requestMap.containsKey("use_flag")
                            ) {
                            Product product = new Product();
                            Map<String, Object> regResult =
                                    product.regProduct(
                                            (String)requestMap.get("product_class_name"),
                                            (String)requestMap.get("name"),
                                            Integer.parseInt((String)requestMap.get("price")),
                                            (boolean)requestMap.get("use_flag"));
                            if ((boolean)regResult.get("result")) {
                                responseMap.replace("result", true);
                                responseMap.put("product_map", product.getProductMap(false));
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
                responseMap.replace("msg", "権限がが存在しません");
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
