<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ログイン</title>
  <link rel="stylesheet" href="../css/color_pattern1.css">
  <link rel="stylesheet" href="../css/default.css">
  <link rel="stylesheet" href="../css/login.css">
</head>
<body>
  <header></header>

  <main>
    <article class="generic_contents">
      <h1 id="img_h1">
        <img src="../image/mascot.png" alt="マスコットキャラクター">
      </h1>
      <h1 class="g_c_header">ログイン</h1>

      <form action="login" method="post" class="y12 x_c" id="login_form">
        <div id="w200px" class="x_c">
          <h3>従業員名</h3>
          <input name="name" type="text" class="in1" id="name" value="${bean.name}" maxlength="32" autocomplete="off" autofocus>
          <h3>パスワード</h3>
          <input type="password" class="in1" id="password">
          <input name="password" type="text" class="d_n" id="password_hash">
        </div>
        <div class="error_msg">
          ${bean.msg}
        </div>
        <button id="login_button" type="button" class="button2 x_c y12">ログイン</button>
      </form>
    </article>
  </main>

  <footer>
    <div>
      <small>&copy; G10 v0.8</small>
    </div>
  </footer>

  <script src="../js/login.js"></script>
</body>
</html>