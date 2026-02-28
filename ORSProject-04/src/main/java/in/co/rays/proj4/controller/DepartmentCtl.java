package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.DepartmentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.DepartmentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * DepartmentCtl handles department related operations such as
 * add, update, view and navigation.
 * 
 * Supported operations: Save, Update, Cancel, Reset.
 * 
 * @author Ankit Rawat
 * @version 1.0
 */
@WebServlet(name = "DepartmentCtl", urlPatterns = { "/ctl/DepartmentCtl" })
public class DepartmentCtl extends BaseCtl {

    /**
     * Validates department form fields.
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("departmentCode"))) {
            request.setAttribute("departmentCode",
                    PropertyReader.getValue("error.require", "Department Code"));
            pass = false;
        } else if (!DataValidator.isInteger(request.getParameter("departmentCode"))) {
            request.setAttribute("departmentCode", "Department Code is invalid");
            pass = false;
        } else if (!DataValidator.isCode(request.getParameter("departmentCode"))) {
            request.setAttribute("departmentCode", "Department Code is invalid");
            pass = false;
        } 

        if (DataValidator.isNull(request.getParameter("departmentName"))) {
            request.setAttribute("departmentName",
                    PropertyReader.getValue("error.require", "Department Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("departmentName"))) {
            request.setAttribute("departmentName", "Invalid Department Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("departmentHead"))) {
            request.setAttribute("departmentHead",
                    PropertyReader.getValue("error.require", "Department Head"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("departmentHead"))) {
            request.setAttribute("departmentHead", "Invalid Department Head");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("location"))) {
            request.setAttribute("location",
                    PropertyReader.getValue("error.require", "Location"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates DepartmentBean from request.
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        DepartmentBean bean = new DepartmentBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setDepartmentCode(DataUtility.getString(request.getParameter("departmentCode")));
        bean.setDepartmentName(DataUtility.getString(request.getParameter("departmentName")));
        bean.setDepartmentHead(DataUtility.getString(request.getParameter("departmentHead")));
        bean.setLocation(DataUtility.getString(request.getParameter("location")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles GET request (Edit/View).
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        DepartmentModel model = new DepartmentModel();

        if (id > 0) {
            try {
                DepartmentBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles POST request (Save/Update/Cancel/Reset).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        DepartmentModel model = new DepartmentModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            DepartmentBean bean = (DepartmentBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Department added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Department Code already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            DepartmentBean bean = (DepartmentBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Department updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Department Code already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.DEPARTMENT_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.DEPARTMENT_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns Department View page.
     */
    @Override
    protected String getView() {
        return ORSView.DEPARTMENT_VIEW;
    }
}