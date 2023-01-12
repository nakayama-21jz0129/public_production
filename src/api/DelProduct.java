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

import conversion.Format;
import model.Employee;
import model.Product;

/**
 * Servlet implementation class DelProduct
 */
@WebServlet("/G10/api/del-product")
public class DelProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
            
            // セッションから従業員データを取得
            Employee employee = (Employee) session.getAttribute("employee");
            
            // 権限を確認
            if (employee.isManagerFlag()) {
                
                // 受信データを取得
                BufferedReader buffer = new BufferedReader(request.getReader());
                String requestJson = buffer.readLine();
                Map<String, Object> requestMap = Format.jsonToMap(requestJson);
                
                // 受信データの存在を確認
                if (requestMap != null) {
                    
                    // 受信データの要素を確認
                    if (requestMap.containsKey("id")) {
                        
                        // インスタンスを作成
                        Product product = new Product();
                        
                        // 商品を削除
                        if (product.del(Integer.parseInt((String)requestMap.get("id")))) {
                            responseMap.replace("result", true);
                            responseMap.replace("msg", "商品の削除に成功しました");
                            responseMap.put("product_map", product.getMapExt(false));
                        }
                        else {
                            responseMap.replace("msg", "商品の削除に失敗しました");
                        }
                    }
                    else {
                        responseMap.replace("msg", "データが不足しています");
                    }
                }
                else {
                    responseMap.replace("msg", "データが存在しません");
                }
            }
            else {
                responseMap.replace("msg", "権限がが存在しません");
            }
        }
        else {
            responseMap.replace("msg", "セッションが存在しません");
        }
        
        // データの送信
        PrintWriter out = response.getWriter();
        out.print(Format.mapToJson(responseMap));
	}

}
