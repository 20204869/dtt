package com.example.dtt.service.tableau.impl;

import com.example.dtt.constant.tableau.TableauConstant;
import com.example.dtt.domain.entity.tableau.Views;
import com.example.dtt.domain.entity.tableau.Workbook;
import com.example.dtt.exception.ServiceException;
import com.example.dtt.mapper.tableau.TableauMapper;
import com.example.dtt.service.tableau.TableauService;
import com.example.dtt.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author reid
 * @date 2022/4/25 14:27
 * @describe
 */
@Service
public class TableauServiceImpl implements TableauService {

    @Autowired
    private TableauMapper tableauMapper;

    @Override
    public List<Workbook> projectList(Workbook workbook) {
        return tableauMapper.allProjects(workbook);
    }

    @Override
    public List<Workbook> projectAll() {
       return SpringUtils.getAopProxy(this).projectList(new Workbook());
    }

    @Override
    public List<Workbook> workbookByUserId(Long userId,Workbook workbookParams) {
        List<Workbook> userWorkbooks = tableauMapper.workbookPermissionByUserId(userId);
        List<Workbook> workbooks = projectList(workbookParams);
        for (Workbook workbook : workbooks) {
            for (Workbook userWorkbook : userWorkbooks) {
                if (workbook.getWorkbookId().longValue() == userWorkbook.getWorkbookId().longValue()) {
                    workbook.setFlag(true);
                    break;
                }
            }
        }
        return workbooks;
    }

    /**
     * 获取所有的project
     * @param userId
     * @return
     */
    @Override
    public List<Workbook> allProject(Long userId) {
        return tableauMapper.allProject(userId);
    }

    /**
     * 根据用户、项目id获取workbook列表
     * @param userId
     * @param projectId
     * @return
     */
    @Override
    public List<Workbook> workbookByProjectId(Long userId, Long projectId) {
        return tableauMapper.workbookByProjectId(userId, projectId);
    }

    /**
     * 获取视图列表
     * @param workbookId
     * @return
     */
    @Override
    public List<Views> viewsByWorkbookId(Long workbookId) {
        return tableauMapper.viewsByWorkbookId(workbookId);
    }


    /**
     * 获取tableau 视图渲染路径
     * @param workbookId
     * @return
     */
    @Override
    public String tableauUrl(Long workbookId) {
        //tableau server服务器地址
        String wgserver = TableauConstant.TABLEAU_URL;
        //Tableau Server 许可用户的用户名
        String tableAuUser = TableauConstant.TABLEAU_USER;
        String ticket = null;
        String tickUrl = "";

        Views tableauView = tableauMapper.tableauUrl(workbookId);
        if (tableauView == null) {
            new ServiceException("不存在查询的视图，请校验视图是否存在！");
        }

        try {
            ticket = getTrustedTicket(wgserver, tableAuUser);
            if (!ticket.equals("-1")) {
                tickUrl = wgserver + "/trusted/" + ticket + "/views/" + tableauView.getViewUrl() + "?:embed=yes";
            }
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return tickUrl;
    }

    /**
     * 获取票证逻辑
     *
     * @param wgserver
     * @param user
     * @return
     * @throws ServletException
     */
    private String getTrustedTicket(String wgserver, String user) throws ServletException {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        try {
            StringBuilder reqUrl = new StringBuilder();
            reqUrl.append(URLEncoder.encode("username", "UTF-8"));
            reqUrl.append("=");
            reqUrl.append(URLEncoder.encode(user, "UTF-8"));
            URL url = new URL(wgserver + "/trusted");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(reqUrl.toString());
            out.flush();

            StringBuilder rsp = new StringBuilder();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                rsp.append(line);
            }
            return rsp.toString();

        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {

            }
        }
    }

}
