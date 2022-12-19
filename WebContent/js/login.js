/**
 * login.jspファイル独自のjsファイル
 */
(function(){
  "use strict";

  const LoginForm = document.querySelector("#login_form");
  const LoginButton = document.querySelector("#login_button");
  const Name = document.querySelector("#name");
  const Password = document.querySelector("#password");
  const PasswordHash = document.querySelector("#password_hash");
  const ErrorMsg = document.querySelector(".error_msg");

  /**
   * 任意の文字列をsha256でハッシュ化する。
   * @param {*} text
   * @returns
   */
  async function sha256(text) {
    const uint8  = new TextEncoder().encode(text);
    const digest = await crypto.subtle.digest("SHA-256", uint8);
    return Array.from(new Uint8Array(digest)).map(v => v.toString(16).padStart(2,"0")).join("");
  }

  /**
   * ログイン処理。
   */
  function submit() {
    LoginButton.disabled = true;
    if (Name.value != "" && Password.value != "") {
      sha256(Password.value).then(hash => {
        PasswordHash.value = hash;
        LoginForm.submit();
      });
    }
    else {
      ErrorMsg.innerHTML = "従業員名・パスワードを入力してください";
      LoginButton.disabled = false;
    }
  };

  LoginButton.addEventListener("click", () => {
    submit();
  });

  document.addEventListener("keydown", event => {
    if (event.key === "Enter") {
      submit();
    }
  });

})();