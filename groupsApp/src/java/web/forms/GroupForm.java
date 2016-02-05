package web.forms;

import java.util.Collection;
import logic.Group;

public class GroupForm
{
    private int groupId;
    private String groupName;
    private String curator;
    private String speciality;
    private int curatorId;
    private int studentCount;
    private Collection curators;

    public void initFromGroup(Group group) {
        this.groupId = group.getGroupId();
        this.groupName = group.getGroupName();
        this.curator = group.getCurator();
        this.speciality = group.getSpeciality();
        this.curatorId = group.getCuratorId();
        this.studentCount = group.getStudentCount();
    }
//GroupId
    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
//GroupName
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }     
//Curator
    public String getCurator() {
        return curator;
    }
    public void setCurator(String curator) {
        this.curator = curator;
    }    
//Speciality
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    public String getSpeciality() {
        return speciality;
    }
//CuratorId
    public int getCuratorId() {
        return curatorId;
    }
    public void setCuratorId(int curatorId) {
        this.curatorId = curatorId;
    }
//StudentCount
    public int getStudentCount() {
        return studentCount;
    }
    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }
//Collection curators
    public void setCurators(Collection curators) {
        this.curators = curators;
    }
    public Collection getCurators() {
        return curators;
    }
}
