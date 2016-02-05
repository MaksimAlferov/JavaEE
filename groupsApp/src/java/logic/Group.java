package logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;


public class Group {
  
    private int groupId;
    private String groupName;
    private String curator;
    private String speciality;
    private int curatorId;
    private int studentCount;

    public Group(ResultSet rs) throws SQLException {
        setGroupId(rs.getInt(1));
        setGroupName(rs.getString(2));
        setCurator(rs.getString(3));
        setSpeciality(rs.getString(4));
        setCuratorId(rs.getInt(7));
        setStudentCount(rs.getInt(8));
    }

    public Group() {
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
    public void setGroupName(String firstName) {
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
    public String getSpeciality() {
        return speciality;
    }
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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
      
}
