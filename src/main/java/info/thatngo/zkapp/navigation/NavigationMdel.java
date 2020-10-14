package info.thatngo.zkapp.navigation;

public class NavigationMdel {
    public static final String DASHBOARD_ECOMMERCE_ZUL = "/hello.zul";
    public static final String DASHBOARD_PROJECT_ZUL = "/hello.zul";
    public static final String BLANK_ZUL = "/hello.zul";

    private String contentUrl = DASHBOARD_ECOMMERCE_ZUL;

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}

