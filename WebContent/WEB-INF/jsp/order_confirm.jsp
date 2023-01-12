<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>注文確認</title>

<c:choose>
	<c:when test="${bean.employee.managerFlag}">
		<link rel="stylesheet" href="../../css/color_pattern2.css">
	</c:when>
	<c:otherwise>
		<link rel="stylesheet" href="../../css/color_pattern1.css">
	</c:otherwise>
</c:choose>

<link rel="stylesheet" href="../../css/default.css">
<link rel="stylesheet" href="../../css/order_confirm.css">
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
				<a href="order-confirm"><li value="true">注文確認</li></a>
				<a href="customer-manage"><li value="false">顧客管理</li></a>

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
			<h2 class="g_c_header">注文確認</h2>
			<div class="g_c_inner">
				<div class="y_c text_c">
					<div class="cb_button">
						<input type="checkbox" id="parameter1" checked>
						<label for="parameter1">注文済</label>
					</div>
					<div class="cb_button">
						<input type="checkbox" id="parameter2">
						<label for="parameter2">キャンセル済</label>
					</div>
					<div class="cb_button">
						<input type="checkbox" id="parameter3" checked>
						<label for="parameter3">入金済</label>
					</div>

					<input type="number" id="parameter4" class="in1 w15 x12l" placeholder="注文番号検索">
					<input type="text" id="parameter5" class="in1 w30 x12r" maxlength="64" placeholder="顧客電話番号・顧客名前検索">
					<button type="button" id="select_button" class="button1">検索</button>
				</div>
			</div>
			<div class="g_c_inner">
				<table class="g_c_inner" id="order_table">
					<tr>
						<th class="w20">番号</th>
						<th class="w15">ステータス</th>
						<th class="w15">請求金額(円)</th>
						<th class="w25">顧客名前</th>
						<th class="w25">日時</th>
					</tr>

					<c:forEach var="obj" items="${bean.orderArray}">
						<tr class="order_tr">
							<td class="order_id text_c">
								<fmt:formatNumber value="${obj.id}" pattern="000000000000"/>
							</td>
							<td class="order_status text_c">${obj.status}</td>
							<td class="num_r">
								<fmt:formatNumber value="${obj.billingPrice}" pattern="#,###,###,###"/>
							</td>
							<td class="order_customer_name">${obj.customer.name}</td>
							<td class="order_time">${obj.dateTime}</td>
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

	<script src="../../js/order_confirm.js"></script>
</body>
</html>