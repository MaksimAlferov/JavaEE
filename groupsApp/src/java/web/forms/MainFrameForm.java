package web.forms;

import java.util.Collection;

public class MainFrameForm
{
    private int count;
    private int curatorId;
    private Collection curators;
    private Collection groups;

//Count    
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

//CuratorId
    public void setCuratorId(int curatorId) {
        this.curatorId = curatorId;
    }
    public int getCuratorId() {
        return curatorId;
    }

//Collection curators
    public void setCurators(Collection curators) {
        this.curators = curators;
    }
    public Collection getCurators() {
        return curators;
    }

//Collection groups    
    public void setGroups(Collection groups) {
        this.groups = groups;
    }
    public Collection getGroups() {
        return groups;
    }
}
