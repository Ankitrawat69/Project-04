package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.GymTrainerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.GymTrainerModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * GymTrainerListCtl handles listing, searching, pagination and bulk actions
 * for GymTrainer entities.
 * 
 * Supported operations:
 * Search, Next, Previous, New, Delete, Reset and Back.
 * 
 * @author Your Name
 * @version 1.0
 */
@WebServlet(name = "GymTrainerListCtl", urlPatterns = { "/ctl/GymTrainerListCtl" })
public class GymTrainerListCtl extends BaseCtl {

    /**
     * Populates GymTrainerBean from request parameters for search.
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        GymTrainerBean bean = new GymTrainerBean();

        bean.setTrainerName(DataUtility.getString(request.getParameter("trainerName")));
        bean.setSpecialization(DataUtility.getString(request.getParameter("specialization")));

        return bean;
    }

    /**
     * Handles GET request (initial load).
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        GymTrainerBean bean = (GymTrainerBean) populateBean(request);
        GymTrainerModel model = new GymTrainerModel();

        try {
            List<GymTrainerBean> list = model.search(bean, pageNo, pageSize);
            List<GymTrainerBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    /**
     * Handles POST request for search, pagination and delete.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? 
                DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        GymTrainerBean bean = (GymTrainerBean) populateBean(request);
        GymTrainerModel model = new GymTrainerModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op)
                    || OP_NEXT.equalsIgnoreCase(op)
                    || OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.GYMTRAINER_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    GymTrainerBean deleteBean = new GymTrainerBean();

                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage(
                            "Trainer deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.GYMTRAINER_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {

                ServletUtility.redirect(
                        ORSView.GYMTRAINER_LIST_CTL, request, response);
                return;
            }

            list = model.search(bean, pageNo, pageSize);
            next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
            return;
        }
    }

    /**
     * Returns GymTrainer list view page.
     */
    @Override
    protected String getView() {
        return ORSView.GYMTRAINER_LIST_VIEW;
    }
}