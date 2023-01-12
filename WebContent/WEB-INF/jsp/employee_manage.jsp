<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>従業員管理</title>

  <c:choose>
    <c:when test="${bean.employee.managerFlag}">
      <link rel="stylesheet" href="../../css/color_pattern2.css">
  	</c:when>
  	<c:otherwise>
      <link rel="stylesheet" href="../../css/color_pattern1.css">
  	</c:otherwise>
  </c:choose>

  <link rel="stylesheet" href="../../css/default.css">
  <link rel="stylesheet" href="../../css/employee_manage.css">
</head>
<body>

  <header>
    <h1>
      <img src="../../image/logo.png" alt="炭火焼鳥山澤">
    </h1>
    <nav>
      <ol>
        <a href="../main"><li value="false">メイン</li></a>
        <a href="order-reg"><li value="false">注文登録</li></a>
        <a href="order-confirm"><li value="false">注文確認</li></a>
        <a href="customer-manage"><li value="false">顧客管理</li></a>

        <c:if test="${bean.employee.managerFlag}">
          <a href="employee-manage"><li value="true">従業員管理</li></a>
          <a href="product-manage"><li value="false">商品管理</li></a>
          <a href="discount-manage"><li value="false">割引設定</li></a>
          <a href="money-received"><li value="false">入金</li></a>
        </c:if>

      </ol>
    </nav>
    <p id="employee">
      <c:choose>
        <c:when test="${bean.employee.managerFlag}">
    	    マネージャ
  		  </c:when>
  		  <c:otherwise>
    	    従業員
  		  </c:otherwise>
      </c:choose>
      ：${bean.employee.name}さん
    </p>
    <a href="../logout" id="logout"><p>ログアウト</p></a>
  </header>

  <main>
    <article class="generic_contents x_c">
      <h2 class="g_c_header">従業員登録</h2>
      <div class="g_c_inner">
        <div class="g_c_i_flex1">
          <div class="w30">
            <h3>名前</h3>
            <input type="text" class="in1" id="name" maxlength="32" autocomplete="off">
          </div>
          <div class="w30">
            <h3>パスワード</h3>
            <input type="text" class="in1" id="password" autocomplete="off">
          </div>
          <div class="w15">
            <h3>利用可能</h3>
            <div class="checkArea">
              <input value="true" type="checkbox" id="check_use" class="d_n check" checked>
              <label for="check_use">
                <div class="box"></div>
              </label>
            </div>
          </div>
          <div class="w15">
            <h3>マネージャ</h3>
            <div class="checkArea">
              <input value="true" type="checkbox" id="check_manager" class="d_n check">
              <label for="check_manager">
                <div class="box"></div>
              </label>
            </div>
          </div>
        </div>
        <div class="error_msg" id="error_msg"></div>
        <button type="button" id="reg_button" class="button2 x_c y12">登録</button>
      </div>
    </article>

    <article class="generic_contents x_c">
      <h2 class="g_c_header">従業員一覧</h2>
      <div class="g_c_inner">
        <div class="y_c text_c">
          <div class="cb_button">
            <input type="checkbox" id="parameter1" checked>
            <label for="parameter1">利用可</label>
          </div>
          <div class="cb_button">
            <input type="checkbox" id="parameter2">
            <label for="parameter2">利用不可</label>
          </div>
          <div class="cb_button">
            <input type="checkbox" id="parameter3" checked>
            <label for="parameter3">従業員</label>
          </div>
          <div class="cb_button">
            <input type="checkbox" id="parameter4" checked>
            <label for="parameter4">マネージャ</label>
          </div>

          <input type="text" id="parameter5" class="in1 w30 x12" maxlength="64" placeholder="名前検索">
          <button type="button" id="select_button" class="button1">検索</button>
        </div>
      </div>
      <div class="g_c_inner">
        <table class="g_c_inner" id="employee_table">
          <tr>
            <th class="w55">名前</th>
            <th class="w25">役職</th>
            <th class="w20">利用可・不可</th>
          </tr>

          <c:forEach var="obj" items="${bean.employeeArray}">
            <tr class="employee_tr">
              <td class="d_n">
                <input type="number" class="employee_id" value="${obj.id}">
              </td>
              <td class="employee_name">${obj.name}</td>
              <c:choose>
                <c:when test="${obj.managerFlag}">
                  <td class="manager_flag t">マネージャ</td>
                </c:when>
                <c:otherwise>
                  <td class="manager_flag f">従業員</td>
                </c:otherwise>
              </c:choose>

              <c:choose>
                <c:when test="${obj.useFlag}">
                  <td class="use_flag t">可</td>
                </c:when>
                <c:otherwise>
                  <td class="use_flag f">不可</td>
                </c:otherwise>
              </c:choose>

            </tr>
          </c:forEach>

        </table>
        <div class="g_c_i_flex3" id="page">
          <button type="button" class="button1 x12 y12 m"><</button>
          <div class="msg w10 y12"></div>
          <button type="button" class="button1 x12 y12 p">></button>
        </div>
      </div>
    </article>
  </main>

  <footer>
    <div>
      <small>${bean.copyright}&ensp;&ensp;${bean.version}</small>
    </div>
  </footer>

  <div class="p_background d_n" id="result_popup">
    <article class="generic_contents popup w30">
      <h2 class="g_c_header">確認</h2>
      <div class="g_c_off">×</div>
      <div class="g_c_inner msg"></div>
      <div class="g_c_inner error_msg"></div>
    </article>
  </div>

  <div class="p_background d_n" id="employee_popup">
    <article class="generic_contents popup w40">
      <h2 class="g_c_header">従業員</h2>
      <div class="g_c_off">×</div>
      <div class="g_c_inner">
        <div class="g_c_inner g_c_i_flex1">
          <input type="number" class="out_id d_n">
          <div class="w100">
            <h3 class="out_h">名前</h3>
            <p class="out1 out_name"></p>
          </div>
          <div class="w48">
            <h3 class="out_h">役割</h3>
            <p class="out1 out_manager"></p>
          </div>
          <div class="w48">
            <h3 class="out_h">利用可・不可</h3>
            <p class="out1 out_use"></p>
          </div>
        </div>
        <div class="g_c_inner g_c_i_flex3">
          <button type="button" class="button2 x12 y12">パスワード変更</button>
          <button type="button" class="button2 x12 y12">設定変更</button>
          <button type="button" class="button2 x12 y12 b_red del">従業員削除</button>
        </div>
      </div>
    </article>
  </div>

  <div class="p_background d_n" id="del_popup">
    <article class="generic_contents popup w30">
      <h2 class="g_c_header">確認</h2>
      <div class="g_c_off">×</div>
      <div class="g_c_inner msg">従業員を削除します</div>
      <div class="g_c_inner error_msg">削除した従業員を元に戻すことはできません</div>
      <div class="g_c_inner g_c_i_flex1">
        <button type="button" class="button2 x_c y12 b_red no">いいえ</button>
        <button type="button" class="button2 x_c y12 yes">はい</button>
      </div>
    </article>
  </div>

  <script src="../../js/employee_manage.js"></script>
</body>
</html>