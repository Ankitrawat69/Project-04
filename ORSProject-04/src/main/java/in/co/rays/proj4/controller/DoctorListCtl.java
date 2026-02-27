package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.DoctorBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.DoctorModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "DoctorListCtl", urlPatterns = { "/ctl/DoctorListCtl" })
public class DoctorListCtl extends BaseCtl {

    /**
     * Preload Expertise dropdown
     */
    @Override
    protected void preload(HttpServletRequest request) {

        List<String> expertiseList = new ArrayList<>();

        expertiseList.add("Cardiologist");
        expertiseList.add("Dentist");
        expertiseList.add("Neurologist");
        expertiseList.add("Orthopedic");
        expertiseList.add("Physician");

        request.setAttribute("expertiseList", expertiseList);
    }

    /**
     * Populate search bean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        DoctorBean bean = new DoctorBean();

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setExpertise(DataUtility.getString(request.getParameter("expertise")));

        return bean;
    }

    /**
     * GET – default list
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        DoctorBean bean = (DoctorBean) populateBean(request);
        DoctorModel model = new DoctorModel();

        try {

            List<DoctorBean> list = model.search(bean, pageNo, pageSize);
            List<DoctorBean> next = model.search(bean, pageNo + 1, pageSize);

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
            return;
        }
    }

    /**
     * POST – search, next, previous, delete, new, reset
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        DoctorBean bean = (DoctorBean) populateBean(request);
        DoctorModel model = new DoctorModel();

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

                ServletUtility.redirect(ORSView.DOCTOR_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    DoctorBean deleteBean = new DoctorBean();

                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage(
                            "Doctor deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
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
            return;
        }
    }

    @Override
    protected String getView() {
        return ORSView.DOCTOR_LIST_VIEW;
    }
}
