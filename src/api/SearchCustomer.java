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

import conversion.Format;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		// TODO Auto-generated method stub

	    // 受信データの形式を指定
        request.setCharacterEncoding("UTF-8");
        
        // 送信データの形式を指定
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "nocache");
        response.setCharacterEncoding("UTF-8");

        // 送信用Mapを作成
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", false);
        responseMap.put("msg", "");

        // セッションを取得
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("employee") != null) {
            
            // 必要なパラメータの存在を確認
            if (request.getParameter("tel") != null) {
                
                // インスタンスを作成
                Customer customer = new Customer();
                
                // 電話番号から顧客を探す
                if (customer.search(request.getParameter("tel"))) {
                    responseMap.replace("result", true);
                    responseMap.replace("msg", "顧客を確認しました");
                    responseMap.put("customer", customer.toMapExt());
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
        
        // データの送信
        PrintWriter out = response.getWriter();
        out.print(Format.mapToJson(responseMap));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
