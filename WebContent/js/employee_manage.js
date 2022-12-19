/**
 * employee_manage.jspファイル独自のjsファイル
 */
(function(){
  "use strict";

  const Name = document.querySelector("#name");
  const Password = document.querySelector("#password");
  const UseFlag = document.querySelector("#check_use");
  const ManagerFlag = document.querySelector("#check_manager");
  const ErrorMsg = document.querySelector("#error_msg");
  const RegButton = document.querySelector("#reg_button");
  let current_page = 1;
  let max_page = 1;
  const Parameter1 = document.querySelector("#parameter1");
  const Parameter2 = document.querySelector("#parameter2");
  const Parameter3 = document.querySelector("#parameter3");
  const Parameter4 = document.querySelector("#parameter4");
  const Parameter5 = document.querySelector("#parameter5");
  let word = "";
  const SelectButton = document.querySelector("#select_button");
  const EmployeeTable = document.querySelector("#employee_table");
  let employeeTr = document.querySelectorAll(".employee_tr");
  const Page = document.querySelector("#page");
  const ResultPopup = document.querySelector("#result_popup");
  const EmployeePopup = document.querySelector("#employee_popup");
  const DelPopup = document.querySelector("#del_popup");

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
   * 従業員一覧の表示・非表示を行う。
   */
  function showList() {
    employeeTr.forEach(element => {
      element.classList.add("d_n");
      if (Parameter1.checked && element.querySelector(".use_flag").classList.contains("t")) {
        if (Parameter3.checked && element.querySelector(".manager_flag").classList.contains("f")) {
          element.classList.remove("d_n");
        }
        if (Parameter4.checked && element.querySelector(".manager_flag").classList.contains("t")) {
          element.classList.remove("d_n");
        }
      }
      if (Parameter2.checked && element.querySelector(".use_flag").classList.contains("f")) {
        if (Parameter3.checked && element.querySelector(".manager_flag").classList.contains("f")) {
          element.classList.remove("d_n");
        }
        if (Parameter4.checked && element.querySelector(".manager_flag").classList.contains("t")) {
          element.classList.remove("d_n");
        }
      }
    });
  };

  /**
   * 従業員の名前の検索を行う。
   * 部分一致も検索対象とする。
   */
  function selctStr() {
    employeeTr.forEach(element => {
      if (!element.classList.contains("d_n")) {
        if (!(element.querySelector(".employee_name").innerHTML.includes(word))) {
          element.classList.add("d_n");
        }
      }
    });
  };

  /**
   * 10行を一ページとして表示する。
   */
  function setPage() {
    let page = 0;
    employeeTr.forEach(element => {
      if (!element.classList.contains("d_n")) {
        if (((page + 1) % 2) == 0) {
          element.classList.add("odd");
        }
        else {
          element.classList.remove("odd");
        }

        if ((Math.floor(page / 10) + 1) != current_page) {
          element.classList.add("d_n");
        }
        page++;

      }
    });
    max_page = (Math.floor((page - 1) / 10) + 1);
    Page.querySelector(".msg").innerHTML = current_page + " / " + max_page;
  };

  /**
   * 従業員ポップアップを表示可能にする。
   */
  function clickEmployee() {
    employeeTr.forEach(element => {
      element.addEventListener("click", () => {
        EmployeePopup.querySelector(".out_id").value = element.querySelector(".employee_id").value;
        EmployeePopup.querySelector(".out_name").innerHTML = element.querySelector(".employee_name").innerHTML;
        EmployeePopup.querySelector(".out_manager").innerHTML = element.querySelector(".manager_flag").innerHTML;
        EmployeePopup.querySelector(".out_use").innerHTML = element.querySelector(".use_flag").innerHTML;

        EmployeePopup.classList.remove("d_n");
      });
    });
  };

  /**
   * リストを更新する。
   * @param {*} data
   */
  function listNew(data) {
    employeeTr.forEach(element => {
      element.parentNode.removeChild(element);
    });

    data["employee_list"].forEach(employee => {
      let tr_element = document.createElement("tr");
      tr_element.classList.add("employee_tr");

      let td0_element = document.createElement("td");
      td0_element.classList.add("d_n");
      let input_element = document.createElement("input");
      input_element.classList.add("employee_id");
      input_element.type = "number";
      input_element.value = employee["id"];
      td0_element.appendChild(input_element);
      tr_element.appendChild(td0_element);

      let td1_element = document.createElement("td");
      td1_element.classList.add("employee_name");
      td1_element.innerHTML = employee["name"];
      tr_element.appendChild(td1_element);

      let td2_element = document.createElement("td");
      td2_element.classList.add("manager_flag");
      if (employee["manager_flag"]) {
        td2_element.classList.add("t");
        td2_element.innerHTML = "マネージャ";
      }
      else {
        td2_element.classList.add("f");
        td2_element.innerHTML = "従業員";
      }
      tr_element.appendChild(td2_element);

      let td3_element = document.createElement("td");
      td3_element.classList.add("use_flag");
      if (employee["use_flag"]) {
        td3_element.classList.add("t");
        td3_element.innerHTML = "可";
      }
      else {
        td3_element.classList.add("f");
        td3_element.innerHTML = "不可";
      }
      tr_element.appendChild(td3_element);

      EmployeeTable.appendChild(tr_element);
    });

    employeeTr = document.querySelectorAll(".employee_tr");
    clickEmployee();
    current_page = 1;
    showList();
    if (word != "") {
      selctStr();
    }
    setPage();
  };

  RegButton.addEventListener("click", () => {
    RegButton.disabled = true;
    if (Name.value != "" && Password.value != "") {
      sha256(Password.value).then(hash => {
        let json = {
          "name": Name.value,
          "password": hash,
          "use_flag": UseFlag.checked,
          "manager_flag": ManagerFlag.checked
        };
        fetch("../api/reg-employee", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(json)
        })
        .then(response => response.json())
        .then(data => {
          // console.log(data);
          if (data["result"]) {
            ResultPopup.querySelector(".msg").innerHTML = data["msg"];
            ResultPopup.classList.remove("d_n");

            listNew(data);

            Name.value = "";
            Password.value = "";
            UseFlag.checked = true;
            ManagerFlag.checked = false;
            ErrorMsg.innerHTML = "";

          }
          else {
            ErrorMsg.innerHTML = data["msg"];
          }
          RegButton.disabled = false;
        });
      });
    }
    else {
      ErrorMsg.innerHTML = "従業員名・パスワードを入力してください";
      RegButton.disabled = false;
    }
  });

  Parameter1.addEventListener("click", () => {
    current_page = 1;
    showList();
    if (word != "") {
      selctStr();
    }
    setPage();
  });
  Parameter2.addEventListener("click", () => {
    current_page = 1;
    showList();
    if (word != "") {
      selctStr();
    }
    setPage();
  });
  Parameter3.addEventListener("click", () => {
    current_page = 1;
    showList();
    if (word != "") {
      selctStr();
    }
    setPage();
  });
  Parameter4.addEventListener("click", () => {
    current_page = 1;
    showList();
    if (word != "") {
      selctStr();
    }
    setPage();
  });

  SelectButton.addEventListener("click", () => {
    current_page = 1;
    showList();
    if (Parameter5.value != null) {
      word = Parameter5.value
      selctStr();
    }
    else {
      word = "";
    }
    setPage();
  });

  ResultPopup.querySelector(".g_c_off").addEventListener("click", () => {
    ResultPopup.classList.add("d_n");
  });

  EmployeePopup.querySelector(".g_c_off").addEventListener("click", () => {
    EmployeePopup.classList.add("d_n");
  });

  EmployeePopup.querySelector(".del").addEventListener("click", () => {
    EmployeePopup.classList.add("d_n");

    DelPopup.classList.remove("d_n");
  });

  Page.querySelector(".m").addEventListener("click", () => {
    Page.querySelector(".m").disabled = true;
    if (current_page > 1) {
      current_page--;
      showList();
      if (word != "") {
        selctStr();
      }
      setPage();
    }

    Page.querySelector(".m").disabled = false;
  });
  Page.querySelector(".p").addEventListener("click", () => {
    Page.querySelector(".p").disabled = true;
    if (current_page < max_page) {
      current_page++;
      showList();
      if (word != "") {
        selctStr();
      }
      setPage();
    }

    Page.querySelector(".p").disabled = false;
  });

  DelPopup.querySelector(".g_c_off").addEventListener("click", () => {
    DelPopup.classList.add("d_n");

    EmployeePopup.classList.remove("d_n");
  });
  DelPopup.querySelector(".no").addEventListener("click", () => {
    DelPopup.classList.add("d_n");

    EmployeePopup.classList.remove("d_n");
  });

  DelPopup.querySelector(".yes").addEventListener("click", () => {
    DelPopup.querySelector(".yes").disabled = true;

    let json = {
      "id": EmployeePopup.querySelector(".out_id").value
    };
    fetch("../api/del-employee", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(json)
    })
    .then(response => response.json())
    .then(data => {
      // console.log(data);
      if (data["result"]) {
        ResultPopup.querySelector(".error_msg").innerHTML = "";
        ResultPopup.querySelector(".msg").innerHTML = data["msg"];
        listNew(data);
      }
      else {
        ResultPopup.querySelector(".msg").innerHTML = "";
        ResultPopup.querySelector(".error_msg").innerHTML = data["msg"];
      }
      DelPopup.classList.add("d_n");
      ResultPopup.classList.remove("d_n");
      DelPopup.querySelector(".yes").disabled = false;
    });
  });

  clickEmployee();
  showList();
  setPage();

})();