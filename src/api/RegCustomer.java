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
import model.Customer;

/**
 * Servlet implementation class RegCustomer
 */
@WebServlet("/G10/api/reg-customer")
public class RegCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
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
            
            // 受信データを取得
            BufferedReader buffer = new BufferedReader(request.getReader());
            String requestJson = buffer.readLine();
            Map<String, Object> requestMap = Format.jsonToMap(requestJson);

            // 受信データの存在を確認
            if (requestMap != null) {
                
                // 受信データの要素を確認
                if (requestMap.containsKey("tel") &&
                        requestMap.containsKey("name") &&
                        requestMap.containsKey("address")
                        ) {
                    
                    // インスタンスを作成
                    Customer customer = new Customer();
                    
                    // 顧客を登録
                    switch (customer.reg(
                            (String)requestMap.get("tel"),
                            (String)requestMap.get("name"),
                            (String)requestMap.get("address"))) {
                        case 1:
                            responseMap.replace("result", true);
                            responseMap.replace("msg", "顧客の登録に成功しました");
                            responseMap.put("customer_list", customer.getMapArray());
                            break;
                        case -1:
                            responseMap.replace("msg", "既に使用されている電話番号です");
                            break;
                        default:
                            responseMap.replace("msg", "顧客の登録に失敗しました");
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
            responseMap.replace("msg", "セッションが存在しません");
        }
        
        // データの送信
        PrintWriter out = response.getWriter();
        out.print(Format.mapToJson(responseMap));
    }

}
