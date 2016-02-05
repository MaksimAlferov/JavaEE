package web;

import java.io.IOException;
import java.sql.SQLException;
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
import web.forms.GroupForm;

public class MainFrameServlet extends HttpServlet
{
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Установка кодировки для принятия параметров
        req.setCharacterEncoding("UTF-8");
        int answer = 0;
        try {
            answer = checkAction(req);
        } catch (SQLException sql_e) {
            throw new IOException(sql_e.getMessage());
        }
        if (answer == 1) {
            // Тут надо сделать вызов другой формы, которая перенаправит сервлет
            // на другую JSP для ввода данных о новом студенте
            try {
                Group group = new Group();
                group.setGroupId(0);
                group.setStudentCount(0);
                Collection curators = ManagementSystem.getInstance().getCurators();
                GroupForm grForm = new GroupForm();
                grForm.initFromGroup(group);
                grForm.setCurators(curators);
                req.setAttribute("group", grForm);
                getServletContext().getRequestDispatcher("/GroupFrame.jsp").forward(req, resp);
                return;
            } catch (SQLException sql_e) {
                throw new IOException(sql_e.getMessage());
            }

        }

        if (answer == 2) {
            // Тут надо сделать вызов другой формы, которая перенаправит сервлет
            // на другую JSP для ввода данных о группе
            try {
                if (req.getParameter("groupId") != null) {
                    int grId = Integer.parseInt(req.getParameter("groupId"));
                    Group group = ManagementSystem.getInstance().getGroupById(grId);
                    Collection curators = ManagementSystem.getInstance().getCurators();
                    GroupForm grForm = new GroupForm();
                    grForm.initFromGroup(group);
                    grForm.setCurators(curators);
                    req.setAttribute("group", grForm);
                    getServletContext().getRequestDispatcher("/GroupFrame.jsp").forward(req, resp);
                    return;
                }
            } catch (SQLException sql_e) {
                throw new IOException(sql_e.getMessage());
            }
        }
        String crts = req.getParameter("curatorId");
        String cnts = req.getParameter("count");

        if (answer == 3) {
            // Здесь мы перемещаем группы к другому куратору
            String newCrts = req.getParameter("newCuratorId");
            String newCnts = req.getParameter("newCount");
            try {
                Curator cur = new Curator();
                cur.setCuratorId(Integer.parseInt(crts));
                Curator nCrts = new Curator();
                nCrts.setCuratorId(Integer.parseInt(newCrts));
                ManagementSystem.getInstance().moveGroupsToCurator(cur, Integer.parseInt(cnts), nCrts, Integer.parseInt(newCnts));
                // Теперь мы будем показывать группу, куда переместили
                crts = newCrts;
                cnts = newCnts;
            } catch (SQLException sql_e) {
                throw new IOException(sql_e.getMessage());
            }
        }
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

// Здесь мы проверям какое действие нам надо сделать – и возвращаем ответ
    private int checkAction(HttpServletRequest req) throws SQLException {
        if (req.getParameter("Add") != null) {
            return 1;
        }
        if (req.getParameter("Edit") != null) {
            return 2;
        }
        if (req.getParameter("MoveCurator") != null) {
            return 3;
        }
        if (req.getParameter("Delete") != null) {
            if (req.getParameter("groupId") != null) {
                Group group = new Group();
                group.setGroupId(Integer.parseInt(req.getParameter("groupId")));
                ManagementSystem.getInstance().deleteGroup(group);
            }
            return 0;
        }
        return 0;
    }

    // Переопределим стандартные методы
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

}
