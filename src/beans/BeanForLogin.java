package beans;

public class BeanForLogin extends AbstractBean {
    // 表示用
    private String name;
    private String msg;

    /**
     * 引数無しコンストラクタ
     */
    public BeanForLogin() {
        super(null);
    }

    // getter・setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
