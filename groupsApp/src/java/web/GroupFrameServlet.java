package web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.Curator;
import logic.ManagementSystem;
import logic.Group;

import web.forms.MainFrameForm;

public class GroupFrameServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Установка кодировки для принятия параметров
        req.setCharacterEncoding("UTF-8");
        String grId = req.getParameter("groupId");
        // Если пользователь нажал кнопку ОК – тогда мы обновляем данные (добавляем новую группу)
        if (grId != null && req.getParameter("OK") != null) {
            try {
                // Если ID группы больше 0, то мы редактируем данные
                if (Integer.parseInt(grId) > 0) {
                    updateGroup(req);
                } // Иначе это новая группа
                else {
                    insertGroup(req);
                }
            } catch (SQLException sql_e) {
                throw new IOException(sql_e.getMessage());
            } catch (ParseException p_e) {
                throw new IOException(p_e.getMessage());
            }
        }
        // А теперь опять получаем данные для отображения на главной форме
        String crts = req.getParameter("curatorId");
        String cnts = req.getParameter("studentCount");
        int curatorId = -1;
        if (crts != null) {
            curatorId = Integer.parseInt(crts);
        }
        int count = 0;
        if (cnts != null) {
            count = Integer.parseInt(cnts);
        }
        MainFrameForm form = new MainFrameForm();
        try {
            Collection curators = ManagementSystem.getInstance().getCurators();
            Curator cur = new Curator();
            cur.setCuratorId(curatorId);
            if (curatorId == -1) {
                Iterator i = curators.iterator();
                cur = (Curator) i.next();
            }
            Collection groups = ManagementSystem.getInstance().getGroupsFromCurator(cur, count);
            form.setCuratorId(cur.getCuratorId());
            form.setCount(count);
            form.setCurators(curators);
            form.setGroups(groups);
        } catch (SQLException sql_e) {
            throw new IOException(sql_e.getMessage());
        }
        req.setAttribute("form", form);
        getServletContext().getRequestDispatcher("/MainFrame.jsp").forward(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void updateGroup(HttpServletRequest req) throws SQLException, ParseException {
        Group group = prepareGroup(req);
        ManagementSystem.getInstance().updateGroup(group);
    }

    private void insertGroup(HttpServletRequest req) throws SQLException, ParseException {
        Group group = prepareGroup(req);
        ManagementSystem.getInstance().insertGroup(group);
    }

    private Group prepareGroup(HttpServletRequest req) throws ParseException {
        Group group = new Group();
        group.setGroupId(Integer.parseInt(req.getParameter("groupId")));
        group.setGroupName(req.getParameter("groupName").trim());
        group.setCurator(req.getParameter("curator").trim());
        group.setSpeciality(req.getParameter("speciality").trim());
        group.setCuratorId(Integer.parseInt(req.getParameter("curatorId").trim()));
        group.setStudentCount(Integer.parseInt(req.getParameter("studentCount").trim()));
        return group;
    }
}
