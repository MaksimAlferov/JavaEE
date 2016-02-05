package logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ManagementSystem
{
    private static Connection con;
    private static ManagementSystem instance;
    private static DataSource dataSource;

    private ManagementSystem() {
    }

    public static synchronized ManagementSystem getInstance() {
        if (instance == null) {
            try {
                instance = new ManagementSystem();
                Context ctx = new InitialContext();
                instance.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/StudentsDS");
                con = dataSource.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public List getCurators() throws SQLException {
        List curators = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT curator_id, curator_nm FROM curators");
        while (rs.next()) {
            Curator cur = new Curator();
            cur.setCuratorId(rs.getInt(1));
            cur.setNameCurator(rs.getString(2));
            curators.add(cur);
        }
        rs.close();
        stmt.close();
        return curators;
    }

    public Collection getAllGroups() throws SQLException {
        Collection groups = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT group_id, groupName, curator, speciality, "
                + "curator_id, student_count FROM groups ORDER BY groupName");
        while (rs.next()) {
            Group group = new Group(rs);
            groups.add(group);
        }
        rs.close();
        stmt.close();
        return groups;
    }

    public Collection getGroupsFromCurator(Curator curator, int count) throws SQLException {
        Collection groups = new ArrayList();
        PreparedStatement stmt = con.prepareStatement("SELECT group_id, groupName, curator, speciality, "
                + "curator_id, student_count FROM groups "
                + "WHERE curator_id =  ? AND  student_count =  ? "
                + "ORDER BY groupName");
        stmt.setInt(1, curator.getCuratorId());
        stmt.setInt(2, count);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Group group = new Group(rs);
            groups.add(group);
        }
        rs.close();
        stmt.close();
        return groups;
    }

    public Group getGroupById(int groupId) throws SQLException {
        Group group = null;
        PreparedStatement stmt = con.prepareStatement("SELECT group_id, groupName, curator, speciality, "
                + "curator_id, student_count FROM groups WHERE group_id = ?");
        stmt.setInt(1, groupId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            group = new Group(rs);
        }
        rs.close();
        stmt.close();
        return group;
    }

    public void moveGroupsToCurator(Curator oldCurator, int oldCount, Curator newCurator, int newCount) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE groups SET curator_id = ?, student_count = ? "
                + "WHERE curator_id =  ? AND  student_count = ?");
        stmt.setInt(1, newCurator.getCuratorId());
        stmt.setInt(2, newCount);
        stmt.setInt(3, oldCurator.getCuratorId());
        stmt.setInt(4, oldCount);
        stmt.execute();
    }

    public void removeGroupsFromCurator(Curator curator, int count) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM groups WHERE curator_id = ? AND student_count = ?");
        stmt.setInt(1, curator.getCuratorId());
        stmt.setInt(2, count);
        stmt.execute();
    }

    public void insertGroup(Group group) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO groups "
                + "(groupName, curator, speciality, curator_id, student_count)"
                + "VALUES( ?,  ?,  ?,  ?,  ?)");
        stmt.setString(1, group.getGroupName());
        stmt.setString(2, group.getCurator());
        stmt.setString(3, group.getSpeciality());
        stmt.setInt(4, group.getCuratorId());
        stmt.setInt(5, group.getStudentCount());
        stmt.execute();
    }

    public void updateGroup(Group group) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE groups "
                + "SET groupName=?, curator=?, speciality=?, curator_id=?, student_count=?"
                + "WHERE group_id=?");
        stmt.setString(1, group.getGroupName());
        stmt.setString(2, group.getCurator());
        stmt.setString(3, group.getSpeciality());
        stmt.setInt(4, group.getCuratorId());
        stmt.setInt(5, group.getStudentCount());
        stmt.setInt(6, group.getGroupId());
        stmt.execute();
    }

    public void deleteGroup(Group group) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM groups WHERE group_id =  ?");
        stmt.setInt(1, group.getGroupId());
        stmt.execute();
    }

}
