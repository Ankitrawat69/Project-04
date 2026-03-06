package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SecurityStaffBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.SecurityStaffModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * SecurityStaffCtl handles Security Staff operations such as
 * add, update, view and navigation.
 */
@WebServlet(name = "SecurityStaffCtl", urlPatterns = { "/ctl/SecurityStaffCtl" })
public class SecurityStaffCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("staffName"))) {
            request.setAttribute("staffName",
                    PropertyReader.getValue("error.require", "Staff Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("staffName"))) {
            request.setAttribute("staffName", "Invalid Staff Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("shift"))) {
            request.setAttribute("shift",
                    PropertyReader.getValue("error.require", "Shift"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("salary"))) {
            request.setAttribute("salary",
                    PropertyReader.getValue("error.require", "Salary"));
            pass = false;
        }else if (!DataValidator.isInteger(request.getParameter("salary"))) {
            request.setAttribute("salary", "salary must be numeric Type");
            pass = false;
        }
        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        SecurityStaffBean bean = new SecurityStaffBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setStaffName(DataUtility.getString(request.getParameter("staffName")));
        bean.setShift(DataUtility.getString(request.getParameter("shift")));
        bean.setSalary(DataUtility.getString(request.getParameter("salary")));

        populateDTO(bean, request);

        return bean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        SecurityStaffModel model = new SecurityStaffModel();

        if (id > 0) {
            try {
                SecurityStaffBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        SecurityStaffModel model = new SecurityStaffModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            SecurityStaffBean bean = (SecurityStaffBean) populateBean(request);

            try {

                long pk = model.add(bean);

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Security Staff added successfully", request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            SecurityStaffBean bean = (SecurityStaffBean) populateBean(request);

            try {

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Security Staff updated successfully", request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.SECURITY_STAFF_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.SECURITY_STAFF_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.SECURITY_STAFF_VIEW;
    }
}