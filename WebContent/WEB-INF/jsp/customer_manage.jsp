<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>顧客管理</title>

<c:choose>
	<c:when test="${bean.employee.managerFlag}">
		<link rel="stylesheet" href="../../css/color_pattern2.css">
	</c:when>
	<c:otherwise>
		<link rel="stylesheet" href="../../css/color_pattern1.css">
	</c:otherwise>
</c:choose>

<link rel="stylesheet" href="../../css/default.css">
<link rel="stylesheet" href="../../css/customer_manage.css">
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
				<a href="customer-manage"><li value="true">顧客管理</li></a>

				<c:if test="${bean.employee.managerFlag}">
					<a href="employee-manage"><li value="false">従業員管理</li></a>
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
			<h2 class="g_c_header">顧客登録</h2>
			<div class="g_c_inner">
				<div class="g_c_i_flex1">
					<div class="w30">
						<h3>電話番号</h3>
						<input type="tel" class="in1" id="tel" maxlength="15"	placeholder="xxx-xxxx-xxxx" autocomplete="off">
					</div>
					<div class="w30">
						<h3>名前</h3>
						<input type="text" class="in1" id="name" maxlength="64" autocomplete="off">
					</div>
					<div class="w30">
						<h3>住所</h3>
						<input type="text" class="in1" id="address" maxlength="192" autocomplete="off">
					</div>

				</div>
				<div class="error_msg" id="error_msg"></div>
				<button type="button" id="reg_button" class="button2 x_c y12">登録</button>
			</div>
		</article>

		<article class="generic_contents x_c">
			<h2 class="g_c_header">顧客一覧</h2>
			<div class="g_c_inner">
				<div class="y_c">
					<input type="text" id="parameter" class="in1 w30 x12" maxlength="64" placeholder="電話番号・名前検索">
					<button type="button" id="select_button" class="button1">検索</button>
				</div>
			</div>
			<div class="g_c_inner">
				<table class="g_c_inner" id="customer_table">
					<tr>
						<th class="w25">電話番号</th>
						<th class="w25">名前</th>
						<th class="w50">住所</th>
					</tr>

					<c:forEach var="obj" items="${bean.customerList}">
						<tr class="customer_tr">
							<td class="d_n"><input type="number" class="customer_id d_n" value="${obj.id}"></td>
							<td class="customer_tel">${obj.tel}</td>
							<td class="customer_name">${obj.name}</td>
							<td class="customer_address">${obj.address}</td>
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
			<small>&copy; G10 v0.8</small>
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

	<div class="p_background d_n" id="customer_popup">
    <article class="generic_contents popup w40">
      <h2 class="g_c_header">顧客</h2>
      <div class="g_c_off">×</div>
      <div class="g_c_inner">
        <div class="g_c_inner g_c_i_flex1">
          <input type="number" class="out_id d_n">
          <div class="w100">
            <h3 class="out_h">名前</h3>
            <p class="out1 out_name"></p>
          </div>
          <div class="w100">
            <h3 class="out_h">電話番号</h3>
            <p class="out1 out_tel"></p>
          </div>
          <div class="w100">
            <h3 class="out_h">住所</h3>
            <p class="out1 out_address"></p>
          </div>
        </div>
        <div class="g_c_inner g_c_i_flex3">
          <button type="button" class="button2 x12 y12">情報変更</button>
          <button type="button" class="button2 x12 y12 b_red del">顧客削除</button>
        </div>
			</div>
    </divticle>
  </div>

	<div class="p_background d_n" id="del_popup">
    <article class="generic_contents popup w30">
      <h2 class="g_c_header">確認</h2>
      <div class="g_c_off">×</div>
      <div class="g_c_inner msg">顧客を削除します</div>
      <div class="g_c_inner error_msg">削除した顧客を元に戻すことはできません</div>
      <div class="g_c_inner g_c_i_flex1">
        <button type="button" class="button2 x_c y12 b_red no">いいえ</button>
        <button type="button" class="button2 x_c y12 yes">はい</button>
      </div>
    </article>
  </div>

	<script src="../../js/customer_manage.js"></script>
</body>
</html>