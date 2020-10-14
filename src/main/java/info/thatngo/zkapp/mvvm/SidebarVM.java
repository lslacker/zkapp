package info.thatngo.zkapp.mvvm;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.MatchMedia;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.ScopeParam;

import info.thatngo.zkapp.navigation.Menu;
import info.thatngo.zkapp.navigation.NavDao;
import info.thatngo.zkapp.navigation.NavigationMdel;
import static info.thatngo.zkapp.mvvm.MainVM.NAVIGATION;


public class SidebarVM {
    private NavigationMdel navigationModel;
    private String userName = "Admin";
    private List<Menu> menuList;
    private boolean collapsed = false; //sidebar is collapsed for narrow screen

    @Init
    public void init(@ScopeParam(NAVIGATION)NavigationMdel navModel){
        navigationModel = navModel;
        menuList = NavDao.queryMenu();
    }

    @Command
    public void navigate(@BindingParam("menu")Menu menu){
        String targetPath = menu.getPath();
        if (!targetPath.equals(NavigationMdel.BLANK_ZUL)) {
            navigationModel.setContentUrl(targetPath);
            BindUtils.postNotifyChange(null, null, navigationModel, "contentUrl");
        }
    }

    // medium breakpoint 768 + 190 (sidebar width)
    @MatchMedia("all and (min-width: 958px)")
    @NotifyChange("collapsed")
    public void beWide(){
        collapsed = false;
    }

    @MatchMedia("all and (max-width: 957px)")
    @NotifyChange("collapsed")
    public void beNarrow(){
        collapsed = true;
    }

    public String getUserName() {
        return userName;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public NavigationMdel getNavigationModel() {
        return navigationModel;
    }
}

